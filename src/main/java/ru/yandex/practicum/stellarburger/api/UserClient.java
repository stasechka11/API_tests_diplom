package ru.yandex.practicum.stellarburger.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.stellarburger.api.model.user.User;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserClient extends BaseApiClient {
    public static final String BASE_PATH_USER = "/api/auth/";

    @Step ("Create user {user}")
    public Response createUser(User user) {
        return given()
                .spec(getReqSpec())
                .body(user)
                .when()
                .log().all()
                .post(BASE_URL + BASE_PATH_USER + "register");
    }

    @Step ("Get user info by accessToken: {accessToken}")
    public Response getUserInfo(String accessToken) {
        return given()
                .spec(getReqSpec())
                .header("Authorization", accessToken)
                .when()
                .log().all()
                .get(BASE_URL + BASE_PATH_USER + "user");
    }

    @Step ("Delete user by accessToken: {accessToken}")
    public void deleteUser(String accessToken) {
        given()
                .spec(getReqSpec())
                .header("Authorization", accessToken)
                .when()
                .log().all()
                .delete(BASE_URL + BASE_PATH_USER + "user")
                .then()
                .assertThat()
                .statusCode(SC_ACCEPTED);
    }
}
