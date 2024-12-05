package org.model.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.model.person.Date;

import static org.junit.jupiter.api.Assertions.*;

public class BookLoanTest {

    private BookLoan bookLoan;

    @BeforeEach
    void setUp() {
        Date borrowedDate = new Date(2024, 11, 30);
        Date requiredReturnDate = new Date(2024, 12, 15);
        bookLoan = new BookLoan("B001", "Java Programming", "C12345", 2, borrowedDate, requiredReturnDate);
    }

    @Test
    void testSettersAndGetters() {
        Date newBorrowedDate = new Date(2024, 12, 1);
        Date newRequiredReturnDate = new Date(2024, 12, 20);

        bookLoan.setBookId("B002");
        bookLoan.setNameBook("Advanced Java");
        bookLoan.setCouponCode("C67890");
        bookLoan.setAmount(3);
        bookLoan.setBorrowedDate(newBorrowedDate);
        bookLoan.setRequiredReturnDate(newRequiredReturnDate);

        assertEquals("B002", bookLoan.getBookId());
        assertEquals("Advanced Java", bookLoan.getNameBook());
        assertEquals("C67890", bookLoan.getCouponCode());
        assertEquals(3, bookLoan.getAmount());
        assertEquals(newBorrowedDate, bookLoan.getBorrowedDate());
        assertEquals(newRequiredReturnDate, bookLoan.getRequiredReturnDate());
    }

    @Test
    void testBorrowedDateValidation() {
        Date invalidBorrowedDate = new Date(2025, 1, 1);
        bookLoan.setBorrowedDate(invalidBorrowedDate);

        assertEquals(invalidBorrowedDate, bookLoan.getBorrowedDate(),
                "Borrowed date should be updated correctly.");
    }

    @Test
    void testRequiredReturnDateValidation() {
        Date invalidRequiredReturnDate = new Date(2025, 1, 15);
        bookLoan.setRequiredReturnDate(invalidRequiredReturnDate);

        assertEquals(invalidRequiredReturnDate, bookLoan.getRequiredReturnDate(),
                "Required return date should be updated correctly.");
    }

}