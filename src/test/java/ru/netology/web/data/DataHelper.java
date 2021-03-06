package ru.netology.web.data;

import lombok.Value;
import lombok.val;

import java.sql.DriverManager;
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

    public static VerificationCode getVerificationCode() {
        val codeSql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";

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
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void cleanMySql() {

        val codes = "DELETE FROM auth_codes";
        val transactions = "DELETE FROM card_transactions";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";

        try (val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
             val prepareStatCode = connect.prepareStatement(codes);
             val prepareStatTransactions = connect.prepareStatement(transactions);
             val prepareStatCard = connect.prepareStatement(cards);
             val prepareStatUser = connect.prepareStatement(users);


        ) {
            prepareStatCode.executeUpdate(codes);
            prepareStatTransactions.executeUpdate(transactions);
            prepareStatCard.executeUpdate(cards);
            prepareStatUser.executeUpdate(users);

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

}

