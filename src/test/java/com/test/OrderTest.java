package com.test;
import controller.*;
import data.Order;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class OrderTest extends SetupMock {
    private static final String PATH = "/processOrder";
    private static final String APPLICATION_JSON = "application/json";

    @MockBean
    private OrderService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test(groups = { "order_test" })
    public void successCreateOrderWithValidData() throws JsonProcessingException {
        // Mock request and response
        int id = 10;
        Order dataResponse = new Order.OrderBuilder(id)
                                .setOrderStatus("Processing")
                                .setTimeStamp()
                                .build();

        APIResponse mockResponse = new APIResponse.ResponseBuilder("success", "Success add new order!")
                                    .setResponseData(dataResponse).build();
        
        stubFor(post(urlEqualTo(PATH)).willReturn(
            aResponse().withStatus(201)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));
        
        // Testing request
        Order newOrder = new Order.OrderBuilder(id)
                            .setDescription(dataResponse.getOrder_desccription())
                            .setOrderStatus("New")
                            .setTimeStamp()
                            .build();

        Response response = RestAssured
                            .given()
                                .body(newOrder).log().all()
                            .when()
                                .post(PATH)
                            .then()
                                .statusCode(201).log().all()
                                .extract().response();
                                
        ResponseBody body = response.getBody();
        Assert.assertEquals(body.jsonPath().get("status"), "success");
        Assert.assertEquals(body.jsonPath().get("message"), "Success add new order!");
        Assert.assertEquals(body.jsonPath().get("data.order_id"), dataResponse.getOrder_id());
        Assert.assertEquals(body.jsonPath().get("data.order_status"), "Processing");
        Assert.assertEquals(body.jsonPath().get("data.order_desccription"), dataResponse.getOrder_desccription());
        Assert.assertEquals(body.jsonPath().get("data.special_order"), dataResponse.getSpecial_order());
    }

    @Test(groups = { "order_test" })
    public void failedCreateOrderWithoutStatus() throws JsonProcessingException {
        // Mock request and response
        int order_id = 10;
        
        APIResponse mockResponse = new APIResponse.ResponseBuilder("error", "Failed create order. Status should be defined.")
                                    .build();

        stubFor(post(urlEqualTo(PATH)).willReturn(
            aResponse().withStatus(400)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));
        
        // Testing request
        Order newOrder = new Order.OrderBuilder(order_id)
                            .setTimeStamp()
                            .build();

        Response response = RestAssured
                            .given()
                                .body(newOrder).log().all()
                            .when()
                                .post(PATH)
                            .then()
                                .statusCode(400).log().all()
                                .extract().response();
        
        ResponseBody body = response.getBody();
        Assert.assertEquals(body.jsonPath().get("status"), "error");
        Assert.assertEquals(body.jsonPath().get("message"), "Failed create order. Status should be defined.");
        Assert.assertNull(body.jsonPath().get("data"), "Data should return null");
    }

    @Test(groups = { "order_test" })
    public void failedCreateOrderWithoutTimestamp() throws JsonProcessingException {
        // Mock request and response
        int order_id = 10;
        
        APIResponse mockResponse = new APIResponse.ResponseBuilder("error", "Failed create order. Timestamp should be defined.")
                                    .build();

        stubFor(post(urlEqualTo(PATH)).willReturn(
            aResponse().withStatus(400)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));
        
        // Testing request
        Order newOrder = new Order.OrderBuilder(order_id)
                            .setOrderStatus("New")
                            .build();

        Response response = RestAssured
                            .given()
                                .body(newOrder).log().all()
                            .when()
                                .post(PATH)
                            .then()
                                .statusCode(400).log().all()
                                .extract().response();
        
        ResponseBody body = response.getBody();
        Assert.assertEquals(body.jsonPath().get("status"), "error");
        Assert.assertEquals(body.jsonPath().get("message"), "Failed create order. Timestamp should be defined.");
        Assert.assertNull(body.jsonPath().get("data"), "Data should return null");
    }

    @Test(groups = { "order_test" })
    public void failedCreateOrderWithoutBody() throws JsonProcessingException {
        // Mock request and response
        APIResponse mockResponse = new APIResponse.ResponseBuilder("error", "Body request should be defined.")
                                    .build();

        stubFor(post(urlEqualTo(PATH)).willReturn(
            aResponse().withStatus(400)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));
        
        // Testing request
        Response response = RestAssured
                            .given()
                            .when()
                                .post(PATH)
                            .then()
                                .statusCode(400).log().all()
                                .extract().response();
        
        ResponseBody body = response.getBody();
        Assert.assertEquals(body.jsonPath().get("status"), "error");
        Assert.assertEquals(body.jsonPath().get("message"), "Body request should be defined.");
        Assert.assertNull(body.jsonPath().get("data"), "Data should return null");
    }
    
    @Test(groups = { "order_test" })
    public void failedCreateOrderWithInvalidStatus() throws JsonProcessingException {
        // Mock request and response
        int order_id = 10;
        
        APIResponse mockResponse = new APIResponse.ResponseBuilder("error", "Failed create order. Status should be one of New, Processing, or Done.").build();

        stubFor(post(urlEqualTo(PATH)).willReturn(
            aResponse().withStatus(400)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));
        
        // Testing request
        Order newOrder = new Order.OrderBuilder(order_id)
                            .setTimeStamp()
                            .setOrderStatus(getInvalidStatus())
                            .build();

        Response response = RestAssured
                            .given()
                                .body(newOrder).log().all()
                            .when()
                                .post(PATH)
                            .then()
                                .statusCode(400).log().all()
                                .extract().response();
        
        ResponseBody body = response.getBody();
        Assert.assertEquals(body.jsonPath().get("status"), "error");
        Assert.assertEquals(body.jsonPath().get("message"), "Failed create order. Status should be one of New, Processing, or Done.");
        Assert.assertNull(body.jsonPath().get("data"), "Data should return null");
    }

    @Test(groups = { "order_test" })
    public void failedCreateOrderWithInvalidTimestamp() throws JsonProcessingException {
        // Mock request and response
        int order_id = 10;
        
        APIResponse mockResponse = new APIResponse.ResponseBuilder("error", "Failed create order. Updated time is not valid unix timestamp").build();

        stubFor(post(urlEqualTo(PATH)).willReturn(
            aResponse().withStatus(400)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody(objectMapper.writeValueAsString(mockResponse))));
        
        // Testing request
        Order newOrder = new Order.OrderBuilder(order_id)
                            .setTimeStamp("invalid")
                            .setOrderStatus("New")
                            .build();

        Response response = RestAssured
                            .given()
                                .body(newOrder).log().all()
                            .when()
                                .post(PATH)
                            .then()
                                .statusCode(400).log().all()
                                .extract().response();
        
        ResponseBody body = response.getBody();
        Assert.assertEquals(body.jsonPath().get("status"), "error");
        Assert.assertEquals(body.jsonPath().get("message"), "Failed create order. Updated time is not valid unix timestamp");
        Assert.assertNull(body.jsonPath().get("data"), "Data should return null");
    }

    // function to get one of invalid status
    public String getInvalidStatus() {
        List<String> invalidStatus = Arrays.asList("Baru", "Invalid", "Order baru");
        Random rand = new Random();
        int randomElement = rand.nextInt(invalidStatus.size());
        return invalidStatus.get(randomElement);
    }

}
