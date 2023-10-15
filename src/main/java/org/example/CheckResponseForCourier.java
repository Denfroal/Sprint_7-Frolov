package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CheckResponseForCourier {
    @Step("checking the \"id\" in the response is not null")
    public static void checkLoggedInWithNotNullId(ValidatableResponse response) {
        response.assertThat()
                .body("id", notNullValue());
    }

    @Step ("checking the \"Ok\" in the response is true")
    public static void checkCreatedWithOkTrue(ValidatableResponse response) {
        response.assertThat()
                .body("ok", is(true))
        ;
    }
}
