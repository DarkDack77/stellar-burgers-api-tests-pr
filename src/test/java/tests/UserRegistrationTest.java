package tests;

import client.UserClient;
import io.restassured.response.Response;
import model.UserData;
import org.junit.After;
import org.junit.Test;
import utils.RandomData;

import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationTest {

    private final UserClient userClient = new UserClient();
    private String accessToken;

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    public void createUniqueUserShouldReturnSuccess() {
        UserData user = RandomData.randomUser();

        Response response = userClient.createUser(user);
        accessToken = response.then().extract().path("accessToken");

        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createExistingUserShouldReturnError() {
        UserData user = RandomData.randomUser();

        Response firstResponse = userClient.createUser(user);
        accessToken = firstResponse.then().extract().path("accessToken");

        Response secondResponse = userClient.createUser(user);

        secondResponse.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    public void createUserWithoutEmailShouldReturnError() {
        UserData user = new UserData(null, "pass123", "Name");

        Response response = userClient.createUser(user);

        response.then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
