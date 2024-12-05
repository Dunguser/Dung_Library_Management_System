package org.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.GlobalState;
import org.model.person.Date;
import org.model.person.*;
import org.model.reader.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.model.operator.LibraryManager.saveReaderToDatabase;

public class SignUpController implements Initializable {
    /**
     * MOI
     */
    @FXML
    private Button BackOnSignUp;

    @FXML
    private Button ExitOnSignUp;

    @FXML
    private Button SubmitOnSignUp;

    @FXML
    private DatePicker chooseBirthDate;

    @FXML
    private ComboBox<?> chooseSex;

    @FXML
    private TextField enterAddress;

    @FXML
    private TextField enterFirstName;

    @FXML
    private TextField enterGmail;

    @FXML
    private TextField enterLastName;

    @FXML
    private TextField enterPhone;

    @FXML
    private Label gmailError;

    @FXML
    private Label phoneError;


    @FXML
    private AnchorPane anchoIconUser;

    @FXML
    private ImageView imageViewOnSignUp;

    @FXML
    private Button insertImageOnSignUp;
    @FXML
    private Label addressError;

    /**
     *
     */

    public static final String pathToCreateAccountView = "/view/CreateAccountView.fxml";
    private Alert alert;
    private Reader readerSignUp;


    /**
     * lưu ảnh người dùng trên database mySQL
     */
    public static final String url1 = "jdbc:mysql://localhost:3307/listimageofreader";
//    public static final String url1 = "jdbc:mysql://192.168.50.103:3307/listimageofreader";
    public static PreparedStatement store, retrieve, update, delete;
    public static final String storeStatement = "INSERT INTO reader_images (readerID,imageData) VALUES(?, ?)";
    public static final String retrieveStatement = "SELECT imageData FROM reader_images WHERE readerID = ?";
    public static final String updateStatement = "UPDATE reader_images SET imageData = ? WHERE readerID = ?";
    public static final String deleteStatement = "DELETE FROM reader_images WHERE readerID = ?";
    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url1, "root", "");
//            connection = DriverManager.getConnection(url1, "user2", "");
            store = connection.prepareStatement(storeStatement);
            retrieve = connection.prepareStatement(retrieveStatement);
            update = connection.prepareStatement(updateStatement);
            delete = connection.prepareStatement(deleteStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private File fileImageReaderSignUp;

    /**
     * lấy ảnh từ sql
     */
    public static Image LoadImage(Reader reader) throws SQLException {
        retrieve.setString(1, reader.getReaderID());
        ResultSet resultSet = retrieve.executeQuery(); // Thực thi truy vấn
        if (resultSet.next()) {
            InputStream input = resultSet
                    .getBinaryStream("imageData"); // Lấy dữ liệu ảnh từ cột "imageData"
            if (input != null) {
                return new Image(input);
            }
        }
        System.out.println("loadImage lấy ảnh từ sql bị null " + reader.getReaderID());
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addGenderReader();
    }

    public void submit() throws IOException, SQLException {
        boolean valid = true;
        // Kiểm tra các trường nhập liệu trống
        if (chooseSex.getValue() == null || chooseBirthDate.getValue() == null
                || enterAddress.getText().isEmpty() || enterFirstName.getText().isEmpty()
                || enterGmail.getText().isEmpty() || enterLastName.getText().isEmpty()
                || enterPhone.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            valid = false;
        }
        // Kiểm tra định dạng email
        if (!Gmail.checkGamilForm(enterGmail.getText())) {
            enterGmail.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                    "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                    "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            gmailError.setTextFill(Color.RED);
            valid = false;
        } else {
            enterGmail.setStyle(""); // Xóa viền đỏ khi không còn lỗi
        }

        if (!PhoneNumber.checkPhoneForm(enterPhone.getText())) {
            if (!enterPhone.getStyleClass().contains("error")) {
                enterPhone.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            }
            phoneError.setTextFill(Color.RED);
            valid = false;
        } else {
            enterPhone.setStyle(""); // Xóa viền đỏ khi không còn lỗi
        }

        if (!Address.checkAddressFormat(enterAddress.getText())) {
            if (!enterAddress.getStyleClass().contains("error")) {
                enterAddress.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            }
            addressError.setTextFill(Color.RED);
            valid = false;
        } else {
            enterAddress.setStyle(""); // Xóa viền đỏ khi không còn lỗi
        }

        if (valid) {        // Chỉ chuyển cảnh nếu tất cả các kiểm tra đều hợp lệ
            LocalDate localDate = chooseBirthDate.getValue();
            Date date = new Date(localDate.getDayOfMonth(),
                    localDate.getMonthValue(),
                    localDate.getYear());
            String sex = (String) chooseSex.getValue();
            readerSignUp = new Reader();
            readerSignUp.setPersonAddress(new Address(enterAddress.getText()));
            readerSignUp.setPersonName(new
                    PersonName(enterFirstName.getText(), enterLastName.getText()));
            readerSignUp.setPersonDatebirth(date);
            readerSignUp.setPersonGmail(new Gmail(enterGmail.getText()));
            readerSignUp.setPersonPhone(new PhoneNumber(enterPhone.getText()));
            readerSignUp.setPersonSex(sex.equals("Male"));
            readerSignUp.setReaderID(Reader.getCurrentTimeAsString());
            readerSignUp.setReaderID("ID" + Reader.getCurrentTimeAsString());
            readerSignUp.setPersonAge(Date.getCurrentDate().getYear()
                    - readerSignUp.getPersonDatebirth().getYear());
            GlobalState.getInstance().setReaderSignUp(readerSignUp);

            GlobalState.getInstance().getReaderSignUp().printInformationOfPerson();

            saveReaderToDatabase(GlobalState.getInstance().getReaderSignUp().getReaderID(),
                    GlobalState.getInstance().getReaderSignUp());

            /* Lưu ảnh vào cơ sở dữ liệu*/
            FileInputStream fileInputStream;
            if (fileImageReaderSignUp != null) {
                fileInputStream = new FileInputStream(fileImageReaderSignUp);
            } else {
                File file = new File("D:\\Library-Management\\src\\main\\resources\\image\\USER.png");
                fileInputStream = new FileInputStream(file);
            }
            store.setString(1, readerSignUp.getReaderID());
            store.setBinaryStream(2, fileInputStream, fileInputStream.available());
            store.executeUpdate();
            store.close();

            switchToNewScene(pathToCreateAccountView, SubmitOnSignUp);
        }
    }

    /**
     * chèn ảnh người dùng
     */
    public void setInsertImageOnSignUp() {
        anchoIconUser.setVisible(false);
        Stage stage = (Stage) insertImageOnSignUp.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("choose image");
        /*lm cho chi chon dc file anh kiểu jpg, png*/
        FileChooser.ExtensionFilter imageFilter = new
                FileChooser.ExtensionFilter("image file", "*.jpg", "*.png");
        fc.getExtensionFilters().addAll(imageFilter);
        fileImageReaderSignUp = fc.showOpenDialog(stage);

        if (fileImageReaderSignUp != null) {
            Image image = new Image(fileImageReaderSignUp.toURI().toString(),
                    220, 390, false, true);
            imageViewOnSignUp.setImage(image);
        }
    }

    public void close() {
        System.exit(0);
    }

    public void back() throws IOException {
        switchToNewScene(LoginController.pathToLoginView, BackOnSignUp);
    }

    public void addGenderReader() {
        List<String> listGender = new ArrayList<>();
        chooseSex.setStyle("-fx-font-family: 'Times New Roman';" +
                " -fx-font-size: 24px; -fx-font-weight: bold;");
        listGender.add("Male");
        listGender.add("Female");
        ObservableList observableList = FXCollections.observableArrayList(listGender);
        chooseSex.setItems(observableList);

    }

    /**
     * Chuyển cảnh tới cảnh mới
     * link dẫn đến cảnh mới
     * Button hiện tại để lây đc cảnh hiện tại
     */

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

