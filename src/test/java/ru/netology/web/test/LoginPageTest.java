package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;


import static com.codeborne.selenide.Selenide.open;

public class LoginPageTest {

    @BeforeEach
    void shouldCleanMySql () {
        DataHelper.cleanMySql();
    }

    @Test
    void shouldVerifyVasya(){
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.check();
    }

    @Test
    void shouldVerifyPetya(){
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getOtherAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.check();
    }
}
