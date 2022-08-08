package baseTestingAPI;

import resources.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class ComplexNestedJson {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());

        // 1. Print Number of courses returned by API
        int count = jsonPath.getInt("courses.size()");
        System.out.println("Number of courses: " + count);

        // 2. Print Purchase Amount
        var amountOfPurchase = jsonPath.get("dashboard.purchaseAmount").toString();
        System.out.println("Purchase Amount: " + amountOfPurchase);

        // 3. Print Title of the first course
        var firstCourseTitle = jsonPath.getString("courses[0].title");
        System.out.println("Title of the first course: " + firstCourseTitle);

        // 4. Print All course titles and their respective Prices
        for (int i = 0; i < count; i++) {
            System.out.print(jsonPath.getString("courses[" + i + "].title") + ": ");
            System.out.println(jsonPath.get("courses[" + i + "].price").toString());
        }

        // 5. Print Number of copies sold by RPA Course
        System.out.print("Number of copies sold by RPA Course: ");
        for (int i = 0; i < count; i++) {
            String title = jsonPath.getString("courses[" + i + "].title");
            if (title.equals("RPA")) {
                int copies = jsonPath.getInt("courses[" + i + "].copies");
                System.out.println(copies);
            }
        }

        // 6. Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        for (int i = 0; i < count; i++) {
            var copies = jsonPath.getInt("courses[" + i + "].copies");
            var price = jsonPath.getInt("courses[" + i + "].price");
            var amount = copies * price;
            sum = sum + amount;
        }
        var purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount);
    }
}
