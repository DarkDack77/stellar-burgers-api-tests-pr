package api.clients;

import api.ApiClient;
import io.qameta.allure.Step;
import model.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class AuthClient extends ApiClient {
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String DELETE_ENDPOINT = "/api/auth/user";

    public Response register(User user) {
        return post(REGISTER_ENDPOINT, user);
    }

    public Response login(User user) {
        return post(LOGIN_ENDPOINT, user);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .log().all()
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URL + DELETE_ENDPOINT)
                .then()
                .log().ifError()
                .extract().response();
    }

}
