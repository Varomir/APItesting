package cdp.bonial.qa.dyco;

import cdp.bonial.qa.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;


import static cdp.bonial.qa.helpers.ConfigHelper.getUrlFor;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class DycoExampleTest extends BaseTest {

    private static final String IMPORT_FILE = "./src/test/resources/mdc/KauflandPublication_1br_001.xlsx";

    @Test
    public void shouldValidateAndStartImport() {
        prepareXLSX(IMPORT_FILE, "Test_" + System.currentTimeMillis());
        Response response = given()
                    .baseUri(getUrlFor("cdp-dynamic-content-api", config))
                    .contentType("multipart/form-data")
                .when()
                    .param("market", "DE")
                    .param("scraper", "excel")
                    .multiPart("profiles", new File("./src/test/resources/mdc/KauflandPublication_1br_001.xlsx"))
                    .multiPart("offers", new File("./src/test/resources/mdc/002_offers_005.xlsx"))
                    .post("/import/start")
                .then()
//                    .log().everything()
                    .statusCode(200)
                    .time(lessThan(5000L))
                .extract().response();
    }

    @Test
    public void shouldGetImportFlightList() {
        given()
                    .baseUri(getUrlFor("cdp-dynamic-content-api", config))
                    .header("Content-Type", "application/json")
                .when()
                    .param("flightState", "ACTIVE")
                    .param("market", "DE")
                    .param("page", 0)
                    .param("size", 10)
                    .param("sort", "uploadDate,desc")
                    .param("fromDate", "02.08.2021")
                .when()
                    .get("/flights")
                .then()
//                    .log().everything()
                    .statusCode(200)
                    .time(lessThan(5000L));
    }
}
