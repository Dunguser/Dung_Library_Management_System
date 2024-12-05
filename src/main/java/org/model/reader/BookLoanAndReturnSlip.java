package org.model.reader;

import org.model.operator.LibraryManager;
import org.model.person.Date;

import static org.model.reader.Reader.getCurrentTimeAsString;

public class BookLoanAndReturnSlip {
    private String idCard;                  // ma the chua cai phieu nay
    private String couponCode;              // mã phiếu
    private String nameBook;                // tên sách
    private int loanAmount;                 // so luong sach muon
    private Date borrowedBookDate;          // ngay muon
    private Date returnBookDate;            // ngay tra sach
    private Date bookReturnSchedule;        // lich tra sach theo yeu cau cua thu vien
    private boolean statusLoan;             // cho xem co duoc cho muon hay khong

    // khoitao
    public BookLoanAndReturnSlip(String idCard, String couponCode,
                                 String nameBook, int loanAmount,
                                 Date borrowedBookDate, Date returnBookDate,
                                 Date bookReturnSchedule, boolean statusLoan) {
        this.idCard = idCard;
        this.couponCode = couponCode;
        this.nameBook = nameBook;
        this.loanAmount = loanAmount;
        this.borrowedBookDate = borrowedBookDate;
        this.returnBookDate = returnBookDate;
        this.bookReturnSchedule = bookReturnSchedule;
        this.statusLoan = statusLoan;
    }

    public BookLoanAndReturnSlip(String idCard, String nameBook,
                                 int loanAmount, Date borrowedBookDate,
                                 Date returnBookDate, Date bookReturnSchedule,
                                 boolean statusLoan) {
        this.idCard = idCard;
        this.nameBook = nameBook;
        this.loanAmount = loanAmount;
        this.borrowedBookDate = borrowedBookDate;
        this.returnBookDate = returnBookDate;
        this.bookReturnSchedule = bookReturnSchedule;
        this.statusLoan = statusLoan;
        this.couponCode = "LOAN" + getCurrentTimeAsString();
    }

    // cac ham set, get

    public boolean isStatusLoan() {
        return statusLoan;
    }

    public String getStatus() {
        if (statusLoan) return "currently loaned";
        return "Not loaned";
    }

    public void setStatusLoan(boolean statusLoan) {
        this.statusLoan = statusLoan;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBorrowedBookDate() {
        return borrowedBookDate;
    }

    public void setBorrowedBookDate(Date borrowedBookDate) {
        this.borrowedBookDate = borrowedBookDate;
    }

    public Date getReturnBookDate() {
        return returnBookDate;
    }

    public void setReturnBookDate(Date returnBookDate) {
        this.returnBookDate = returnBookDate;
    }

    public Date getBookReturnSchedule() {
        return bookReturnSchedule;
    }

    public void setBookReturnSchedule(Date bookReturnSchedule) {
        this.bookReturnSchedule = bookReturnSchedule;
    }

    // phuong thuc
    public String getFullNameOfBorrower() {
        Reader r = LibraryManager.getListReader().get(idCard);
        return r.getPersonName().fullName();
    }
}
