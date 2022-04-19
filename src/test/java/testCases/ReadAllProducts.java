package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {

	@Test
	public void read_All_Products() {
		
		SoftAssert softAssert = new SoftAssert();
		
		Response response= 
		given()
		//		.log().all()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Contetnt-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").
			when()
	//				.log().all()
					.get("/read.php").
			then()
	//				.log().all()
					.extract().response();
//					.statusCode(200)
//					.header("Content-Type","application/json; charset=UTF-8");
				
			int actualStatusCode= response.getStatusCode();
			System.out.println("actualStatusCode:" + actualStatusCode);
			Assert.assertEquals(actualStatusCode, 200);
			
			String actualHeader= response.getHeader("Contetnt-Type");
			
			String actualResponseBody= response.getBody().asString();
			System.out.println(actualResponseBody);
			
			JsonPath jp= new JsonPath(actualResponseBody);
			String firstProdcutId= jp.get("records[0].id");
			System.out.println(firstProdcutId);
			
			
			
			
			
	}
	
	
}
