package org.model;

import org.model.account.Account;
import org.model.account.ManageAccount;
import org.model.book.Book;
import org.model.book.BookLoan;
import org.model.book.ManageBook;
import org.model.librarian.Librarian;
import org.model.librarian.LoanManagement;
import org.model.operator.LibraryManager;
import org.model.person.*;
import org.model.reader.BookLoanAndReturnSlip;
import org.model.reader.Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.model.GlobalState.*;
import static org.model.operator.LibraryManager.checkFile;

public class LoaderSystem {
    public static Librarian librarian1 = new Librarian(
            new PersonName("Lê Tuấn Cảnh"),
            new Address("xóm 7", "Du La", "Cẩm Chế"
                    , "Thanh Hà", "Hải Dương"),
            new Gmail("letuancanhhd@gmail.com"),
            new PhoneNumber("0986357214"),
            true,
            new Date(20, 10, 2005),
            "LB1");

    public static Librarian librarian2 = new Librarian(
            new PersonName("Nguyễn Nho Dương"),
            new Address("xóm 8", "Đồng Vinh", "Hậu Lộc"
                    , "Triệu Sơn", "Thanh Hóa"),
            new Gmail("duongnn@gmail.com"),
            new PhoneNumber("0986657214"),
            true,
            new Date(20, 12, 2005),
            "LB2");

    public static final LibraryManager libraryManager = new LibraryManager(
            new PersonName("Nguyễn Xuân Dũng"),
            new Address("79", "Khúc Thừa Dụ", "Dịch Vọng"
                    , "Cầu Giấy", "Hà Nội"),
            new Gmail("nguyenxuandung457@gmail.com"),
            new PhoneNumber("0868364133"),
            true,
            new Date(11, 7, 2005));

    // xử lí load file vào cấu trúc
    public LoaderSystem() {
        librarian1.setAccountLibrarian(
                new Account("LETUANCANH", "34tuanCAnh%%%"));
        librarian2.setAccountLibrarian(
                    new Account("NGUYENNHODUONG", "Dftret&%&$&^1234"));

        File file = new File(pathToDataListAccount);
        if (checkFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.trim().split("\t");
                    if (parts.length == 3) {
                        Account account = new Account(parts[1], parts[2]);
                        ManageAccount.getListAccountOfReaderByID().put(parts[0], account);
                        ManageAccount.getListAccountOfUsername().put(parts[1], account);
                    } else {
                        System.out.println("dong ko hop le: listAccount + LoaderSystem: "
                                + line + "so phan: " + parts.length);
                    }
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(LoaderSystem.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }

        file = new File(getPathToDataListReader);
        if (checkFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.trim().split("\t");
                    if (parts.length == 8) {
                        Reader reader = null;
                        if (parts[5].equals("Nam")) {
                            reader = new Reader(new PersonName(parts[1]), new Address(parts[2]),
                                    new Gmail(parts[3]), new PhoneNumber(parts[4]),
                                    true, new Date(parts[6]), parts[7]);
                        } else if (parts[5].equals("Nữ")) {
                            reader = new Reader(new PersonName(parts[1]), new Address(parts[2]),
                                    new Gmail(parts[3]), new PhoneNumber(parts[4]),
                                    false, new Date(parts[6]), parts[7]);
                        }
                        assert reader != null;
                        reader.setReaderAccount(ManageAccount.getListAccountOfReaderByID().get(parts[7]));
                        LibraryManager.getListReader().put(reader.getReaderID(), reader); // Thêm vào danh sách
                        LibraryManager.getListAccountOfReader().put(parts[0], reader);  // key la userName
                    }
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(LoaderSystem.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }

        file = new File(pathToDataListBook);
        if (checkFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] attributes = line.split(",");
                    if (attributes.length == 10) {
                        Book book = new Book(
                                attributes[0],                               // id
                                Integer.parseInt(attributes[1]),             // numberBook
                                attributes[2],                               // title
                                attributes[3],                               // authors
                                attributes[4],                               // publisher
                                attributes[5],                               // publishedDate
                                attributes[6],                               // description
                                attributes[7],                               // imageLinks
                                Integer.parseInt(attributes[8]),             // pageCount
                                attributes[9]                                // language
                        );
                        ManageBook.getBooks().put(book.getTitle(), book);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
            }
        }

        file = new File(pathToDataListBorrowedBook);
        if (checkFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t");
                    if (parts.length == 7) {
                        BookLoan book = new BookLoan(parts[0], parts[1], parts[2],
                                Integer.parseInt(parts[3]), new Date(parts[4]), new Date(parts[5]));
                        LoanManagement.getListBorrowedBook().put(book.getCouponCode(), book);
                        LoanManagement.getListUserAndBook().computeIfAbsent(parts[6], value ->
                                new ArrayList<>()).add(book.getCouponCode());
                        if (LibraryManager.getListReader().get(parts[6]) != null) {
                            LibraryManager.getListReader().get(parts[6]).getListBorrowedBook()
                                    .put(book.getCouponCode(), book);
                        }
                    } else {
                        System.out.println("LoanManagement listBorrowedBook.txt length " + parts.length);
                    }
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(LoaderSystem.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }

        file = new File(pathToDataBookLoanRequest);
        if (checkFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t");
                    if (parts.length == 8) {
                        BookLoanAndReturnSlip book = new BookLoanAndReturnSlip(
                                parts[0],   //id card
                                parts[1],   // coupon code
                                parts[2],   // name book
                                Integer.parseInt(parts[3]),     //loan amount
                                new Date(parts[4]),             // borrowDate
                                new Date(parts[5]),             //return Date
                                new Date(parts[6]),             // lịch trả sách
                                parts[7].equals("TRUE")         // status
                        );
                        if (!book.isStatusLoan()) {
                            Librarian.getListBookLoanRequest().computeIfAbsent(parts[0], value ->
                                    new ArrayList<>()).add(book);//load các sách vào 1 thẻ
                        } else {
                            Librarian.getListBookLoaned().computeIfAbsent(parts[0], value ->
                                    new ArrayList<>()).add(book);//load các sách vào 1 thẻ
                        }
                    } else {
                        System.out.println("Librarian length 1  = " + parts.length);
                    }
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(LoaderSystem.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }
    }
}

//    public static void main(String[] args) {
//        LoaderSystem l = new LoaderSystem();
////        Map<String, Reader> kk = LibraryManager.listAccountOfReader;
////        System.out.println(kk.size());
////        for (String i : kk.keySet()) {
////            System.out.println(i + kk.get(i).getReaderID());
////        }
//        Map<String, Reader> kk1 = LibraryManager.getListReader();
//        System.out.println(kk1.size());
//        for (String i : kk1.keySet()) {
//            System.out.println(i);
//        }
//        System.out.println();
//        System.out.println("**************************************************************");
//        System.out.println();
//        for (String id : ManageAccount.getListAccountOfReaderByID().keySet()) {
//            Account ac = ManageAccount.getListAccountOfReaderByID().get(id);
//            System.out.println(ac.getUserName() + " " + ac.getPassWord());
//        }
//        System.out.println();
//        System.out.println("**************************************************************");
//        System.out.println();
////        for (String us : LibraryManager.listAccountOfReader.keySet()) {
////            Reader rd = LibraryManager.listAccountOfReader.get(us);
////            rd.printInformationOfPerson();
////        }
//    }



