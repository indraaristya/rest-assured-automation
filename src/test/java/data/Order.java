package data;

public class Order {
    private int order_id;
    private String order_desccription;
    private String order_status;
    private Boolean special_order;
    private Long last_updated_timestamp;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_desccription() {
        return order_desccription;
    }

    public void setOrder_desccription(String order_desccription) {
        this.order_desccription = order_desccription;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Boolean getSpecial_order() {
        return special_order;
    }

    public void setSpecial_order(Boolean special_order) {
        this.special_order = special_order;
    }

    public Long getLast_updated_timestamp() {
        return this.last_updated_timestamp;
    }

    public void setLast_updated_timestamp(Long time) {
        this.last_updated_timestamp = time;
    }

    public Order(OrderBuilder order) {
        this.order_id = order.order_id;
        this.order_desccription = order.order_desccription;
        this.order_status = order.order_status;
        this.special_order = order.special_order;
        this.last_updated_timestamp = order.last_updated_timestamp;
    }

    public static class OrderBuilder {
        private int order_id;
        private String order_desccription;
        private String order_status;
        private Boolean special_order;
        private Long last_updated_timestamp;

        public OrderBuilder(int id, String desc, String status, Boolean special, String timestamp) {
            this.order_id = id;
            this.order_desccription = desc;
            this.order_status = status;
            this.special_order = special;
            this.last_updated_timestamp = Long.parseLong(timestamp);
        }

        public Order build() {
            return new Order(this);
        }

    }
    
}
