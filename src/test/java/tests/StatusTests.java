package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

public class StatusTests {
     /*
    1. Make request to https://selenoid.autotests.cloud/status
    2. Get response { total: 5, used: 0, queued: 0, pending: 0, browsers: ...
    3. Check total is 5
     */

    @Test
    public void totalAmountTest() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(5));
    }

    @Test
    public void totalAmountTest_withResponseLogs() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    @Test
    public void totalAmountTest_withLogs() {
        given()
                .log().all()
//                .log().uri()
//                .log().method()
//                .log().headers()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .body("total", is(5));
    }

    @Test
    public void status200Test() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void browserVersionTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void requiredKeysTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("", hasKey("total"))
                .body("", hasKey("used"))
                .body("", hasKey("queued"))
                .body("", hasKey("pending"))
                .body("", hasKey("browsers"));
    }

    @Test
    public void chromeVersionsTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("browsers.chrome", hasKey("127.0"))
                .body("browsers.chrome", hasKey("128.0"));
    }

    @Test
    public void statusSchemaTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"));
    }

    @Test
    public void bestTotalAmountTest() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/status_response_schema.json"))
                .body("total", is(5));
    }
}
