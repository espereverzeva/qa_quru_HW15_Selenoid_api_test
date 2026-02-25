package tests;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class WdHubStatusTests extends TestBase {

   @BeforeAll
    static void setupPath() {
       RestAssured.basePath = "/wd/hub";
   }

    @Test
    public void successStatus200TestTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void successContentMessageTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", is("Selenoid 1.11.3 built at 2024-05-25_12:34:40PM"));
    }

    @Test
    public void successContentReadyTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }

    @Test
    public void successSchemaTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/wd_hub_status_response_schema.json"));
    }

    @Test
    public void negativeUnauthorizedNoLoginPasswordTest() {
        given()
                .log().all()
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401)
                .body(containsString("Authorization Required"));
    }

    @Test
    public void negativeUnauthorizedPasswordTest() {
        given()
                .log().all()
                .auth().basic("user1", "12345")
                .when()
                .get("/status")
                .then()
                .log().all()
                .statusCode(401)
                .header("WWW-Authenticate", Matchers.containsString("Basic realm="));
    }
}
