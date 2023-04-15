package com.test;
import controller.*;
import data.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import io.restassured.RestAssured;

import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.ArrayList;

public class OrderTest {
    private static WireMockServer wireMockServer;
    private static final String EVENTS_PATH = "/order";
    private static final String APPLICATION_JSON = "application/json";

    @MockBean
    private OrderService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws JsonProcessingException {
        Order newOrder = new Order.OrderBuilder(1, "Coba", "New", true, "10101").build();
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
        // this.service = mock(OrderService.class);
        // OrderRequest orderRequest = mock(OrderRequest.class);
        // Mockito.doReturn(1).when(service).createNewOrder(orderRequest);

        Order newOrder = new Order.OrderBuilder(1, "Coba", "New", true, "10101").build();

        RestAssured
            .given()
                .body(newOrder).log().all()
            .when()
                .post("/order")
            .then()
                .statusCode(201).log().all();
                
    }
    
}
