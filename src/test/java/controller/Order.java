package controller;

import java.util.Date;

public class Order {
    private int order_id;
    private String order_description;
    private String order_status;
    private Long last_updated_timestamp;
    private Boolean special_order;


    public Order() {

    };

    public Order(int id, String description, String status, Boolean special_order) {
        this.order_id = id;
        this.order_description = description;
        this.order_status = status;
        this.last_updated_timestamp = new Date().getTime();
        this.special_order = special_order;
    }

    public void setId(int id) {
        this.order_id = id;
    }

    public void setDescription(String desc) {
        this.order_description = desc;
    }

    public void setStatus(String status) {
        this.order_status = status;
    }

    public void setTimestamp(Long time) {
        this.last_updated_timestamp = time;
    }

    public void setSpecialOrder(Boolean special) {
        this.special_order = special;
    }
}
