package ir.ac.kntu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

public class Main extends Application {
    private static AnchorPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        URL url = new File("src/main/resources/view/main.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(root, 500, 375, false,
                SceneAntialiasing.BALANCED);
        stage.setScene(scene);
        stage.getIcons().add(new Image("view/icons/icon512.png"));
        stage.setTitle("KNTU Grader");
        stage.show();
    }
}
