package ru.yandex.practicum.stellarburger.api.model.order;

import java.util.List;

public class UserOrdersResponse {
    boolean success;
    List<UserOrders> orders;
    int total;
    int totalToday;

    public UserOrdersResponse(boolean success, List<UserOrders> orders, int total, int totalToday) {
        this.success = success;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public boolean getSuccess() {
        return success;
    }

    public List<UserOrders> getOrders() {
        return orders;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    @Override
    public String toString() {
        return "UserOrdersResponse{" +
                "success='" + success + '\'' +
                ", orders=" + orders +
                ", total=" + total +
                ", totalToday=" + totalToday +
                '}';
    }
}
