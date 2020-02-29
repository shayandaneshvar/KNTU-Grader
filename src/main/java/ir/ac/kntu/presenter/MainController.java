package ir.ac.kntu.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ir.ac.kntu.model.TestResult;
import ir.ac.kntu.model.TestResultDTO;
import ir.ac.kntu.services.MavenTestInvokerProvider;
import ir.ac.kntu.services.TestResult2TestResultDTO;
import ir.ac.kntu.util.CSVWriteUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.apache.commons.io.FilenameUtils.getName;

public class MainController implements Initializable {
    private ExecutorService executor;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXCheckBox injectionOnlyButton;

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
        event.consume();
        handleBrowse(assignmentsField, root);
    }

    private static void handleBrowse(TextField textField, Parent root) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Optional<File> selectedDirectory = Optional.ofNullable(directoryChooser.showDialog(root.getScene().getWindow()));
        selectedDirectory.ifPresent(x -> textField.setText(x.getAbsolutePath()));
    }

    @FXML
    void browseResult(MouseEvent event) {
        event.consume();
        handleBrowse(resultsField, root);
    }

    @FXML
    void browseTests(MouseEvent event) {
        event.consume();
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
        JFXProgressBar progressBar = new JFXProgressBar(0.01);
        progressBar.getStylesheets().add("view/styles/styles.css");
        progressBar.setPrefWidth(240);
        progressBar.setPrefHeight(40);
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.initOwner(root.getScene().getWindow());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(progressBar);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        Thread runner = new Thread(() -> handleGrading(progressBar, stage));
        runner.start();
        event.consume();
    }

    private void handleGrading(JFXProgressBar progressBar, Stage stage) {
        try {
            progressBar.setProgress(0.1);
            cleanTests();
            progressBar.setProgress(0.2);
            copyTests();
            progressBar.setProgress(0.3);
            if (injectionOnlyButton.selectedProperty().get()) {
                System.err.println("Returning");
                progressBar.setProgress(0.98);
                Platform.runLater(stage::close);
                return;
            }
            System.err.println("Implementing Callable");
            List<Callable<TestResult>> results = new ArrayList<>();
            progressBar.setProgress(0.35);
            int score;
            if (assignmentsMark.getText().isEmpty()) {
                score = 100;
            } else {
                try {
                    score = Integer.parseInt(assignmentsMark.getText());
                } catch (Exception e) {
                    score = 100;
                }
            }
            progressBar.setProgress(0.38);
            for (File file : listFiles(assignmentsField.getText())) {
                var result = MavenTestInvokerProvider.prepareInstance(
                        file.getAbsolutePath(), getName(file.getAbsolutePath()),
                        score)
                        .provide("clean", "test");
                results.add(result);
            }
            progressBar.setProgress(0.45);
            System.err.println("Invoking Futures");
            List<Future<TestResult>> finalResults = executor.invokeAll(results);
            List<TestResultDTO> dtos = finalResults.stream().map(x -> {
                if (x.isDone()) {
                    try {
                        return x.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }).map(z -> TestResult2TestResultDTO.getInstance().convert(z))
                    .collect(Collectors.toList());
            progressBar.setProgress(0.9);
            CSVWriteUtil.writeAll(getResultAddress(), dtos);
        } catch (InterruptedException | CsvRequiredFieldEmptyException |
                IOException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
        progressBar.setProgress(1);
        Platform.runLater(stage::close);
    }

    private Path getResultAddress() {
        String fileName = assignmentsName.getText().trim().isEmpty() ? "/result.csv"
                : "/" + assignmentsName.getText() + ".csv";
        if (resultsField.getText().isEmpty()) {
            return Paths.get(assignmentsField.getText() + fileName);
        }
        return Paths.get(resultsField.getText() + fileName);
    }

    private void copyTests() throws IOException {
        for (File f : listFiles(assignmentsField.getText())) {
            copyDirectory(Paths.get(testsField.getText()), Paths.get(f.getAbsolutePath() + "/src/test"));
        }
    }

    private File[] listFiles(String address) {
        File file = new File(address);
        return file.listFiles(File::isDirectory);
    }

    private void copyDirectory(Path srcPath, Path destinationPath) throws IOException {
        FileUtils.copyDirectory(srcPath.toFile(), destinationPath.toFile());
    }

    private void cleanTests() throws IOException {
        for (File f : listFiles(assignmentsField.getText())) {
            Path path = Paths.get(f.getAbsolutePath() + "/src/test");
            if (path.toFile().exists()) {
                deleteDirectory(path);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private boolean isEligible() {
        List<File> list = new ArrayList<>();
        list.add(new File(assignmentsField.getText()));
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
        executor = Executors.newCachedThreadPool();
    }
}
