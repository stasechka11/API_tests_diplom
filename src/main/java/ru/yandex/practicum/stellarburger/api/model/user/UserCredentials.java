package ru.yandex.practicum.stellarburger.api.model.user;

public class UserCredentials {
    public String email;
    public String password;

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "login='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
