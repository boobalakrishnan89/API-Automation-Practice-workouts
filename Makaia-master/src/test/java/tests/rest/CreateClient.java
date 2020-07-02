package tests.rest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class CreateClient extends RESTAssuredBase{
	public static String id;
	public static String name;
	Map<String, String> headers = new HashMap<String, String>() ;
	
	
	@BeforeTest
	public void setValues() {
		testCaseName = "Create A client ";
		testDescription = "Create A new Client ";
		nodes = "Client";
		authors = "BOO";
		category = "API";
		dataFileName = "createClient";
		dataFileType = "JSON";
	}

	@Test (dataProvider ="fetchData") 
	public void createClient(File file) {		
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		// Post the request
		Response response = postWithHeaderAndBodyAsFile(headers, file, "clients");
		response.prettyPrint();
		// Verify the Content type
		verifyContentType(response, "JSON");
		// Verify the response status code
		verifyResponseCode(response, 201);	
		// Get the Client_id & name 
		id = getContentWithKey(response, "client_id");
		name = getContentWithKey(response, "client_name"); 
		System.out.println("id :"+id+"   "+ "name is :"+name );
	}

	@Test(dependsOnMethods={"createClient"})  
	public void verifyCreatedClient() {	
		
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		Response response = getWithHeader(headers,"clients");
		verifyContentsWithKey(response,"client_id",id);
		verifyContentsWithKey(response,"client_name",name);
	}
}





