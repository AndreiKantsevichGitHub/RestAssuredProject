package baseTestingAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import resources.Payload;
import resources.ReUsableMethods;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basic {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Add place
        String response =
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .headers("Content-Type", "application/json")
                .body(Payload.AddPlace())
        .when()
                .post("/maps/api/place/add/json")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        String placeID = jsonPath.getString("place_id");

        // Update place
        String newAddress = "Rome, Italy";
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .headers("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
        .when()
                .put("/maps/api/place/update/json")
        .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //Get place
        String getPlaceResponse =
        given()
                .log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeID)
                .headers("Content-Type", "application/json")
        .when()
                .get("/maps/api/place/get/json")
        .then()
                .log().all().assertThat()
                .statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath1 = ReUsableMethods.rowToJson(getPlaceResponse);
        String actualAddress = jsonPath1.getString("address");

//        Assert.assertEquals
//                (actualAddress, newAddress);

        if (actualAddress.equals(newAddress))
            System.out.println("After updating of address the actual one matches to new ones.");

    }
}
