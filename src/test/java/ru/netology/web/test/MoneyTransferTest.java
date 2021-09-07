package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferTest {
    String cardTo = "0001";
    String cardFrom = "0002";
    String uRL = "http://localhost:9999";

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open(uRL);
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var amount = 1000;
        var cardToBalanceInit = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceInit = dashboardPage.getСardBalance(cardFrom);

        var transferPage = new TransferPage();
        transferPage.moneyTransfer(cardTo, cardFrom, amount);

        var cardToBalanceActual = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceActual = dashboardPage.getСardBalance(cardFrom);
        var cardToBalanceExpected = cardToBalanceInit + amount;
        var cardFromBalanceExpected = cardFromBalanceInit - amount;

        assertEquals(cardToBalanceActual, cardToBalanceExpected);
        assertEquals(cardFromBalanceActual, cardFromBalanceExpected);
    }

    @Test
    void shouldTransferMoneyZeroAmount() {
        open(uRL);
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var cardToBalanceInit = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceInit = dashboardPage.getСardBalance(cardFrom);

        var transferPage = new TransferPage();
        transferPage.moneyTransferZeroAmount(cardTo, cardFrom);

        var cardToBalanceActual = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceActual = dashboardPage.getСardBalance(cardFrom);
        var cardToBalanceExpected = cardToBalanceInit;
        var cardFromBalanceExpected = cardFromBalanceInit;

        assertEquals(cardToBalanceActual, cardToBalanceExpected);
        assertEquals(cardFromBalanceActual, cardFromBalanceExpected);
    }

    @Test
    void shouldTransferMoneyBetweenTheSameCard() {
        open(uRL);
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferPage = new TransferPage();

        var amount = 1000;
        var cardToBalanceInit = dashboardPage.getСardBalance(cardTo);
        transferPage.moneyTransferBetweenTheSameCard(cardTo, amount);
        var cardToBalanceActual = dashboardPage.getСardBalance(cardTo);
        var cardToBalanceExpected = cardToBalanceInit;

        assertEquals(cardToBalanceActual, cardToBalanceExpected);
    }

    @Test
    void shouldTransferMoneyInsufficientBalance() {
        open(uRL);
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var amount = 1005;
        var cardToBalanceInit = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceInit = dashboardPage.getСardBalance(cardFrom);

        var transferPage = new TransferPage();
        transferPage.moneyTransferInsufficientBalance(cardTo, cardFrom, cardFromBalanceInit + amount);

        var cardToBalanceActual = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceActual = dashboardPage.getСardBalance(cardFrom);
        var cardToBalanceExpected = cardToBalanceInit; //ничего не должно поменяться
        var cardFromBalanceExpected = cardFromBalanceInit; //ничего не должно поменяться

        assertEquals(cardToBalanceActual, cardToBalanceExpected);
        assertEquals(cardFromBalanceActual, cardFromBalanceExpected);
    }

    @Test
    void shouldPositiveBalance() {
        open(uRL);
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var transferPage = new TransferPage();
        assertTrue(transferPage.isPositiveBalance(cardTo));
        assertTrue(transferPage.isPositiveBalance(cardFrom));
    }
}


