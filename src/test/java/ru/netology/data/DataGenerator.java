package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationInfo generateRusUser(String city) {
            Faker faker = new Faker(new Locale("ru"));
            String fullName = faker.name().fullName();
            String[] words = fullName.split(" ");
            String userName = words[0] + " " + words[1];
            return new RegistrationInfo(
                    city,
                    userName,
                    faker.phoneNumber().phoneNumber());
        }

        public static String dateMeeting(int daysAfter) {
            return LocalDate.now().plusDays(daysAfter).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        public static void fillOutTheApplication(RegistrationInfo user, String date) {
            $("[data-test-id='city'] input").setValue(user.getCity());
            $("[data-test-id='name'] input").setValue(user.getUserName());
            $(".calendar-input input").doubleClick().sendKeys("BackSpace");
            $("[data-test-id='date'] input").setValue(date);
            $("[data-test-id='phone'] input").setValue(user.getPhoneNumber());
            $(".checkbox__box").click();
        }

        public static void fillInTheDate(String date) {
            $(".calendar-input input").doubleClick().sendKeys("BackSpace");
            $("[data-test-id='date'] input").setValue(date);
        }
    }
}
