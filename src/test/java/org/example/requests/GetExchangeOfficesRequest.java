package org.example.requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import org.example.PojoModels.OfficeDataModel;


import static io.restassured.RestAssured.given;

public class GetExchangeOfficesRequest {

    private static final String API_URL = "https://www.bspb.ru/api/currency-service/office-rates";

    public static OfficeDataModel readExpectedJson(String filePath) throws Exception {
        String json = Files.readString(Path.of(filePath));
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, OfficeDataModel.class);
    }

    public static Response performGet() {
        return given()
                .when()
                .get(API_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .extract()
                .response();
    }
}
