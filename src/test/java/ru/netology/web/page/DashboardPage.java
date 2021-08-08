package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.apache.commons.lang3.StringUtils.trim;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private ElementsCollection buttons = $$(".button__content");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getСardBalance(String card) {
        var searchBegWord = "баланс:";
        var searchEndWord = "р.";
        String text;
        int begIndex;
        int endIndex;
        var balance = 0; //по умолчанию баланс нулевой
        boolean flag = true; // для выхода из цикла
        int index = 0;
        String substrCard; //для поиска подстроки
        int cardLength = 6; // длина строки с номером карточки
        int cardLengthRest = 2; // длина хвоста строки с номером карточки, содержащего ненужные символы
        int maxIndex = cards.size();
        while (index <= maxIndex && flag) {
            text = cards.get(index).text();
            begIndex = text.indexOf(searchBegWord);
            substrCard = text.substring(begIndex - cardLength, begIndex - cardLengthRest);
            if (substrCard.equals(card)) { //нашли нужную карту
                flag = false;
                endIndex = text.indexOf(searchEndWord);
                var substr = trim(text.substring(begIndex + searchBegWord.length(), endIndex));
                balance = Integer.parseInt(substr);
            }
            ;
            index++;
        }
        return balance; //если карту не нашли, то нулевой остаток
    }

    public int getСardId(String card) {
        var searchBegWord = "баланс:";
        String text;
        int begIndex;
        boolean flag = true; // для выхода из цикла
        int index = 0;
        String substrCard; //для поиска подстроки
        int cardLength = 6; // длина строки с номером карточки
        int cardLengthRest = 2; // длина хвоста строки с номером карточки, содержащего ненужные символы
        int maxIndex = cards.size();
        while (index <= maxIndex && flag) {
            text = cards.get(index).text();
            begIndex = text.indexOf(searchBegWord);
            substrCard = text.substring(begIndex - cardLength, begIndex - cardLengthRest);
            if (substrCard.equals(card)) { //нашли нужную карту
                flag = false;
                return index;
            }
            index++;
        }
        return -1;
    }

    public String cardFullname(String card) { //для ввода волного номера карты
        if (card.equals("0001")) return "5559 0000 0000 0001";
        if (card.equals("0002")) return "5559 0000 0000 0002";
        return null;
    }

    public boolean moneyTransfer(String cardFrom, String cardTo, int amount) {
        var cardFrombalance = getСardBalance(cardFrom);
        var cardTobalance = getСardBalance(cardTo);

        if (amount <= 0) return false; // нельзя переводить ноль
        if (amount > cardFrombalance) return false; // нельзя переводить большую сумму, чем на балансе
        if (cardFrom.equals(cardTo)) return false; // нельзя переводить между одним и тем же счетом

        var cardToId = getСardId(cardFrom);
        buttons.get(cardToId).click();

        ElementsCollection inputs = $$(".input__control");
        inputs.get(0).sendKeys( Keys.CONTROL +"A",Keys.DELETE);
        inputs.get(0).setValue(Integer.toString(amount));
        inputs.get(1).sendKeys( Keys.CONTROL +"A",Keys.DELETE);
        inputs.get(1).setValue(cardFullname(cardTo));

        $$("button").find(exactText("Пополнить")).click();

        var cardFrombalanceUpdated = getСardBalance(cardFrom);
        var cardTobalanceUpdated = getСardBalance(cardTo);

        if ((cardFrombalanceUpdated - cardFrombalance != amount) || (cardTobalance - cardTobalanceUpdated != amount)) {
            return false;
        }
        else{
            return true;
        }
    }
}
