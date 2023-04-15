package com.test;
import controller.*;
import data.Order;
import org.testng.Assert;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.post;

import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.ArrayList;

public class OrderTest {
    private static WireMockServer wireMockServer;
    private static final String EVENTS_PATH = "/employees";
    private static final String APPLICATION_JSON = "application/json";

    @MockBean
    private OrderService service;

    @Autowired
    private WebApplicationContext mockMvc;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // RestAssuredMockMvc.webAppContextSetup(mockMvc);
        final int port = 8009;
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
        RestAssured.port = port;
        stubFor(post(urlEqualTo(EVENTS_PATH)).willReturn(
          aResponse().withStatus(200)
            .withHeader("Content-Type", APPLICATION_JSON)));
            // .withBody(EMPLOYEES)));
    }

    @Test(groups = {"p3"})
    public void successCreateOrderWithValidData() {
        this.service = mock(OrderService.class);
        OrderRequest orderRequest = mock(OrderRequest.class);
        // controller.Order mockResponse = new controller.Order(1, "mock", "mock", false);
        // Mockito.when(service.createNewOrder(orderRequest)).thenReturn(1);
        Mockito.doReturn(1).when(service).createNewOrder(orderRequest);

        Order newOrder = new Order.OrderBuilder(1, "Coba", "New", true, "10101").build();
        
        // RestAssuredMockMvc.standaloneSetup(new OrderController(this.service));
        RestAssured
            .given()
                .body(newOrder).log().all()
            .when()
                .post("/order")
            .then().log().all();
                // .statusCode(201).log().all();
                
    }
    
}
