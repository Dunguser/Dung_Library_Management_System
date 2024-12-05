package org.model.reader;

import org.model.account.Account;
import org.model.book.BookLoan;
import org.model.operator.LibraryManager;
import org.model.person.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.model.operator.LibraryManager.checkFile;


public class Reader extends Person {
    private String readerID;                                                                    //ma ban doc
    private Account readerAccount;                                                              //acc muon sach
    private HashMap<String, BookLoan> listBorrowedBook; // map chứa key là mã phiếu , value là sách đã mượn
    private int amountBorrowedBook;

    //constructor
    public Reader(PersonName personName, Address personAddress, Gmail personGmail,
                  PhoneNumber personPhone, boolean personSex,
                  Date personDateBirth, String readerID) {
        super(personName, personAddress, personGmail, personPhone, personSex, personDateBirth);
        this.readerID = readerID;
        listBorrowedBook = new HashMap<>();
    }

    public Reader() {
    }

    //cac ham set, ge

    public Account getReaderAccount() {
        return readerAccount;
    }

    public void setReaderAccount(Account readerAccount) {
        this.readerAccount = readerAccount;
    }

    public String getReaderID() {
        return readerID;
    }

    public void setReaderID(String readerID) {
        this.readerID = readerID;
    }

    public int getAmountBorrowedBook() {
        return amountBorrowedBook;
    }

    public void setAmountBorrowedBook(int amountBorrowedBook) {
        this.amountBorrowedBook = amountBorrowedBook;
    }

    public HashMap<String, BookLoan> getListBorrowedBook() {
        if(listBorrowedBook == null){
            return listBorrowedBook = new HashMap<>();
        }
        return listBorrowedBook;
    }


    public static String getCurrentTimeAsString() {                                         // cai nay la de t test thôi, ko cần quan tâm lắm đâu
        LocalDateTime currentTime = LocalDateTime.now();                                    // Lấy thời gian hiện tại
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");        // Định dạng thời gian theo kiểu yyyyMMddHHmmss
        return currentTime.format(formatter);                                               // Trả về thời gian hiện tại dưới dạng chuỗi
    }
}


