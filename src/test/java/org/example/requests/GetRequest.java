package org.example.requests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetRequest {

    private static final String API_URL = "https://www.bspb.ru/api/currency-service/office-rates";

    public static void main(String[] args) {

        Response response = given()
                .when()
                .get(API_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        List<String> actualNames = jsonPath.getList("items.name");

        Assertions.assertThat(actualNames)
                .contains(
                        "ДО \"Гаванский\"",
                        "ДО \"Пушкинский\"",
                        "ДО \"Тосненский\""
                );
    }
}