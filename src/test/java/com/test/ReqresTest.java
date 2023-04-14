package com.test;

import org.apache.http.util.Asserts;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.*;
import data.*;
import io.restassured.response.Response;

public class ReqresTest extends Setup {
    
    @Test(groups = { "p0" })
    public void getAllEmployee() {
        getRequest()
            .when()
                .get("/api/users?page=2")
            .then()
                .statusCode(200);
    }

    @Test(groups = { "p0" })
    public void getEmployeeDetails() {
        getRequest()
            .when()
                .get("/api/users/2")
            .then()
                .statusCode(200);
    }

    @Test(groups = { "p0" })
    public void createNewEmployee() {
        User employee = new User("Indra", "QA");

        Response response = getRequest()
                                .log().all()
                                .with()
                                    .body(employee)
                                .when()
                                    .post("/api/users")
                                .then()
                                    .extract().response();
        
        String id = response.getBody().jsonPath().get("id");
        Assert.assertNotNull(id, "ID should be returned");
    }
}
