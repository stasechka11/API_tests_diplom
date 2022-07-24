package ru.yandex.practicum.stellarburger.api.model.order;

import java.util.Date;
import java.util.List;

public class GetOrders {
    String _id;
    List<String> ingredients;
    String status;
    String name;
    Date createdAt;
    Date updatedAt;
    int number;

    public GetOrders(String _id, List<String> ingredients, String status, String name, Date createdAt, Date updatedAt, int number) {
        this._id = _id;
        this.ingredients = ingredients;
        this.status = status;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.number = number;
    }

    public String get_id() {
        return _id;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
