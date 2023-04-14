package com.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.specification.RequestSpecification;

import io.restassured.RestAssured;
/**
 * Unit test for simple App.
 */
public class AppTest {
    private RequestSpecification request;

    @BeforeMethod
	public void createRequestSpecification() {
		request = RestAssured.given();
		request.baseUri("https://reqres.in");
	}

    @Test(groups = { "p0" })
    public void getAllEmployee() {
        request
            .when()
                .get("/api/users?page=2")
            .then()
                .statusCode(200);
    }

    @Test(groups = { "p0" })
    public void getEmployeeDetails() {
        request
            .when()
                .get("/api/users/2")
            .then()
                .statusCode(200);
    }
}
