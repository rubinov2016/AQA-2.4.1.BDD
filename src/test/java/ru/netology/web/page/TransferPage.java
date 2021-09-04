package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;


public class TransferPage {

    private ElementsCollection buttons = $$(".button__content");

    public boolean moneyTransfer(String cardTo, String cardFrom, int amount) {
        var dashboardPage = new DashboardPage();
        var cardTobalance = dashboardPage.getСardBalance(cardTo);
        var cardFrombalance = dashboardPage.getСardBalance(cardFrom);

        if (amount <= 0) return false; // нельзя переводить ноль
        if (amount > cardFrombalance) return false; // нельзя переводить большую сумму, чем на балансе
        if (cardFrom.equals(cardTo)) return false; // нельзя переводить между одним и тем же счетом

        var cardToId = dashboardPage.getСardId(cardTo);
        buttons.get(cardToId).click();

        ElementsCollection inputs = $$(".input__control");
        inputs.get(0).sendKeys( Keys.CONTROL +"A",Keys.DELETE);
        inputs.get(0).setValue(Integer.toString(amount));
        inputs.get(1).sendKeys( Keys.CONTROL +"A",Keys.DELETE);
        inputs.get(1).setValue(DataHelper.cardFullname(cardFrom));

        $$("button").find(exactText("Пополнить")).click();

        var cardFrombalanceUpdated = dashboardPage.getСardBalance(cardFrom);
        var cardTobalanceUpdated = dashboardPage.getСardBalance(cardTo);

        if ((cardFrombalance - cardFrombalanceUpdated != amount) || (cardTobalanceUpdated - cardTobalance != amount)) {
            return false;
        }
        else{
            return true;
        }
    }
}
