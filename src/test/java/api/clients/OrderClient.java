package api.clients;

import api.ApiClient;
import model.Order;
import io.restassured.response.Response;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class OrderClient extends ApiClient {
    private static final String CREATE_ORDER_ENDPOINT = "/api/orders";

    @Step("Создание заказа с авторизацией")
    public Response createOrder(Order order, String accessToken) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(BASE_URL + CREATE_ORDER_ENDPOINT)
                .then()
                .log().ifError()
                .extract().response();
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuth(Order order) {
        return post(CREATE_ORDER_ENDPOINT, order);
    }
}
