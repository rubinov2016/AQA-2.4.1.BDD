package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class MoneyTransferTest {
    String cardTo = "0001";
    String cardFrom = "0002";
    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var dashboardPage = new DashboardPage();
        var amount = 1000;
        var cardToBalanceInit = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceInit = dashboardPage.getСardBalance(cardFrom);

        var transferPage = new TransferPage();
        transferPage.moneyTransfer(cardTo, cardFrom, amount);

        var cardToBalanceActual = dashboardPage.getСardBalance(cardTo);
        var cardFromBalanceActual = dashboardPage.getСardBalance(cardFrom);

        var cardToBalanceExpected = cardToBalanceInit + amount;
        var cardFromBalanceExpected = cardFromBalanceInit - amount;

        assertEquals(cardFromBalanceActual,cardFromBalanceExpected);
        assertEquals(cardToBalanceActual,cardToBalanceExpected);
    }
    @Test
    void shouldTransferMoneyZeroAmount() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var transferPage = new TransferPage();

        assertFalse(transferPage.moneyTransfer(cardTo, cardFrom, 0));
    }
    @Test
    void shouldTransferMoneyBetweenTheSameCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var transferPage = new TransferPage();

        assertFalse(transferPage.moneyTransfer(cardTo, cardTo, 1000));

    }

    @Test
    void shouldTransferMoneyInsufficientBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var dashboardPage = new DashboardPage();
        var cardFromBalanceInit = dashboardPage.getСardBalance(cardFrom);

        var transferPage = new TransferPage();
        assertFalse(transferPage.moneyTransfer(cardTo, cardFrom, cardFromBalanceInit+100));

    }
}


