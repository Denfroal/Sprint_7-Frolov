package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CheckOther {
    @Step("checking the Status Code")
    public static void checkForStatusCode(ValidatableResponse response, int status) {
        response.assertThat()
                .statusCode(status)
        ;
    }

    @Step ("checking the parameter in the response has some value")
    public static void checkParamWithValue(ValidatableResponse response, String param, Object value) {
        response.assertThat()
                .body(param, is(value))
        ;
    }

    @Step ("checking the parameter in the response is not null")
    public static void checkParamIsNotNull(ValidatableResponse response, String param) {
        response.assertThat()
                .body(param, notNullValue())
        ;
    }
}
