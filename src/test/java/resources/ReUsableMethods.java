package resources;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {
    public static JsonPath rowToJson(String response) {
        JsonPath jsonPath;
        jsonPath = new JsonPath(response);
        return jsonPath;
    }
}
