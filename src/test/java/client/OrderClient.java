package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import model.OrderData;

import static io.restassured.RestAssured.given;
import static utils.Config.API_PATH;
import static utils.Config.BASE_URL;

public class OrderClient {

    public Response createOrder(OrderData orderData) {
        return given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(orderData)
                .post(API_PATH + "/orders");
    }

    public Response createOrderAuthorized(OrderData orderData, String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(orderData)
                .post(API_PATH + "/orders");
    }
}
