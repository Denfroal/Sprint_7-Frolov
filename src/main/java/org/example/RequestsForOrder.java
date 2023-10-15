package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.OrderData;
import org.example.OtherRequests;

import static org.example.BaseURLHandlesAndWarnings.BASIC_HANDLE;
import static org.example.BaseURLHandlesAndWarnings.ORDERS_HANDLE;

public class RequestsForOrder {
    @Step("create order")
    public static ValidatableResponse createOrder(OrderData orderData) {
        return OtherRequests.scope()
                .body(orderData)
                .when()
                .post(BASIC_HANDLE + ORDERS_HANDLE)
                .then().log().body()
                ;
    }
}
