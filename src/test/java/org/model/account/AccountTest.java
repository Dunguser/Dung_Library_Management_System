package org.model.account;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void setUserName() {
        Account account = new Account();
        account.setUserName("testUser");
        assertEquals("testUser", account.getUserName(), "User name should be set correctly.");
    }

    @Test
    void getUserName() {
        Account account = new Account("testUser", "Strong@123");
        assertEquals("testUser", account.getUserName(), "User name should be retrieved correctly.");
    }

    @Test
    void getPassWord() {
        Account account = new Account("testUser", "Strong@123");
        assertEquals("Strong@123", account.getPassWord(), "Password should be retrieved correctly.");
    }

    @Test
    void setPassWord() {
        Account account = new Account();
        account.setPassWord("Strong@123");
        assertEquals("Strong@123", account.getPassWord(), "Password should be set correctly.");
    }

    @Test
    void setPassWord_invalid() {
        Account account = new Account();
        account.setPassWord("weak"); // Password không mạnh
        assertNull(account.getPassWord(), "Password should not be set if it's weak.");
    }

    @Test
    void checkCredentials() {
        Account account = new Account("testUser", "Strong@123");
        assertTrue(account.checkCredentials("testUser", "Strong@123"), "Credentials should be valid.");
        assertFalse(account.checkCredentials("testUser", "WrongPass"), "Credentials should be invalid with wrong password.");
        assertFalse(account.checkCredentials("wrongUser", "Strong@123"), "Credentials should be invalid with wrong username.");
    }

    @Test
    void checkPasswordStrong() {
        assertTrue(Account.checkPasswordStrong("Strong@123"), "Password should be strong.");
        assertFalse(Account.checkPasswordStrong("weak"), "Password should be weak if less than 8 characters.");
        assertFalse(Account.checkPasswordStrong("NoSpecialChar1"), "Password should be weak without special characters.");
        assertFalse(Account.checkPasswordStrong("nocapitalspecial@1"), "Password should be weak without uppercase letters.");
        assertFalse(Account.checkPasswordStrong("NOCAPITALS123!"), "Password should be weak without lowercase letters.");
        assertFalse(Account.checkPasswordStrong("NoDigits@"), "Password should be weak without digits.");
    }
}
