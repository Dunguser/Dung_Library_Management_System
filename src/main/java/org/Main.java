package org;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.LoaderSystem;

import java.util.Objects;

import static org.controller.LoginController.pathToChooseRoleView;

public class Main extends Application {

    private double x = 0;
    private double y = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoaderSystem loaderSystem = new LoaderSystem();
        FXMLLoader loader = new FXMLLoader(getClass().
                getResource(pathToChooseRoleView));
        Parent root = loader.load();
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getScreenX() - stage.getX();
            y = event.getScreenY() - stage.getY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8);
        });
        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass()
                        .getResource("/styles/loginAnchoPane.css"))
                        .toExternalForm());

        stage.setResizable(false);
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }
}
