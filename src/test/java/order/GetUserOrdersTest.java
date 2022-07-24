package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburger.api.OrderClient;
import ru.yandex.practicum.stellarburger.api.UserClient;
import ru.yandex.practicum.stellarburger.api.model.order.Order;
import ru.yandex.practicum.stellarburger.api.model.order.OrderResponse;
import ru.yandex.practicum.stellarburger.api.model.order.UserOrdersResponse;
import ru.yandex.practicum.stellarburger.api.model.user.User;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;

import java.util.List;
import java.util.Random;

import static org.apache.http.HttpStatus.SC_OK;

public class GetUserOrdersTest {
    UserClient userClient;
    User user;
    String accessToken;
    OrderClient orderClient;
    Order order;
    String orderId;
    List<String> ingredientIdList;


    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandomUser();
        UserResponse createUserResponsePOJO = userClient.createUser(user).as(UserResponse.class);
        accessToken = createUserResponsePOJO.getAccessToken();

        orderClient = new OrderClient();
        ingredientIdList = orderClient.getIdsListOfIngredients();
        int ingredientsCount = new Random().nextInt(ingredientIdList.size() - 1) + 1;
        order = new Order(orderClient.getRandomNIngredientIds(ingredientsCount, ingredientIdList));
        OrderResponse createOrderResponsePOJO = orderClient.createOrder(accessToken, order).as(OrderResponse.class);
        orderId = createOrderResponsePOJO.getOrder().get_id();
    }

    @After
    public void clear() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Check get user orders api")
    public void getUserOrdersTest() {
        Response getUserOrdersResponse = orderClient.getUserOrders(accessToken);

        //check status code
        Assert.assertEquals(SC_OK, getUserOrdersResponse.statusCode());

        UserOrdersResponse getUserOrdersResponsePOJO = getUserOrdersResponse.as(UserOrdersResponse.class);
        //check response body
        Assert.assertTrue(getUserOrdersResponsePOJO.getSuccess());
        Assert.assertNotNull(getUserOrdersResponsePOJO.getOrders());
    }
}
