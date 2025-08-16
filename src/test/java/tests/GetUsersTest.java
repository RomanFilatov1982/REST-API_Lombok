package tests;

import models.lombok.RegisterResponseLombokModel;
import models.lombok.SingleUserResponseLombokTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUsersTest extends TestBase {
    @Test
    void successfulSingleUserTest() {

        String supportUrl = "https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral";
        String supportText = "Tired of writing endless social media content? Let Content Caddy generate it for you.";

        SingleUserResponseLombokTest response = given()
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(SingleUserResponseLombokTest.class);

        assertEquals(2, response.data.getId());
        assertEquals("janet.weaver@reqres.in", response.data.getEmail());
        assertEquals("Janet", response.data.getFirst_name());
        assertEquals("Weaver", response.data.getLast_name());
        assertEquals(baseURI + "/img/faces/2-image.jpg", response.data.getAvatar());
        assertEquals(supportUrl, response.support.getUrl());
        assertEquals(supportText, response.support.getText());
    }
}