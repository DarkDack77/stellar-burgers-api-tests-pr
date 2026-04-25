package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.User;
import org.junit.Test;
import tests.base.ApiBaseTest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("User login tests")
public class LoginUserTests extends ApiBaseTest {

    @Test
    @DisplayName("Login existing user")
    @Description("A user can log in with valid credentials")
    public void canLoginExistingUser() {
        authClient.login(testUser)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(testUser.email))
                .body("user.name", equalTo(testUser.name))
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Login with wrong email")
    @Description("A user cannot log in with an incorrect email")
    public void cantLoginWithWrongEmail() {
        User wrongUser = new User("wrong_" + testUser.email, testUser.password, testUser.name);

        authClient.login(wrongUser)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login with wrong password")
    @Description("A user cannot log in with an incorrect password")
    public void cantLoginWithWrongPassword() {
        User wrongUser = new User(testUser.email, "wrong_" + testUser.password, testUser.name);

        authClient.login(wrongUser)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login without email")
    @Description("A user cannot log in without email")
    public void cantLoginWithoutEmail() {
        User noEmailUser = new User(null, testUser.password, testUser.name);

        authClient.login(noEmailUser)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login without password")
    @Description("A user cannot log in without password")
    public void cantLoginWithoutPassword() {
        User noPasswordUser = new User(testUser.email, null, testUser.name);

        authClient.login(noPasswordUser)
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
