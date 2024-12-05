package org.model.librarian;

import org.junit.jupiter.api.Test;
import org.model.book.BookLoan;
import org.model.person.Date;
import static org.junit.jupiter.api.Assertions.*;

class LoanManagementTest {

    @Test
    void testAddBorrower() {
        BookLoan loan1 = new BookLoan("B001", "Java Programming", "LOAN20241201154732", 1,
                new Date(2, 12, 2024), new Date(20, 12, 2024));
        BookLoan loan2 = new BookLoan("B002", "Python Basics", "LOAN20241201154234", 2,
                new Date(24, 12, 2024), new Date(28, 12, 2024));

        LoanManagement.addBorrower("R001", loan1);
        LoanManagement.addBorrower("R001", loan2);

        // Verify the books are added to the list
        assertEquals(2, LoanManagement.getListBorrowedBook().size());
        assertTrue(LoanManagement.getListBorrowedBook().containsKey("LOAN20241201154732"));
        assertTrue(LoanManagement.getListBorrowedBook().containsKey("LOAN20241201154234"));

        // Verify the user and book mapping
        assertEquals(1, LoanManagement.getListUserAndBook().size());
        assertTrue(LoanManagement.getListUserAndBook().get("R001").contains("LOAN20241201154732"));
        assertTrue(LoanManagement.getListUserAndBook().get("R001").contains("LOAN20241201154234"));
    }
}