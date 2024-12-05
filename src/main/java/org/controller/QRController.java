package org.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class QRController {

    @FXML
    private Button enterExit;

    @FXML
    private ImageView imageQR;

    public void setImageQR(Image imageQR) {
        this.imageQR.setImage(imageQR);
    }

    @FXML
    void actionExit(MouseEvent event) {
        Stage stage = (Stage) enterExit.getScene().getWindow();
        stage.close();
    }

}
