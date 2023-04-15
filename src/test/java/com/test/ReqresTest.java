package com.test;

import org.testng.Assert;
import org.testng.annotations.Test;
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
    public void createNewEmployeeWithJob() {
        User employee = new User.UserBuilder("Indra").setUserAge(24).setUserJob("QA").build();

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

    @Test(groups = { "p0" })
    public void createNewEmployeeWithoutJob() {
        User employee = new User.UserBuilder("Dia").build();

        Response response = getRequest()
                                .log().all()
                                .with()
                                    .body(employee)
                                .when()
                                    .post("/api/users")
                                .then()
                                    .extract().response();
        String name = response.getBody().jsonPath().get("name");
        Assert.assertNotNull(name, "name should be returned");
    }
}
