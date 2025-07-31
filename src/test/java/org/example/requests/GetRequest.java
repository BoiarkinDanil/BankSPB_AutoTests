package org.example.requests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Map;

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
        List<Integer> actualIds = jsonPath.getList("items.id");
        List<Map<String, Object>> items = jsonPath.getList("items");

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(actualNames)
                .contains(
                        "ДО \"Гаванский\"",
                        "ДО \"Пушкинский\"",
                        "ДО \"Тосненский\""
                );

        softly.assertThat(actualIds)
                .contains(48, 23, 2114);

        items.forEach(item -> {
            softly.assertThat(item.get("id")).isNotNull();
            softly.assertThat(item.get("name")).isNotNull();
            softly.assertThat(item.get("rates")).isNotNull();
        });

        softly.assertAll();
    }
}