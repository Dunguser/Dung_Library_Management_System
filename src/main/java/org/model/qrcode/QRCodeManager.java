package org.model.qrcode;

import org.model.book.Book;

import java.io.File;
import java.util.Collection;

public class QRCodeManager {

    private String qrDirectoryPath;

    // Constructor
    public QRCodeManager(String qrDirectoryPath) {
        this.qrDirectoryPath = qrDirectoryPath;
        // Ensure the directory exists
        File directory = new File(qrDirectoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}


//
//    // Method to generate a QR code for a single book
//    public void generateQRCodeForBook(Book book) {
//        String bookInfo = book.formatBookInfo(); // Thông tin sách
//        String filePath = qrDirectoryPath + File.separator + "book_" + book.getIdBook() + ".png";
//
//        // Gọi phương thức từ lớp QRCodeGenerator (được giả định là đã viết trước đó)
//        QRCodeGenerator.generateQRCode(bookInfo, filePath);
//        System.out.println("QR code generated for book: " + book.getTitle());
//    }

//    // Method to generate QR codes for all books
//    public void generateQRCodeForAllBooks(Collection<Book> books) {
//        for (Book book : books) {
//            generateQRCodeForBook(book);
//        }
//        System.out.println("QR codes generated for all books.");
//    }

//    // Method to delete all QR codes
//    public void deleteAllQRCodes() {
//        File directory = new File(qrDirectoryPath);
//
//        if (directory.exists() && directory.isDirectory()) {
//            File[] files = directory.listFiles();
//
//            if (files != null) {
//                for (File file : files) {
//                    if (file.isFile() && file.getName().endsWith(".png")) {
//                        if (file.delete()) {
//                            System.out.println("Deleted: " + file.getName());
//                        } else {
//                            System.out.println("Failed to delete: " + file.getName());
//                        }
//                    }
//                }
//            } else {
//                System.out.println("No files found in the directory.");
//            }
//        } else {
//            System.out.println("Directory does not exist: " + qrDirectoryPath);
//        }
//    }
