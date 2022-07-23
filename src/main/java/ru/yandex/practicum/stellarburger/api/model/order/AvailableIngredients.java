package ru.yandex.practicum.stellarburger.api.model.order;

import java.util.List;

public class AvailableIngredients {
    boolean success;
    List<Ingredient> data;

    public AvailableIngredients(boolean success, List<Ingredient> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredient> getIngredientsList() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}
