package cdp.bonial.qa.sbi;

import cdp.bonial.qa.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static cdp.bonial.qa.helpers.ConfigHelper.getUrlFor;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class SbiExampleTest extends BaseTest {

    @Test
    public void shouldValidateImportData() {
        given()
                    .baseUri(getUrlFor("cdp-sbi-parser", config))
                    .contentType("multipart/form-data")
                    .param("version", "V1")
                    .param("market", "DE")
                .when()
                    .multiPart("importFile", new File("./src/test/resources/SBI_DE_manuf_001.xls"))
                    .post("/static-brochure-import/validate")
                .then()
                    .log().body()
                    .statusCode(200)
                    .time(lessThan(5000L));
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
