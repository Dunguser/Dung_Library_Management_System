package org.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.model.GlobalState;
import org.model.account.Account;
import org.model.account.ManageAccount;
import org.model.book.Book;
import org.model.book.BookLoan;
import org.model.book.ManageBook;
import org.model.chatbot.Chatbot;
import org.model.librarian.Librarian;
import org.model.librarian.LoanManagement;
import org.model.person.Date;
import org.model.person.*;
import org.model.reader.BookLoanAndReturnSlip;
import org.model.reader.Reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.controller.LoginController.pathToChooseRoleView;
import static org.controller.SignUpController.*;
import static org.model.account.Account.checkPasswordStrong;


public class ReaderController implements Initializable {
    private Reader reader;
    private Alert alert;
    private Book bookBorrow;
    private BookLoan bookToReturn;
    private Stack<AnchorPane> anchorPaneStack;
    private File fileChangeAvatar;
    private Chatbot chatbot;

    public void setReader(Reader reader) throws SQLException {
        this.reader = reader;
        welcomUser.setText("Welcome ! \n" + reader.getPersonName().fullName());
        enterNewName.setPromptText(reader.getPersonName().fullName());
        enterNewAddress.setPromptText(reader.getPersonAddress().getFullAddress());
        enterNewBirthday.setPromptText(reader.getPersonDatebirth().stringDate());
        enterNewGmail.setPromptText(reader.getPersonGmail().getGmail());
        enterNewPhone.setPromptText(reader.getPersonPhone().getPhoneNum());
        setMyProfile();
    }

    private List<Book> bookList
            = new ArrayList<>(ManageBook.getBooks().values()); // danh sách sách trong kho
    private List<String> nameList
            = new ArrayList<>(ManageBook.getBooks().keySet()); // danh sashc tên sách
    private ObservableList<String> resultSearch;    // danh sách kết quả tìm kiếm
    private List<AnchorPane> paneList;              // danh sách giao diện
    private ObservableList<BookLoan> listBookLoan;  // list sách đã mượn


    @FXML
    private ToggleGroup setNewSex;
    @FXML
    private VBox chatBox;
    @FXML
    private VBox displayBook;
    @FXML
    private VBox displayResult;

    @FXML
    private ImageView imageBook;

    @FXML
    private Button enterBorrowedBook;

    @FXML
    private Button enterComment;

    @FXML
    private Button enterHome;

    @FXML
    private TextField enterMessage;
    @FXML
    private TextField enterInput;
    @FXML
    private MenuButton listAnnouncement;

    @FXML
    private MenuItem enterLogout;

    @FXML
    private MenuItem enterProfile;

    @FXML
    private Button enterSearch;
    @FXML
    private Button enterSendMessage;
    @FXML
    private Button enterSetting;

    @FXML
    private Button enterReturnBook;
    @FXML
    private Button enterShowQR;
    @FXML
    private Label welcomUser;
    @FXML
    private Label informationApp;


    @FXML
    private Button enterModifyInfo;
    @FXML
    private Button enterBorrow;
    @FXML
    private Button enterCreateBorrow;
    @FXML
    private Button enterReDo;
    @FXML
    private Button enterSave;
    @FXML
    private Button enterChange;
    @FXML
    private Button enterDelAcc;
    @FXML
    private Button enterExit;
    @FXML
    private Button enterExitBorrow;
    @FXML
    private Button enterReturnAll;
    @FXML
    private Button enterChangePassword;
    @FXML
    private TextField enterQuantiyBorrow;


    @FXML
    private Label textAuthor;
    @FXML
    private Label textDescribeBook;
    @FXML
    private Label textNameID;
    @FXML
    private Label textQuantity;
    @FXML
    private Label textTotalBorrow;
    @FXML
    private Label textCoupCode;

    @FXML
    private AnchorPane sceneHome;
    @FXML
    private AnchorPane sceneDescribeBook;
    @FXML
    private AnchorPane sceneResultSearch;
    @FXML
    private AnchorPane sceneProfile;
    @FXML
    private AnchorPane sceneModifyInfo;
    @FXML
    private AnchorPane sceneChangePassword;
    @FXML
    private AnchorPane sceneSetting;
    @FXML
    private AnchorPane sceneBorrowBook;
    @FXML
    private AnchorPane sceneBorrowedBook;
    @FXML
    private AnchorPane sceneChat;
    @FXML
    private ListView<String> enterSuggest;
    @FXML
    private TableView<BookLoan> tableBorrowedBook;
    @FXML
    private TableColumn<BookLoan, String> colAmount;

    @FXML
    private TableColumn<BookLoan, Date> colBorrowDate;

    @FXML
    private TableColumn<BookLoan, String> colCpCode;

    @FXML
    private TableColumn<BookLoan, String> colID;

    @FXML
    private TableColumn<BookLoan, Date> colReturnDate;

    @FXML
    private TableColumn<BookLoan, String> colTitle;

    @FXML
    private Text profileAddress;

    @FXML
    private Text profileDate;

    @FXML
    private Text profileGmail;

    @FXML
    private Text profileID;

    @FXML
    private Text profileName;

    @FXML
    private Text profilePhone;

    @FXML
    private Text profileSex;
    @FXML
    private TextField enterNewAddress;

    @FXML
    private TextField enterNewBirthday;

    @FXML
    private TextField enterNewGmail;

    @FXML
    private TextField enterNewName;

    @FXML
    private TextField enterNewPhone;
    @FXML
    private PasswordField enterNewPassword;
    @FXML
    private PasswordField enterOldPassword;
    @FXML
    private PasswordField reEnterNewPassword;
    @FXML
    private DatePicker enterReturnDate;

    @FXML
    private ScrollPane scrollChat;
    @FXML
    private ScrollPane scrollAllBook;
    @FXML
    private ScrollPane scrollResultSearch;
    @FXML
    private ImageView imageOnMyProFile;

    @FXML
    private ImageView imageOnWelcomReader;

    // các hành động của người dùng
    @FXML
    void actionPersonal(ActionEvent event) throws IOException {
        if (event.getSource() == enterProfile) {
            setVisiblePane(sceneProfile);
        }
    }

    /**
     * xu li dang xuat
     */
    private double x1 = 0;//tọa độ x của stage
    private double y1 = 0; // tọa độ y của stage

    public void Logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout???");
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get().equals(ButtonType.OK)) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                        .getResource(pathToChooseRoleView)));
                Stage oldStage = (Stage) enterSearch.getScene().getWindow();
                oldStage.hide();
                double oldStageX = oldStage.getX();
                double oldStageY = oldStage.getY();
                Stage newStage = new Stage();
                newStage.setX(oldStageX);
                newStage.setY(oldStageY);
                Scene scene = new Scene(root);
                root.setOnMousePressed((MouseEvent event) -> {
                    x1 = event.getScreenX() - newStage.getX();
                    y1 = event.getScreenY() - newStage.getY();
                });
                root.setOnMouseDragged((MouseEvent event) -> {
                    newStage.setX(event.getScreenX() - x1);
                    newStage.setY(event.getScreenY() - y1);
                    newStage.setOpacity(0.8);
                });
                root.setOnMouseReleased((MouseEvent event) -> {
                    newStage.setOpacity(1);
                });
                newStage.initStyle(StageStyle.TRANSPARENT);
                newStage.setScene(scene);
                newStage.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * hiển thị thông tin của reader
     */
    private void setMyProfile() throws SQLException {
        profileName.setText(reader.getPersonName().fullName());
        profileAddress.setText(reader.getPersonAddress().getFullAddress());
        profileID.setText(reader.getReaderID());
        profileDate.setText(reader.getPersonDatebirth().stringDate());
        profileSex.setText(reader.getPersonSex());
        profileGmail.setText(reader.getPersonGmail().getGmail());
        profilePhone.setText(reader.getPersonPhone().getPhoneNum());
        Image image = LoadImage(GlobalState.getReaderLoggedIn());
        if (image != null) {
            imageOnMyProFile.setImage(image);
            imageOnWelcomReader.setImage(image);
        }
    }

    private int indexBookRecently;
    private int indexBookSearch;

    /**
     * các hành động của reader
     */
    @FXML
    void actionReader(ActionEvent event) throws Exception {
        if (event.getSource() == enterExit) {
            System.exit(0);
        } else if (event.getSource() == enterReDo) {
            anchorPaneStack.pop();
            setVisiblePane(anchorPaneStack.peek());
        } else if (event.getSource() == enterSearch) {
            setVisiblePane(sceneResultSearch);
            indexBookSearch = 0;
            setShowResultSearch();
        } else if (event.getSource() == enterHome) {
            setVisiblePane(sceneHome);
            enterHome.setStyle("-fx-background-color: " +
                    "linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            enterSetting.setStyle("-fx-background-color: transparent;");
            enterBorrowedBook.setStyle("-fx-background-color: transparent;");
            enterComment.setStyle("-fx-background-color: transparent;");
        } else if (event.getSource() == enterModifyInfo) {
            setVisiblePane(sceneModifyInfo);
        } else if (event.getSource() == enterSave) {
            processSaveChangeInfo();
            /* update vào sql*/
            if (fileChangeAvatar != null) {
                FileInputStream fileInputStream = new FileInputStream(fileChangeAvatar);
                update.setBinaryStream(1, fileInputStream, fileInputStream.available());
                update.setString(2, GlobalState.getReaderLoggedIn().getReaderID());
                int rowsAffected = update.executeUpdate();
                if (rowsAffected == 0) { // Không tìm thấy readerID -> Chèn dòng mới
                    store.setString(1, GlobalState.getReaderLoggedIn().getReaderID());
                    store.setBinaryStream(2, fileInputStream, fileInputStream.available());
                    store.executeUpdate();
                    store.close();
                    System.out.println("Không tìm thấy readerID. Đã thêm dòng mới.");
                } else {
                    System.out.println("Cập nhật ảnh thành công.");
                }
            } else {
                System.out.println("lưu ảnh sau khi thay đổi bị null");
            }

            setMyProfile();
            Image image = LoadImage(GlobalState.getReaderLoggedIn());
            if (image != null) {
                imageOnMyProFile.setImage(image);
                imageOnWelcomReader.setImage(image);
                newImage.setImage(image);
            }
            setVisiblePane(sceneProfile);

        } else if (event.getSource() == enterSetting) {
            setVisiblePane(sceneSetting);
            enterSetting.setStyle("-fx-background-color:" +
                    " linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            enterHome.setStyle("-fx-background-color: transparent;");
            enterBorrowedBook.setStyle("-fx-background-color: transparent;");
            enterComment.setStyle("-fx-background-color: transparent;");
        } else if (event.getSource() == enterDelAcc) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete your account? ");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) enterDelAcc.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
            alert.close();
        } else if (event.getSource() == enterComment) {
            setVisiblePane(sceneChat);
            enterComment.setStyle("-fx-background-color:" +
                    " linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            enterSetting.setStyle("-fx-background-color: transparent;");
            enterHome.setStyle("-fx-background-color: transparent;");
            enterBorrowedBook.setStyle("-fx-background-color: transparent;");
        } else if (event.getSource() == enterChange) {
            setVisiblePane(sceneChangePassword);
        } else if (event.getSource() == enterChangePassword) {
            processChangePassword();
        } else if (event.getSource() == enterCreateBorrow) {
            setVisiblePane(sceneBorrowBook);
        } else if (event.getSource() == enterExitBorrow) {
            setVisiblePane(sceneDescribeBook);
        } else if (event.getSource() == enterBorrow) {
            processBorrowingBook();
        } else if (event.getSource() == enterBorrowedBook) {
            setColTableBorrowedBook();
            enterBorrowedBook.setStyle("-fx-background-color: " +
                    "linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            enterSetting.setStyle("-fx-background-color: transparent;");
            enterHome.setStyle("-fx-background-color: transparent;");
            enterComment.setStyle("-fx-background-color: transparent;");
        } else if (event.getSource() == enterReturnBook) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to return these books? ");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                Book book = ManageBook.getBooks().get(bookToReturn.getNameBook());
                if (book != null) {
                    LoanManagement.getListBorrowedBook().remove(bookToReturn.getCouponCode()); //xóa phiếu mượn sách
                    LoanManagement.getListUserAndBook().get(reader.getReaderID())
                            .remove(bookToReturn.getCouponCode()); // xóa phiếu khỏi danh sách
                    LoanManagement.saveListBorrowedBookToDatabase(); // lưu
                    Librarian.getListBookLoaned().get(reader.getReaderID())
                            .remove(Librarian.findSlip(reader.getReaderID(),
                                    bookToReturn.getCouponCode())); // xóa sách khỏi danh sách mượn của hệ thống
                    Librarian.saveLoanAndRequestToDataBase(); //lưu
                    reader.getListBorrowedBook()
                            .remove(bookToReturn.getCouponCode());   // xóa khỏi danh sách sách đã mượn của người đọc
                    reader.setAmountBorrowedBook(reader.getAmountBorrowedBook()
                            - bookToReturn.getAmount()); // cập nhật số lượng sách đã mượn
                    book.setNumberBook(book.getNumberBook() + bookToReturn.getAmount());
                    ManageBook.saveBooksToFile();
                }// cập nhật lại số lượng sách đã mượn
                else {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("The library no longer has this book type. ");
                    alert.showAndWait();
                }
                textTotalBorrow.setText("Total : " + reader.getAmountBorrowedBook());
                setColTableBorrowedBook();
            }
        } else if (event.getSource() == enterReturnAll) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to return all these books? ");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                processReturnAllBook();
                textTotalBorrow.setText("Total : " + reader.getAmountBorrowedBook());
                setColTableBorrowedBook();
            }
        } else if (event.getSource() == enterSendMessage) {
            setMessage();
        } else if (event.getSource() == enterShowQR) {
            processShowQR();
        }
    }

    @FXML
    void actionKeySearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            setVisiblePane(sceneResultSearch);
            indexBookSearch = 0;
            setShowResultSearch();
        }
    }

    @FXML
    void actionKeySendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            setMessage();
        }
    }

    public void setMessage() {
        addNewMessage(enterMessage.getText(), true);
        waitMessage(enterMessage.getText());
        enterMessage.clear();
        scrollChat.vvalueProperty().bind(chatBox.heightProperty());
    }

    /**
     * chọn sách khi ấn vào thông tin sách
     */

    /**
     * sự kiện để di chuyển ảnh sách trên giao diện
     */
    @FXML
    void actionMouseScroll(ScrollEvent event) {
        int size = resultSearch.size();
        if (event.getDeltaY() < 0) {
            indexBookSearch++;
            if (indexBookSearch > size - 3) {
                indexBookSearch = Math.min(size - 3, size - 1);
            }
        } else if (event.getDeltaY() > 0) {
            indexBookSearch--;
            if (indexBookSearch < 0) indexBookSearch = 0;
        }
        if (sceneResultSearch.isVisible()) {
            setShowResultSearch();
        }
    }

    /**
     * hiển thị sách dựa trên kết quả tìm kiếm
     */
    public void setShowResultSearch() {
        int endIndex = Math.min(indexBookSearch + 10, bookList.size()); // Load tối đa 10 sách mỗi lần
        displayResult.getChildren().clear();
        for (int i = indexBookSearch; i < endIndex; i++) {
            Book book = ManageBook.getBooks().get(resultSearch.get(i));

            // Tạo HBox cho từng sách
            HBox bookRow = new HBox(10);
            bookRow.setAlignment(Pos.CENTER_LEFT);
            bookRow.setStyle("-fx-border-color: #ddd; " +
                    "-fx-border-width: 1px; -fx-background-color: #f9f9f9;A");

            // Tạo ImageView cho ảnh sách
            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);
            loadAndShowImage(book, imageView); // Tải ảnh sách trong nền

            // Tạo VBox cho thông tin sách
            VBox bookInfo = new VBox(5);
            bookInfo.setAlignment(Pos.CENTER_LEFT);

            Label titleLabel = new Label(book.getTitle());
            titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label authorLabel = new Label("Tác giả: " + book.getAuthors());
            authorLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

            Label descriptionLabel = new Label(book.getDescription());
            descriptionLabel.setWrapText(true);
            descriptionLabel.setMaxWidth(400);
            descriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

            // Thêm thông tin vào VBox
            bookInfo.getChildren().addAll(titleLabel, authorLabel, descriptionLabel);

            // Thêm ImageView và VBox vào HBox
            bookRow.getChildren().addAll(imageView, bookInfo);

            // Sự kiện khi click vào hàng sách
            bookRow.setOnMouseClicked(event -> {
                bookBorrow = book;
                getDescribeBook(book); // Hiển thị mô tả chi tiết
            });

            // Thêm HBox (hàng sách) vào VBox hiển thị
            displayResult.getChildren().add(bookRow);
        }
    }

    public void setResultSearch(ObservableList<String> list) {
        this.resultSearch = list;
    }

    /**
     * hiển thị sách ở giao diện chính
     */

    /**
     * thay đổi sang giao diện tương ứng mỗi khi có sự kiện
     */
    public void setVisiblePane(AnchorPane anchorPane) {
        if (anchorPane != sceneBorrowBook) {
            anchorPaneStack.push(anchorPane);
        }
        for (AnchorPane pane : paneList) {
            if (anchorPane != sceneBorrowBook) {
                pane.setVisible(pane == anchorPane);
            } else {
                pane.setVisible(pane == anchorPane || pane == sceneDescribeBook);
            }
        }
    }

    /**
     * Sử dụng đa luồng giúp cho ảnh load lên không ảnh hưởng đên tốc độ giao diện
     */
    public static void loadAndShowImage(Book book, ImageView imageView) {
        // Tạo Task để tải ảnh trong nền
        Task<Image> loadImageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                // Gọi phương thức getImage() của đối tượng book để lấy ảnh
                return book.getImage();
            }
        };
        // Khi tải ảnh hoàn tất, cập nhật ảnh vào ImageView bằng Platform.runLater
        loadImageTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                imageView.setImage(loadImageTask.getValue());

            });
        });
        // Chạy Task trong một luồng nền
        Thread backgroundThread = new Thread(loadImageTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    public void waitMessage(String message) {
        Label waitLoad = new Label(); // tạo hiệu ứng chờ
        chatBox.getChildren().add(waitLoad);
        waitLoad.setAlignment(Pos.CENTER_LEFT);
        waitLoad.setStyle(
                "-fx-font-size: 50px;" +        // Tăng kích thước chữ
                        "-fx-text-fill: #7a7a7a;" +     // Màu chữ xám
                        "-fx-padding: 10;"              // Khoảng cách nội dung
        );
        Timeline typingAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> waitLoad.setText(".")),
                new KeyFrame(Duration.seconds(1), e -> waitLoad.setText("..")),
                new KeyFrame(Duration.seconds(1.5), e -> waitLoad.setText("..."))
        );

        typingAnimation.setCycleCount(Timeline.INDEFINITE); // Lặp vô hạn
        typingAnimation.play();
        Task<String> loadMessageTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                // Gọi phương thức getImage() của đối tượng book để lấy ảnh
                return chatbot.getPythonResponse(message);
            }
        };
        // Khi tải ảnh hoàn tất, cập nhật ảnh vào ImageView bằng Platform.runLater
        loadMessageTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                typingAnimation.stop();
                chatBox.getChildren().remove(waitLoad);
                addNewMessage(loadMessageTask.getValue(), false);
            });
        });
        // Chạy Task trong một luồng nền
        Thread backgroundThread = new Thread(loadMessageTask);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    /**
     * cài đặt thông báo
     */
    public void setAnnouncement() {
        listAnnouncement.getItems().clear();
        LocalDate now = LocalDate.now();
        for (BookLoan book : reader.getListBorrowedBook().values()) {
            Date date = book.getRequiredReturnDate();
            LocalDate requireDay = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
            if (ChronoUnit.DAYS.between(now, requireDay) <= 2) { // sẽ thông báo trả sách trước 2 ngày
                MenuItem menuItem = new MenuItem();
                Label label = new Label(book.getBookId() + ": " + book.getNameBook()
                        + " : the borrowing period for the following book is about to end");
                label.setMaxWidth(180);
                label.setWrapText(true);
                menuItem.setGraphic(label);
                menuItem.setOnAction(actionEvent -> setColTableBorrowedBook());
                listAnnouncement.getItems().add(menuItem);
                listAnnouncement.setText(listAnnouncement.getItems().size() + "");
            }
        }
    }

    public void processChangePassword() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure you want to change your password? ");

        // Hiển thị cảnh báo và chờ người dùng chọn
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Kiểm tra kết quả
        if (result == ButtonType.OK) {
            Account account = reader.getReaderAccount();
            if (enterOldPassword.getText().equals(account.getPassWord())
                    && enterNewPassword.getText().equals(reEnterNewPassword.getText())) {
                String newPass = enterNewPassword.getText();
                if (checkPasswordStrong(newPass)) {
                    account.setPassWord(newPass);
                    ManageAccount.getListAccountOfReaderByID().remove(reader.getReaderID());
                    ManageAccount.getListAccountOfReaderByID().put(reader.getReaderID(),reader.getReaderAccount());
                    ManageAccount.saveAccountToDatabase();
                    setVisiblePane(sceneSetting);
                }else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Your PassWord is not strong");
                    alert.showAndWait();
                }
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Change Password Fail ! Please try again");
                // Hiển thị cảnh báo
                alert.showAndWait();
                enterNewPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;"
                        + "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);"
                        + "-fx-background-color: #ffdddd;");
                enterOldPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;"
                        + "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);"
                        + "-fx-background-color: #ffdddd;");
                reEnterNewPassword.setStyle("-fx-border-color: red; -fx-border-width: 2px;"
                        + "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);"
                        + "-fx-background-color: #ffdddd;");
            }
            alert.close();
        }
    }

    @FXML
    private ImageView newImage;

    @FXML
    private Button changeAvatar;

    public void changeAvartar() {
        Stage stage = (Stage) changeAvatar.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.setTitle("choose image");
        /* lm cho chi chọn dc file anh kiểu jpg, png */
        FileChooser.ExtensionFilter imageFilter = new
                FileChooser.ExtensionFilter("image file", "*.jpg", "*.png");
        fc.getExtensionFilters().addAll(imageFilter);
        fileChangeAvatar = fc.showOpenDialog(stage);

        if (fileChangeAvatar != null) {
            Image image = new Image(fileChangeAvatar.toURI().toString(),
                    220, 390, false, true);
            newImage.setImage(image);
        }
    }

    public void processSaveChangeInfo() {
        if (!Objects.equals(enterNewName.getText(), "")) {
            reader.setPersonName(new PersonName(enterNewName.getText()));
        }
        if (!Objects.equals(enterNewAddress.getText(), "")) {
            reader.setPersonAddress(new Address(enterNewAddress.getText()));
        }
        if (!Objects.equals(enterNewBirthday.getText(), "")) {
            reader.setPersonDatebirth(new Date(enterNewBirthday.getText()));
        }
        if (!Objects.equals(enterNewGmail.getText(), "")) {
            reader.setPersonGmail(new Gmail(enterNewGmail.getText()));
        }
        if (!Objects.equals(enterNewPhone.getText(), "")) {
            reader.setPersonPhone(new PhoneNumber(enterNewPhone.getText()));
        }

        if (sexOnReaderView.getValue() != null) {
            if (sexOnReaderView.getValue().toString().equals("Male")) {
                reader.setPersonSex(true);
            }
        }
    }

    /**
     * xử lí input yêu cầu mượn sách
     */
    public void processBorrowingBook() {
        LocalDate localDate = enterReturnDate.getValue();
        try {
            if (Integer.parseInt(enterQuantiyBorrow.getText()) > bookBorrow.getNumberBook()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Please enter the quantity again !");
                alert.setContentText("Not enough books available");
                alert.showAndWait();
                enterQuantiyBorrow.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;");
                alert.close();
            } else if (!enterReturnDate.getValue().isBefore(LocalDate.now())) {
                int quantity = Integer.parseInt(enterQuantiyBorrow.getText());
                String date = localDate.toString();
                BookLoanAndReturnSlip slip = new BookLoanAndReturnSlip(reader.getReaderID(),
                        bookBorrow.getTitle(),
                        quantity,
                        new Date(LocalDate.now().toString(), true),
                        null,
                        new Date(date, true).addDays(30),
                        false);

                Librarian.getListBookLoanRequest().computeIfAbsent(reader.getReaderID(),
                        value -> new ArrayList<>()).add(slip);

                Librarian.saveLoanAndRequestToDataBase(slip);

                reader.setAmountBorrowedBook(reader.getAmountBorrowedBook() + quantity);
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText("You have successfully registered to borrow books!");
                alert.showAndWait();
                alert.close();
                setVisiblePane(sceneDescribeBook);
                enterQuantiyBorrow.setText("0");
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Please enter the return date again !");
                alert.showAndWait();
                enterReturnDate.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;");
                alert.close();
            }
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Please enter the quantity again !");
            alert.showAndWait();
            enterQuantiyBorrow.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                    "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                    "-fx-background-color: #ffdddd;");
            alert.close();
        }


    }

    @FXML
    private Label textPublishedDate;

    @FXML
    private Label textPublisher;

    /**
     * giao diện mô tả 1 quyển sách
     */
    public void getDescribeBook(Book book) {
        setVisiblePane(sceneDescribeBook);
        textNameID.setText(book.getIdBook() + " - " + book.getTitle());
        textAuthor.setText("Author : " + book.getAuthors());
        textPublisher.setText("Publisher : " + book.getPublisher());
        textPublishedDate.setText("PublishedDate : " + book.getPublishedDate());
        textQuantity.setText("Quantity : " + book.getNumberBook());
        textDescribeBook.setText("Description : " + book.getDescription());
        loadAndShowImage(book, imageBook);
    }

    private void addNewMessage(String message, boolean isSentByUser) {
        // Tạo HBox chứa tin nhắn
        HBox messageBox = new HBox();
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(scrollChat.getMaxWidth());

        // Áp dụng style tùy thuộc vào người gửi hay nhận
        messageLabel.setStyle(isSentByUser
                ? "-fx-background-color: #0084FF; -fx-text-fill:" +
                " white; -fx-padding: 10; -fx-background-radius: 10;"
                : "-fx-background-color: #E5E5EA; -fx-text-fill:" +
                " black; -fx-padding: 10; -fx-background-radius: 10;");

        messageBox.getChildren().add(messageLabel);
        messageBox.setAlignment(isSentByUser ? javafx.geometry.Pos.CENTER_RIGHT :
                javafx.geometry.Pos.CENTER_LEFT);

        // Thêm tin nhắn mới vào VBox
        chatBox.getChildren().add(messageBox);

        // Tạo hiệu ứng chuyển động khi thêm tin nhắn
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), messageBox);
        transition.setFromY(50); // Tin nhắn bắt đầu từ dưới lên
        transition.setToY(0);    // Kết thúc ở vị trí cố định
        transition.play();
    }

    private void loadMoreBooks() {
        int endIndex = Math.min(indexBookRecently + 18, bookList.size());

        HBox currentRow = new HBox(10); // Tạo một HBox mới cho một hàng

        // Duyệt qua các sách chưa được tải và thêm vào HBox
        for (int i = indexBookRecently; i < endIndex; i++) {
            VBox contentBook = new VBox(5);
            contentBook.setPrefHeight(190);
            contentBook.setPrefWidth(162);
            contentBook.setAlignment(Pos.CENTER);
            Book book = bookList.get(i);
            ImageView imageView = new ImageView();
            imageView.setFitWidth(120);
            imageView.setFitHeight(162);
            Label label = new Label(book.getTitle());
            label.setAlignment(Pos.CENTER);
            // Tải ảnh sách trong nền
            loadAndShowImage(book, imageView);
            contentBook.getChildren().addAll(imageView, label);
            contentBook.setStyle("-fx-border-color: #333; -fx-border-width:" +
                    " 2px; -fx-padding: 10px; -fx-background-color: #f4f4f4;-fx-font-weight: bold;");
            contentBook.setOnMouseClicked(event -> {
                bookBorrow = book;
                getDescribeBook(book);
            });
            HBox bookItem = new HBox(5);
            bookItem.getChildren().addAll(contentBook);
            currentRow.getChildren().add(bookItem);

            // Nếu đã đủ số sách trong một hàng (BOOKS_PER_ROW), thêm HBox vào VBox và tạo HBox mới
            if (currentRow.getChildren().size() == 6) {
                displayBook.getChildren().add(currentRow);
                currentRow = new HBox(10); // Tạo HBox mới cho hàng tiếp theo
            }
        }

        // Nếu hàng hiện tại vẫn còn sách, thêm vào danh sách
        if (currentRow.getChildren().size() > 0) {
            displayBook.getChildren().add(currentRow);
        }

        indexBookRecently = endIndex; // Cập nhật số lượng sách đã tải
    }

    /**
     * cập nhật thông tin cột
     */
    public void setColTableBorrowedBook() {
        setVisiblePane(sceneBorrowedBook);
        tableBorrowedBook.getItems().clear();
        colID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("nameBook"));
        colCpCode.setCellValueFactory(new PropertyValueFactory<>("couponCode"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        colBorrowDate.setCellFactory(column -> new TableCell<BookLoan, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.stringDate());
                }
            }
        });
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("requiredReturnDate"));
        colReturnDate.setCellFactory(column -> new TableCell<BookLoan, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.stringDate());
                }
            }
        });
        listBookLoan = FXCollections.observableArrayList(reader.getListBorrowedBook().values());
        tableBorrowedBook.setItems(listBookLoan);
        textTotalBorrow.setText("Total : " + reader.getAmountBorrowedBook());
    }

    /**
     * xử lí trả hết sách
     */
    public void processReturnAllBook() {
        for (String cpcode : LoanManagement.getListUserAndBook().get(reader.getReaderID())) {
            LoanManagement.getListBorrowedBook().remove(cpcode);
        }
        LoanManagement.getListUserAndBook().remove(reader.getReaderID());
        LoanManagement.saveListBorrowedBookToDatabase(); // lưu
        for (BookLoanAndReturnSlip slip : Librarian.getListBookLoaned().get(reader.getReaderID())) {
            Book book = ManageBook.getBooks().get(slip.getNameBook());
            book.setNumberBook(book.getNumberBook() + slip.getLoanAmount()); // cập nhật lại số lượng sách đã mượn
        }
        Librarian.getListBookLoaned().remove(reader.getReaderID());
        Librarian.saveLoanAndRequestToDataBase(); //lưu
        reader.getListBorrowedBook().clear();   // xóa khỏi danh sách sách đã mượn của người đọc
        reader.setAmountBorrowedBook(0); // cập nhật số lượng sách đã mượn
        ManageBook.saveBooksToFile();
    }

    @FXML
    void actionScrollResult(ScrollEvent event) {
        if (scrollResultSearch.getVvalue() > 0.9) {
            setShowResultSearch();
        }
    }

    @FXML
    void actionScrollAllBook(ScrollEvent event) {
        if (scrollAllBook.getVvalue() > 0.9) {
            loadMoreBooks();
        }
    }

    /**
     * chọn sách để trả
     */
    @FXML
    void actionTable(MouseEvent event) {
        bookToReturn = tableBorrowedBook.getSelectionModel().getSelectedItem();
        if (event.getClickCount() % 2 == 1) {
            textCoupCode.setText("Coupon Code : " + bookToReturn.getCouponCode());
            textCoupCode.setVisible(true);
            enterReturnBook.setVisible(true);
        } else if (event.getClickCount() % 2 == 0) {
            tableBorrowedBook.getSelectionModel().clearSelection();
            textCoupCode.setVisible(false);
            enterReturnBook.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookBorrow = null;
        indexBookRecently = 0;
        indexBookSearch = 0;
        anchorPaneStack = new Stack<>();
        anchorPaneStack.push(sceneHome);
        paneList = Arrays.asList(sceneHome, sceneResultSearch,
                sceneProfile, sceneModifyInfo, sceneSetting,
                sceneChangePassword, sceneDescribeBook,
                sceneBorrowBook, sceneBorrowedBook, sceneChat);
        enterSuggest.setVisible(false);
        // Lắng nghe thay đổi trong searchField để cập nhật gợi ý
        enterInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                enterSuggest.setVisible(false); // Ẩn ListView nếu TextField trống
            } else {
                ObservableList<String> filteredSuggestions = FXCollections.observableArrayList();
                for (String suggestion : nameList) {
                    if (suggestion.toLowerCase().contains(newValue.toLowerCase())) {
                        filteredSuggestions.add(suggestion);
                    }
                }
                // Cập nhật danh sách gợi ý và hiển thị ListView nếu có kết quả
                enterSuggest.setItems(filteredSuggestions);
                enterSuggest.setVisible(!filteredSuggestions.isEmpty());
                setResultSearch(filteredSuggestions);
            }
        });
        // Cập nhật TextField khi chọn mục từ ListView
        enterSuggest.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        enterInput.setText(newValue);
                        enterSuggest.getSelectionModel().clearSelection(); // Thiết lập lại lựa chọn
                        enterInput.requestFocus(); // Trả focus về lại cho TextField
                    }
                });
        // khi chuyển sang các pane khác thì xóa hết các lựa chọn của table
        sceneBorrowedBook.visibleProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue) { // Nếu Pane bị ẩn
                tableBorrowedBook.getSelectionModel().clearSelection(); // Xóa trạng thái chọn
                textCoupCode.setVisible(false); // Ẩn thông tin
                enterReturnBook.setVisible(false); // Ẩn nút
            }
        });
        informationApp.setWrapText(true);
        try {
            setReader(GlobalState.getReaderLoggedIn());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // cập nhật thông báo mỗi ngày
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> setAnnouncement());
            }
        }, 0, 24 * 60 * 60 * 1000); // Khoảng thời gian giữa những lần cập nhật
        if (!reader.getListBorrowedBook().isEmpty()) {
            for (BookLoan book : reader.getListBorrowedBook().values()) {// cài tổng số sách đã mượn
                reader.setAmountBorrowedBook(reader.getAmountBorrowedBook() + book.getAmount());
            }
        }
        chatbot = new Chatbot();
        scrollChat.setFitToWidth(true);
        chatBox.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            // Nếu chiều cao của chatBox vượt quá chiều cao của ScrollPane
            if (newHeight.doubleValue() > scrollChat.getHeight()) {
                // Cuộn xuống cuối cùng
                scrollChat.setVvalue(1.0); //1 là cuối cùng
            }
        });
        addGenderReader();
        loadMoreBooks();
    }

    @FXML
    private ComboBox<String> sexOnReaderView;

    public void addGenderReader() {
        List<String> listGender = new ArrayList<>();
        sexOnReaderView.setStyle("-fx-font-family: 'Times New Roman';" +
                " -fx-font-size: 24px; -fx-font-weight: bold;");
        listGender.add("Male");
        listGender.add("Female");
        ObservableList observableList = FXCollections.observableArrayList(listGender);
        sexOnReaderView.setItems(observableList);

    }

    public void processShowQR() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/view/QRView.fxml"));
        try {
            Parent root = loader.load();

            root.setOnMousePressed((MouseEvent event) -> {
                x1 = event.getScreenX() - stage.getX();
                y1 = event.getScreenY() - stage.getY();
            });
            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x1);
                stage.setY(event.getScreenY() - y1);
                stage.setOpacity(0.8);
            });
            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });
            stage.initStyle(StageStyle.TRANSPARENT);

            QRController qrController = loader.getController();
            qrController.setImageQR(bookBorrow.getQrCode());

            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
