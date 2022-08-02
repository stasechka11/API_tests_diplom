package ru.yandex.practicum.stellarburger.api.model.user;

public class UserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

    public UserResponse(boolean success, User user, String accessToken, String refreshToken) {
        this.success = success;
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "success=" + success +
                ", user=" + user +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
