package ru.yandex.practicum.stellarburger.api.model.user;

import com.github.javafaker.Faker;

public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getRandomUser() {
        Faker faker = new Faker();
        String name = faker.name().username();
        String email = name + "@yandex.ru";
        String password = faker.regexify("\\d{6}[!@#$%^&*()]{3}");

        return new User(email, password,name);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
