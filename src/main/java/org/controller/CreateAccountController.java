package org.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.GlobalState;
import org.model.account.Account;
import org.model.account.ManageAccount;
import org.model.operator.LibraryManager;
import org.model.reader.Reader;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.model.account.Account.checkPasswordStrong;
import static org.model.operator.GmailSender.sendEmail;

public class CreateAccountController implements Initializable {

    @FXML
    private Button enterCreate;

    @FXML
    private TextField enterPassword;

    @FXML
    private PasswordField enterPasswordHidden;

    @FXML
    private CheckBox enterShowPassword;

    @FXML
    private TextField enterUserName;

    @FXML
    private TextField reEnterPassword;

    @FXML
    private PasswordField reEnterPasswordHidden;

    @FXML
    private Button backOnCreateAccount;

    @FXML
    private Button ExitOnSignUp;

    @FXML
    private Label passWordNotStrong;


    @FXML
    private Label usernameExist;


    private ManageAccount ma = new ManageAccount();

    @FXML
    void actionCreate(ActionEvent event) throws IOException {
        if (event.getSource() == enterCreate) {
            boolean check = true;

            String username = enterUserName.getText().trim();
            System.out.println(ManageAccount.getListAccountOfUsername().keySet());

            if (ManageAccount.getListAccountOfUsername().containsKey(username)) {
                System.out.println("Tài khoản đã tồn tại");
                System.out.println(ManageAccount.getListAccountOfUsername().keySet());
                enterUserName.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
                usernameExist.setTextFill(Color.RED);
                check = false;
            } else {
                enterUserName.setStyle(""); // Reset style nếu không có lỗi
            }

            String mk = enterPassword.getText();
            if (!checkPasswordStrong(mk)) {
                enterPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
                passWordNotStrong.setTextFill(Color.RED);
                check = false;
            } else {
                enterPassword.setStyle("");
            }

            if (check) {
                Reader reader = GlobalState.getInstance().getReaderSignUp();
                Account account = new Account(enterUserName.getText(),
                        enterPassword.getText());
                reader.setReaderAccount(account);

                reader.printInformationOfPerson();

                /* lưu vào txt */
                LibraryManager.getListReader()
                        .put(reader.getReaderID(), reader);     // lưu người đọc theo ID
                LibraryManager.getListAccountOfReader()
                        .put(enterUserName.getText(), reader);   // lưu người đọc theo tên đăng nhâp
                LibraryManager.saveReaderToDatabase
                        (enterUserName.getText(), reader);       // lưu người đọc theo tên đăng nhập

                ManageAccount.getListAccountOfUsername()
                        .put(enterUserName.getText(), account);  // lưu tài khoản theo tên dăng nhập
                ManageAccount.saveAccountToDatabase(reader, account); // lưu tài khoản theo ID reader

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Sign Up !!! (❁´◡`❁)");
                alert.showAndWait().ifPresent(response -> {
                    // Sau khi người dùng nhấn OK, thực hiện gửi email
                    String content = "Dear " + reader.getPersonName().fullName() +
                            "\nThank you for joining my Library Management System!"
                            +" We're thrilled to have you as part of our growing community.\n" +
                            "\nTo enjoy the best experience, we encourage you to log in and explore the features"
                            +" we've designed to make reading and managing your library easier and more enjoyable.\n" +
                            "\nFeel free to reach out to our support team if you have any questions or need assistance.\n" +
                            "\nHappy reading!";

                    sendEmail(reader.getPersonGmail().getGmail(), content, 2);
                });
                /* chuyển về LOGIN*/
                switchToNewScene(LoginController.pathToChooseRoleView, enterCreate);
            }
        } else if (enterShowPassword.isSelected()) {
            enterPassword.setText(enterPasswordHidden.getText());
            enterPassword.setVisible(true);
            enterPasswordHidden.setVisible(false);
            reEnterPassword.setText(reEnterPasswordHidden.getText());
            reEnterPassword.setVisible(true);
            reEnterPasswordHidden.setVisible(false);

        } else if (!enterShowPassword.isSelected()) {
            enterPasswordHidden.setText(enterPassword.getText());
            enterPasswordHidden.setVisible(true);
            enterPassword.setVisible(false);
            reEnterPasswordHidden.setText(reEnterPassword.getText());
            reEnterPasswordHidden.setVisible(true);
            reEnterPassword.setVisible(false);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterPassword.setVisible(false);
        reEnterPassword.setVisible(false);
    }

    public void setBackOnCreateAccount() throws IOException {
        switchToNewScene(LoginController.pathToSignUpView, backOnCreateAccount);
    }

    public void close() {
        System.exit(0);
    }

    private double x = 0;
    private double y = 0;

    private void switchToNewScene(String pathToNewScene, Button button)
            throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                .getResource(pathToNewScene)));

        Stage oldStage = (Stage) button.getScene().getWindow();
        oldStage.hide();
        double oldStageX = oldStage.getX();
        double oldStageY = oldStage.getY();

        Stage newStage = new Stage();
        newStage.setX(oldStageX);
        newStage.setY(oldStageY);
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getScreenX() - newStage.getX();
            y = event.getScreenY() - newStage.getY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            newStage.setX(event.getScreenX() - x);
            newStage.setY(event.getScreenY() - y);

            newStage.setOpacity(0.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            newStage.setOpacity(1);
        });

        newStage.initStyle(StageStyle.TRANSPARENT);
        newStage.setScene(scene);
        newStage.show();
    }
}

