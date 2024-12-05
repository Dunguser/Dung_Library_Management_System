package org.model.account;

public class Account {

    private String userName;
    private String passWord;

    public Account() {
    }

    public Account(String userName, String passWord) {
        this.userName = userName;
        if (checkPasswordStrong(passWord))
            this.passWord = passWord;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        if (checkPasswordStrong(passWord)) this.passWord = passWord;
    }

    // Phương thức kiểm tra thông tin đăng nhập
    public boolean checkCredentials(String userName, String passWord) {
        return this.userName.equals(userName) && this.passWord.equals(passWord);
    }
    //kiem tra mk manh
    public static boolean checkPasswordStrong(String password) {
        if (password.length() < 8) return false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
}
