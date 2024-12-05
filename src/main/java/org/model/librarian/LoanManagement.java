package org.model.librarian;

import org.model.book.BookLoan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.model.GlobalState.pathToDataListBorrowedBook;
import static org.model.operator.LibraryManager.checkFile;

public class LoanManagement {
    private static HashMap<String, BookLoan> listBorrowedBook
            = new HashMap<>(); //key là mã phiếu
    private static HashMap<String, List<String>> listUserAndBook
            = new HashMap<>(); //key là readerID , value là mã phiếu các cuốn sách họ mượn

    // Constructor
    public LoanManagement() {

    }

    public static void saveListBorrowedBookToDatabase(String readerId, BookLoan bookLoan) {
        File file = new File(pathToDataListBorrowedBook);
        if (checkFile(file)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(bookLoan.getBookId());
                bw.write("\t");
                bw.write(bookLoan.getNameBook());
                bw.write("\t");
                bw.write(bookLoan.getCouponCode());
                bw.write("\t");
                bw.write(bookLoan.getAmount() + "");
                bw.write("\t");
                bw.write(bookLoan.getBorrowedDate().stringDate());
                bw.write("\t");
                bw.write(bookLoan.getRequiredReturnDate().stringDate());
                bw.write("\t");
                bw.write(readerId);
                bw.write("\n");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }

    }

    public static void saveListBorrowedBookToDatabase() {
        File file = new File(pathToDataListBorrowedBook);
        if (checkFile(file)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String key : listUserAndBook.keySet()) {
                    List<String> list = listUserAndBook.get(key);
                    for (String cpcode : list) {
                        BookLoan bookLoan = listBorrowedBook.get(cpcode);
                        bw.write(bookLoan.getBookId());
                        bw.write("\t");
                        bw.write(bookLoan.getNameBook());
                        bw.write("\t");
                        bw.write(bookLoan.getCouponCode());
                        bw.write("\t");
                        bw.write(bookLoan.getAmount() + "");
                        bw.write("\t");
                        bw.write(bookLoan.getBorrowedDate().stringDate());
                        bw.write("\t");
                        bw.write(bookLoan.getRequiredReturnDate().stringDate());
                        bw.write("\t");
                        bw.write(key);
                        bw.write("\n");
                    }
                }

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }


    public static HashMap<String, BookLoan> getListBorrowedBook() {
        return listBorrowedBook;
    }

    public static HashMap<String, List<String>> getListUserAndBook() {
        return listUserAndBook;
    }

    public static void addBorrower(String id, BookLoan bookLoan) {
        listBorrowedBook.put(bookLoan.getCouponCode(), bookLoan);
        listUserAndBook.computeIfAbsent(id, value -> new ArrayList<>()).add(bookLoan.getCouponCode());
        saveListBorrowedBookToDatabase(id, bookLoan);
    }
}
