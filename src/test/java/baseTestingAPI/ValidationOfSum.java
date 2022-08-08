package baseTestingAPI;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import resources.Payload;

public class ValidationOfSum {

        // Verify if Sum of all Course prices matches with Purchase Amount

    @Test
    public void verifyTheMatches() {

        JsonPath jsonPath = new JsonPath(Payload.CoursePrice());

        int coursesNumber = jsonPath.getInt("courses.size()");
        int sum = 0;
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");

        for (int i = 0; i < coursesNumber; i++) {
            int price = jsonPath.getInt("courses[" + i + "].price");
            int copies = jsonPath.getInt("courses[" + i + "].copies");
            int amount = price * copies;
            sum = sum + amount;

            if (sum == purchaseAmount)
                System.out.println("Sum of all Course prices matches with Purchase Amount");
        }
    }
}
