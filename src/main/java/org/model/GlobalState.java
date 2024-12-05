package org.model;

import org.model.book.Book;
import org.model.reader.Reader;

import java.util.HashMap;

public class GlobalState {
    private static GlobalState instance;
    private int saveChoose;
    private static Reader readerLoggedIn;      // người đăng nhập
    private Reader readerSignUp; // nguoi dang  ki
    private static int libraryNum = 0;
    private static Book bookIsSelectedByOperator;
    private static Reader readerIsChoosed;
    private static HashMap<Object, Object> listGmails;

    // Các hằng số cho saveChoose
    public static final int CHOOSEREADER = 1;
    public static final int CHOOSELIBRARY = 2;
    public static final int CHOOSEMANGER = 3;

    public static final String pathToDataListAccount
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\listAccount.txt";

    public static final String pathToDataListBook
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\listBook.txt";

    public static final String pathToDataBookLoanRequest
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\bookLoanRequest.txt";

    public static final String pathToDataListBorrowedBook
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\listBorrowedBook.txt";

    public static final String getPathToDataListReader
            = "D:\\Library-Management\\src\\main\\resources\\filetxt\\listReader.txt";

    private String pathtoimageCanh = "file:///D:/Library-Management/src/main/resources/image/canh.png";
    private String pathtoimageDuong = "file:///D:/Library-Management/src/main/resources/image/duong.jpg";


    public static HashMap<Object, Object> getListGmails() {
        return listGmails;
    }

    public static void setListGmails(HashMap<Object, Object> listGmails) {
        GlobalState.listGmails = listGmails;
    }


    public static Reader getReaderIsChoosed() {
        return readerIsChoosed;
    }

    public static void setReaderIsChoosed(Reader readerIsChoosed) {
        GlobalState.readerIsChoosed = readerIsChoosed;
    }


    public static Book getBookIsSelectedByOperator() {
        return bookIsSelectedByOperator;
    }

    public static void setBookIsSelectedByOperator(Book bookIsSelectedByOperator1) {
        bookIsSelectedByOperator = bookIsSelectedByOperator1;
    }

    public Reader getReaderSignUp() {
        return readerSignUp;
    }

    public void setReaderSignUp(Reader readerSignUp) {
        this.readerSignUp = readerSignUp;
    }

    public static Reader getReaderLoggedIn() {
        return readerLoggedIn;
    }

    public void setReaderLoggedIn(Reader readerLoggedIn) {
        this.readerLoggedIn = readerLoggedIn;
    }

    private GlobalState() {
    }

    public static GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public int getSaveChoose() {
        return saveChoose;
    }

    public void setSaveChoose(int saveChoose) {
        this.saveChoose = saveChoose;
    }

    public static void setLibraryNum(int libraryNum) {
        GlobalState.libraryNum = libraryNum;
    }

    public static int getLibraryNum() {
        return libraryNum;
    }

    public String getPathtoimageCanh() {
        return pathtoimageCanh;
    }

    public String getPathtoimageDuong() {
        return pathtoimageDuong;
    }
}
