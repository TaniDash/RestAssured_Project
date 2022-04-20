package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;

public class UpdateProduct {
	
	SoftAssert softAssert;
	Map<String, String>createPayloadMap;
	Map<String, String>updatePayloadMap;
	String expectedProductName;
	String expectedProductPrice;
	String expectedProductDescription;
	String firstProdcutId;
	
	
	public UpdateProduct() {
		softAssert = new SoftAssert();
	}

	public Map<String, String> createPayloadMap(){
		createPayloadMap= new HashMap<String, String>();
		
		createPayloadMap.put("name", "Best Chair 2.0");
		createPayloadMap.put("price", "100");
		createPayloadMap.put("description", "The best chair for programmers by TD");
		createPayloadMap.put("category_id", "2");
		
		return createPayloadMap;
	}
	public Map<String, String> updatePayloadMap(){
		updatePayloadMap= new HashMap<String, String>();
		
		updatePayloadMap.put("id", firstProdcutId);
		updatePayloadMap.put("name", "Best Chair forever");
		updatePayloadMap.put("price", "150");
		updatePayloadMap.put("description", "The best updated chair for programmers");
		updatePayloadMap.put("category_id", "2");
		
		return updatePayloadMap;
	}
	
	@Test(priority=0)
	public void create_New_Product() {
		
		Response response= 
		given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Contetnt-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123")
				.body(createPayloadMap()).   
		when()
					.post("/create.php").
			then()
					.extract().response();

				
			int actualResponseStatus= response.getStatusCode();
			System.out.println("actual response status: " + actualResponseStatus);
			softAssert.assertEquals(actualResponseStatus, 201, "Status codes are not matching!");
			
			String actualResponseContentType= response.getHeader("Content-Type");
			System.out.println("actual Response ContentType: " + actualResponseContentType);
			softAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8", "Response Content-Types are not matching!");
			
			String actualResponseBody= response.getBody().asString();
			System.out.println("actualResponseBody: "+ actualResponseBody);
			
			JsonPath jp = new JsonPath(actualResponseBody);
			String producMessage = jp.get("message");
			softAssert.assertEquals(producMessage, "Product was created.", "Product messages are not matching!");		
			softAssert.assertAll(); 
	}
	
	@Test (priority=1)
	public void read_All_Products() {
		
					
		Response response= 
		given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Contetnt-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").		
			when()
					.get("/read.php").
			then()
					.extract().response();
				
			int actualResponseStatus= response.getStatusCode();
			System.out.println("actualResponseStatus:" + actualResponseStatus);
			softAssert.assertEquals(actualResponseStatus, 200);
			
			String actualResponseBody= response.getBody().asString();
		//	System.out.println(actualResponseBody);
			
			JsonPath jp= new JsonPath(actualResponseBody);
			firstProdcutId= jp.get("records[0].id");  
			
			System.out.println("firstProdcutId is :" + firstProdcutId);
			softAssert.assertAll(); 
	}
	
	@Test(priority=2)
	public void update_Product() {
					
		Response response= 
		given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Contetnt-Type", "application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").
				body(updatePayloadMap()).
		when()
					.put("/update.php").
			then()
					.extract().response();

				
			int actualResponseStatusCode= response.getStatusCode();
			System.out.println("actualResponseStatusCode:" + actualResponseStatusCode);
			softAssert.assertEquals(actualResponseStatusCode, 200, "Status codes are not matching!");
			
			String actualResponseContentType= response.getHeader("Content-Type");
			System.out.println("actualResponseContentType: "+ actualResponseContentType);
			softAssert.assertEquals(actualResponseContentType, "application/json; charset=UTF-8");
			
			String actualResponseBody= response.getBody().asString();
			System.out.println(actualResponseBody);
			
			JsonPath jp = new JsonPath(actualResponseBody);
			String actualProductMessage = jp.get("message");
			softAssert.assertEquals( actualProductMessage, "Product was updated.", "Product messages are not matching!");
			
			softAssert.assertAll(); 							
	}
	
		@Test(priority=3)
		public void read_One_Product() {
			
			expectedProductName= updatePayloadMap.get("name");
			System.out.println("Expected Product name is: "+ expectedProductName);
			
			expectedProductPrice= updatePayloadMap.get("price");
			System.out.println("Expected Product price is: "+ expectedProductPrice);
			
			expectedProductDescription= updatePayloadMap.get("description");
			System.out.println("Expected Product description is: "+ expectedProductDescription);
			
			Response response= 
			given()
					.baseUri("https://techfios.com/api-prod/api/product")
					.header("Contetnt-Type", "application/json")
					.auth().preemptive().basic("demo@techfios.com", "abc123")		
					.queryParam("id", firstProdcutId).
			when()
						.get("/read_one.php").
				then()
						.extract().response();

					
				int actualResponseStatusCode= response.getStatusCode();
				System.out.println("actualResponseStatusCode:" + actualResponseStatusCode);
				softAssert.assertEquals(actualResponseStatusCode, 200, "Status codes are not matching!");
				
				String actualResponseContentType= response.getHeader("Content-Type");
				System.out.println("actualResponseContentType: "+ actualResponseContentType);
				softAssert.assertEquals(actualResponseContentType, "application/json");
				
				String actualResponseBody= response.getBody().asString();
				System.out.println(actualResponseBody);
				
				JsonPath jp = new JsonPath(actualResponseBody);
				String actualProductId = jp.get("id");
				softAssert.assertEquals(actualProductId, firstProdcutId, "Product Ids are not matching!");

				String actualProductName = jp.get("name");
				softAssert.assertEquals(actualProductName, expectedProductName, "Product names are not matching!");

				String actualProductPrice = jp.get("price");
				softAssert.assertEquals(actualProductPrice, expectedProductPrice, "Product prices are not matching!");
				
				String actualProductDescription = jp.get("description");
				softAssert.assertEquals(actualProductDescription, expectedProductDescription, "Product descriptions are not matching!");
				
				softAssert.assertAll(); 							
		}
		
}
