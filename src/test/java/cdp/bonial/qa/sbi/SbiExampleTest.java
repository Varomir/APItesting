package cdp.bonial.qa.sbi;

import cdp.bonial.qa.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static cdp.bonial.qa.helpers.ConfigHelper.getUrlFor;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SbiExampleTest extends BaseTest {

    private static final String IMPORT_FILE = "./src/test/resources/mdc/SBI_Reg_020.xls";

    @Test
    public void shouldValidateData() {
        prepareXLSX(IMPORT_FILE, "Test_" + System.currentTimeMillis());
        Response response = given()
                    .baseUri(getUrlFor("cdp-sbi-parser", config))
                    .contentType("multipart/form-data")
                    .param("version", "V1")
                    .param("market", "DE")
                    .param("flightName", "")
                .when()
                    .multiPart("importFile", new File(IMPORT_FILE))
                    .post("/static-brochure-import/validate")
                .then()
                    .statusCode(200)
                    .time(lessThan(5000L))
                .extract().response();

        List<List<String>> validationResults = response.jsonPath().getList("rows.validationResults.resultType");
        List<String> flatResults = validationResults
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        assertEquals(0, flatResults.size(), "The 'validationResults' are not empty");
    }

    @Test
    public void shouldImportData() {
        prepareXLSX(IMPORT_FILE, "Test_" + System.currentTimeMillis());
        Response response = given()
                .baseUri(getUrlFor("cdp-sbi-parser", config))
                .contentType("multipart/form-data")
                .param("version", "V1")
                .param("market", "DE")
                .param("flightName", "")
                .param("user", "vadym.feshchenko")
                .param("priority", "NORMAL")
                .param("requestId", "4e3b6bbd-3575-1d70-eb96-b15281f8f155")
                .when()
                .multiPart("importFile", new File(IMPORT_FILE))
                .post("/static-brochure-import/start")
                .then()
//                .log().everything()
                .statusCode(200)
                .time(lessThan(5000L))
                .extract().response();

        List<String> flightNames = response.jsonPath().getList("flightNames");
        assertEquals(1, flightNames.size(), "Import were failed");
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
