package org.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.GlobalState;
import org.model.LoaderSystem;
import org.model.account.Account;
import org.model.account.ManageAccount;
import org.model.book.Book;
import org.model.book.ManageBook;
import org.model.librarian.Librarian;
import org.model.librarian.LoanManagement;
import org.model.operator.LibraryManager;
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
import java.util.*;

import static org.controller.LoginController.pathToChooseRoleView;
import static org.controller.ReaderController.loadAndShowImage;
import static org.controller.SignUpController.*;
import static org.model.LoaderSystem.librarian1;
import static org.model.LoaderSystem.librarian2;
import static org.model.account.ManageAccount.saveAccountToDatabase;
import static org.model.book.ManageBook.saveBooksToFile;
import static org.model.librarian.Librarian.*;
import static org.model.operator.GmailSender.sendEmail;
import static org.model.operator.LibraryManager.getListReader;
import static org.model.operator.LibraryManager.saveReaderToDatabase;

public class DashBoardController implements Initializable {

    @FXML
    private TableView<Book> tableListBookView;
    @FXML
    private Button transactionsButton;

    @FXML
    private Button BookShelf;

    @FXML
    private ImageView imageBookOnListBookView;

    @FXML
    private TableColumn<?, ?> ColumnIdReaderOnBorrow;

    @FXML
    private Button DeleteOnListBookView;

    @FXML
    private Button HomeButton;

    @FXML
    private AnchorPane HomeView;

    @FXML
    private AnchorPane HomeViewLibrary;

    @FXML
    private Button ListReaderButton;

    @FXML
    private AnchorPane MAIN_FORM_LIBRARYMANAGER;

    @FXML
    private Label NameOfAdmin;

    @FXML
    private TextField readerOnTableBorrow;

    @FXML
    private Button SignOut;

    @FXML
    private Button addBuOnReaderView;

    @FXML
    private Button addOnListBookView;

    @FXML
    private TextField addressOnReaderView;

    @FXML
    private TextField ageOnReaderView;

    @FXML
    private TextField authorOnListBookView;

    @FXML
    private DatePicker birthDateOnReaderView;

    @FXML
    private Button clearBuOnReaderView;

    @FXML
    private Button clearOnListBookView;

    @FXML
    private Button closeBuOnHome;

    @FXML
    private TableColumn<Reader, String> colAddressOnReaderView;

    @FXML
    private TableColumn<Book, String> colAuthorBookOnListBookView;

    @FXML
    private TableColumn<Reader, String> colBirthOnReaderView;

    @FXML
    private TableColumn<Reader, String> colFirstNameOnReaderView;

    @FXML
    private TableColumn<Reader, String> colGamilOnReaderView;

    @FXML
    private TableColumn<Book, String> colIdBookOnListBookView;

    @FXML
    private TableColumn<Reader, String> colLastNameOnReaderView;

    @FXML
    private TableColumn<Reader, String> colPhoneOnReaderView;

    @FXML
    private TableColumn<Book, String> colPublisherOnListBookView;

    @FXML
    private TableColumn<Book, String> colQuantityOnListBookView;

    @FXML
    private TableColumn<Reader, String> colReaderIDOnReaderView;

    @FXML
    private TableColumn<Reader, String> colSexOnReaderView;

    @FXML
    private TableColumn<Book, String> colTitleBookOnListBookView;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> columnBorrowDateOnBorrow;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> columnNameReaderOnBorrow;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> columnQuantityOnBorrow;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> columnReturnDateOnBorrow;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> columnStatusOnBorrow;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> columnTitleBookOnBorrow;

    @FXML
    private Button deleteOnReaderView;

    @FXML
    private Button detailBuLibrary1;

    @FXML
    private Button detailBuLibrary2;

    @FXML
    private TextField firstNameOnReaderView;

    @FXML
    private TextField gmailOnReaderView;

    @FXML
    private TextField lastNameOnReaderView;

    @FXML
    private AnchorPane listBookOnLibrary;

    @FXML
    private AnchorPane listOfBookBorrowers;

    @FXML
    private AnchorPane listReaderView;

    @FXML
    private TextField phoneOnReaderView;
    @FXML
    private TextField readerIDOnReaderView;

    @FXML
    private TextField searchOnListReaderView;

    @FXML
    private ComboBox<String> sexOnReaderView;

    @FXML
    private TableView<BookLoanAndReturnSlip> tableListBookBorrow;

    @FXML
    private TableView<Reader> tableViewListReader = new TableView<>();

    /**
     * detail
     */
    @FXML
    private TextField detailAddressReader;

    @FXML
    private TextField detailBirthReader;

    @FXML
    private TextField detailFirstNameReader;

    @FXML
    private TextField detailGamilReader;

    @FXML
    private TextField detailIdReader;

    @FXML
    private ImageView detailImageReader;

    @FXML
    private TextField detailLastNameReader;

    @FXML
    private TextField detailPhoneReader;

    @FXML
    private TextField detailSexReader;


    @FXML
    private AnchorPane readerDetail;
    private Alert alert;
    private Reader rdIsChooseByOperator;
//    private Book clickBook;

    /**********************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewListReader.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Nháy đúp chuột
                try {
                    getDetailViewOfReader();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else if (event.getClickCount() == 1) {
                selectReader();
            }
        });

        tableListBookView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Nháy đúp chuột
                try {
                    getDetailViewOfBook();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else if (event.getClickCount() == 1) {
                selectBook();
            }
        });
        tableListBookBorrow.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Nháy đúp chuột
                try {
                    getDetailLoan();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        addReaderShow();  // to show immidiate when we proceed dashboard
        addGenderReader();
        tableBookShow();
        setBookLoanAndReturnSlipsView();
    }


    /**
     * nut close.
     */
    public void close() {
        System.exit(0);
    }

    /**
     * nut minimize.
     */
    public void minimize() {
        Stage stage = (Stage) MAIN_FORM_LIBRARYMANAGER.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * chuyen doi form
     */
    public void switchForm(ActionEvent event) {
        if (event.getSource() == HomeButton) {
            HomeViewLibrary.setVisible(true);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            libraryDetail.setVisible(false);

            // Đặt màu nền cho nút đang chọn
            HomeButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            // Đặt lại màu nền cho các nút khác
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");
            transactionsButton.setStyle("-fx-background-color: transparent;");

        } else if (event.getSource() == ListReaderButton) {
            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(true);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            libraryDetail.setVisible(false);

            ListReaderButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");
            transactionsButton.setStyle("-fx-background-color: transparent;");

            // to become update once you click add reader
            addReaderShow();
            addGenderReader();

        } else if (event.getSource() == BookShelf) {
            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(true);
            listOfBookBorrowers.setVisible(false);
            libraryDetail.setVisible(false);

            BookShelf.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            transactionsButton.setStyle("-fx-background-color: transparent;");

            tableBookShow();

        } else if (event.getSource() == transactionsButton) {
            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(true);
            libraryDetail.setVisible(false);
            setBookLoanAndReturnSlipsView();

            transactionsButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");

        }
        readerDetail.setVisible(false);
        upDateBookOnOperator.setVisible(false);
        upDateReader.setVisible(false);
        addBookOnOperator.setVisible(false);
        detailBook.setVisible(false);
        detailLoan.setVisible(false);
    }

    /**
     * xu li dang xuat
     */
    private double x1 = 0;
    private double y1 = 0;

    public void SignOut() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout???");
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get().equals(ButtonType.OK)) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass()
                        .getResource(pathToChooseRoleView)));
                Stage oldStage = (Stage) SignOut.getScene().getWindow();
                double oldStageX = oldStage.getX();
                double oldStageY = oldStage.getY();
                oldStage.hide();
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
     * them reader
     * xu li phan hien thi
     */
    public ObservableList<Reader> addListReader() {
        ObservableList<Reader> readerObservableList = FXCollections.observableArrayList();
        Map<String, Reader> dataReader = LibraryManager.getListReader();
        readerObservableList.addAll(dataReader.values());
        return readerObservableList;
    }

    private ObservableList<Reader> readersData;

    public void addReaderShow() {
//        tableViewListReader.getItems().clear();
        readersData = addListReader();

        colReaderIDOnReaderView.setCellValueFactory(new PropertyValueFactory<>("readerID"));

        colFirstNameOnReaderView.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPersonName().getFirstName()));
        colLastNameOnReaderView.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPersonName().getLastName()));

        colBirthOnReaderView.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPersonDatebirth().stringDate()));
        colSexOnReaderView.setCellValueFactory(new PropertyValueFactory<>("personSex"));

        colPhoneOnReaderView.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPersonPhone().getPhoneNum()));

        colGamilOnReaderView.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPersonGmail().getGmail()));
        colAddressOnReaderView.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPersonAddress2()));

        tableViewListReader.setItems(readersData);
    }

    @FXML
    private void setupReaderSearchFeature() {
        FilteredList<Reader> filteredData = new FilteredList<>(readersData, b -> true);
        searchOnListReaderView.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reader -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (reader.getReaderID().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonName().getFirstName().toLowerCase()
                        .contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonName().getLastName().toLowerCase()
                        .contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonPhone().getPhoneNum().toLowerCase()
                        .contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonGmail().getGmail().toLowerCase()
                        .contains(lowerCaseFilter)) {
                    return true;
                } else return reader.getPersonAddress2().toLowerCase()
                        .contains(lowerCaseFilter);
            });
        });
        SortedList<Reader> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewListReader.comparatorProperty());
        tableViewListReader.setItems(sortedData);
    }


    /**
     * EM CHI TIET 1 READER TRONG BANG
     */

    public void selectReader() {
        int num = tableViewListReader.getSelectionModel().getSelectedIndex();
        if (num >= 0) {
            GlobalState.setReaderIsChoosed(tableViewListReader.getSelectionModel()
                    .getSelectedItem());
        }
    }

    public void getDetailViewOfReader() throws SQLException {
        rdIsChooseByOperator = tableViewListReader.getSelectionModel().getSelectedItem();
        int num = tableViewListReader.getSelectionModel().getSelectedIndex();
        if (num >= 0) {
//            rdIsChooseByOperator = GlobalState.getReaderIsChoosed();
            detailIdReader.setText(rdIsChooseByOperator.getReaderID());
            detailFirstNameReader.setText(rdIsChooseByOperator
                    .getPersonName().getFirstName());
            detailLastNameReader.setText(rdIsChooseByOperator
                    .getPersonName().getLastName());
            detailGamilReader.setText(rdIsChooseByOperator
                    .getPersonGmail().getGmail());
            detailPhoneReader.setText(rdIsChooseByOperator
                    .getPersonPhone().getPhoneNum());
            detailAddressReader.setText(rdIsChooseByOperator
                    .getPersonAddress2());
            detailBirthReader.setText(rdIsChooseByOperator
                    .getPersonDatebirth().stringDate());
            detailSexReader.setText(rdIsChooseByOperator
                    .getPersonSex());
            Image a = LoadImage(rdIsChooseByOperator);
            detailImageReader.setImage(a);

            detailIdReader.setEditable(false);
            detailFirstNameReader.setEditable(false);
            detailLastNameReader.setEditable(false);
            detailGamilReader.setEditable(false);
            detailPhoneReader.setEditable(false);
            detailAddressReader.setEditable(false);
            detailBirthReader.setEditable(false);
            detailSexReader.setEditable(false);

            readerDetail.setVisible(true);

            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            addBookOnOperator.setVisible(false);
        }
    }

    public void addGenderReader() {
        List<String> listGender = new ArrayList<>();
        sexOnReaderView.setStyle("-fx-font-family: 'Times New Roman';"
                + " -fx-font-size: 24px; -fx-font-weight: bold;");
        listGender.add("Male");
        listGender.add("Female");
        ObservableList observableList = FXCollections.observableArrayList(listGender);
        sexOnReaderView.setItems(observableList);
        upDateReaderSex.setItems(observableList);
    }


    /**
     * hien thi chi tiet library
     */
    @FXML
    private ImageView detailImageLibrary;
    @FXML
    private TextField libraryAddress;

    @FXML
    private TextField libraryBirth;

    @FXML
    private AnchorPane libraryDetail;

    @FXML
    private TextField libraryFirstName;

    @FXML
    private TextField libraryGmail;

    @FXML
    private TextField libraryID;

    @FXML
    private TextField libraryLastName;

    @FXML
    private TextField libraryPhone;

    @FXML
    private TextField sexLibrary;

    public void showLibraryDetails(Librarian librarian, String imagePath) {
        libraryDetail.setVisible(true);

        libraryID.setText(librarian.getIdLibrarian());
        libraryID.setEditable(false);

        libraryAddress.setText(librarian.getPersonAddress2());
        libraryAddress.setEditable(false);

        libraryBirth.setText(librarian.getPersonDatebirth()
                .stringDate());
        libraryBirth.setEditable(false);

        libraryPhone.setText(librarian.getPersonPhone()
                .getPhoneNum());
        libraryPhone.setEditable(false);

        libraryGmail.setText(librarian.getPersonGmail()
                .getGmail());
        libraryGmail.setEditable(false);

        libraryFirstName.setText(librarian.getPersonName()
                .getFirstName());
        libraryFirstName.setEditable(false);

        libraryLastName.setText(librarian.getPersonName()
                .getLastName());
        libraryLastName.setEditable(false);
        if (librarian.getPersonSex().equals("Nam")) {
            sexLibrary.setText("nam");
        } else sexLibrary.setText("Nữ");
        sexLibrary.setEditable(false);

        // Cập nhật ảnh thư viện
        Image a = new Image(imagePath);
        detailImageLibrary.setImage(a);

        readerDetail.setVisible(false);
        HomeViewLibrary.setVisible(false);
        listReaderView.setVisible(false);
        listBookOnLibrary.setVisible(false);
        listOfBookBorrowers.setVisible(false);
    }

    public void showDetailBuLibrary1() {
        showLibraryDetails(librarian1, GlobalState.getInstance()
                .getPathtoimageCanh());
    }

    public void showDetailBuLibrary2() {
        showLibraryDetails(librarian2, GlobalState.getInstance()
                .getPathtoimageDuong());
    }


    /**
     * hiển thị những người mượn sách
     */
    private ObservableList<BookLoanAndReturnSlip> bookLoanAndReturnSlips;

    public void setBookLoanAndReturnSlipsView() {
        bookLoanAndReturnSlips = FXCollections.observableArrayList();
        for (String id : listBookLoanRequest.keySet()) {
            bookLoanAndReturnSlips.addAll(listBookLoanRequest.get(id));
            setupTableColumnsOnBorrower();
        }
        for (String id : listBookLoaned.keySet()) {
            bookLoanAndReturnSlips.addAll(listBookLoaned.get(id));
            setupTableColumnsOnBorrower();
        }
    }

    private void setupTableColumnsOnBorrower() {
        ColumnIdReaderOnBorrow.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        columnNameReaderOnBorrow.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFullNameOfBorrower()));
        columnTitleBookOnBorrow.setCellValueFactory(new PropertyValueFactory<>("nameBook"));
        columnQuantityOnBorrow.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
        columnBorrowDateOnBorrow.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBorrowedBookDate().stringDate()));
        columnReturnDateOnBorrow.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBookReturnSchedule().stringDate()));
        columnStatusOnBorrow.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));
        tableListBookBorrow.setItems(bookLoanAndReturnSlips);
    }

    @FXML
    private void setupSearchBorrowerFeature() {
        FilteredList<BookLoanAndReturnSlip> filteredData = new FilteredList<>(bookLoanAndReturnSlips, b -> true);
        readerOnTableBorrow.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(slips -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (slips.getIdCard().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (slips.getFullNameOfBorrower().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (slips.getNameBook().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return slips.getStatus().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<BookLoanAndReturnSlip> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableListBookBorrow.comparatorProperty());
        tableListBookBorrow.setItems(sortedData);
    }

    /**
     * book
     */
    @FXML
    private AnchorPane upDateBookOnOperator;

    @FXML
    private TextField upDateauthorOnListBookView;

    @FXML
    private TextField upDateidBookOnListBookView;

    @FXML
    private TextField upDatepublisherOnListBookView;

    @FXML
    private TextField upDatequantityOnListBookView;

    @FXML
    private TextField upDatetitleBookOnListBookView;
    @FXML
    private TextField UpdatePageNum;
    @FXML
    private TextField UpdatePubDate;
    @FXML
    private TextField UpdateLanguage;
    @FXML
    private TextField UpdateDescribe;

    @FXML
    private Button updateSaveOnListBookView;

    @FXML
    private ImageView imageBookOnListBookView1;

    /**
     * xử lí hiển thị cho book
     */

    @FXML
    private TextField searchBook;

    public ObservableList<Book> bookObservableList() {
        ObservableList<Book> listBookData = FXCollections.observableArrayList();
        Map<String, Book> dataBook = ManageBook.getBooks();
        listBookData.addAll(dataBook.values());
        return listBookData;
    }

    private ObservableList<Book> booksData;

    public void tableBookShow() {
        tableListBookView.getItems().clear();
        booksData = bookObservableList();
        colIdBookOnListBookView.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        colTitleBookOnListBookView.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthorBookOnListBookView.setCellValueFactory(new PropertyValueFactory<>("authors"));
        colPublisherOnListBookView.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        colQuantityOnListBookView.setCellValueFactory(new PropertyValueFactory<>("numberBook"));
        tableListBookView.setItems(booksData);
    }

    public void selectBook() {
        int num = tableListBookView.getSelectionModel()
                .getSelectedIndex();
        if (num >= 0) {
            GlobalState.setBookIsSelectedByOperator(tableListBookView
                    .getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void setupSearchBookFeature() {
        FilteredList<Book> filteredData = new FilteredList<>(booksData, b -> true);
        searchBook.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getIdBook().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (book.getAuthors().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return book.getPublisher().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableListBookView.comparatorProperty());
        tableListBookView.setItems(sortedData);
    }

    /**
     * update book cho operator
     */
    public void upDateBookForOperator() {
        upDateBookOnOperator.setVisible(true);

        Book bo = GlobalState.getBookIsSelectedByOperator();

        upDatetitleBookOnListBookView.setText(bo.getTitle());
        upDatetitleBookOnListBookView.setEditable(false);
        upDateidBookOnListBookView.setPromptText(bo.getIdBook());
        upDateauthorOnListBookView.setPromptText(bo.getAuthors());
        upDatepublisherOnListBookView.setPromptText(bo.getPublishedDate());
        upDatequantityOnListBookView.setPromptText(bo.getNumberBook() + "");
        UpdatePageNum.setPromptText(bo.getPageCount() + "");
        UpdatePubDate.setPromptText(bo.getPublishedDate());
        UpdateLanguage.setPromptText(bo.getLanguage());
        UpdateDescribe.setPromptText(bo.getDescription());
        loadAndShowImage(bo, imageBookOnListBookView1);

        readerDetail.setVisible(false);
        HomeViewLibrary.setVisible(false);
        listReaderView.setVisible(false);
        listBookOnLibrary.setVisible(false);
        listOfBookBorrowers.setVisible(false);
        upDateReader.setVisible(false);
    }

    @FXML
    public void deleteBookOnOperator() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Are you sure you want to delete this book? ");

        // Hiển thị cảnh báo và chờ người dùng chọn
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        // Kiểm tra kết quả
        if (result == ButtonType.OK) {
            ManageBook.getBooks().remove(GlobalState
                    .getBookIsSelectedByOperator().getTitle());
            tableBookShow();
        }
    }

    @FXML
    private Button saveUpdate;

    @FXML
    public void saveChangeForBook() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Changes");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to update the information for the book ?");
        ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonYes) {
            if (GlobalState.getBookIsSelectedByOperator() == null) {
                System.out.println("son of bitch");
                return;
            } else {
                System.out.println(GlobalState.getBookIsSelectedByOperator().getIdBook());
            }

            Book clickBook = GlobalState.getBookIsSelectedByOperator();

            if (!Objects.equals(upDateidBookOnListBookView.getText(), "")) {
                clickBook.setIdBook(upDateidBookOnListBookView.getText());
                upDateidBookOnListBookView.clear();
            }
            if (!upDatequantityOnListBookView.getText().isEmpty()) {
                clickBook.setNumberBook(Integer.parseInt(upDatequantityOnListBookView.getText()));
                upDatequantityOnListBookView.clear();
            }
            if (!UpdatePageNum.getText().isEmpty()) {
                clickBook.setPageCount(Integer.parseInt(UpdatePageNum.getText()));
                UpdatePageNum.clear();
            }
            if (!Objects.equals(UpdatePubDate.getText(), "")) {
                clickBook.setPublishedDate(UpdatePubDate.getText());
                UpdatePubDate.clear();
            }
            if (!Objects.equals(upDatepublisherOnListBookView.getText(), "")) {
                clickBook.setPublisher(upDatepublisherOnListBookView.getText());
                upDatepublisherOnListBookView.clear();
            }
            if (!Objects.equals(UpdateDescribe.getText(), "")) {
                clickBook.setDescription(UpdateDescribe.getText());
                UpdateDescribe.clear();
            }
            if (!Objects.equals(UpdateLanguage.getText(), "")) {
                clickBook.setLanguage(UpdateLanguage.getText());
                UpdateLanguage.clear();
            }
            if (!Objects.equals(upDateauthorOnListBookView.getText(), "")) {
                clickBook.setAuthors(upDateauthorOnListBookView.getText());
                upDateauthorOnListBookView.clear();
            }

            saveBooksToFile();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Completed");
            alert.setHeaderText(null);
            alert.setContentText("The information for the book \n"
                    + GlobalState.getBookIsSelectedByOperator().getIdBook() + "\n"
                    + "has been updated successfully.");
            alert.showAndWait();

            tableBookShow();

            listBookOnLibrary.setVisible(true);
            upDateBookOnOperator.setVisible(false);
        } else {
            System.out.println("Action canceled.");
        }
    }

    /**
     * thêm reader vào thư viện
     */
    @FXML
    private Label errorgmail;
    @FXML
    private Label errorID;
    @FXML
    private Label eroraddress;
    @FXML
    private Label errorphone;

    public void addReaderbyOperator() throws IOException, SQLException {
        String lname = lastNameOnReaderView.getText();
        String fname = firstNameOnReaderView.getText();
        String id = readerIDOnReaderView.getText();
        String sex = sexOnReaderView.getValue();
        boolean sex1 = "Male".equals(sex);
        LocalDate date = birthDateOnReaderView.getValue();
        Date date1 = new Date(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        String phone = phoneOnReaderView.getText();
        String address = addressOnReaderView.getText();

        boolean check = true;

        if (getListReader().containsKey(id)) {
            readerIDOnReaderView.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                    "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                    "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            errorID.setTextFill(Color.RED);
            check = false;
        } else {
            readerIDOnReaderView.setStyle(""); // Xóa viền đỏ khi không còn lỗi
            errorID.setTextFill(Color.WHITE);
        }

        if (!Gmail.checkGamilForm(gmailOnReaderView.getText())) {
            gmailOnReaderView.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                    "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                    "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            errorgmail.setTextFill(Color.RED);
            check = false;
        } else {
            gmailOnReaderView.setStyle(""); // Xóa viền đỏ khi không còn lỗi
            errorgmail.setTextFill(Color.WHITE);
        }

        if (!Address.checkAddressFormat(addressOnReaderView.getText())) {
            if (!addressOnReaderView.getStyleClass().contains("error")) {
                addressOnReaderView.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            }
            eroraddress.setTextFill(Color.RED);
            check = false;
        } else {
            addressOnReaderView.setStyle(""); // Xóa viền đỏ khi không còn lỗi
            eroraddress.setTextFill(Color.WHITE);
        }

        if (!PhoneNumber.checkPhoneForm(phoneOnReaderView.getText())) {
            if (!phoneOnReaderView.getStyleClass().contains("error")) {
                phoneOnReaderView.setStyle("-fx-border-color: red; -fx-border-width: 2px;" +
                        "-fx-effect: innershadow(gaussian, red, 5, 0.5, 0, 0);" +
                        "-fx-background-color: #ffdddd;"); // Viền đỏ khi lỗi
            }
            errorphone.setTextFill(Color.RED);
            check = false;
        } else {
            phoneOnReaderView.setStyle(""); // Xóa viền đỏ khi không còn lỗi
            errorphone.setTextFill(Color.WHITE);
        }

        if (!check) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Some thing are wrong");
            alert.setHeaderText(null);
            alert.setContentText("Please check information again !!!");
            alert.showAndWait();
        } else {
            Reader addReader = new Reader(new PersonName(fname, lname),
                    new Address(address), new Gmail(gmailOnReaderView.getText()),
                    new PhoneNumber(phone), sex1, date1, id);
            Account ac = new Account(id, "Hello" + id + "!!!");
            addReader.setReaderAccount(ac);

            LibraryManager.getListReader().put(id, addReader);
            LibraryManager.getListAccountOfReader().put(id, addReader);

            saveAccountToDatabase(addReader, ac);
            saveReaderToDatabase(id, addReader);

            File file = new File("D:\\Library-Management\\src\\main\\resources\\image\\USER.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            store.setString(1, id);
            store.setBinaryStream(2, fileInputStream, fileInputStream.available());
            store.executeUpdate();
            store.close();
            LoaderSystem c = new LoaderSystem();

            String content = "Dear " + addReader.getPersonName().fullName()
                    + "Welcome to [Your Library App's Name]! We're excited to have you onboard.\n"
                    + "Here are your account details:\n"
                    + "Username: " + addReader.getReaderAccount().getUserName() + "\n"
                    + "Password: " + addReader.getReaderAccount().getPassWord() + "\n"
                    + "Please log in to start exploring all the features"
                    + " we've designed to help you manage your library efficiently and enhance your reading experience.\n"
                    + "If you have any questions or need assistance, feel free to contact us at"
                    + " [Support Email/Contact Information].\n"
                    + "Enjoy your journey with us!";

            sendEmail(addReader.getPersonGmail().getGmail(), content, 3);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add reader Successful");
            alert.setHeaderText(null);
            alert.setContentText("The information for the user with ID \n"
                    + addReader.getPersonName().fullName() + "\n" + "has been successfully added.");
            alert.showAndWait();

            addReaderShow();
        }
    }

    public void clear() {
        readerIDOnReaderView.setText("");
        firstNameOnReaderView.setText("");
        lastNameOnReaderView.setText("");
        sexOnReaderView.setValue(null);//combobox
        birthDateOnReaderView.setValue(null); // datepicker;
        phoneOnReaderView.setText("");
        addressOnReaderView.setText("");
        gmailOnReaderView.setText("");
    }

    public void deleteReader() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this user?");
        alert.setContentText("This action cannot be undone.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                rdIsChooseByOperator = GlobalState.getReaderIsChoosed();
                if (rdIsChooseByOperator.getListBorrowedBook() != null) {
                    for (String id : rdIsChooseByOperator.getListBorrowedBook().keySet()) {
                        int tra = rdIsChooseByOperator.getListBorrowedBook().get(id).getAmount();
                        String title = rdIsChooseByOperator.getListBorrowedBook().get(id).getNameBook();
                        if (ManageBook.getBooks().containsKey(title)) {
                            ManageBook.getBooks().get(title).setNumberBook(tra
                                    + ManageBook.getBooks().get(title).getNumberBook());
                        }
                    }

                    listBookLoanRequest.remove(rdIsChooseByOperator.getReaderID());
                    listBookLoaned.remove(rdIsChooseByOperator.getReaderID());
                    LoanManagement.getListUserAndBook().remove(rdIsChooseByOperator.getReaderID());

                    LoanManagement.saveListBorrowedBookToDatabase();
                    saveLoanAndRequestToDataBase();
                    saveBooksToFile();
                }

                LibraryManager.getListReader().remove(rdIsChooseByOperator.getReaderID());
                LibraryManager.getListAccountOfReader().remove(rdIsChooseByOperator.getReaderAccount().getUserName());
                ManageAccount.getListAccountOfReaderByID().remove(rdIsChooseByOperator.getReaderID());

                ManageAccount.saveAccountToDatabase();
                saveReaderToDatabase();
                saveAccountToDatabase();

                try {
                    delete.setString(1, rdIsChooseByOperator.getReaderID());
                    delete.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Successful");
                alert.setHeaderText(null);
                alert.setContentText("The information for the user with ID \n"
                        + rdIsChooseByOperator.getReaderID()
                        + "\n" + "has been successfully deleted.");
                alert.showAndWait();

                LoaderSystem l = new LoaderSystem();
            }
        });
    }

    @FXML
    private AnchorPane upDateReader;

    @FXML
    private TextField upDateReaderAddress;

    @FXML
    private TextField upDateReaderFirstName;

    @FXML
    private TextField upDateReaderGamil;

    @FXML
    private TextField upDateReaderID;

    @FXML
    private TextField upDateReaderLastName;

    @FXML
    private TextField upDateReaderPhone;

    @FXML
    private DatePicker upDateReaderBirth;
    @FXML
    private ComboBox<String> upDateReaderSex;

    public void goToUpdateReader() {
        upDateReader.setVisible(true);
        rdIsChooseByOperator = GlobalState.getReaderIsChoosed();

        upDateReaderAddress.setPromptText(rdIsChooseByOperator.getPersonAddress2());

        upDateReaderFirstName.setPromptText(rdIsChooseByOperator.getPersonName()
                .getFirstName());
        upDateReaderGamil.setPromptText(rdIsChooseByOperator.getPersonGmail()
                .getGmail());

        upDateReaderID.setPromptText(rdIsChooseByOperator.getReaderID());

        upDateReaderLastName.setPromptText(rdIsChooseByOperator.getPersonName()
                .getLastName());

        upDateReaderPhone.setPromptText(rdIsChooseByOperator.getPersonPhone()
                .getPhoneNum());
        LocalDate lc = LocalDate.of(rdIsChooseByOperator.getPersonDatebirth().getYear(),
                rdIsChooseByOperator.getPersonDatebirth().getMonth(),
                rdIsChooseByOperator.getPersonDatebirth().getDay());
        upDateReaderBirth.setValue(lc);
        String sex = rdIsChooseByOperator.getPersonSex();
        if (upDateReaderSex.getItems().contains(sex)) {
            upDateReaderSex.setValue(sex); // Đặt giá trị nếu hợp lệ
        } else {
            System.err.println("Value not found in ComboBox items: " + sex);
        }
    }

    public void updateReader() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to update this user?");
        alert.setContentText("This action cannot be undone.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                updateReader1();

                saveReaderToDatabase();
                saveAccountToDatabase();

                LoaderSystem c = new LoaderSystem();
                addReaderShow();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Update Successful");
                alert.setHeaderText(null);
                alert.setContentText("The information for the user with ID \n"
                        + rdIsChooseByOperator.getReaderID()
                        + "\n" + "has been successfully updated.");
                alert.showAndWait();

            }
        });

    }

    private void updateReader1() {
        if (!Objects.equals(upDateReaderLastName.getText(), "")) {
            rdIsChooseByOperator.setPersonName(new
                    PersonName(rdIsChooseByOperator.getPersonName().getFirstName(),
                    upDateReaderLastName.getText()));
            upDateReaderLastName.clear();
        }
        if (!Objects.equals(upDateReaderFirstName.getText(), "")) {
            rdIsChooseByOperator.setPersonName(new
                    PersonName(upDateReaderFirstName.getText(),
                    rdIsChooseByOperator.getPersonName().getLastName()));
            upDateReaderFirstName.clear();
        }
        if (!Objects.equals(upDateReaderAddress.getText(), "")) {
            rdIsChooseByOperator.setPersonAddress(new Address(upDateReaderAddress.getText()));
            upDateReaderAddress.clear();
        }
        if (!Objects.equals(upDateReaderBirth.getValue().toString(), "")) {
            rdIsChooseByOperator.setPersonDatebirth(new Date(upDateReaderBirth
                    .getValue().getDayOfMonth(),
                    upDateReaderBirth.getValue().getMonthValue(),
                    upDateReaderBirth.getValue().getYear()));
            upDateReaderBirth.setValue(null);
        }
        if (!Objects.equals(upDateReaderGamil.getText(), "")) {
            rdIsChooseByOperator.setPersonGmail(new Gmail(upDateReaderGamil.getText()));
            upDateReaderGamil.clear();
        }
        if (!Objects.equals(upDateReaderPhone.getText(), "")) {
            rdIsChooseByOperator.setPersonPhone(new PhoneNumber(upDateReaderPhone.getText()));
            upDateReaderPhone.clear();
        }
        if (!Objects.equals(upDateReaderID.getText(), "")) {
            LibraryManager.getListReader().remove(rdIsChooseByOperator.getReaderID());
            ManageAccount.getListAccountOfReaderByID().remove(rdIsChooseByOperator.getReaderID());

            rdIsChooseByOperator.setReaderID(upDateReaderID.getText());
            LibraryManager.getListReader().put(upDateReaderID.getText(), rdIsChooseByOperator);
            ManageAccount.getListAccountOfReaderByID().put(upDateReaderID.getText(), rdIsChooseByOperator.getReaderAccount());

            upDateReaderID.clear();
        }

        if (upDateReaderSex.getValue() != null) {
            if (upDateReaderSex.getValue().toString().equals("Male")) {
                rdIsChooseByOperator.setPersonSex(true);
            }
            upDateReaderSex.getItems().clear();
        }

    }


    /**
     * thêm sách
     */

    @FXML
    private AnchorPane addBookOnOperator;
    @FXML
    private TextField addDesciptionBookOnListBookView;

    @FXML
    private TextField addIdBookOnListBookView;

    @FXML
    private TextField addLanguageBookOnListBookView;

    @FXML
    private TextField addLinkImageBookOnListBookView;

    @FXML
    private TextField addAuthorBookOnListBookView;

    @FXML
    private TextField addPageNumBookOnListBookView;

    @FXML
    private TextField addPubDateBookOnListBookView;

    @FXML
    private TextField addPublisherBookOnListBookView;

    @FXML
    private TextField addQuantityBookOnListBookView;

    @FXML
    private TextField addTitleBookOnListBookView;

    @FXML
    private Button confirmAddBookOnListBookView;

    @FXML
    private Button cancelAddBook;

    @FXML
    void actionAddBook(ActionEvent event) {
        if (event.getSource() == confirmAddBookOnListBookView) {
            if (addTitleBookOnListBookView.getText() == null
                    || addIdBookOnListBookView.getText() == null
                    || addAuthorBookOnListBookView == null
                    || addPublisherBookOnListBookView.getText() == null
                    || addQuantityBookOnListBookView.getText() == null
                    || addPubDateBookOnListBookView.getText() == null
                    || addDesciptionBookOnListBookView.getText() == null
                    || addLanguageBookOnListBookView.getText() == null
                    || addPageNumBookOnListBookView.getText() == null
                    || addLinkImageBookOnListBookView.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Please enter all the information fully !");
                // Hiển thị cảnh báo
                alert.showAndWait();
                alert.close();
            } else {
                if (ManageBook.getBooks().containsKey(addTitleBookOnListBookView.getText())) {
                    Book book = ManageBook.getBooks().get(addTitleBookOnListBookView.getText());
                    book.setNumberBook(book.getNumberBook()
                            + Integer.parseInt(addQuantityBookOnListBookView.getText()));
                    ManageBook.saveBooksToFile();
                } else {
                    Book book = new Book();
                    book.setTitle(addTitleBookOnListBookView.getText());
                    book.setIdBook(addIdBookOnListBookView.getText());
                    book.setAuthors(addAuthorBookOnListBookView.getText());
                    book.setNumberBook(Integer.parseInt(addQuantityBookOnListBookView.getText()));
                    book.setPublisher(addPublisherBookOnListBookView.getText());
                    book.setPublishedDate(addPubDateBookOnListBookView.getText());
                    book.setImageLinks(addLinkImageBookOnListBookView.getText());
                    book.setDescription(addDesciptionBookOnListBookView.getText());
                    book.setLanguage(addLanguageBookOnListBookView.getText());
                    book.setPageCount(Integer.parseInt(addPageNumBookOnListBookView.getText()));
                    ManageBook.getBooks().put(book.getTitle(), book);
                    ManageBook.saveBooksToFile(book);

                }
                tableBookShow();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Adding book successfully !");
                // Hiển thị cảnh báo
                alert.showAndWait();
                alert.close();
                listBookOnLibrary.setVisible(true);
                addBookOnOperator.setVisible(false);
            }
        } else if (event.getSource() == cancelAddBook) {
            listBookOnLibrary.setVisible(true);
            addBookOnOperator.setVisible(false);
        }

    }

    @FXML
    void addBookForOperator(ActionEvent event) {
        readerDetail.setVisible(false);
        HomeViewLibrary.setVisible(false);
        listReaderView.setVisible(false);
        listBookOnLibrary.setVisible(false);
        listOfBookBorrowers.setVisible(false);
        upDateReader.setVisible(false);
        addBookOnOperator.setVisible(true);
    }


    @FXML
    private TextField detailAuthorBook;

    @FXML
    private TextField detailDecriptionBook;

    @FXML
    private TextField detailIdBook;

    @FXML
    private ImageView detailImageBook;

    @FXML
    private TextField detailLanguageBook;

    @FXML
    private TextField detailPageNumBook;

    @FXML
    private TextField detailPublishedDateBook;

    @FXML
    private TextField detailPublisherBook;

    @FXML
    private TextField detailQuantityBook;

    @FXML
    private TextField detailTitleBook;

    @FXML
    private AnchorPane detailBook;

    public void getDetailViewOfBook() throws SQLException {
        Book bo = tableListBookView.getSelectionModel().getSelectedItem();
        int num = tableListBookView.getSelectionModel().getSelectedIndex();
        if (num >= 0) {
            detailAuthorBook.setText(bo.getAuthors());
            detailTitleBook.setText(bo.getTitle());
            detailIdBook.setText(bo.getIdBook());
            detailLanguageBook.setText(bo.getLanguage());
            detailDecriptionBook.setText(bo.getDescription());
            detailQuantityBook.setText(bo.getNumberBook() + "");
            detailPublisherBook.setText(bo.getPublisher());
            detailPublishedDateBook.setText(bo.getPublishedDate());
            detailPageNumBook.setText(bo.getPageCount() + "");
            loadAndShowImage(bo, detailImageBook);

            detailAuthorBook.setEditable(false);
            detailTitleBook.setEditable(false);
            detailIdBook.setEditable(false);
            detailLanguageBook.setEditable(false);
            detailDecriptionBook.setEditable(false);
            detailQuantityBook.setEditable(false);
            detailPublisherBook.setEditable(false);
            detailPublishedDateBook.setEditable(false);
            detailPageNumBook.setEditable(false);

            detailBook.setVisible(true);

            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            addBookOnOperator.setVisible(false);
            readerDetail.setVisible(false);
            detailLoan.setVisible(false);
        }
    }

    /**
     * hiện chi tiết phần phiếu mượn
     */
    @FXML
    private AnchorPane detailLoan;

    @FXML
    private TextField detailLoanBorrowDate;

    @FXML
    private TextField detailLoanID;

    @FXML
    private ImageView detailLoanImageReader;

    @FXML
    private TextField detailLoanNameReader;

    @FXML
    private TextField detailLoanQuantity;

    @FXML
    private TextField detailLoanReaderID;

    @FXML
    private TextField detailLoanReturnSchedule;

    @FXML
    private ImageView detailLoanimageBook;

    @FXML
    private TextField detailLoannameBook;

    public void getDetailLoan() throws SQLException {
        BookLoanAndReturnSlip bl = tableListBookBorrow.getSelectionModel().getSelectedItem();
        int n = tableListBookBorrow.getSelectionModel().getSelectedIndex();
        if (n >= 0) {

            detailLoanBorrowDate.setText(bl.getBorrowedBookDate().stringDate());
            detailLoanID.setText(bl.getCouponCode());
            detailLoanNameReader.setText(bl.getFullNameOfBorrower());
            detailLoanQuantity.setText(bl.getLoanAmount() + "");
            detailLoanReaderID.setText(bl.getIdCard());
            detailLoanReturnSchedule.setText(bl.getBookReturnSchedule().stringDate());
            detailLoannameBook.setText(bl.getNameBook());
            Book bo = ManageBook.getBooks().get(bl.getNameBook());
            loadAndShowImage(bo, detailLoanimageBook);
            Reader rd = LibraryManager.getListReader().get(bl.getIdCard());
            Image a = LoadImage(rd);
            detailLoanImageReader.setImage(a);

            detailLoanBorrowDate.setEditable(false);
            detailLoanID.setEditable(false);
            detailLoanNameReader.setEditable(false);
            detailLoanQuantity.setEditable(false);
            detailLoanReaderID.setEditable(false);
            detailLoanReturnSchedule.setEditable(false);
            detailLoannameBook.setEditable(false);

            detailLoan.setVisible(true);


            detailBook.setVisible(false);
            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            addBookOnOperator.setVisible(false);
            readerDetail.setVisible(false);
        }
    }

}
