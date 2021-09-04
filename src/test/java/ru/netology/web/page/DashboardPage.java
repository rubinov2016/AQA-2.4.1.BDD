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
    private final String searchBegWord = "баланс:";
    private final String searchEndWord = "р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getСardBalance(String card) {
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



}
