package pojo.pojoSerialization.serialization;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.pojoSerialization.AddPlace;
import pojo.pojoSerialization.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);

        List<String> types = new ArrayList<>();
        types.add("shoe park");
        types.add("shop");
        addPlace.setType(types);

        RequestSpecification requestSpecification =
                new RequestSpecBuilder()
                        .setBaseUri("https://rahulshettyacademy.com")
                        .addQueryParam("key", "qaclick123")
                        .setContentType(ContentType.JSON)
                        .build();

        RequestSpecification request =
                given()
                        .spec(requestSpecification)
                        .body(addPlace);

        ResponseSpecification responseSpecification =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .build();

        Response response =
        request
        .when()
                .post("/maps/api/place/add/json")
        .then()
                .spec(responseSpecification)
                .extract()
                .response();

        String responseString = response.asString();
        System.out.println("Response: " + responseString);
    }
}
