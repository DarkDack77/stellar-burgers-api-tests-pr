package api;

import io.restassured.response.Response;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class ApiClient {
    protected static final String BASE_URL = "https://stellarburgers.education-services.ru";

    @Step("Отправка POST‑запроса ")
    public Response post(String endpoint, Object requestBody) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(BASE_URL + endpoint)
                .then()
                .log().ifError()
                .extract().response();
    }

    @Step("Отправка GET‑запроса с токеном авторизации")
    public Response get(String endpoint, String token) {
        if (token != null) {
            return given()
                    .log().all()
                    .header("Authorization", token)
                    .when()
                    .get(BASE_URL + endpoint)
                    .then()
                    .log().ifError()
                    .extract().response();
        } else {
            return given()
                    .log().all()
                    .when()
                    .get(BASE_URL + endpoint)
                    .then()
                    .log().ifError()
                    .extract().response();
        }
    }
}
