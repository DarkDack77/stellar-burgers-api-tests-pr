package utils;

import io.qameta.allure.Step;
import model.Order;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderData {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private static final String INVALID_INGREDIENT = "invalid_hash";

    @Step("Create order with valid ingredients")
    public static Order getOrderWithIngredients() {
        List<String> ingredients = given()
                .when()
                .get(BASE_URL + "/api/ingredients")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("data._id");

        return new Order(ingredients.subList(0, 2));
    }

    @Step("Create empty order")
    public static Order getEmptyOrder() {
        return new Order(null);
    }

    @Step("Create order with invalid ingredient")
    public static Order getOrderWithInvalidIngredients() {
        return new Order(List.of(INVALID_INGREDIENT));
    }
}
