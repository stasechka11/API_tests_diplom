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
import ru.yandex.practicum.stellarburger.api.model.user.UserCredentials;
import ru.yandex.practicum.stellarburger.api.model.user.UserResponse;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@RunWith(Parameterized.class)
public class LoginUserParameterizedTest {
    private static final User user = User.getRandomUser();
    private UserCredentials userCredentialsIncorrect;
    private UserClient userClient;
    private String accessToken;

    public LoginUserParameterizedTest(UserCredentials userCredentialsIncorrect) {
        this.userCredentialsIncorrect = userCredentialsIncorrect;
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        UserResponse createUserResponsePOJO = userClient.createUser(user).as(UserResponse.class);
        accessToken = createUserResponsePOJO.getAccessToken();
    }

    @After
    public void clear() {
        userClient.deleteUser(accessToken);
    }

    @Parameterized.Parameters(name = "Test data: {0}")
    public static Object[][] getCredentials(){
        Faker faker = new Faker();
        return new Object[][]{
                {new UserCredentials("", "")},
                {new UserCredentials(user.getEmail(), "")},
                {new UserCredentials(user.getEmail(), faker.bothify("#?##????"))},
                {new UserCredentials("", user.getPassword())},
                {new UserCredentials(faker.bothify("#?##????@yandex.ru"), user.getPassword())}
        };
    }

    @Test
    @DisplayName("Check login with incorrect credentials")
    public void loginUserIncorrectDataTest() {
        Response responseLogin = userClient.loginUser(userCredentialsIncorrect);

        //Check response status code
        Assert.assertEquals(SC_UNAUTHORIZED, responseLogin.statusCode());

        GeneralResponse responseLoginPOJO = responseLogin.as(GeneralResponse.class);
        //Check response body
        Assert.assertFalse(responseLoginPOJO.isSuccess());
        Assert.assertEquals(UserClient.INCORRECT_CREDENTIALS_MESSAGE, responseLoginPOJO.getMessage());
    }
}
