package org.example;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.example.BaseURLHandlesAndWarnings.BASIC_HANDLE;
import static org.example.BaseURLHandlesAndWarnings.YANDEX_SCOOTER;

public class OtherRequests {
    public static RequestSpecification scope() {
        return  given().log().method()
                .contentType(ContentType.JSON)
                .baseUri(YANDEX_SCOOTER)
                ;
    }
    @Step("getting Response via GET Request")
    public static ValidatableResponse getResponseViaGet(String finalHandle) {
        return scope()
                .get(BASIC_HANDLE + finalHandle)
                .then().log().body()
                ;
    }
}
