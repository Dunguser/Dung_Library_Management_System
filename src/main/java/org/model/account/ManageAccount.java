package org.model.account;

import org.model.reader.Reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.model.GlobalState.pathToDataListAccount;
import static org.model.operator.LibraryManager.checkFile;

public class ManageAccount {
    private static HashMap<String, Account> listAccountOfReaderByID = new HashMap<>(); // account tìm theo ID
    private static HashMap<String, Account> listAccountOfUsername = new HashMap<>();   // account tìm theo name

    public ManageAccount() {

    }

    /**
     *
     */
    public static void saveAccountToDatabase(Reader reader, Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        File file = new File(pathToDataListAccount);
        if (checkFile(file)) {
            try (BufferedWriter fw = new BufferedWriter(new FileWriter(file, true))) {
                fw.write(reader.getReaderID());
                fw.write("\t");
                fw.write(account.getUserName());
                fw.write("\t");
                fw.write(account.getPassWord());
                fw.newLine();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(ManageAccount.class.getName());
                logger.log(Level.SEVERE, "Error writing account to file", e);
            }
        }
    }

    public static void saveAccountToDatabase() {
        File file = new File(pathToDataListAccount);
        if (checkFile(file)) {
            try (BufferedWriter fw = new BufferedWriter(new FileWriter(file))) {
                for (String id : listAccountOfReaderByID.keySet()) {
                    Account account = listAccountOfReaderByID.get(id);
                    fw.write(id);
                    fw.write("\t");
                    fw.write(account.getUserName());
                    fw.write("\t");
                    fw.write(account.getPassWord());
                    fw.newLine();
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(ManageAccount.class.getName());
                logger.log(Level.SEVERE, "Error writing account to file", e);
            }
        }
    }

    public static HashMap<String, Account> getListAccountOfReaderByID() {
        return listAccountOfReaderByID;
    }

    public static HashMap<String, Account> getListAccountOfUsername() {
        return listAccountOfUsername;
    }

    public static void printListAccountOfReader() {
        for (String key : listAccountOfReaderByID.keySet()) {
            Account ac = listAccountOfReaderByID.get(key);
            System.out.printf("Tên đăng nhâp: %-20s " +
                    "   Mật khẩu: %-20s%n", ac.getUserName(), ac.getPassWord());

        }
    }

    public static void setListAccountOfUsername(HashMap<String, Account> listAccountOfUsername) {
        ManageAccount.listAccountOfUsername = listAccountOfUsername;
    }

//    public static void main(String[] args) {
//        ManageAccount manageAccount = new ManageAccount();
//        printListAccountOfReader();
//        for (String us : listAccountOfUsername.keySet()) {
//            Account rd = listAccountOfUsername.get(us);
//            System.out.println(us + " " + rd.getPassWord());
//        }
//    }
}
