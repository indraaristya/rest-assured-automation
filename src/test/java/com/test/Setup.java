package com.test;
import org.testng.annotations.BeforeMethod;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

public class Setup {
    private RequestSpecification request;

    @BeforeMethod(alwaysRun = true)
	public void RequestSpecification() {
		request = RestAssured.given();
		request.baseUri("https://reqres.in");
	}

    public RequestSpecification getRequest() {
        return this.request;
    }
}
