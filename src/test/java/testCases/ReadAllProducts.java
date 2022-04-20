package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {
	
	SoftAssert softAssert;
	

	@Test
	public void read_All_Products() {
		
		softAssert = new SoftAssert();
		
		Response response= 
		given()
		//		.log().all() 
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Contetnt-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").		//preemptive: if there is basic auth then we have to use it
			when()
	//				.log().all()
					.get("/read.php").
			then()
	//				.log().all()			//it will give you all the products to validate this code worked 
					.extract().response();
//					.statusCode(200)
//					.header("Content-Type","application/json; charset=UTF-8");
				
			int actualStatusCode= response.getStatusCode();
			System.out.println("actualStatusCode:" + actualStatusCode);
			Assert.assertEquals(actualStatusCode, 200);
			
			String actualResponseContentType= response.getHeader("Content-Type");
			System.out.println(actualResponseContentType);
			Assert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");
			
			
			String actualResponseBody= response.getBody().asString();
			System.out.println(actualResponseBody);
			
			
			//to find out if the first ID is null or not
			
			JsonPath jp= new JsonPath(actualResponseBody);
			String firstProdcutId= jp.get("records[0].id");  //.get JsonPath is an object so we can save it in any types of variable but here string
			System.out.println(firstProdcutId);
			
			if (firstProdcutId !=null) {
				System.out.println("Product exists.");
			}
			else {
				System.out.println("Product doesn't exist!");
			}
			
			
			
			
			
	}
	
	
}
