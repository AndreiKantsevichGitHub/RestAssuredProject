package oAuth;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class oAuthTestWithSelenium {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=https://www.googleapis.com/auth/userinfo.email" +
                "&https://accounts.google.com/o/oauth2/v2/auth" +
                "&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com" +
                "&response_type=code" +
                "&redirect_uri=https://rahulshettyacademy.com/getCourse.php");

        WebElement email = driver.findElement(By.cssSelector("input[@type='email']"));
        email.sendKeys("master");

        WebElement password = driver.findElement(By.cssSelector("[type='password']"));
        password.sendKeys("slave");

        driver.findElement(By.cssSelector("[type='submit']")).sendKeys(Keys.ENTER);


        String url = driver.getCurrentUrl();
        System.out.println(url);
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];

        String response =
                given()
                        .urlEncodingEnabled(false)
                        .queryParam("code", code)
                        .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                        .queryParam("grant_type", "authorization_code")
                        .when()
                        .log().all()
                        .post("https://www.googleapis.com/oauth2/v4/token")
                        .asString();
        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");

        String response2 =
                given()
                        .queryParam("access_token", accessToken)
                        .when()
                        .log().all()
                        .get("https://rahulshettyacademy.com/getCourse.php")
                        .asString();
        System.out.println(response2);
    }
}
