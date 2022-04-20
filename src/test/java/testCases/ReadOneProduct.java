package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class ReadOneProduct {
	
	SoftAssert softAssert;
	
	public ReadOneProduct() {
		softAssert = new SoftAssert();
	}

	@Test
	public void read_One_Product() {
		
		Response response= 
		given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Contetnt-Type", "application/json")
				.auth().preemptive().basic("demo@techfios.com", "abc123")		//preemptive: if there is basic auth then we have to use it
				.queryParam("id", "4276").
		when()
					.get("/read_one.php").
			then()
					.extract().response();

				
			int actualStatusCode= response.getStatusCode();
			System.out.println("actualStatusCode:" + actualStatusCode);
			softAssert.assertEquals(actualStatusCode, 200, "Status codes are not matching!");
			
			String actualResponseContentType= response.getHeader("Content-Type");
			System.out.println(actualResponseContentType);
			softAssert.assertEquals(actualResponseContentType, "application/json");
			
			String actualResponseBody= response.getBody().asString();
			System.out.println(actualResponseBody);
			
			JsonPath jp = new JsonPath(actualResponseBody);
			String productId = jp.get("id");
			softAssert.assertEquals(productId, "4276", "Product Ids are not matching!");

			String productName = jp.get("name");
			softAssert.assertEquals(productName, "Best Chair 2.0", "Product names are not matching!");

			String productPrice = jp.get("price");
			softAssert.assertEquals(productPrice, "100", "Product prices are not matching!");
			System.out.println("productPrice: "+ productPrice);
			
			softAssert.assertAll(); 					//it'll execute everything as well give u which steps failed
			
			
	}
	
	
}
