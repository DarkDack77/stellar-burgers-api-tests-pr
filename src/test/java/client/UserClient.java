package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import model.UserCredentials;
import model.UserData;

import static io.restassured.RestAssured.given;
import static utils.Config.API_PATH;
import static utils.Config.BASE_URL;

public class UserClient {

    public Response createUser(UserData user) {
        return given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(user)
                .post(API_PATH + "/auth/register");
    }

    public Response login(UserCredentials credentials) {
        return given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(credentials)
                .post(API_PATH + "/auth/login");
    }

    public Response deleteUser(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .header("Authorization", accessToken)
                .delete(API_PATH + "/auth/user");
    }
}
