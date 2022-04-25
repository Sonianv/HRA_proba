package org.loose.fis.sre.hotelreservationapplication.services;

import org.loose.fis.sre.hotelreservationapplication.database.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.*;
import java.util.regex.*;

public class UserService {

    public static void addUser(String username, String password, String role, String fullName, String phoneNumber) throws SQLException {

        PreparedStatement statement;
        statement = DBConnection.connection.prepareStatement("INSERT INTO users (username, password, role, fullName, phoneNumber) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, role);
        statement.setString(4, fullName);
        statement.setString(5, phoneNumber);
        statement.executeUpdate();
    }

    public static boolean searchUser(String username, String password, String role) throws SQLException {

        PreparedStatement statement;
        statement = DBConnection.connection.prepareStatement("SELECT * from users where  username = ? and password = ? and role = ? ");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, role);
        ResultSet user = statement.executeQuery();
        return user.next();
    }

    public static String encodePassword (String password) {
        String encryptedpassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedpassword;
    }

    public static boolean validatePhoneNumber (String phone) {
        Pattern p = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");
        Matcher m = p.matcher(phone);
        return (m.matches());
    }

}

