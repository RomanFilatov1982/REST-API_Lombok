package specs;

import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
//import static tests.TestBase.apiKey;

public class RequestSpec {
    public static RequestSpecification registrationRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            //.header("x-api-key", apiKey)
            .contentType(JSON);
}
