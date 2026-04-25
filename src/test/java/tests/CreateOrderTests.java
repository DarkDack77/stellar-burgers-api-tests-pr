package tests;

import api.clients.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.Order;
import org.junit.Test;
import tests.base.ApiBaseTest;
import utils.OrderData;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@DisplayName("Order creation tests")
public class CreateOrderTests extends ApiBaseTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Create order with authorization and ingredients")
    @Description("An authorized user can create an order with valid ingredients")
    public void canCreateOrderWithAuthAndIngredients() {
        Order order = OrderData.getOrderWithIngredients();

        orderClient.createOrder(order, accessToken)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Create order without authorization")
    @Description("A guest can create an order with valid ingredients")
    public void canCreateOrderWithoutAuth() {
        Order order = OrderData.getOrderWithIngredients();

        orderClient.createOrderWithoutAuth(order)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Create order without ingredients")
    @Description("An order cannot be created without ingredient ids")
    public void cantCreateOrderWithoutIngredients() {
        Order emptyOrder = OrderData.getEmptyOrder();

        orderClient.createOrder(emptyOrder, accessToken)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create order with invalid ingredient")
    @Description("An order cannot be created with an invalid ingredient id")
    public void cantCreateOrderWithInvalidIngredients() {
        Order invalidOrder = OrderData.getOrderWithInvalidIngredients();

        orderClient.createOrder(invalidOrder, accessToken)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("One or more ids provided are incorrect"));
    }
}
