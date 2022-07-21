import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburger.api.UserClient;
import ru.yandex.practicum.stellarburger.api.model.user.GeneralResponse;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;
import ru.yandex.practicum.stellarburger.api.model.user.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserTest {
    User user;
    UserClient userClient;
    String accessToken;

    @Before
    public void setUp() {
        user = User.getRandomUser();
        userClient = new UserClient();
    }

    @After
    public void clear() {
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Check create user with correct data /api/auth/register")
    public void createUserCorrectDataTest() {
        Response responseCreate = userClient.createUser(user);

        //Check response status code
        Assert.assertEquals(SC_OK, responseCreate.statusCode());

        UserResponse responseCreateUserPOJO = responseCreate.as(UserResponse.class);
        accessToken = responseCreateUserPOJO.getAccessToken();

        //Check response body
        Assert.assertTrue(responseCreateUserPOJO.isSuccess());

        //Check user exists
        Response responseGet = userClient.getUserInfo(accessToken);

        UserResponse responseGetInfoPOJO = responseGet.as(UserResponse.class);
        Assert.assertTrue(responseGetInfoPOJO.isSuccess());
    }

    @Test
    @DisplayName("Check create user with existing name")
    public void createUserNameExistsTest() {
        Response responseCreate = userClient.createUser(user);
        UserResponse responseCreateUserPOJO = responseCreate.as(UserResponse.class);
        accessToken = responseCreateUserPOJO.getAccessToken();

        Response responseCreateDuplicate = userClient.createUser(user);
        GeneralResponse responseCreateDuplicateUserPOJO = responseCreateDuplicate.as(GeneralResponse.class);

        //Check response status code
        Assert.assertEquals(SC_FORBIDDEN, responseCreateDuplicate.statusCode());

        //Check response body
        Assert.assertFalse(responseCreateDuplicateUserPOJO.isSuccess());
        Assert.assertEquals(UserClient.USER_ALREADY_EXISTS_MESSAGE, responseCreateDuplicateUserPOJO.getMessage());
    }
}
