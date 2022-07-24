package user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburger.api.UserClient;
import ru.yandex.practicum.stellarburger.api.model.GeneralResponse;
import ru.yandex.practicum.stellarburger.api.model.user.User;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;

import static org.apache.http.HttpStatus.*;

public class ChangeUserDataTest {
    User user;
    User user2;
    UserClient userClient;
    String accessToken;
    String accessToken2;

    @Before
    public void setUp() {
        user = User.getRandomUser();
        userClient = new UserClient();
        UserResponse createUserResponsePOJO = userClient.createUser(user).as(UserResponse.class);
        accessToken = createUserResponsePOJO.getAccessToken();
    }

    @After
    public void clear() {
        userClient.deleteUser(accessToken);
        if(accessToken2 != null){
            userClient.deleteUser(accessToken2);
        }
    }

    @Test
    @DisplayName("Check changing name in user data")
    public void changeUserNameTest() {
        Faker faker = new Faker();
        String newName = faker.name().username();
        user.setName(newName);
        Response changeUserResponse = userClient.changeUserData(accessToken, user);
        //Check response status code
        Assert.assertEquals(SC_OK, changeUserResponse.statusCode());

        UserResponse changeUserResponsePOJO = changeUserResponse.as(UserResponse.class);
        //Check response body
        Assert.assertTrue(changeUserResponsePOJO.isSuccess());
        Assert.assertEquals(newName, changeUserResponsePOJO.getUser().getName());
    }

    @Test
    @DisplayName("Check changing email in user data")
    public void changeUserEmail() {
        Faker faker = new Faker();
        String newEmail = faker.bothify("#?##????@yandex.ru");
        user.setEmail(newEmail);
        Response changeUserResponse = userClient.changeUserData(accessToken, user);
        //Check response status code
        Assert.assertEquals(SC_OK, changeUserResponse.statusCode());

        UserResponse changeUserResponsePOJO = changeUserResponse.as(UserResponse.class);
        //Check response body
        Assert.assertTrue(changeUserResponsePOJO.isSuccess());
        Assert.assertEquals(newEmail, changeUserResponsePOJO.getUser().getEmail());
    }

    @Test
    @DisplayName("Check changing password in user data")
    public void changeUserPasswordTest() {
        Faker faker = new Faker();
        String newPassword = faker.bothify("#?##????");
        user.setPassword(newPassword);

        Response changeUserResponse = userClient.changeUserData(accessToken, user);
        //Check response status code
        Assert.assertEquals(SC_OK, changeUserResponse.statusCode());

        UserResponse changeUserResponsePOJO = changeUserResponse.as(UserResponse.class);
        //Check response body
        Assert.assertTrue(changeUserResponsePOJO.isSuccess());
    }

    @Test
    @DisplayName("Check changing user email to already existing")
    public void changeUserEmailAlreadyExistTest() {
        user2 = User.getRandomUser();
        UserResponse createUserResponsePOJO = userClient.createUser(user2).as(UserResponse.class);
        accessToken2 = createUserResponsePOJO.getAccessToken();
        String email2 = createUserResponsePOJO.getUser().getEmail();
        user.setEmail(email2);

        Response changeUserResponse = userClient.changeUserData(accessToken, user);
        //Check response status code
        Assert.assertEquals(SC_FORBIDDEN, changeUserResponse.statusCode());

        GeneralResponse changeUserResponsePOJO = changeUserResponse.as(GeneralResponse.class);
        //Check response body
        Assert.assertFalse(changeUserResponsePOJO.isSuccess());
        Assert.assertEquals(UserClient.EMAIL_ALREADY_EXIST_MESSAGE, changeUserResponsePOJO.getMessage());
    }

    @Test
    @DisplayName("Check changing user data without authorization")
    public void changeUserDataNoAuthorizationTest() {
        user2 = User.getRandomUser();
        user.setEmail(user2.getEmail());
        user.setPassword(user2.getPassword());
        user.setName(user2.getName());

        Response changeUserResponse = userClient.changeUserData("", user);
        //Check response status code
        Assert.assertEquals(SC_UNAUTHORIZED, changeUserResponse.statusCode());

        GeneralResponse changeUserResponsePOJO = changeUserResponse.as(GeneralResponse.class);
        //Check response body
        Assert.assertFalse(changeUserResponsePOJO.isSuccess());
        Assert.assertEquals(UserClient.SHOULD_BE_AUTHORISED_MESSAGE, changeUserResponsePOJO.getMessage());
    }
}
