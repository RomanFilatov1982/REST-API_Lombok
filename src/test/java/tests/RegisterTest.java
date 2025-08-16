package tests;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.RegisterBodyLombokModel;
import models.lombok.RegisterResponseLombokModel;
import models.lombok.UnsuccessfulRegisterResponseLombokModel;
import models.pojo.RegisterBodyModel;
import models.pojo.RegisterResponseModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RequestSpec.registrationRequestSpec;
import static specs.ResponseSpec.registrationResponseSpec;

public class RegisterTest extends TestBase {

    @Test
    void successfulRegisterPojoTest() {
        //String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyModel registerData = new RegisterBodyModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterResponseModel response = given()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseModel.class);

        assertEquals(4, response.getId());
        assertThat(response.getToken(), not(isEmptyOrNullString()));
    }

    @Test
    void successfulRegisterLombokTest() {
        //String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertEquals(4, response.getId());
        assertThat(response.getToken(), not(isEmptyOrNullString()));
    }

    @Test
    void successfulRegisterAllureTest() {
        //String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .filter(new AllureRestAssured())
                .log().uri()
                .log().body()
                .log().headers()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)

                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertEquals(4, response.getId());
        assertThat(response.getToken(), not(isEmptyOrNullString()));
    }

    @Test
    void successfulRegisterCustomAllureTest() {
        //String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");

        RegisterResponseLombokModel response = given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)

                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegisterResponseLombokModel.class);

        assertEquals(4, response.getId());
        assertThat(response.getToken(), not(isEmptyOrNullString()));
    }

    @Test
    void successfulRegisterWithStepsTest() {
        //String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");
        RegisterResponseLombokModel response = step("Make request", () -> given()
               .filter(withCustomTemplates())
               .log().uri()
               .log().body()
               .log().headers()
               .body(registerData)
               .header("x-api-key", apiKey)
               .contentType(JSON)

               .when()
               .post("/register")
               .then()
               .log().status()
               .log().body()
               .statusCode(200)
               .extract().as(RegisterResponseLombokModel.class));
        step("Check response", () -> {
        assertEquals(4, response.getId());
        assertThat(response.getToken(), not(isEmptyOrNullString()));
        });
    }

    @Test
    void successfulRegisterWithSpecsTest() {
        //String registrationData = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        registerData.setPassword("pistol");
        RegisterResponseLombokModel response = step("Make request", () -> given(registrationRequestSpec)
                .body(registerData)
         .when()
                .post("/register")
         .then()
                .spec(registrationResponseSpec)
                .extract().as(RegisterResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals(4, response.getId());
            assertThat(response.getToken(), not(isEmptyOrNullString()));
        });
    }

    @Test
    void unsuccessfulRegisterWithoutPasswordTest() {
        //String registrationData = "{\"email\":\"holt@reqres.in\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("eve.holt@reqres.in");
        UnsuccessfulRegisterResponseLombokModel response = given()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(UnsuccessfulRegisterResponseLombokModel.class);
                 assertEquals("Missing password", response.getError());
    }

    @Test
    void unsuccessfulRegisterWithIncorrectEmailTest() {
        //String registrationData = "{\"email\":\"holt@reqres.in\",\"password\":\"pistol\"}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("holt@reqres.in");
        registerData.setPassword("pistol");
        UnsuccessfulRegisterResponseLombokModel response = given()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(UnsuccessfulRegisterResponseLombokModel.class);
                assertEquals("Note: Only defined users succeed registration", response.getError());
    }

    @Test
    void unsuccessfulRegisterWithoutEmailAndPasswordTest() {
        //String registrationData = "{}";
        RegisterBodyLombokModel registerData = new RegisterBodyLombokModel();
        registerData.setEmail("");
        registerData.setPassword("");
        UnsuccessfulRegisterResponseLombokModel response = given()
                .body(registerData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(UnsuccessfulRegisterResponseLombokModel.class);
                 assertEquals("Missing email or username", response.getError());
    }
}