package baseTestingAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import resources.ReUsableMethods;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ConversationOfHashMapIntoJson {

    @Test
    public void AddBookHashMap() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "RestAssured");
        map.put("isbn", "zzz");
        map.put("aisle", "999");
        map.put("author", "Andrei");

        RestAssured.baseURI = "http://216.10.245.166";
        String response =
        given()
                .headers("Content-Type", "application/json")
                .body(map)
        .when()
                .post("Library/Addbook.php")
        .then()
                .log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath jsonPath = ReUsableMethods.rowToJson(response);

        String id = jsonPath.getString("ID");
        System.out.println("ID: " + id);
    }
}
