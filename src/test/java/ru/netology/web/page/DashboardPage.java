package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.apache.commons.lang3.StringUtils.trim;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private ElementsCollection buttons = $$(".button__content");
    private final String balanceStart = "баланс:";
    private final String balanceFinish = "р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = trim(text.substring(start + balanceStart.length(), finish));
        return Integer.parseInt(value);
    }

    private String extractCardNumber(String text) {
        int cardLength = 4; // длина номера
        int oddSymbols = 2; // ненужные символы
        var finish = text.indexOf(balanceStart) - oddSymbols;
        var start = finish - cardLength;
        var value = text.substring(start, finish);
        return value;
    }

    public TransferPage topUp(String card) {
        var cardToId = getСardId(card);
        buttons.get(cardToId).click();
        return new TransferPage();
    }

    public int getСardBalance(String card) {
        var index = getСardId(card);
        var text = cards.get(index).text();
        return extractBalance(text);
    }

    public int getСardId(String card) {
        var text = cards.first().text();
        var substrCard = extractCardNumber(text);
        int index = 0;
        while ((index <= cards.size()) && (!card.equals(substrCard))) {
            index++;
            text = cards.get(index).text();
            substrCard = extractCardNumber(text);
        }
        return index;
    }

}
