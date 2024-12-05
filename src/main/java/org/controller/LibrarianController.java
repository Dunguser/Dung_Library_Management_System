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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.model.GlobalState;
import org.model.book.Book;
import org.model.book.BookLoan;
import org.model.book.ManageBook;
import org.model.googlebookapi.GoogleBooksAPI;
import org.model.librarian.Librarian;
import org.model.librarian.LoanManagement;
import org.model.operator.LibraryManager;
import org.model.reader.BookLoanAndReturnSlip;
import org.model.reader.Reader;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import static org.controller.LoginController.pathToChooseRoleView;
import static org.controller.ReaderController.loadAndShowImage;
import static org.controller.SignUpController.LoadImage;

public class LibrarianController implements Initializable {

    private Reader clickReader;
    private BookLoanAndReturnSlip clickRequestSlip;
    private Book clickBook;
    private List<AnchorPane> listPane;
    private GoogleBooksAPI googleBooksAPI;
    @FXML
    private VBox lineBookAPI;
    @FXML
    private TableView<Book> tableListBookView;
    @FXML
    private TableView<BookLoanAndReturnSlip> tableRequestBorrow;
    @FXML
    private Button loanShipButton;

    @FXML
    private Button BookShelf;

    @FXML
    private ImageView imageBookOnListBookView;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> ColumnIdReaderOnBorrow;

    @FXML
    private Button DeleteOnListBookView;

    @FXML
    private Button HomeButton;

    @FXML
    private AnchorPane HomeViewLibrary;

    @FXML
    private Button ListReaderButton;

    @FXML
    private AnchorPane mainFormLibrarymanager;
    @FXML
    private Label textInfoAuthorBook;

    @FXML
    private Label textInfoDescribeBook;

    @FXML
    private Label textInfoIdBook;

    @FXML
    private Label textInfoPage;

    @FXML
    private Label textInfoPublishedDate;

    @FXML
    private Label textInfoPublisherBook;

    @FXML
    private Label textInfoQuantityBook;

    @FXML
    private Label textInfoTitleBook;

    @FXML
    private TextField readerOnTableBorrow;

    @FXML
    private Button SignOut;

    @FXML
    private Button enterSaveUpdate;

    @FXML
    private Button enterAddBook;
    @FXML
    private Button enterDeleteBook;

    @FXML
    private Button enterShowInfoBook;

    @FXML
    private Button enterUpdateBook;

    @FXML
    private Button closeBuOnHome;
    @FXML
    private Button enterAllowBorrow;

    @FXML
    private Button enterConfirm;

    @FXML
    private TextField enterNewAuthorBook;

    @FXML
    private TextField enterNewIdBook;

    @FXML
    private TextField enterNewPubDate;

    @FXML
    private TextField enterNewPublisher;

    @FXML
    private TextField enterNewQuantityBook;

    @FXML
    private TextField enterNewTitleBook;
    @FXML
    private TextField enterNewDescrib;
    @FXML
    private TextField enterNewImage;

    @FXML
    private TextField enterNewLang;

    @FXML
    private TextField enterNewPageNum;

    @FXML
    private TextField enterUpdateAuthor;
    @FXML
    private TextField enterUpdateId;

    @FXML
    private TextField enterUpdateDescribe;

    @FXML
    private TextField enterUpdateLanguage;

    @FXML
    private TextField enterUpdatePageNum;

    @FXML
    private TextField enterUpdatePubDate;

    @FXML
    private TextField enterUpdatePublisher;

    @FXML
    private TextField enterUpdateQuantity;

    @FXML
    private TextField enterUpdateTitle;

    @FXML
    private TableColumn<Reader, String> colAddressOnReaderView;

    @FXML
    private TableColumn<Book, String> colAuthorBookOnListBookView;
    @FXML
    private TableColumn<Book, String> colPublishedDateOnListBookView;

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
    private TableColumn<BookLoanAndReturnSlip, String> colRequestBorrowDate;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> colRequestID;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> colRequestQuantity;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> colRequestReturnDate;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> colRequestTitleBook;

    @FXML
    private TableColumn<BookLoanAndReturnSlip, String> colResquestFullName;

    @FXML
    private AnchorPane addBookView;

    @FXML
    private AnchorPane infoBookView;

    @FXML
    private AnchorPane listBookOnLibrary;

    @FXML
    private AnchorPane listOfBookBorrowers;

    @FXML
    private AnchorPane listReaderView;
    @FXML
    private AnchorPane updateView;
    @FXML
    private Button minimizeBuOnHome;

    @FXML
    private TextField searchOnListReaderView;

    @FXML
    private TableView<BookLoanAndReturnSlip> tableListBookBorrow;

    @FXML
    private TableView<Reader> tableViewListReader = new TableView<>();

    @FXML
    private Button updateOnListBookView;
    @FXML
    private Button exitInfo;
    @FXML
    private Button exitUpdate;

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
    private TextField enterSearchBook;

    @FXML
    private AnchorPane readerDetail;

    @FXML
    public Label NameOfLibrary = new Label();

    @FXML
    private ImageView imageLibararianOnWelcom;


    /******************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableListBookBorrow.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Nháy đúp chuột
                try {
                    getDetailLoan();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        googleBooksAPI = new GoogleBooksAPI();
        clickReader = null;
        clickRequestSlip = null;
        clickBook = null;
        listPane = Arrays.asList(HomeViewLibrary, listReaderView, listBookOnLibrary,
                listOfBookBorrowers, addBookView, infoBookView,
                updateView, searchBookAPIView);
        if (GlobalState.getLibraryNum() == 1) {
            NameOfLibrary.setText("Lê Tuấn Cảnh");
        } else {
            NameOfLibrary.setText("Nguyễn Nho Dương");
        }

        addReaderShow();  // to show immidiate when we proceed dashboard
        tableBookShow();
        setBookLoanedView();
        setBookLoanRequestView();

        if (GlobalState.getLibraryNum() == 1) {
            imageLibararianOnWelcom.setImage(new
                    Image(GlobalState.getInstance().getPathtoimageCanh()));
        } else {
            imageLibararianOnWelcom.setImage(new
                    Image(GlobalState.getInstance().getPathtoimageDuong()));
        }

    }

    private void setVisiblePane(AnchorPane pane) {
        boolean check = false;
        for (AnchorPane anchorPane : listPane) {
            if (pane != addBookView && pane != infoBookView && pane != updateView) {
                anchorPane.setVisible(anchorPane == pane);
            } else {
                check = true;
            }
        }
        if (check) {
            if (pane == addBookView) {
                pane.setVisible(true);
                updateView.setVisible(false);
                infoBookView.setVisible(false);
            } else if (pane == infoBookView) {
                pane.setVisible(true);
                updateView.setVisible(false);
                addBookView.setVisible(false);
            } else {
                pane.setVisible(true);
                infoBookView.setVisible(false);
                addBookView.setVisible(false);
            }
        }


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
        Stage stage = (Stage) mainFormLibrarymanager.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * chuyen doi form
     */
    public void switchForm(ActionEvent event) {
        if (event.getSource() == HomeButton) {

            setVisiblePane(HomeViewLibrary);
            // Đặt màu nền cho nút đang chọn
            HomeButton.setStyle("-fx-background-color: " +
                    "linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            // Đặt lại màu nền cho các nút khác
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");
            loanShipButton.setStyle("-fx-background-color: transparent;");
            resourceBook.setStyle("-fx-background-color: transparent;");
            setBookLoanRequestView();
            readerDetail.setVisible(false);
        } else if (event.getSource() == ListReaderButton) {
            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(true);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            readerDetail.setVisible(false);

            ListReaderButton.setStyle("-fx-background-color: " +
                    "linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");
            loanShipButton.setStyle("-fx-background-color: transparent;");
            resourceBook.setStyle("-fx-background-color: transparent;");
            // to become update once you click add reader
            addReaderShow();


        } else if (event.getSource() == BookShelf) {

            setVisiblePane(listBookOnLibrary);
            BookShelf.setStyle("-fx-background-color:" +
                    " linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            loanShipButton.setStyle("-fx-background-color: transparent;");
            resourceBook.setStyle("-fx-background-color: transparent;");
            readerDetail.setVisible(false);
            tableBookShow();

        } else if (event.getSource() == loanShipButton) {

            setVisiblePane(listOfBookBorrowers);
            loanShipButton.setStyle("-fx-background-color:" +
                    " linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");
            resourceBook.setStyle("-fx-background-color: transparent;");
            readerDetail.setVisible(false);
            setBookLoanedView();
        } else if (event.getSource() == resourceBook) {
            setVisiblePane(searchBookAPIView);
            resourceBook.setStyle("-fx-background-color:" +
                    " linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            HomeButton.setStyle("-fx-background-color: transparent;");
            ListReaderButton.setStyle("-fx-background-color: transparent;");
            BookShelf.setStyle("-fx-background-color: transparent;");
            loanShipButton.setStyle("-fx-background-color: transparent;");

        }
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
        readersData = addListReader();

        colReaderIDOnReaderView.setCellValueFactory(new PropertyValueFactory<>("readerID"));

        colFirstNameOnReaderView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPersonName().getFirstName()));
        colLastNameOnReaderView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPersonName().getLastName()));

        colBirthOnReaderView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPersonDatebirth().stringDate()));
        colSexOnReaderView.setCellValueFactory(new PropertyValueFactory<>("personSex"));

        colPhoneOnReaderView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPersonPhone().getPhoneNum()));

        colGamilOnReaderView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPersonGmail().getGmail()));
        colAddressOnReaderView.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPersonAddress2()));

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
                } else if (reader.getPersonName().getFirstName()
                        .toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonName().getLastName()
                        .toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonPhone().getPhoneNum()
                        .toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (reader.getPersonGmail().getGmail()
                        .toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return reader.getPersonAddress2()
                        .toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Reader> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableViewListReader.comparatorProperty());
        tableViewListReader.setItems(sortedData);
    }

    public void getInfoBook(Book book) {
        textInfoTitleBook.setText("Title : " + book.getTitle());
        textInfoIdBook.setText("ID : " + book.getIdBook());
        textInfoAuthorBook.setText("Author : " + book.getAuthors());
        textInfoQuantityBook.setText("Quantity : " + book.getNumberBook());
        textInfoPage.setText("Page Number : " + book.getPageCount());
        textInfoPublisherBook.setText("Publisher : " + book.getPublisher());
        textInfoPublishedDate.setText("Published Date : " + book.getPublishedDate());
        textInfoDescribeBook.setText("Description : " + book.getDescription());
        loadAndShowImage(book, imageBookOnListBookView);
    }

    /**
     * EM CHI TIET 1 READER TRONG BANG
     */
    public void getDetailViewOfReader() throws SQLException {
        if (clickReader != null) {
            detailIdReader.setText(clickReader.getReaderID());
            detailFirstNameReader.setText(clickReader.getPersonName().getFirstName());
            detailLastNameReader.setText(clickReader.getPersonName().getLastName());
            detailGamilReader.setText(clickReader.getPersonGmail().getGmail());
            detailPhoneReader.setText(clickReader.getPersonPhone().getPhoneNum());
            detailAddressReader.setText(clickReader.getPersonAddress2());
            detailBirthReader.setText(clickReader.getPersonDatebirth().stringDate());
            detailSexReader.setText(clickReader.getPersonSex());
            Image a = LoadImage(clickReader);
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
        }

    }

    /**
     * xử lí hiển thị cho book
     */
    public ObservableList<Book> bookObservableList() {
        ObservableList<Book> listBookData = FXCollections
                .observableArrayList(ManageBook.getBooks().values());
        return listBookData;
    }

    private ObservableList<Book> booksData;

    public void tableBookShow() {
        booksData = bookObservableList();
        colIdBookOnListBookView.setCellValueFactory(
                new PropertyValueFactory<>("idBook"));
        colTitleBookOnListBookView.setCellValueFactory(
                new PropertyValueFactory<>("title"));
        colAuthorBookOnListBookView.setCellValueFactory(
                new PropertyValueFactory<>("authors"));
        colPublisherOnListBookView.setCellValueFactory(
                new PropertyValueFactory<>("publisher"));
        colQuantityOnListBookView.setCellValueFactory(
                new PropertyValueFactory<>("numberBook"));
        colPublishedDateOnListBookView.setCellValueFactory(
                new PropertyValueFactory<>("publishedDate"));
        tableListBookView.setItems(booksData);
    }

    @FXML
    private void setupSearchBookFeature() {
        FilteredList<Book> filteredData = new FilteredList<>(booksData, b -> true);
        enterSearchBook.textProperty().addListener((observable, oldValue, newValue) -> {
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

    @FXML
    void selectBook(MouseEvent event) {
        clickBook = tableListBookView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() % 2 == 1) {
            enterShowInfoBook.setVisible(true);
            enterUpdateBook.setVisible(true);
            enterDeleteBook.setVisible(true);
        } else if (event.getClickCount() % 2 == 0) {
            enterShowInfoBook.setVisible(false);
            enterUpdateBook.setVisible(false);
            enterDeleteBook.setVisible(false);
        }
    }

    /**
     * hiển thị những người mượn sách
     */
    private ObservableList<BookLoanAndReturnSlip> listBookLoanedSlip;

    public void setBookLoanedView() {
        tableListBookBorrow.getItems().clear();

        listBookLoanedSlip = FXCollections.observableArrayList();
        for (String id : Librarian.getListBookLoaned().keySet()) {

            listBookLoanedSlip.addAll(Librarian.getListBookLoaned().get(id));

            ColumnIdReaderOnBorrow.setCellValueFactory(
                    new PropertyValueFactory<>("idCard"));

            columnNameReaderOnBorrow.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullNameOfBorrower()));
            columnTitleBookOnBorrow.setCellValueFactory(
                    new PropertyValueFactory<>("nameBook"));
            columnQuantityOnBorrow.setCellValueFactory(
                    new PropertyValueFactory<>("loanAmount"));
            columnBorrowDateOnBorrow.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue()
                            .getBorrowedBookDate().stringDate()));
            columnReturnDateOnBorrow.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue()
                            .getBookReturnSchedule().stringDate()));
            columnStatusOnBorrow.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue()
                            .getStatus()));

            tableListBookBorrow.setItems(listBookLoanedSlip);

        }
    }

    @FXML
    private void setupSearchBorrowerFeature() {
        FilteredList<BookLoanAndReturnSlip> filteredData
                = new FilteredList<>(listBookLoanedSlip, b -> true);
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


    private ObservableList<BookLoanAndReturnSlip> listBookLoanRequestSlip;

    public void setBookLoanRequestView() {
        tableRequestBorrow.getItems().clear();
        listBookLoanRequestSlip = FXCollections.observableArrayList();
        for (String id : Librarian.getListBookLoanRequest().keySet()) {

            listBookLoanRequestSlip.addAll(Librarian.getListBookLoanRequest().get(id));

            colRequestID.setCellValueFactory(new PropertyValueFactory<>("idCard"));

            colResquestFullName.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getFullNameOfBorrower()));
            colRequestTitleBook.setCellValueFactory(new PropertyValueFactory<>("nameBook"));
            colRequestQuantity.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));
            colRequestBorrowDate.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getBorrowedBookDate().stringDate()));
            colRequestReturnDate.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getBookReturnSchedule().stringDate()));
            tableRequestBorrow.setItems(listBookLoanRequestSlip);
        }
    }

    public void processUpdateBook() {
        if (!Objects.equals(enterUpdateTitle.getText(), "")) {
            ManageBook.getBooks().remove(clickBook.getTitle());
            clickBook.setTitle(enterUpdateTitle.getText());
            ManageBook.getBooks().put(enterUpdateTitle.getText(), clickBook);
            enterUpdateTitle.clear();
        }
        if (!Objects.equals(enterUpdateId.getText(), "")) {
            clickBook.setIdBook(enterUpdateId.getText());
            enterUpdateId.clear();
        }
        if (!Objects.equals(enterUpdateAuthor.getText(), "")) {
            clickBook.setAuthors(enterUpdateAuthor.getText());
            enterUpdateAuthor.clear();
        }
        if (!enterUpdateQuantity.getText().isEmpty()) {
            clickBook.setNumberBook(Integer.parseInt(enterUpdateQuantity.getText()));
            enterUpdateQuantity.clear();
        }
        if (!enterUpdatePageNum.getText().isEmpty()) {
            clickBook.setPageCount(Integer.parseInt(enterUpdatePageNum.getText()));
            enterUpdatePageNum.clear();
        }
        if (!Objects.equals(enterUpdatePubDate.getText(), "")) {
            clickBook.setPublishedDate(enterUpdatePubDate.getText());
            enterUpdatePubDate.clear();
        }
        if (!Objects.equals(enterUpdatePublisher.getText(), "")) {
            clickBook.setPublisher(enterUpdatePublisher.getText());
            enterUpdatePublisher.clear();
        }
        if (!Objects.equals(enterUpdateDescribe.getText(), "")) {
            clickBook.setDescription(enterUpdateDescribe.getText());
            enterUpdateDescribe.clear();
        }
        if (!Objects.equals(enterUpdateLanguage.getText(), "")) {
            clickBook.setLanguage(enterUpdateLanguage.getText());
            enterUpdateLanguage.clear();
        }
        ManageBook.saveBooksToFile();
    }

    @FXML
    void actionTableReader(MouseEvent event) throws SQLException {
        clickReader = tableViewListReader.getSelectionModel().getSelectedItem();
        getDetailViewOfReader();
    }

    @FXML
    void actionTableRequest(MouseEvent event) {
        clickRequestSlip = tableRequestBorrow.getSelectionModel().getSelectedItem();
        System.out.println("1");
        if (event.getClickCount() % 2 == 1) {
            enterAllowBorrow.setVisible(true);
        } else if (event.getClickCount() % 2 == 0) {
            enterAllowBorrow.setVisible(false);
            tableRequestBorrow.getSelectionModel().clearSelection();
        }
    }

    public void setShowResultSearchAPI() {

        lineBookAPI.getChildren().clear();
        String result = googleBooksAPI.searchAndParseBooks(searchBookAPI.getText());
        String[] part = result.split("\n");
        for (int i = 0; i < part.length; i++) {

            Label titleLabel = new Label(part[i]);
            titleLabel.setStyle("-fx-font-size: 16px;" +
                    " -fx-font-weight: bold;  -fx-border-color: #753030;");
            titleLabel.setPrefHeight(65);
            // Thêm thông tin vào VBox
            lineBookAPI.getChildren().addAll(titleLabel);
            // Sự kiện khi click vào hàng sách
            titleLabel.setOnMouseClicked(event -> {
                setVisiblePane(addBookView);
                String[] infoBook = titleLabel.getText().split("\t");
                enterNewQuantityBook.setText(infoBook[0]);
                enterNewTitleBook.setText(infoBook[1]);
                enterNewIdBook.setText(infoBook[2]);
                enterNewAuthorBook.setText(infoBook[3]);
                enterNewPublisher.setText(infoBook[4]);
                enterNewPubDate.setText(infoBook[5]);
                enterNewDescrib.setText(infoBook[6]);
                enterNewImage.setText(infoBook[7]);
                enterNewPageNum.setText(infoBook[8]);
                enterNewLang.setText(infoBook[9]);

            });


        }
    }

    @FXML
    private AnchorPane searchBookAPIView;
    @FXML
    private Button resourceBook;

    @FXML
    private TextField searchBookAPI;
    @FXML
    private Button buttonSearchAPI;
    @FXML
    private Button exitAddBook;

    @FXML
    void actionLibrarian(ActionEvent event) {
        if (event.getSource() == enterAllowBorrow) {
            if (ManageBook.getBooks().get(clickRequestSlip.getNameBook()).getNumberBook()
                    >= clickRequestSlip.getLoanAmount()) {
                clickRequestSlip.setStatusLoan(true);
                Librarian.getListBookLoanRequest().get(clickRequestSlip.getIdCard())
                        .remove(clickRequestSlip);
                Librarian.getListBookLoaned().computeIfAbsent(clickRequestSlip.getIdCard(),
                        value -> new ArrayList<>()).add(clickRequestSlip);
                Librarian.saveLoanAndRequestToDataBase();
                setBookLoanRequestView();
                Book bookBorrow = ManageBook.getBooks().get(clickRequestSlip.getNameBook());
                BookLoan bookLoan = new BookLoan(bookBorrow.getIdBook(),
                        bookBorrow.getTitle(),
                        clickRequestSlip.getCouponCode(),
                        clickRequestSlip.getLoanAmount(),
                        clickRequestSlip.getBorrowedBookDate(),
                        clickRequestSlip.getBookReturnSchedule());

                Reader reader = LibraryManager.getListReader().get(clickRequestSlip.getIdCard());
                LoanManagement.addBorrower(reader.getReaderID(), bookLoan);
                reader.getListBorrowedBook().put(bookLoan.getCouponCode(), bookLoan);
                bookBorrow.setNumberBook(bookBorrow.getNumberBook() - clickRequestSlip.getLoanAmount());
                ManageBook.saveBooksToFile();
                tableBookShow();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Not Enough Books Available");
                // Hiển thị thông báo
                alert.showAndWait();
            }
        } else if (event.getSource() == enterAddBook) {
            setVisiblePane(addBookView);
        } else if (event.getSource() == enterShowInfoBook) {
            getInfoBook(clickBook);
            setVisiblePane(infoBookView);
        } else if (event.getSource() == enterUpdateBook) {
            enterUpdateTitle.setPromptText(clickBook.getTitle());
            enterUpdateId.setPromptText(clickBook.getIdBook());
            enterUpdateAuthor.setPromptText(clickBook.getAuthors());
            enterUpdateQuantity.setPromptText(clickBook.getNumberBook() + "");
            enterUpdatePageNum.setPromptText(clickBook.getPageCount() + "");
            enterUpdatePubDate.setPromptText(clickBook.getPublishedDate());
            enterUpdatePublisher.setPromptText(clickBook.getPublisher());
            enterUpdateDescribe.setPromptText(clickBook.getDescription());
            enterUpdateLanguage.setPromptText(clickBook.getLanguage());
            setVisiblePane(updateView);

        } else if (event.getSource() == enterDeleteBook) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete this book? ");

            // Hiển thị cảnh báo và chờ người dùng chọn
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                ManageBook.getBooks().remove(clickBook.getTitle());
                tableBookShow();
                ManageBook.saveBooksToFile();
            }
        } else if (event.getSource() == enterConfirm) {
            if (enterNewTitleBook.getText() == null
                    || enterNewIdBook.getText() == null
                    || enterNewAuthorBook == null
                    || enterNewPublisher.getText() == null
                    || enterNewQuantityBook.getText() == null
                    || enterNewPubDate.getText() == null
                    || enterNewDescrib.getText() == null
                    || enterNewLang.getText() == null
                    || enterNewPageNum.getText() == null
                    || enterNewImage.getText() == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("Please enter all the information fully !");
                // Hiển thị cảnh báo
                alert.showAndWait();
                alert.close();
            } else {
                if (ManageBook.getBooks().containsKey(enterNewTitleBook.getText())) {
                    Book book = ManageBook.getBooks().get(enterNewTitleBook.getText());
                    book.setNumberBook(book.getNumberBook() +
                            Integer.parseInt(enterNewQuantityBook.getText()));
                } else {
                    Book book = new Book();
                    book.setTitle(enterNewTitleBook.getText());
                    book.setIdBook(enterNewIdBook.getText());
                    book.setAuthors(enterNewAuthorBook.getText());
                    book.setNumberBook(Integer.parseInt(enterNewQuantityBook.getText()));
                    book.setPublisher(enterNewPublisher.getText());
                    book.setPublishedDate(enterNewPubDate.getText());
                    book.setImageLinks(enterNewImage.getText());
                    book.setDescription(enterNewDescrib.getText());
                    book.setLanguage(enterNewLang.getText());
                    book.setPageCount(Integer.parseInt(enterNewPageNum.getText()));
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
              addBookView.setVisible(false);
            }
        } else if (event.getSource() == enterSaveUpdate) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to update this book? ");

            // Hiển thị cảnh báo và chờ người dùng chọn
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

            // Kiểm tra kết quả
            if (result == ButtonType.OK) {
                processUpdateBook();
                tableBookShow();
                setVisiblePane(listBookOnLibrary);
            }
            alert.close();
        } else if (event.getSource() == exitInfo) {
            setVisiblePane(listBookOnLibrary);
        } else if (event.getSource() == exitUpdate) {
            setVisiblePane(listBookOnLibrary);
        } else if (event.getSource() == exitAddBook) {
            addBookView.setVisible(false);
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

            HomeViewLibrary.setVisible(false);
            listReaderView.setVisible(false);
            listBookOnLibrary.setVisible(false);
            listOfBookBorrowers.setVisible(false);
            readerDetail.setVisible(false);
        }
    }

    @FXML
    void actionSearchAPI(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            setShowResultSearchAPI();
        }
    }

}
