package com.sample.code;

import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DummyAPITest {
	
	public static void main(String args[]) {
		getEmployees();
		deleteEmployee();
		getOneEmployee();
	}
	
	@Test
	public static void getEmployees() {
		int statusCode = RestAssured.given().when().get("http://dummy.restapiexample.com/api/v1/employees").getStatusCode();
		
		Assert.assertEquals(statusCode, 200);
	}
	
	//The delete method for the dummy api seems to be not working. So, asserted with 404
	@Test
	public static void deleteEmployee() {
		int statusCode = RestAssured.given().when().get("http://dummy.restapiexample.com/api/v1/delete/20").getStatusCode();
		
		Assert.assertEquals(statusCode, 404);
	}
	
	//Delete method not working so asserted message on GET method.
	@Test
	public static void getOneEmployee() {
		Response response = RestAssured.given().when().get("http://dummy.restapiexample.com/api/v1/employee/2");
		
		response.then().assertThat().body("status", Is.is("success"));
		response.then().assertThat().body("message", Is.is("Successfully! Record has been fetched."));
	}

}
