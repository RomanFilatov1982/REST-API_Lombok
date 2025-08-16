package tests;

import models.lombok.RegisterBodyLombokModel;
import models.lombok.RegisterResponseLombokModel;
import models.lombok.UnsuccessfulRegisterResponseLombokModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpec.responseSpec;

public class RegisterTest extends TestBase {


    @Test
    void successfulRegistrationTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");
        RegisterResponseLombokModel response = step("Make request", () -> given(requestSpec)
                .body(registerData)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec(200))
                .extract().as(RegisterResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals(4, response.getId());
            assertThat(response.getToken(), not(isEmptyOrNullString()));
        });
    }

    @Test
    void unsuccessfulRegistrationWithoutPasswordTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");

        UnsuccessfulRegisterResponseLombokModel response = step("Make request", () -> given(requestSpec)
               .body(registerData)
               .when()
               .post("/register")
               .then()
                .spec(responseSpec(400))
               .extract().as(UnsuccessfulRegisterResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals("Missing password", response.getError());
        });
    }

    @Test
    void unsuccessfulRegistrationWithIncorrectEmailTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("holt@reqres.in");
        registerData.setPassword("pistol");
        UnsuccessfulRegisterResponseLombokModel response = step("Make request", () -> given(requestSpec)
                .body(registerData)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec(400))
                .extract().as(UnsuccessfulRegisterResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals("Note: Only defined users succeed registration", response.getError());
        });
    }

    @Test
    void unsuccessfulRegistrationWithoutEmailAndPasswordTest() {
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("");
        registerData.setPassword("");
        UnsuccessfulRegisterResponseLombokModel response = step("Make request", () -> given(requestSpec)
                .body(registerData)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec(400))
                .extract().as(UnsuccessfulRegisterResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals("Missing email or username", response.getError());
        });
    }
}