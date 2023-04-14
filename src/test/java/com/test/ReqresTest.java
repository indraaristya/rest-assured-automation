package com.test;

import org.testng.annotations.Test;

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
}
