package org.model.operator;

import org.model.account.Account;
import org.model.person.*;
import org.model.reader.Reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.model.GlobalState.getPathToDataListReader;

public class LibraryManager extends Person {

    private final Account accountOperator;
    private static HashMap<String, Reader> listReader
            = new HashMap<>(); //key is id
    private static HashMap<String, Reader> listAccountOfReader
            = new HashMap<>();   //key : ten dang nhap

    public LibraryManager(PersonName personName, Address personAddress,
                          Gmail personGmail, PhoneNumber personPhone,
                          boolean personSex, Date personDateBirth) {
        super(personName, personAddress, personGmail, personPhone, personSex, personDateBirth);
        accountOperator = new Account("ADMIN", "Ducadu%1234");
    }

    //Lưu người đọc
    public static void saveReaderToDatabase(String userName, Reader reader) {
        File file = new File(getPathToDataListReader);
        if (checkFile(file)) {
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(userName);
                fw.write("\t");
                fw.write(reader.getPersonName().fullName());
                fw.write("\t");
                fw.write(reader.getPersonAddress().getFullAddress());
                fw.write("\t");
                fw.write(reader.getPersonGmail().getGmail());
                fw.write("\t");
                fw.write(reader.getPersonPhone().getPhoneNum());
                fw.write("\t");
                fw.write(reader.getPersonSex());
                fw.write("\t");
                fw.write(reader.getPersonDatebirth().stringDate());
                fw.write("\t");
                fw.write(reader.getReaderID());
                fw.write("\n");
            } catch (IOException e) {
                Logger logger = Logger.getLogger(LibraryManager.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }
    }

    public static void saveReaderToDatabase() {
        File file = new File(getPathToDataListReader);
        if (checkFile(file)) {
            try (FileWriter fw = new FileWriter(file)) {
                for (String key : listAccountOfReader.keySet()) {
                    Reader reader = listAccountOfReader.get(key);
                    fw.write(key);
                    fw.write("\t");
                    fw.write(reader.getPersonName().fullName());
                    fw.write("\t");
                    fw.write(reader.getPersonAddress().getFullAddress());
                    fw.write("\t");
                    fw.write(reader.getPersonGmail().getGmail());
                    fw.write("\t");
                    fw.write(reader.getPersonPhone().getPhoneNum());
                    fw.write("\t");
                    fw.write(reader.getPersonSex());
                    fw.write("\t");
                    fw.write(reader.getPersonDatebirth().stringDate());
                    fw.write("\t");
                    fw.write(reader.getReaderID());
                    fw.write("\n");
                }
            } catch (IOException e) {
                Logger logger = Logger.getLogger(LibraryManager.class.getName());
                logger.log(Level.SEVERE, "Error ", e);
            }
        }

    }

    public Account getAccountOperator() {
        return accountOperator;
    }

    public static HashMap<String, Reader> getListReader() {
        return listReader;
    }

    public static HashMap<String, Reader> getListAccountOfReader() {
        return listAccountOfReader;
    }

    /**
     * kiểm tra file hợp lệ
     */
    public static boolean checkFile(File file) {
        if (!file.exists()) {
            System.out.println("LibraryManager: " + file + " is not exists");
            return false;
        }
        if (!file.canWrite()) {
            System.out.println("LibraryManager: " + file + " can not write");
            return false;
        }
        if (!file.canRead()) {
            System.out.println("LibraryManager: " + file + " can not read");
            return false;
        }
        if (!file.canExecute()) {
            System.out.println("LibraryManager: " + file + " can not execute");
            return false;
        }
        return true;
    }
}
