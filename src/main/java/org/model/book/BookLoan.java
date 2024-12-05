package org.model.book;

import org.model.person.Date;

public class BookLoan  {
    private String bookId;
    private String nameBook;
    private String couponCode;
    private int amount;
    private Date borrowedDate;
    private Date requiredReturnDate;

    public BookLoan(String bookId, String nameBook, String couponCode,
                    int amount, Date borrowedDate, Date requiredReturnDate) {
        this.bookId = bookId;
        this.nameBook = nameBook;
        this.couponCode = couponCode;
        this.amount = amount;
        this.borrowedDate = borrowedDate;
        this.requiredReturnDate = requiredReturnDate;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getRequiredReturnDate() {
        return requiredReturnDate;
    }

    public void setRequiredReturnDate(Date requiredReturnDate) {
        this.requiredReturnDate = requiredReturnDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }
}
