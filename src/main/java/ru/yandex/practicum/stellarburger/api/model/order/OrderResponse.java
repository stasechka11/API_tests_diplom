package ru.yandex.practicum.stellarburger.api.model.order;

import java.util.List;

public class OrderResponse {
    private boolean success;
    private String name;
    private SubOrder order;

    public OrderResponse(boolean success, String name, SubOrder order) {
        this.success = success;
        this.name = name;
        this.order = order;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubOrder getOrder() {
        return order;
    }

    public void setOrder(SubOrder order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "success=" + success +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
