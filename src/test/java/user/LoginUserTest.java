package user;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburger.api.UserClient;
import ru.yandex.practicum.stellarburger.api.model.user.User;
import ru.yandex.practicum.stellarburger.api.model.user.UserCredentials;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;

import static org.apache.http.HttpStatus.SC_OK;

public class LoginUserTest {
    User user;
    UserCredentials userCredentials;
    UserClient userClient;
    String accessToken;

    @Before
    public void setUp() {
        user = User.getRandomUser();
        userClient = new UserClient();
        UserResponse createUserResponse = userClient.createUser(user).as(UserResponse.class);
        userCredentials = new UserCredentials(user.getEmail(), user.getPassword());
        accessToken = createUserResponse.getAccessToken();
    }

    @After
    public void clear() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Login user with correct credentials /api/auth/login")
    public void loginUserCorrectCredentialsTest() {
        Response responseLogin = userClient.loginUser(userCredentials);

        //Check response status code
        Assert.assertEquals(SC_OK, responseLogin.statusCode());

        UserResponse responseLoginPOJO = responseLogin.as(UserResponse.class);
        //Check response body
        Assert.assertTrue(responseLoginPOJO.isSuccess());
    }

}
