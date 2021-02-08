package cdp.bonial.qa.dyco;

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

public class DycoExampleTest extends BaseTest {

    @Test
    public void shouldValidateAndStartImport() {
        Response response = given()
                    .baseUri(getUrlFor("cdp-dynamic-content-api", config))
                    .contentType("multipart/form-data")
                .when()
                    .param("market", "DE")
                    .param("scraper", "Kaufland")
                    .multiPart("profiles", new File("./src/test/resources/PubProfile_Kaufland_001.xlsx"))
                    .post("/import/start")
                .then()
                    .log().everything()
                    .statusCode(200)
                    .time(lessThan(5000L))
                .extract().response();

        List<Map<String, String>> validationResults = response.jsonPath().getList("publicationValidationMessages");
        assertEquals(0, validationResults.size(), "The 'validationResults' are not empty");
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
