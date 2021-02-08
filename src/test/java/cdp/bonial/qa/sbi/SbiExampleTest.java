package cdp.bonial.qa.sbi;

import cdp.bonial.qa.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

import static cdp.bonial.qa.helpers.ConfigHelper.getUrlFor;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SbiExampleTest extends BaseTest {

    @Test
    public void shouldValidateImportData() {
        Response response = given()
                    .baseUri(getUrlFor("cdp-sbi-parser", config))
                    .contentType("multipart/form-data")
                    .param("version", "V1")
                    .param("market", "DE")
                    .param("flightName", "")
                .when()
                    .multiPart("importFile", new File("./src/test/resources/SBI_DE_manuf_001.xls"))
                    .post("/static-brochure-import/validate")
                .then()
                    .statusCode(200)
                    .time(lessThan(5000L))
                .extract().response();

        List<Map<String, String>> validationResults = response.jsonPath().getList("rows[0].validationResults");
        assertEquals(0, validationResults.size(), "The 'validationResults' are not empty");
    }

    @Test
    public void shouldGetImportFlightList() {
        given()
                    .baseUri(getUrlFor("cdp-sbi-parser", config))
                    .header("Content-Type", "application/json")
                .when()
                    .param("incidentsOnly", "false")
                    .param("market", "DE")
                    .param("page", 0)
                    .param("size", 10)
                    .param("sort", "startedAt,desc")
                .when()
//                    .log().all()
//                    .log().method()
//                    .log().uri()
//                    .log().headers()
//                    .log().body()
                .get("/import/flight/list")
                .then()
//                    .log().body()
//                    .log().cookies()
//                    .log().everything()
                    .statusCode(200)
                    .time(lessThan(5000L));
    }
}
