package ru.yandex.practicum.stellarburger.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.stellarburger.api.model.order.AvailableIngredients;
import ru.yandex.practicum.stellarburger.api.model.order.Ingredient;
import ru.yandex.practicum.stellarburger.api.model.order.Order;
import ru.yandex.practicum.stellarburger.api.model.order.UserOrders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApiClient {
    public static final String BASE_PATH_ORDER = "/api/orders";
    public static final String NO_INGREDIENTS_MESSAGE = "Ingredient ids must be provided";
    public static final String SHOULD_BE_AUTHORISED = "You should be authorised";

    @Step("Get available ingredients")
    public AvailableIngredients getAvailableIngredients() {
        return
                given()
                        .spec(getReqSpec())
                        .when()
                        .log().all()
                        .get(BASE_URL + "/api/ingredients")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .and()
                        .extract().as(AvailableIngredients.class);
    }

    @Step("Create order {order}")
    public Response createOrder(String accessToken, Order order) {
        return
                given()
                        .spec(getReqSpec())
                        .header("Authorization", accessToken)
                        .body(order)
                        .when()
                        .log().all()
                        .post(BASE_URL + BASE_PATH_ORDER);
    }

    @Step("Get ids list of available ingredients")
    public List<String> getIdsListOfIngredients() {
        AvailableIngredients availableIngredients = getAvailableIngredients();
        List<Ingredient> ingredientList = availableIngredients.getIngredientsList();
        List<String> ingredientIdList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            ingredientIdList.add(ingredient.get_id());
        }
        return ingredientIdList;
    }

    @Step("Get list with random {idCount} ingredients")
    public List<String> getRandomNIngredientIds(int idCount, List<String> ingredientsIdsList) {
        Collections.shuffle(ingredientsIdsList);
        return ingredientsIdsList.subList(0, idCount);
    }

    @Step("Get user orders ids")
    public List<String> getUserOrdersIds(List<UserOrders> userOrdersList) {
        List<String> orderIdList = new ArrayList<>();
        for (UserOrders userOrders : userOrdersList)
        {
            orderIdList.add(userOrders.get_id());
        }
        return orderIdList;
    }

    @Step("Get user orders with accessToken: {accessToken}")
    public Response getUserOrders(String accessToken){
        return
                given()
                        .spec(getReqSpec())
                        .header("Authorization", accessToken)
                        .when()
                        .log().all()
                        .get(BASE_URL + BASE_PATH_ORDER);
    }
}
