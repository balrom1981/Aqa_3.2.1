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
    public static class VerificationId {
        private String id;
    }

    public static VerificationId getVerificationIdForVasya() throws SQLException {
        val idSql = "SELECT id FROM users WHERE login='vasya';";


        try (
                val conn = getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
                val idStmt = conn.createStatement();

        ) {
            try (val rs = idStmt.executeQuery(idSql)) {
                if (rs.next()) {
                    val id = rs.getString(1);

                    return new VerificationId(id);

                }
                return null;
            }
        }
    }

//    @Value
//    public static class VerificationPassword {
//        private String password;
//    }
//
//    public static VerificationPassword getVerificationPasswordForVasya() {
//        return new VerificationPassword("qwerty123");
//    }

        @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeForVasya() throws SQLException {
        VerificationId verificationId=new VerificationId(getVerificationIdForVasya().id);
        val codeSql = "SELECT code FROM auth_codes WHERE user_id=user_id;";


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