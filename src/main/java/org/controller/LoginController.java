package org.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.GlobalState;
import org.model.account.Account;
import org.model.account.ManageAccount;
import org.model.operator.LibraryManager;
import org.model.reader.Reader;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.model.LoaderSystem.*;
import static org.model.operator.GmailSender.sendEmail;


public class LoginController implements Initializable {

    public static final String pathToDashboard = "/view/dashboard.fxml";
    public static final String pathToReaderView = "/view/ReaderView.fxml";
    public static final String pathToLoginView = "/view/LoginView.fxml";
    public static final String pathToChooseRoleView = "/view/chooseRole.fxml";
    public static final String pathToSignUpView = "/view/SignUpView.fxml";
    public static final String pathToLibraryView = "/view/LibrarianView.fxml";
    public static final String pathToForgetPassView = "/view/forgetpassword.fxml";

    private Alert alert;


    /**
     * login
     */
    @FXML
    private Button SignUpButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginClose;

    @FXML
    private Label loginForgotPassWord;

    @FXML
    private PasswordField loginPassWord;

    @FXML
    private CheckBox loginShowPassword;

    @FXML
    private TextField loginUserName;

    @FXML
    private TextField showPass;

    @FXML
    private Button backToChooseRole;

    @FXML
    private Button buttonForget;


    /**
     * choose role
     */

    @FXML
    private RadioButton chooseLibrary;

    @FXML
    private RadioButton chooseManager;

    @FXML
    private RadioButton chooseReader;

    @FXML
    private ToggleGroup chooseRole;


    @FXML
    private Button nextButton;
    @FXML
    private Button chooseRoleExit;
    private static HashMap<Object, Object> gmails;
    private String random;
    private String idOfForgottenPerson;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * dang nhap cho operator
     */


    @FXML
    private void methodManager() throws IOException {
        String operatorUsername = loginUserName.getText();
        String operatorPass = loginPassWord.getText();
        if (checkBlank()) {
            doCheckBlank();
        } else {
            if (libraryManager.getAccountOperator()
                    .checkCredentials(operatorUsername, operatorPass)) {
                /* dang nhap thanh cong.*/
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login !!!");
                alert.showAndWait();
                loginButton.getScene().getWindow().hide();       // hide login form
                switchToNewScene(pathToDashboard, loginButton);        // xử lí chuyển
            } else {
                /*  sai thong tin dang nhap */
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username or Password");
                alert.showAndWait();
            }
        }
    }

    /**
     * dang nhap cho library
     */
    private void methodLibrarian() throws IOException {
        String libraryUsername, libraryPass;
        if (checkBlank()) {
            doCheckBlank();
        } else {
            libraryUsername = loginUserName.getText();
            libraryPass = loginPassWord.getText();

            if (libraryUsername.equals(librarian1.getAccountLibrarian().getUserName())
                    && libraryPass.equals(librarian1.getAccountLibrarian().getPassWord())) {
                GlobalState.setLibraryNum(1);
            } else if (libraryUsername.equals(librarian2.getAccountLibrarian().getUserName())
                    && libraryPass.equals(librarian2.getAccountLibrarian().getPassWord())) {
                GlobalState.setLibraryNum(2);
            }
            if (GlobalState.getLibraryNum() != 0) {
                /*dang nhap thanh cong.*/
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login !!!");
                alert.showAndWait();

                switchToNewScene(pathToLibraryView, loginButton);

            } else {
                /*sai thong tin dang nhap */
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong Username or Password");
                alert.showAndWait();
            }
        }
    }

    /**
     * dang nhap cho reader
     */
    @FXML
    private void methodReader() throws IOException {
        String readerUsername, readerPass;
        if (checkBlank()) {
            doCheckBlank();
        } else {
            readerUsername = loginUserName.getText();
            readerPass = loginPassWord.getText();
            ManageAccount mn = new ManageAccount();
            Map<String, Account> listAccountReader = ManageAccount.getListAccountOfUsername();

            if (ManageAccount.getListAccountOfUsername().containsKey(readerUsername)) {

                Account account = listAccountReader.get(readerUsername);
                GlobalState.getInstance().setReaderLoggedIn(LibraryManager
                        .getListAccountOfReader().get(readerUsername));

                if (readerPass.equals(account.getPassWord())) {/*dang nhap thanh cong.*/
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login !!!");
                    alert.showAndWait();
                    switchToNewScene(pathToReaderView, loginButton);
                } else { /*sai thong tin dang nhap */
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password");
                    alert.showAndWait();
                }
            } else { /*sai thong tin dang nhap */
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Account does not exist");
                alert.showAndWait();
            }
        }
    }

    /**
     * dong chuong trinh.
     */
    @FXML
    public void loginClose() {
        System.exit(0);
    }

    public void setChooseRoleExit() {
        System.exit(0);
    }

    /**
     * in canh bao khi 2 o dien bi trong
     */
    public void doCheckBlank() {
        if (loginUserName.getText().isEmpty() || loginPassWord.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
    }

    /**
     * kiem tra 2 o dien trong
     */
    public boolean checkBlank() {
        return loginUserName.getText().isEmpty()
                || loginPassWord.getText().isEmpty();
    }

    /**
     * chon nhap vai tro dang nhap
     */
    @FXML
    public void checkChooseRole() {
        if (!chooseLibrary.isSelected()
                && !chooseManager.isSelected()
                && !chooseReader.isSelected()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your role");
            alert.showAndWait();
        }
        if (chooseReader.isSelected()) {
            GlobalState.getInstance().setSaveChoose(GlobalState.CHOOSEREADER);
        }
        if (chooseLibrary.isSelected()) {
            GlobalState.getInstance().setSaveChoose(GlobalState.CHOOSELIBRARY);
        }
        if (chooseManager.isSelected()) {
            GlobalState.getInstance().setSaveChoose(GlobalState.CHOOSEMANGER);
        }

        nextButton.setOnAction(event -> {
            try {
                switchToNewScene(pathToLoginView, nextButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * xu li dang nhap
     */
    @FXML
    public void loginForThree() throws IOException {
        int saveChoose = GlobalState.getInstance().getSaveChoose();
        switch (saveChoose) {
            case GlobalState.CHOOSEREADER:
                methodReader();
                break;
            case GlobalState.CHOOSELIBRARY:
                methodLibrarian();
                break;
            case GlobalState.CHOOSEMANGER:
                methodManager();
                break;
            default:
                System.out.println("No role selected");
                break;
        }

    }

    /**
     * hien thi password
     */
    @FXML
    public void showPass() {
        Bindings.bindBidirectional(showPass.textProperty(),
                loginPassWord.textProperty());
        if (loginShowPassword.isSelected()) {
            showPass.setVisible(true);
            loginPassWord.setVisible(false);
        } else {
            loginPassWord.setVisible(true);
            showPass.setVisible(false);
        }
    }


    /**
     * Chuyển cảnh tới cảnh mới
     * link dẫn đến cảnh mới
     * Button hiện tại để lây đc cảnh hiện tại
     */
    private double x = 0;
    private double y = 0;

    public void switchToNewScene(String pathToNewScene, Button button) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(pathToNewScene)));

        Stage oldStage = (Stage) button.getScene().getWindow();

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
        oldStage.hide();
    }

    /**
     * chon lai vai tro dang nhap
     */
    public void setBackToChooseRole() throws IOException {
        switchToNewScene(pathToChooseRoleView, backToChooseRole);
    }

    /**
     * xu li sign up
     */
    public void signUpForReader() throws IOException {
        switchToNewScene(pathToSignUpView, SignUpButton);
    }

    /**
     * xử lí quên mk
     */

    @FXML
    private Button backToLogin;

    @FXML
    private Button confir;

    @FXML
    private TextField confirmationCode;

    @FXML
    private Label errorFormatGmail;

    @FXML
    private AnchorPane failTocheck;

    @FXML
    private TextField gmailOnForgetPass;

    @FXML
    private TextField passwordIfSuceed;

    @FXML
    private Button retrievePassButton;

    @FXML
    private AnchorPane succeedAncho;
    @FXML
    private AnchorPane notification;


    public void goToForgetPass() throws IOException {
        switchToNewScene(pathToForgetPassView, buttonForget);
        loadGmails();
        GlobalState.setListGmails(gmails);
    }

    public static void loadGmails(){
        gmails = new HashMap<>();
        gmails.put("ADMIN", libraryManager.getPersonGmail().getGmail());
        gmails.put(librarian1.getIdLibrarian(), librarian1.getPersonGmail().getGmail());
        gmails.put(librarian2.getIdLibrarian(), librarian2.getPersonGmail().getGmail());
        for (String id : LibraryManager.getListReader().keySet()) {
            gmails.put(id, LibraryManager.getListReader().get(id).getPersonGmail().getGmail());
        }
    }

    public void forgotPasswordHandling() {
        gmails = GlobalState.getListGmails();
        if (gmails.isEmpty()) {
            System.out.println("null roi");
            return;
        }
        String gmail = gmailOnForgetPass.getText();
        if (!gmails.containsValue(gmail)) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email is not exist");
            alert.showAndWait();
        } else {
            idOfForgottenPerson = getKeyFromValue(gmails, gmail);
            random = "";
            Random rd = new Random();
            for (int i = 0; i < 6; ++i) {
                int c = rd.nextInt(0, 10);
                random += String.valueOf(c);
            }
            String content = "Thank you for using our Library Management System."
                    + "Please find below the confirmation code for your request:\n"
                    + "Confirmation Code: " + random
                    + "\nPlease enter this code in the provided field to complete your process.\n"
                    + "If you did not request this code, please disregard this message.";
            sendEmail(gmail, content,1);
        }
    }

    public void checkConfirmationCode() {
        if (confirmationCode.getText().equals(random)) {
            String mkc = "";
            if (idOfForgottenPerson.equals("ADMIN")) {
                mkc = libraryManager.getAccountOperator().getPassWord();
            } else if (idOfForgottenPerson.equals(librarian1.getIdLibrarian())) {
                mkc = librarian1.getAccountLibrarian().getPassWord();
            } else if (idOfForgottenPerson.equals(librarian2.getIdLibrarian())) {
                mkc = librarian2.getAccountLibrarian().getPassWord();
            } else {
                if (LibraryManager.getListReader().containsKey(idOfForgottenPerson)) {
                    Reader r = LibraryManager.getListReader().get(idOfForgottenPerson);
                    mkc = r.getReaderAccount().getPassWord();
                }
            }
            notification.setVisible(true);
            succeedAncho.setVisible(true);
            failTocheck.setVisible(false);
            passwordIfSuceed.setText(mkc);

        } else {
            notification.setVisible(true);
            failTocheck.setVisible(true);
            succeedAncho.setVisible(false);

        }
    }

    public void setBackToLogin() throws IOException {
        switchToNewScene(pathToLoginView, backToLogin);
    }

    public String getKeyFromValue(HashMap<Object, Object> a, String value) {
        for (Object i : a.keySet()) {
            if (a.get(i).equals(value)) {
                return (String) i;
            }
        }
        return null;
    }
}
