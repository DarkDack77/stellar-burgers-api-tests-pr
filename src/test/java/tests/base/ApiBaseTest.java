package tests.base;

import api.clients.AuthClient;
import utils.UserData;
import model.User;
import org.junit.After;
import org.junit.Before;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;


public class ApiBaseTest {
    protected AuthClient authClient = new AuthClient();
    protected String accessToken;
    protected User testUser;

    @Before
    public void setUp() {
        // Создаём тестового пользователя перед каждым тестом
        testUser = UserData.getUniqueUser();
        Response response = authClient.register(testUser);
        response.then().statusCode(SC_OK);
        accessToken = response.jsonPath().getString("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            Response deleteResponse = authClient.deleteUser(accessToken);
            deleteResponse.then().statusCode(SC_ACCEPTED);
        }
    }
}