package ir.ac.kntu.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainController implements Initializable {
    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField assignmentsName;

    @FXML
    private JFXTextField assignmentsMark;

    @FXML
    private JFXTextField assignmentsField;

    @FXML
    private JFXTextField testsField;

    @FXML
    private JFXTextField resultsField;

    @FXML
    private JFXButton assignmentsBrowseButton;

    @FXML
    private JFXButton testsBrowseButton;

    @FXML
    private JFXButton resultBrowseButton;

    @FXML
    private JFXButton startButton;

    @FXML
    void browseAssignments(MouseEvent event) {
        handleBrowse(assignmentsField, root);
    }

    private static void handleBrowse(TextField textField, Parent root) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Optional<File> selectedDirectory = Optional.ofNullable(directoryChooser.showDialog(root.getScene().getWindow()));
        selectedDirectory.ifPresent(x -> textField.setText(x.getAbsolutePath()));
    }

    @FXML
    void browseResult(MouseEvent event) {
        handleBrowse(resultsField, root);
    }

    @FXML
    void browseTests(MouseEvent event) {
        handleBrowse(testsField, root);
    }

    @FXML
    void startGrading(MouseEvent event) {
        if (!isEligible()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Information Alert");
            String s = "Make sure the given directories exist!";
            alert.setContentText(s);
            alert.show();
            return;
        }

        try {
            cleanTests();
            copyTests();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // FIXME: 2/14/2020 

    }

    private void copyTests() throws IOException {
        File file = new File(assignmentsField.getText());
        File[] directories = file.listFiles(File::isDirectory);
        for (File f : directories) {
            copyDirectory(Paths.get(testsField.getText()), Paths.get(f.getAbsolutePath() + "/src/test"));
        }
    }

    private void copyDirectory(Path srcPath, Path destinationPath) throws IOException {
        FileUtils.copyDirectory(srcPath.toFile(), destinationPath.toFile());
    }

    private void cleanTests() throws IOException {
        File file = new File(assignmentsField.getText());
        List<File> directories = Arrays.asList(file.listFiles(File::isDirectory));
        for (File f : directories) {
            deleteDirectory(Paths.get(f.getAbsolutePath() + "/src/test"));
        }

    }

    private static void deleteDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private boolean isEligible() {
        List<File> list = new ArrayList<>();
        list.add(new File(assignmentsField.getText()));
        list.add(new File(resultsField.getText()));
        list.add(new File(testsField.getText()));
        AtomicBoolean eligibility = new AtomicBoolean(true);
        list.forEach(x -> {
            if (!(x.exists() && x.isDirectory())) {
                eligibility.set(false);
            }
        });
        return eligibility.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
