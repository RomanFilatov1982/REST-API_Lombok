package tests;

import models.lombok.CreateUserBodyLombokModel;
import models.lombok.CreateUserResponseLombokModel;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RequestSpec.requestSpec;
import static specs.ResponseSpec.responseSpec;

public class UsersTest extends TestBase {

    @Test
    void successfulCreateNewUserTest() {
        CreateUserBodyLombokModel createData = new CreateUserBodyLombokModel();
        createData.setName("morpheus");
        createData.setJob("leader");
        CreateUserResponseLombokModel response = step("Make request", () -> given(requestSpec)
                .body(createData)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec(201))
                .extract().as(CreateUserResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
            assertThat(response.getId(), notNullValue());
            assertThat(response.getCreatedAt(), notNullValue());
        });
    }
}