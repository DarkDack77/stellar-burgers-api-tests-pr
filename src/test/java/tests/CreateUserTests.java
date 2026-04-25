package tests;

import api.clients.AuthClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserData;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

@DisplayName("User creation tests")
public class CreateUserTests {

    private final AuthClient authClient = new AuthClient();
    private String accessToken;

    @Test
    @DisplayName("Create unique user")
    @Description("A user can be registered with valid unique data")
    public void canCreateUniqueUser() {
        User createdUser = UserData.getUniqueUser();

        accessToken = authClient.register(createdUser)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(createdUser.email))
                .body("user.name", equalTo(createdUser.name))
                .extract()
                .jsonPath()
                .getString("accessToken");
    }

    @Test
    @DisplayName("Create existing user")
    @Description("A user cannot be registered twice with the same email")
    public void cantCreateExistingUser() {
        User createdUser = UserData.getUniqueUser();
        accessToken = authClient.register(createdUser)
                .then()
                .statusCode(SC_OK)
                .extract()
                .jsonPath()
                .getString("accessToken");

        authClient.register(createdUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Create user without email")
    @Description("A user cannot be registered without email")
    public void cantCreateUserWithoutEmail() {
        authClient.register(UserData.getUserWithoutEmail())
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without password")
    @Description("A user cannot be registered without password")
    public void cantCreateUserWithoutPassword() {
        authClient.register(UserData.getUserWithoutPassword())
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without name")
    @Description("A user cannot be registered without name")
    public void cantCreateUserWithoutName() {
        authClient.register(UserData.getUserWithoutName())
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void cleanup() {
        if (accessToken != null) {
            authClient.deleteUser(accessToken).then().statusCode(SC_ACCEPTED);
        }
    }
}
