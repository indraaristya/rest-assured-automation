package com.test;

import org.testng.annotations.Test;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

public class MockTest {
    @Test
    public void testMock() {
        RestAssuredMockMvc.when()
            .get("/test")
            .then()
                .statusCode(200);
    }
}
