package org.model.librarian;

import org.model.account.Account;
import org.model.person.*;
import org.model.reader.BookLoanAndReturnSlip;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.model.GlobalState.pathToDataBookLoanRequest;
import static org.model.operator.LibraryManager.checkFile;

public class Librarian extends Person {
    private String idLibrarian;         // Mã thủ thư
    private Account accountLibrarian;
    public static HashMap<String, List<BookLoanAndReturnSlip>> listBookLoanRequest
            = new HashMap<>();
    public static HashMap<String, List<BookLoanAndReturnSlip>> listBookLoaned
            = new HashMap<>();

    public Librarian(PersonName personName, Address personAddress,
                     Gmail personGmail, PhoneNumber personPhone,
                     boolean personSex, Date personDateBirth, String idLibrarian) {
        super(personName, personAddress, personGmail, personPhone, personSex, personDateBirth);
        this.idLibrarian = idLibrarian;
    }

    public static void saveLoanAndRequestToDataBase() {
        File file = new File(pathToDataBookLoanRequest);
        if (checkFile(file)) {
            try (FileWriter fw = new FileWriter(file)) {
                for (String key : listBookLoanRequest.keySet()) {
                    List<BookLoanAndReturnSlip> list = listBookLoanRequest.get(key);
                    for (BookLoanAndReturnSlip slip : list) {
                        fw.write(slip.getIdCard());
                        fw.write("\t");
                        fw.write(slip.getCouponCode());
                        fw.write("\t");
                        fw.write(slip.getNameBook());
                        fw.write("\t");
                        fw.write(slip.getLoanAmount() + "");
                        fw.write("\t");
                        fw.write(slip.getBorrowedBookDate().stringDate());
                        fw.write("\t");
                        fw.write(slip.getBookReturnSchedule().stringDate());
                        fw.write("\t");
                        fw.write(slip.getBookReturnSchedule().stringDate());
                        fw.write("\t");
                        if (slip.isStatusLoan()) fw.write("TRUE");
                        else fw.write("FALSE");
                        fw.write("\n");
                    }
                }

                for (String key : listBookLoaned.keySet()) {
                    List<BookLoanAndReturnSlip> list = listBookLoaned.get(key);
                    for (BookLoanAndReturnSlip slip : list) {
                        fw.write(slip.getIdCard());
                        fw.write("\t");
                        fw.write(slip.getCouponCode());
                        fw.write("\t");
                        fw.write(slip.getNameBook());
                        fw.write("\t");
                        fw.write(slip.getLoanAmount() + "");
                        fw.write("\t");
                        fw.write(slip.getBorrowedBookDate().stringDate());
                        fw.write("\t");
                        fw.write(slip.getBookReturnSchedule().stringDate());
                        fw.write("\t");
                        fw.write(slip.getBookReturnSchedule().stringDate());
                        fw.write("\t");
                        if (slip.isStatusLoan()) fw.write("TRUE");
                        else fw.write("FALSE");
                        fw.write("\n");
                    }
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(Librarian.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }
    }


    public static void saveLoanAndRequestToDataBase(BookLoanAndReturnSlip slip) {
        File file = new File(pathToDataBookLoanRequest);
        if (checkFile(file)) {
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(slip.getIdCard());
                fw.write("\t");
                fw.write(slip.getCouponCode());
                fw.write("\t");
                fw.write(slip.getNameBook());
                fw.write("\t");
                fw.write(slip.getLoanAmount() + "");
                fw.write("\t");
                fw.write(slip.getBorrowedBookDate().stringDate());
                fw.write("\t");
                fw.write(slip.getBookReturnSchedule().stringDate());
                fw.write("\t");
                fw.write(slip.getBookReturnSchedule().stringDate());
                fw.write("\t");
                if (slip.isStatusLoan()) fw.write("TRUE");
                else fw.write("FALSE");
                fw.write("\n");
            } catch (IOException e) {
                Logger logger = Logger.getLogger(Librarian.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }

    }


    public static BookLoanAndReturnSlip findSlip(String readerCard, String coupon) {
        List<BookLoanAndReturnSlip> list = listBookLoanRequest.get(readerCard);
        for (BookLoanAndReturnSlip slip : list) {
            if (Objects.equals(slip.getCouponCode(), coupon)) {
                return slip;
            }
        }
        return null;
    }

    public static HashMap<String, List<BookLoanAndReturnSlip>> getListBookLoanRequest() {
        return listBookLoanRequest;
    }

    public static HashMap<String, List<BookLoanAndReturnSlip>> getListBookLoaned() {
        return listBookLoaned;
    }

    // Getter và Setter cho idLibrarian
    public String getIdLibrarian() {
        return idLibrarian;
    }

    public void setIdLibrarian(String idLibrarian) {
        this.idLibrarian = idLibrarian;
    }

    // Phương thức hiển thị thông tin thủ thư
    public void printInfoLibrarian() {
        System.out.printf("Librarian ID: %-20s", idLibrarian);
        super.printInformationOfPerson();
    }

    public Account getAccountLibrarian() {
        return accountLibrarian;
    }

    public void setAccountLibrarian(Account accountLibrarian) {
        this.accountLibrarian = accountLibrarian;
    }


    public static List<BookLoanAndReturnSlip> getListLoanOfReader(String id) {
        return listBookLoanRequest.get(id);
    }
}