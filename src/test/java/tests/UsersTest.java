package tests;

import models.lombok.CreateUserBodyLombokModel;
import models.lombok.CreateUserResponseLombokModel;
import models.lombok.RegisterBodyLombokModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest extends TestBase {

    @Test
    void successfulCreateNewUserTest() {
        //String usersData = "{\"name\": \"morpheus\",\"job\":\"leader\"}";
        CreateUserBodyLombokModel createData = new CreateUserBodyLombokModel();
        createData.setName("morpheus");
        createData.setJob("leader");
        CreateUserResponseLombokModel response = given()
                .body(createData)
                .header("x-api-key", apiKey)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateUserResponseLombokModel.class);
                assertEquals("morpheus", response.getName());
                assertEquals("leader", response.getJob());
                assertThat(response.getId(), notNullValue() );
                assertThat(response.getCreatedAt(), notNullValue() );
    }

}