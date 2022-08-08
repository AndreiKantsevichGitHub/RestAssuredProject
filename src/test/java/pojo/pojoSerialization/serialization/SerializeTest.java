package pojo.pojoSerialization.serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.pojoSerialization.AddPlace;
import pojo.pojoSerialization.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTest {

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

        Response response =
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .body(addPlace)
        .when()
                .post("/maps/api/place/add/json")
        .then()
                .assertThat()
                .statusCode(200).extract()
                .response();
        String responseString = response.asPrettyString();
        System.out.println("Response: " + responseString);
    }
}
