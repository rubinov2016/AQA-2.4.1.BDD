package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage moneyTransfer(String cardTo, String cardFrom, int amount) {
        ElementsCollection inputs = $$(".input__control");
        inputs.get(0).sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        inputs.get(0).setValue(Integer.toString(amount));
        inputs.get(1).sendKeys(Keys.CONTROL + "A", Keys.DELETE);
        inputs.get(1).setValue(DataHelper.cardFullname(cardFrom));

        $$("button").find(exactText("Пополнить")).click();
        return new DashboardPage();
    }
}