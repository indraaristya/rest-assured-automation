# Rest Assured & TestNG API Automation

This project is used as my learning repository of creating API automation using Rest Assured.

## Requirements

Apache Maven 3.9.1 or later

Java version: 17.0.2 or later

Please refer to the documentation for [Maven](https://maven.apache.org/install.html) and [Java MacOS](https://www.java.com/en/download/help/mac_install.html)/[Java Windows](https://www.java.com/en/download/help/windows_manual_download.html) installation.

## How to Run 

After installation completed, pull latest branch master and open using your IDE. Usually, Eclipse/IntelliJ will support Java project and there are the button or direct shortcut to run the test. 

But, if you are using VSCode, just like I did, you can run the test by following command directly.


```bash
  mvn clean test
```

Then test file will be running and the report will be produced in folder `/target/surefire-reports/emailable-report.html`.

## Folder Structure
This repository contains of sample Spring Boot Application include the API test. Since the API server for Order is not hosted or available, I will try to create simple controller and mock the reponse on the API test to verify if its the automation is working or not.

```
.
├── src
│   ├── com/test
│       ├── APIResponse.java
│       ├── OrderTest.java
│       └── SetupMock.java
│   ├── controller
│       ├── Application.java
│       ├── Order.java
│       ├── OrderController.java
│       ├── OrderService.java
│       └── OrderRequest.java
│   └── data
│       └── Order.java
├── pom.xml
├── testng.xml
└── README.md
```
In `test` folder, there are 3 classes file named ***APIResponse*** as the response class include the builder class to create the object as value of mocking the API response, 

***OrderTest*** is used to define test scenario for order endpoint, 

and ***SetupMock*** is a parent class of testing class. This class is included the *@BeforeMethod* and *@AfterMethod* to start and stop wiremock. It should be easier to create another test class by extended this class and the before-after method will be defined.

`controller` folder is all the place for Spring Boot class, including the request object as validation when do real requests to the server.

Then, in `data` folder there is a `Order.java` class including the builder class to help on creating multiple object with different value parameters.

The configuration of dependencies are listed in `pom.xml`, also configuration for testing is listed on `testng.xml` file.

## Assumptions

Before started to explain the approach, for clear use-case I applied some assumptions related to the endpoint request.

1. Endpoint to create order is `/processOrder` with method POST

2. Body requests is contains of *order_id, order_description, order_status, last_updated_timestamp,* and *special_order*. All string except *order_id* and *special_order* which are integer and boolean

3. Order is successfully created if and only if all keys in body requests is valid type and not null

4. Order API will return error if one of body requests is invalid or `null`

5. Order should be submitted with status **New**, and the response will be update the status value into **Processing**

6. Since the API is not hosted or created, I will mock the response using *WireMock*

## Approach on Solving Problems

The automation tools is using Rest Assured and ran using TestNG. Rest Assured is one of Java library that capable of doing request and validate the response to be tested. In this code, the automation combined using TestNG to run (that's why `testng.xml` file exist and mentioned in configuration `pom.xml`).

TestNG is included by some features that helps to improve the testing process, such as;

1. Support tagging or grouping test scenario. In real case, we usually divide the scenarios or test cases into several groups. These groups has their own meaning, such as group **smoke test**, or based on the priority by using **high**, **medium**, and **low** as a example. TestNG support us by running the tests based on the group name. Let's say we wanted to run the automation with group **high** only for pre-deployment or post-deployment testing then we just need to add the configuration of group name in `testng.xml` file.

2. Multithread is possible. This feature will helps team on running the automation with complex and many scenarios need to be run. By define the thread count in `testng.xml`, we can running the automation parallel to save more running duration.

3. Generate default ***emailable report***. This report is generated automatically by TestNG so we can analyze and share the reports of the automatio testing.

Other that that, I also implement Object Oriented Programming to improve the code readibilty by divide the test elements into classes. 

The example of implement OOP is to create a class of before-after tests. In this automation, I am using *@BeforeMethod* and *@AfterMethod* annotation to start and stop wire mock. It will be redundant if we create new before-after hook method for every test class, instead we can placed the before-after hook as a parent class (`SetupMock.java`) then every test class should extend SetupMock class to inherit all the function.

In terms of design pattern, I apply the builder pattern on this code. As far as I know, builder pattern helps us on create new object of a class with several optional parameters. 

Order object has attributes *order_id, order_description, order_status, last_updated_timestamp,* and *special_order*.

Based on the assumptions above and scenario testing, I will need order object with all valid data, or object with *order_status* is `null` or *invalid*, and etc. So instead of create several consturtor of Order class, implement builder pattern will giving the advantages. Order builder will contains of some function to "build" the object, we can "call" the function to define the variable if needed, or no need to "call" the function if we wish to left the variable to be `null`.

## Fields of Improvement

To improve these code, we are able to review the API requirements to create readable framework and easy to use. This improvement also included to apply more reliable design patter if needed.

The code also able to Dockerize so it will be able to used in any platform or OS. This dockerize automation also helps us on running the automation in cloud node.

## Report Sample

![Emailable Report Sample](https://i.ibb.co/dLDk50C/image.png)
