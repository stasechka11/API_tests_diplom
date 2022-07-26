package user;

import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellarburger.api.UserClient;
import ru.yandex.practicum.stellarburger.api.model.GeneralResponse;
import ru.yandex.practicum.stellarburger.api.model.user.User;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

@RunWith(Parameterized.class)
public class CreateUserParameterizedTest {
    private User user;
    private UserClient userClient;
    private Response responseCreate;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void clear() {
        UserResponse userResponsePOJO = responseCreate.as(UserResponse.class);
        if (userResponsePOJO.isSuccess()) {
          String accessToken = userResponsePOJO.getAccessToken();
            userClient.deleteUser(accessToken);
        }
    }

    public CreateUserParameterizedTest(User user) {
        this.user = user;
    }

    @Parameterized.Parameters(name = "Test data: {0}")
    public static Object[][] getUserData() {
        Faker faker = new Faker();
        return new Object[][]{
                {new User("", "", "")},
                {new User("", faker.bothify("#?##????"), faker.name().username())},
                {new User(faker.bothify("#?##????@yandex.ru"), "", faker.name().username())},
                {new User(faker.bothify("#?##????@yandex.ru"), faker.bothify("#?##????"), "")}
        };
    }

    @Test
    @DisplayName("Check create user without required fields")
    public void createUserNotAllDataTest() {
        responseCreate = userClient.createUser(user);

        //Check response status code
        Assert.assertEquals(SC_FORBIDDEN, responseCreate.statusCode());

        GeneralResponse responseCreateUserPOJO = responseCreate.as(GeneralResponse.class);
        //Check response body
        Assert.assertFalse(responseCreateUserPOJO.isSuccess());
        Assert.assertEquals(UserClient.NOT_ALL_REQUIRED_FIELDS_MESSAGE, responseCreateUserPOJO.getMessage());
    }
}
