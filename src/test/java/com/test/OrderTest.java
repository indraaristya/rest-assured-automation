package com.test;
import controller.*;
import data.Order;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class OrderTest {
    private static WireMockServer wireMockServer;
    private static final String EVENTS_PATH = "/order";
    private static final String APPLICATION_JSON = "application/json";

    @MockBean
    private OrderService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws JsonProcessingException {
        Order newOrder = new Order.OrderBuilder(1, "Coba", true)
                            .setOrderStatus("Processing")
                            .setTimeStamp(System.currentTimeMillis() / 1000)
                            .build();
        final int port = 8009;
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
        RestAssured.port = port;
        stubFor(post(urlEqualTo(EVENTS_PATH)).willReturn(
          aResponse().withStatus(201)
            .withHeader("Content-Type", APPLICATION_JSON)
            .withBody(objectMapper.writeValueAsString(newOrder))));
    }

    @AfterMethod(alwaysRun = true)
    public void stopWiremock() {
        wireMockServer.stop();
    }

    @Test(groups = {"p3"})
    public void successCreateOrderWithValidData() {
        Order newOrder = new Order.OrderBuilder(1, "Coba", true)
                        .setOrderStatus("New Order")
                        .setTimeStamp(System.currentTimeMillis() / 1000)
                        .build();

        Response response = RestAssured
                            .given()
                                .body(newOrder).log().all()
                            .when()
                                .post("/order")
                            .then()
                                .statusCode(201).log().all()
                                .extract().response();
                                
        // String id = response.getBody().jsonPath().get("id");
        ResponseBody body = response.getBody();
        Assert.assertNotNull(body.jsonPath().get("order_id"), "ID should be returned");
        Assert.assertEquals(body.jsonPath().get("order_status"), "Processing");
    }
    
}
