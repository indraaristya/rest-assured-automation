package com.test;
import controller.*;
import data.Order;
import org.testng.Assert;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.ArrayList;

@WebMvcTest(OrderController.class)
public class OrderTest {

    @MockBean
    private OrderService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeMethod
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test(groups = {"p3"})
    public void successCreateOrderWithValidData() {
        this.service = mock(OrderService.class);
        OrderRequest orderRequest = mock(OrderRequest.class);
        // controller.Order mockResponse = new controller.Order(1, "mock", "mock", false);
        // Mockito.when(service.createNewOrder(orderRequest)).thenReturn(1);
        Mockito.doReturn(1).when(service).createNewOrder(orderRequest);

        Order newOrder = new Order.OrderBuilder(1, "Coba", "New", true, "10101").build();
        
        RestAssuredMockMvc
            .given()
                .body(newOrder).log().all()
            .when()
                .post("/order");
            // .then()
                // .statusCode(201).log().all();
                
    }
    
}
