package utils;

import com.github.javafaker.Faker;
import model.UserData;

public class RandomData {
    private static final Faker FAKER = new Faker();

    public static UserData randomUser() {
        String email = "qa_" + System.currentTimeMillis() + "_" + FAKER.number().digits(4) + "@mail.com";
        String password = "pass123";
        String name = FAKER.name().firstName();
        return new UserData(email, password, name);
    }
}
