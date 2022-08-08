package oAuth;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class oAuthTest {

    public static void main(String[] args) {

        String url = "https://rahulshettyacademy.com/getCourse.php?" +
                "code=4%2F0AX4XfWgouy2pQLJytzc1lrCbdJ3oqR-B3XOyyY0gH0MYN9uacpdqNL-6Jsndn_nnVTYwlw" +
                "&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid" +
                "&authuser=0&prompt=none";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];

        String accessTokenResponse =
        given()
                .urlEncodingEnabled(false)
                .queryParam("code", code)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
        .when()
                .log().all()
                .post("https://www.googleapis.com/oauth2/v4/token") // Access token url
                .asString();

        JsonPath jsonPath = new JsonPath(accessTokenResponse);
        String accessToken = jsonPath.getString("access_token");

        String response =
        given()
                .queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)
        .when()
                .get("https://rahulshettyacademy.com/getCourse.php") // redirect_uri/Callback URL
                .asString();
        System.out.println(response);
    }
}
