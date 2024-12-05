package org.model.book;

import javafx.scene.image.Image;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Book {
    private String idBook;          // Mã định danh duy nhất của sách
    private int numberBook;         // Số lượng sách còn lại
    private String title;           // Tiêu đề sách
    private String authors;         // Danh sách tác giả
    private String publisher;       // Nhà xuất bản
    private String publishedDate;   // Ngày xuất bản
    private String description;     // Mô tả sách
    private String imageLinks;      // Link ảnh sách
    private int pageCount;          // Số trang
    private String language;        // Ngôn ngữ của sách
    private Image qrCode;           // Mã QR của sách

    // Constructor
    public Book(String idBook, String title) {
        this.idBook = idBook;
        this.title = title;
        generateQRCode(); // Tự động tạo mã QR
    }

    public Book() {
    }

    public Book(String idBook, int numberBook, String title, String authors,
                String publisher, String publishedDate, String description,
                String imageLinks, int pageCount, String language) {
        this.idBook = idBook;
        this.numberBook = numberBook;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.imageLinks = imageLinks;
        this.pageCount = pageCount;
        this.language = language;
        generateQRCode(); // Tự động tạo mã QR
    }


    public Image getQrCode() {
        return qrCode;
    }

    private void generateQRCode() {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String qrContent = "Book ID: " + idBook + "\nTitle: " + title; // Nội dung mã QR
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent,
                    BarcodeFormat.QR_CODE, 200, 200);

            // Chuyển BitMatrix thành Image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            ByteArrayInputStream inputStream
                    = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            this.qrCode = new Image(inputStream);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            System.out.println("Không thể tạo mã QR.");
        }
    }

    public String getIdBook() {
        return idBook;
    }

    public void setIdBook(String idBook) {
        this.idBook = idBook;
    }

    public int getNumberBook() {
        return numberBook;
    }

    public void setNumberBook(int numberBook) {
        this.numberBook = numberBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public Image getImage() {
        return new Image(imageLinks);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" + "id='" + idBook + '\''
                + ", numberBook=" + numberBook
                + ", title='" + title + '\''
                + ", authors='" + authors + '\''
                + ", publisher='" + publisher + '\''
                + ", publishedDate='" + publishedDate + '\''
                + ", description='" + description + '\''
                + ", imageLinks='" + imageLinks + '\''
                + ", pageCount=" + pageCount
                + ", language='" + language + '\'' + '}';
    }

    public String formatBookInfo() {
        return String.format(
                "ID: %s\n" +
                        "Title: %s\n" +
                        "Authors: %s\n" +
                        "Publisher: %s\n" +
                        "Published Date: %s\n" +
                        "Description: %s\n" +
                        "Image Link: %s\n" +
                        "Page Count: %d\n" +
                        "Language: %s\n" +
                        "Available Copies: %d",
                this.getIdBook(),
                this.getTitle(),
                this.getAuthors(),
                this.getPublisher(),
                this.getPublishedDate(),
                this.getDescription(),
                this.getImageLinks(),
                this.getPageCount(),
                this.getLanguage(),
                this.getNumberBook()
        );
    }

}