package ru.netology.web.test;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getValidAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);


    }

    @Test
    void shouldTransferFromFirstToSecond() {
        var firstCard = DataHelper.getFirstCardNumber();
        var secondCard = DataHelper.getSecondCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateValidAmount(firstCardBalance);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var transferPage = dashboardPage.deposit2();
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCard);
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(expectedFirstCardBalance, actualBalanceFirstCard);
        assertEquals(expectedSecondCardBalance, actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfAmountMoreBalance() {
        var firstCard = DataHelper.getFirstCardNumber();
        var secondCard = DataHelper.getSecondCardNumber();
        var firstCardBalance = dashboardPage.getCardBalance(firstCard);
        var secondCardBalance = dashboardPage.getCardBalance(secondCard);
        var amount = generateInvalidAmount(secondCardBalance);
        var transferPage = dashboardPage.deposit2();
        transferPage.makeValidTransfer(String.valueOf(amount), secondCard);
        transferPage.findErrorMessage("Не хватает денег для перевода. Уменьшите сумму или переведите с другой карты или счета");
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCard);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCard);
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);

    }

}
