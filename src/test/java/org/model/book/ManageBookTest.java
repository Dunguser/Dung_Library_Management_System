package org.model.book;

import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ManageBookTest {
    private static final String TEST_FILE_PATH = "listBook.txt";

    @Test
    void testAddBook() {
        ManageBook manageBook = new ManageBook();
        Book book = new Book("BO200", 10, "Test Book", "Author", "Publisher", "2023", "Description", "image.jpg", 200, "EN");

        manageBook.addBook(book);
        assertTrue(ManageBook.getBooks().containsKey("Test Book"), "Book should be added to the HashMap");
//        manageBook.removeBook(Test Book);
    }

    @Test
    void testRemoveBook() {
        ManageBook manageBook = new ManageBook();
        Book book = new Book("BO200", 10, "Test Book", "Author", "Publisher", "2023", "Description", "image.jpg", 200, "EN");
        manageBook.addBook(book);
        manageBook.removeBook("Test Book");
        assertFalse(ManageBook.getBooks().containsKey("Test Book"), "Book should be removed from the HashMap");
    }

    @Test
    void testSaveBooks() {
        ManageBook manageBook = new ManageBook();
        Book book = new Book("1", 10, "Test Book", "Author", "Publisher", "2023", "Description", "image.jpg", 200, "EN");

        manageBook.addBook(book);
        manageBook.saveBooksToFile();

        assertTrue(ManageBook.getBooks().containsKey("Test Book"), "Book should be loaded from file");
        manageBook.removeBook("Test Book");
    }

    @Test
    void getBooks() {
    }

    @Test
    void addBook() {
    }

    @Test
    void removeBook() {
    }

    @Test
    void saveBooksToFile() {
    }

    @Test
    void testSaveBooksToFile() {
    }

    @Test
    void loadBooksFromFile() {
    }
}
