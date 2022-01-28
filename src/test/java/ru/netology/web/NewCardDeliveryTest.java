package ru.netology.web;

import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationInfo;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class NewCardDeliveryTest {

    @Test
    public void shouldSendRepeatedApplication() {
        RegistrationInfo info = DataGenerator.Registration.generateRusUser("Москва");
        String testDate = DataGenerator.Registration.dateMeeting(5);
        open("http://localhost:9999");
        DataGenerator.Registration.fillOutTheApplication(info, testDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + testDate));
        String newTestDate = DataGenerator.Registration.dateMeeting(14);
        DataGenerator.Registration.fillInTheDate(newTestDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] .button__text").click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно запланирована на " + newTestDate));
    }
}
