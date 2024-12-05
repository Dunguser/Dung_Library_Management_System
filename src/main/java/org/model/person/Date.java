package org.model.person;

import java.time.LocalDate;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    public Date(String fullDate) {
        String[] full = fullDate.split("/");
        this.setYear(Integer.parseInt(full[2]));
        this.setMonth(Integer.parseInt(full[1]));
        this.setDay(Integer.parseInt(full[0]));
    }

    public Date(String fullDate, boolean reverse) {
        String[] full = fullDate.split("-");
        this.setYear(Integer.parseInt(full[0]));
        this.setMonth(Integer.parseInt(full[1]));
        this.setDay(Integer.parseInt(full[2]));
    }

    public static String getDateString(Date date) {
        return "" + date.getDay()
                + date.getMonth() + date.getYear();
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        if (month >= 1 && month <= 12)
            this.month = month;
    }


    public void setDay(int day) {
        if ((month - 1) * (month - 3) * (month - 5) * (month - 7)
                * (month - 8) * (month - 10) * (month - 12) == 0) {
            if (day >= 1 && day <= 31) {
                this.day = day;
            }
        }
        if (month == 2) {
            if (this.checkNamNhuan()) {
                if (day >= 1 && day <= 29) this.day = day;
            } else {
                if (day >= 1 && day <= 28) {
                    this.day = day;
                }
            }
        } else {
            if (day >= 1 && day <= 30) {
                this.day = day;
            }
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    boolean checkNamNhuan() {
        if (year % 4 == 0 && year % 100 != 0) return true;
        return year % 400 == 0;
    }

    public String stringDate() {
        String kq = "";
        if (day < 10) kq += "0" + day;
        else kq += day;
        kq += "/";

        if (month < 10) kq += "0" + month;
        else kq += month;
        kq += "/";

        kq += year;
        return kq;
    }

    public static int compareDate(Date date1, Date date2) {//
        if (date1.year == date2.year && date1.month == date2.month
                && date1.day == date2.day) return 0;
        if (date1.year < date2.year) return -1;
        if (date1.year == date2.year && date1.month < date2.month) return -1;
        if (date1.year == date2.year && date1.month == date2.month
                && date1.day < date2.day) return -1;
        return 1;
    }

    public static Date getCurrentDate() {
        LocalDate currentDate = LocalDate.now();// Bước 1: Lấy ngày tháng năm hiện tại
        return new Date(currentDate.getDayOfMonth(),
                currentDate.getMonthValue(), currentDate.getYear());
    }

    public Date addDays(int daysToAdd) {
        int newDay = this.day + daysToAdd;
        int newMonth = this.month;
        int newYear = this.year;

        // Kiểm tra số ngày trong tháng
        while (true) {
            int daysInMonth;

            // Xác định số ngày trong tháng hiện tại
            if (newMonth == 2) { // Tháng 2
                daysInMonth = (checkNamNhuan() ? 29 : 28);
            } else { // Các tháng còn lại
                daysInMonth = (newMonth == 4 || newMonth == 6 || newMonth == 9
                        || newMonth == 11) ? 30 : 31;
            }

            // Nếu số ngày vượt quá số ngày trong tháng, điều chỉnh lại
            if (newDay > daysInMonth) {
                newDay -= daysInMonth;
                newMonth++;
                if (newMonth > 12) { // Nếu tháng vượt quá tháng 12, tăng năm
                    newMonth = 1;
                    newYear++;
                }
            } else {
                break; // Nếu không vượt quá, dừng lại
            }
        }
        return new Date(newDay, newMonth, newYear); // Trả về đối tượng Date mới
    }

}

