package org.model.book;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("123", 5, "Test Title", "Test Author",
                "Test Publisher", "2023-12-01", "Test Description",
                "https://example.com/image.png", 200, "English");
    }

    @Test
    void testConstructor() {
        assertNotNull(book);
        assertEquals("123", book.getIdBook());
        assertEquals(5, book.getNumberBook());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthors());
        assertEquals("Test Publisher", book.getPublisher());
        assertEquals("2023-12-01", book.getPublishedDate());
        assertEquals("Test Description", book.getDescription());
        assertEquals("https://example.com/image.png", book.getImageLinks());
        assertEquals(200, book.getPageCount());
        assertEquals("English", book.getLanguage());
    }

    @Test
    void testSettersAndGetters() {
        book.setIdBook("456");
        book.setNumberBook(10);
        book.setTitle("New Title");
        book.setAuthors("New Author");
        book.setPublisher("New Publisher");
        book.setPublishedDate("2024-01-01");
        book.setDescription("New Description");
        book.setImageLinks("https://example.com/new_image.png");
        book.setPageCount(300);
        book.setLanguage("Spanish");

        assertEquals("456", book.getIdBook());
        assertEquals(10, book.getNumberBook());
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthors());
        assertEquals("New Publisher", book.getPublisher());
        assertEquals("2024-01-01", book.getPublishedDate());
        assertEquals("New Description", book.getDescription());
        assertEquals("https://example.com/new_image.png", book.getImageLinks());
        assertEquals(300, book.getPageCount());
        assertEquals("Spanish", book.getLanguage());
    }

    @Test
    void testQRCodeGeneration() {
        Image qrCode = book.getQrCode();
        assertNotNull(qrCode, "QR code should be generated successfully.");
    }

    @Test
    void testFormatBookInfo() {
        String formattedInfo = book.formatBookInfo();
        assertTrue(formattedInfo.contains("ID: 123"));
        assertTrue(formattedInfo.contains("Title: Test Title"));
        assertTrue(formattedInfo.contains("Authors: Test Author"));
        assertTrue(formattedInfo.contains("Publisher: Test Publisher"));
        assertTrue(formattedInfo.contains("Published Date: 2023-12-01"));
        assertTrue(formattedInfo.contains("Description: Test Description"));
        assertTrue(formattedInfo.contains("Image Link: https://example.com/image.png"));
        assertTrue(formattedInfo.contains("Page Count: 200"));
        assertTrue(formattedInfo.contains("Language: English"));
        assertTrue(formattedInfo.contains("Available Copies: 5"));
    }

    @Test
    void testToString() {
        String toStringResult = book.toString();
        assertTrue(toStringResult.contains("id='123'"));
        assertTrue(toStringResult.contains("numberBook=5"));
        assertTrue(toStringResult.contains("title='Test Title'"));
    }
}
