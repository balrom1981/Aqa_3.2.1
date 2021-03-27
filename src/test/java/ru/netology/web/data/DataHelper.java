package ru.netology.web.data;

import lombok.Value;
import lombok.val;

import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo() {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() throws SQLException {
        val codeSql = "SELECT code FROM auth_codes WHERE user_id";


        try (
                val connection = getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
                val codeStmt = connection.createStatement();

        ) {
            try (val rs = codeStmt.executeQuery(codeSql)) {
                if (rs.next()) {
                    val code = rs.getString(1);

                    return new VerificationCode(code);

                }
                return null;
            }

        }
    }

}