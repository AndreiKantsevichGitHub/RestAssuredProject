package pojo.pojoDeserialization.deserialization;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.pojoDeserialization.Api;
import pojo.pojoDeserialization.GetCourse;
import pojo.pojoDeserialization.Mobile;
import pojo.pojoDeserialization.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class oAuthDeserializeTest {

    public static void main(String[] args) {

        String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qgorynjoZ_Ihi8QsHRM2PStVMlNLV6ggkvg-R2Vdietk3-9N_ldzsFHbV7VJClS0g&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=2&prompt=none";
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

//        System.out.println(accessTokenResponse);

        JsonPath jsonPath = new JsonPath(accessTokenResponse);
        String accessToken = jsonPath.getString("access_token");

        GetCourse getCourse =
        given()
                .queryParam("access_token", accessToken)
                .expect().defaultParser(Parser.JSON)
        .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .as(GetCourse.class);

        // Get price of API course - "SoapUI Webservices testing"
        System.out.print(getCourse.getCourses().getApi().get(1).getCourseTitle() + ": ");
        List<Api> apiCourse = getCourse.getCourses().getApi();
        for (Api api : apiCourse)
            if (api.getCourseTitle().equals("SoapUI Webservices testing")) {
                System.out.println(api.getPrice());
            }

        // Get price of Mobile course - "Appium-Mobile Automation using Java"
        System.out.print(getCourse.getCourses().getMobile().get(0).getCourseTitle() + ": ");
        List<Mobile> mobileCourse = getCourse.getCourses().getMobile();
        for (Mobile mobile : mobileCourse)
            if (mobile.getCourseTitle()
                    .equalsIgnoreCase("Appium-Mobile Automation using Java")) {
                System.out.println(mobile.getPrice());
            }

        // Get the course names of WebAutomation
        ArrayList<String> arrayList = new ArrayList<>();
        List<WebAutomation> webAutomationCourses = getCourse.getCourses().getWebAutomation();
        for (int i = 0; i < webAutomationCourses.size(); i++)
            arrayList.add(webAutomationCourses.get(i).getCourseTitle());

        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        List<String> expectedList = Arrays.asList(courseTitles);

        if (expectedList.equals(arrayList))
           System.out.println("List of the course names of WebAutomation is correct");
        else
           System.out.println("List is not correct");

//        Assert.assertEquals(arrayList, expectedList);
    }
}
