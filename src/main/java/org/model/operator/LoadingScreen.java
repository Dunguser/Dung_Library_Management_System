package org.model.operator;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadingScreen {
    private Stage loadingStage;

    // Constructor để tạo cửa sổ loading
    public LoadingScreen() {
        Platform.runLater(() -> {
            loadingStage = new Stage();
            loadingStage.initModality(Modality.APPLICATION_MODAL); // Khóa giao diện chính
            loadingStage.setTitle("Sending Email...");

            // Vòng tròn loading
            ProgressIndicator progressIndicator = new ProgressIndicator();
            StackPane root = new StackPane(progressIndicator);
            Scene scene = new Scene(root, 150, 150);

            // Thiết lập giao diện
            loadingStage.setScene(scene);
            loadingStage.setResizable(false);
        });
    }

    // Hiển thị cửa sổ loading
    public void show() {
        Platform.runLater(() -> loadingStage.show());
    }

    // Đóng cửa sổ loading
    public void close() {
        Platform.runLater(() -> {
            if (loadingStage != null) {
                loadingStage.close();
            }
        });
    }
}
