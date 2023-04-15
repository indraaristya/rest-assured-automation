package com.test;

import data.Order;

public class APIResponse {
    private String status;
    private String message;
    private Order data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }

    public APIResponse(ResponseBuilder response) {
        this.status = response.status;
        this.message = response.message;
        this.data = response.data;

    }

    public static class ResponseBuilder {
        private String status;
        private String message;
        private Order data;

        public ResponseBuilder(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public ResponseBuilder setResponseData(Order data) {
            this.data = data;
            return this;
        }

        public APIResponse build() {
            return new APIResponse(this);
        }

    }
}
