package controller;

import jakarta.validation.constraints.NotEmpty;

public class OrderRequest {
    @NotEmpty
    private int order_id;

    @NotEmpty
    private String order_desccription;

    @NotEmpty
    private String order_status;

    @NotEmpty
    private Boolean special_order;

    @NotEmpty
    private Long last_updated_timestamp;

    public int getOrderId() {
        return this.order_id;
    }

    public String getDesc() {
        return this.order_desccription;
    }

    public String getStatus() {
        return this.order_status;
    }

    public Boolean getSpecialOrder() {
        return this.special_order;
    }

    public Long getUpdatedTime() {
        return this.last_updated_timestamp;
    }
}
