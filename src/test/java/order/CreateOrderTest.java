package order;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburger.api.OrderClient;
import ru.yandex.practicum.stellarburger.api.UserClient;
import ru.yandex.practicum.stellarburger.api.model.GeneralResponse;
import ru.yandex.practicum.stellarburger.api.model.order.AvailableIngredients;
import ru.yandex.practicum.stellarburger.api.model.order.Ingredient;
import ru.yandex.practicum.stellarburger.api.model.order.Order;
import ru.yandex.practicum.stellarburger.api.model.order.OrderResponse;
import ru.yandex.practicum.stellarburger.api.model.user.User;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.apache.http.HttpStatus.*;

public class CreateOrderTest {
    UserClient userClient;
    User user;
    String accessToken;
    OrderClient orderClient;
    Order order;
    List<String> ingredientIdList;



    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getRandomUser();
        UserResponse createUserResponsePOJO = userClient.createUser(user).as(UserResponse.class);
        accessToken = createUserResponsePOJO.getAccessToken();
        orderClient = new OrderClient();
    }

    @After
    public void clear() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Check create order with random ingredients count")
    public void createOrderRandomIngredientsCountTest() {
        ingredientIdList = orderClient.getIdsListOfIngredients();
        int ingredientsCount = new Random().nextInt(ingredientIdList.size() - 1) + 1;
        order = new Order(orderClient.getRandomNIngredientIds(ingredientsCount, ingredientIdList));

        Response createOrderResponse = orderClient.createOrder(accessToken, order);
        //check status code
        Assert.assertEquals(SC_OK, createOrderResponse.statusCode());

        OrderResponse createOrderResponsePOJO = createOrderResponse.as(OrderResponse.class);
        //check response body
        Assert.assertTrue(createOrderResponsePOJO.isSuccess());
        Assert.assertEquals("done", createOrderResponsePOJO.getOrder().getStatus());
    }

    @Test
    @DisplayName("Check create order without ingredients")
    public void createOrderWithoutIngredientsTest() {
        order = new Order(List.of());

        Response createOrderResponse = orderClient.createOrder(accessToken, order);
        //check status code
        Assert.assertEquals(SC_BAD_REQUEST, createOrderResponse.statusCode());

        GeneralResponse createOrderResponsePOJO = createOrderResponse.as(GeneralResponse.class);
        //check response body
        Assert.assertFalse(createOrderResponsePOJO.isSuccess());
        Assert.assertEquals(OrderClient.NO_INGREDIENTS_MESSAGE, createOrderResponsePOJO.getMessage());
    }

    @Test
    @DisplayName("Check create order without authorization")
    public void createOrderNoAuthorizationTest() {
        ingredientIdList = orderClient.getIdsListOfIngredients();
        int ingredientsCount = new Random().nextInt(ingredientIdList.size() - 1) + 1;
        order = new Order(orderClient.getRandomNIngredientIds(ingredientsCount, ingredientIdList));

        Response createOrderResponse = orderClient.createOrder("", order);
        //check status code
        Assert.assertEquals(SC_OK, createOrderResponse.statusCode());

        OrderResponse createOrderResponsePOJO = createOrderResponse.as(OrderResponse.class);
        //check response body
        Assert.assertTrue(createOrderResponsePOJO.isSuccess());
        Assert.assertNull(createOrderResponsePOJO.getOrder().getStatus());
    }

    @Test
    @DisplayName("Check create order with incorrect ingredients ids")
    public void createOrderIncorrectIngredientsIdTest() {
        Faker faker = new Faker();
        List<String> ingredientsIdsList = Arrays.asList(faker.bothify("???###?##?##"), faker.bothify("???###?##?#####?#"));
        order = new Order(ingredientsIdsList);

        Response createOrderResponse = orderClient.createOrder(accessToken, order);
        //check status code
        Assert.assertEquals(SC_INTERNAL_SERVER_ERROR, createOrderResponse.statusCode());
    }
}
