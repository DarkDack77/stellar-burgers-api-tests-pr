package utils;

import io.qameta.allure.Step;
import model.User;

import java.util.UUID;

public class UserData {
    @Step("Генерация данных для уникального пользователя")
    public static User getUniqueUser() {
        String uniqueEmail = "test_" + UUID.randomUUID().toString() + "@yandex.ru";
        String uniquePassword = "password123";
        String uniqueName = "Test User";

        return new User(uniqueEmail, uniquePassword, uniqueName);
    }

    @Step("Генерация пользователя с отсутствующим полем email")
    public static User getUserWithoutEmail() {
        return new User(null, "password", "Name");
    }

    @Step("Генерация пользователя с отсутствующим полем password")
    public static User getUserWithoutPassword() {
        return new User("test@yandex.ru", null, "Name");
    }

    @Step("Генерация пользователя с отсутствующим полем name")
    public static User getUserWithoutName() {
        return new User("test@yandex.ru", "password", null);
    }
}
