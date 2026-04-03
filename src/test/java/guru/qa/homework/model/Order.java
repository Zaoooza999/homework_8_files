package guru.qa.homework.model;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private Customer customer;
    private ArrayList<Item> items;
    private Boolean paid;
    private Delivery delivery;

    public String getOrderId() {
        return orderId;
    }


    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Delivery getDelivery() {
        return delivery;
    }
}
