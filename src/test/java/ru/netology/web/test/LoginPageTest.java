package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class LoginPageTest {

    @BeforeEach
    void shouldCleanAuthCodes() throws SQLException {
        val codes = "DELETE FROM auth_codes where code";

        try (val connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "user", "pass");
             val prepareStatCode = connect.prepareStatement(codes);) {
            prepareStatCode.executeUpdate(codes);
        }
    }


    @Test
    void shouldVerifyVasya() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.check();
    }

    @Test
    void shouldVerifyPetya() throws SQLException {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getOtherAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.check();
    }
}
