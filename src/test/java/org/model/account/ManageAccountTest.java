package org.model.account;

import org.model.person.*;
import org.model.reader.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ManageAccountTest {

    @BeforeEach
    void setUp() {
        ManageAccount.getListAccountOfReaderByID().clear();
        ManageAccount.getListAccountOfUsername().clear();
    }

    @Test
    void getListAccountOfReaderByID() {
        ManageAccount.getListAccountOfReaderByID().put("R001", new Account("user1", "Password@1"));
        assertEquals(1, ManageAccount.getListAccountOfReaderByID().size(), "The list should contain 1 account.");
        assertNotNull(ManageAccount.getListAccountOfReaderByID().get("R001"), "Account with ID 'R001' should exist.");
    }

    @Test
    void getListAccountOfUsername() {
        Account account = new Account("user1", "Password@1");
        ManageAccount.getListAccountOfUsername().put("user1", account);

        assertEquals(1, ManageAccount.getListAccountOfUsername().size(), "The list should contain 1 account.");
        assertNotNull(ManageAccount.getListAccountOfUsername().get("user1"), "Account with username 'user1' should exist.");
    }

    @Test
    void printListAccountOfReader() {
        ManageAccount.getListAccountOfReaderByID().put("R001", new Account("user1", "Password@1"));
        ManageAccount.getListAccountOfReaderByID().put("R002", new Account("user2", "Password@2"));

        // Không thực sự kiểm tra việc in ra console, nhưng kiểm tra không xảy ra lỗi
        assertDoesNotThrow(ManageAccount::printListAccountOfReader, "Printing the list of accounts should not throw an exception.");
    }

    @Test
    void setListAccountOfUsername() {
        HashMap<String, Account> newMap = new HashMap<>();
        newMap.put("user3", new Account("user3", "Password@3"));

        ManageAccount.setListAccountOfUsername(newMap);

        assertEquals(1, ManageAccount.getListAccountOfUsername().size(), "The list should now contain 1 account.");
        assertNotNull(ManageAccount.getListAccountOfUsername().get("user3"), "Account with username 'user3' should exist.");
    }
}
