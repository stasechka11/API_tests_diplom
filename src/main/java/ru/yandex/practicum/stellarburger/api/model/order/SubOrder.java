package ru.yandex.practicum.stellarburger.api.model.order;

import java.util.Date;
import java.util.List;

public class SubOrder {
    private List<Ingredient> ingredients;
    private String _id;
    private Owner owner;
    private String status;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private int number;
    private int price;

    public SubOrder(List<Ingredient> ingredients, String _id, Owner owner, String status, String name, Date createdAt, Date updatedAt, int number, int price) {
        this.ingredients = ingredients;
        this._id = _id;
        this.owner = owner;
        this.status = status;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.number = number;
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getNumber() {
        return number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SubOrder{" +
                "_id='" + _id + '\'' +
                ", owner=" + owner +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
