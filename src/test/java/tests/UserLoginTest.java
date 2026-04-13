package tests;

import client.UserClient;
import io.restassured.response.Response;
import model.UserCredentials;
import model.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.RandomData;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {

    private final UserClient userClient = new UserClient();
    private UserData user;
    private String accessToken;

    @Before
    public void setUp() {
        user = RandomData.randomUser();
        Response response = userClient.createUser(user);
        accessToken = response.then().extract().path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    public void loginWithExistingUserShouldReturnSuccess() {
        UserCredentials credentials = new UserCredentials(user.getEmail(), user.getPassword());

        Response response = userClient.login(credentials);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void loginWithInvalidCredentialsShouldReturnError() {
        UserCredentials credentials = new UserCredentials("wrong@mail.com", "wrongpass");

        Response response = userClient.login(credentials);

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
