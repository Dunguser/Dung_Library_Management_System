package org.model.book;

import org.model.qrcode.QRCodeManager;

import java.io.*;
import java.util.HashMap;

import static org.model.GlobalState.pathToDataListBook;
import static org.model.operator.LibraryManager.checkFile;


public class ManageBook {
    private static HashMap<String, Book> books = new HashMap<>(); // key is title

    // Constructor
    public ManageBook() {
        loadBooksFromFile();
    }

    public static HashMap<String, Book> getBooks() {
        return books;
    }

    // Method to add a new book
    public void addBook(Book book) {
        books.put(book.getTitle(), book);
        saveBooksToFile();
    }

    // Method to remove a book by title
    public void removeBook(String title) {
        if (books.containsKey(title)) {
            books.remove(title);
            saveBooksToFile(); // Save changes to file after removing
        } else {
            System.out.println("Book with title \"" + title + "\" does not exist.");
        }
    }

    // Method to save books to file
    public static void saveBooksToFile() {
        File file = new File(pathToDataListBook);
        if (checkFile(file)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String key : books.keySet()) {
                    Book book = books.get(key);
                    bw.write(book.getIdBook()
                            + "," + book.getNumberBook()
                            + "," + book.getTitle()
                            + "," + book.getAuthors()
                            + "," + book.getPublisher()
                            + "," + book.getPublishedDate()
                            + "," + book.getDescription()
                            + "," + book.getImageLinks()
                            + "," + book.getPageCount()
                            + "," + book.getLanguage());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    public static void saveBooksToFile(Book book) {
        File file = new File(pathToDataListBook);
        if (checkFile(file)) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
                bw.write(book.getIdBook()
                        + "," + book.getNumberBook()
                        + "," + book.getTitle()
                        + "," + book.getAuthors()
                        + "," + book.getPublisher()
                        + "," + book.getPublishedDate()
                        + "," + book.getDescription()
                        + "," + book.getImageLinks()
                        + "," + book.getPageCount()
                        + "," + book.getLanguage());
                bw.newLine();

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }

    }

    // Method to load books from file
    public static void loadBooksFromFile() {
        File file = new File(pathToDataListBook);
        if (checkFile(file)) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 10) {
                        Book book = new Book(data[0], // idBook
                                Integer.parseInt(data[1]), // numberBook
                                data[2], // title
                                data[3], // authors
                                data[4], // publisher
                                data[5], // publishedDate
                                data[6], // description
                                data[7], // imageLinks
                                Integer.parseInt(data[8]), // pageCount
                                data[9]  // language
                        );
                        books.put(book.getTitle(), book);
                    } else {
                        System.out.println("Invalid data format: " + line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
            }
        }

    }

//    public static void main(String[] args) {
//        ManageBook manageBook = new ManageBook();
//
//        // Tạo đối tượng QRCodeManager
//        QRCodeManager qrManager = new QRCodeManager("D:\\Library-Management\\src\\main\\resources\\QR_Codes");
//
//        // Tạo QR code cho toàn bộ sách
//        qrManager.generateQRCodeForAllBooks(manageBook.getBooks().values());
//
//        /*// Xóa tất cả QR code
//        qrManager.deleteAllQRCodes();*/
//    }


}
