package ru.yandex.practicum.stellarburger.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.stellarburger.api.model.user.User;
import ru.yandex.practicum.stellarburger.api.model.user.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;

public class UserClient extends BaseApiClient {
    public static final String BASE_PATH_USER = "/api/auth/";
    //create user messages
    public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists";
    public static final String NOT_ALL_REQUIRED_FIELDS_MESSAGE = "Email, password and name are required fields";
    //login user messages
    public static final String INCORRECT_CREDENTIALS_MESSAGE = "email or password are incorrect";
    //change user data messages
    public static final String EMAIL_ALREADY_EXIST_MESSAGE = "User with such email already exists";
    public static final String SHOULD_BE_AUTHORISED_MESSAGE = "You should be authorised";

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

    @Step ("Login user {userCredentials}")
    public Response loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getReqSpec())
                .body(userCredentials)
                .when()
                .log().all()
                .post(BASE_URL + BASE_PATH_USER + "login");

    }

    @Step("Change user data to {user}")
    public Response changeUserData(String accessToken, User user){
        return given()
                .spec(getReqSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .log().all()
                .patch(BASE_URL + BASE_PATH_USER + "user");
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
