package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.by;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.SetValueOptions.withText;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement transferHead = $(byText("Пополнение карты"));

    private SelenideElement amountButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public TransferPage() {
        transferHead.shouldBe(Condition.visible);
    }

    public DashboardPage makeValidTransfer(String amountTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountTransfer, DataHelper.CardInfo cardInfo) {
        amount.setValue(amountTransfer);
        from.setValue(cardInfo.getCardNumber());
        amountButton.click();
    }

    public void findErrorMessage(String errorText) {
        errorNotification.shouldBe(visible).shouldHave(exactText(errorText), Duration.ofSeconds(15));
    }


}
