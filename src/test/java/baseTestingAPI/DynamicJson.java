package baseTestingAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import resources.Payload;
import resources.ReUsableMethods;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void  addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        String response =
        given()
                .headers("Content-Type", "application/json")
                .body(Payload.AddBook(isbn, aisle))
        .when()
                .post("Library/Addbook.php")
        .then()
                .log().all().assertThat()
                .statusCode(200).extract().response().asString();

        JsonPath jsonPath = ReUsableMethods.rowToJson(response);
        String id = jsonPath.getString("ID");
        System.out.println(id);
    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        return new Object[][]
                {
                        {"x", "01"}, {"xx", "02"}, {"xxx", "03"}
                };
    }
}
