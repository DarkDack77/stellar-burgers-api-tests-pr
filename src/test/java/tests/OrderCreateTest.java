package tests;

import client.OrderClient;
import client.UserClient;
import io.restassured.response.Response;
import model.OrderData;
import model.UserData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.RandomData;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreateTest {

    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    private String accessToken;
    private UserData user;

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
    public void createOrderWithInvalidIngredientHashShouldReturnBadRequest() {
        OrderData orderData = new OrderData(Collections.singletonList("invalid_hash"));

        Response response = orderClient.createOrder(orderData);

        response.then()
                .statusCode(400);
    }

    @Test
    public void createOrderWithoutAuthorizationShouldReturnSuccess() {
        OrderData orderData = new OrderData(Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        ));

        Response response = orderClient.createOrder(orderData);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    public void createOrderWithIngredientsShouldReturnSuccess() {
        OrderData orderData = new OrderData(Arrays.asList(
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82001bdaaa6f"
        ));

        Response response = orderClient.createOrder(orderData);

        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    public void createOrderWithoutIngredientsShouldReturnError() {
        OrderData orderData = new OrderData(Collections.emptyList());

        Response response = orderClient.createOrder(orderData);

        response.then()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }
}
