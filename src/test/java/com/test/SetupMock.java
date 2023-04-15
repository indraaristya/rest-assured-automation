package com.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class SetupMock {
    private static WireMockServer wireMockServer;
    private RequestSpecification request;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        final int port = 8009;
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        configureFor("localhost", port);
        RestAssured.port = port;
    }

    @AfterMethod(alwaysRun = true)
    public void stopWiremock() {
        wireMockServer.stop();
    }

    public RequestSpecification setupMock() {
        return this.request;
    }

    public WireMockServer setupMocks() {
        return wireMockServer;
    }
}
