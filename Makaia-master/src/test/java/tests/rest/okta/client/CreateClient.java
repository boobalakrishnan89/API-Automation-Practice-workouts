package tests.rest.okta.client;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class CreateClient extends RESTAssuredBase{
	
	
	
	@BeforeTest
	public void setValues() {
		testCaseName = "Createclient (REST)";
		testDescription = "Create A new Client";
		nodes = "Client";
		authors = "BOO";
		category = "API";
		dataFileName = "createClient";
		dataFileType = "JSON";
	}

	@Test (dataProvider ="fetchData") 
	public void createClient(File file) {
		
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		// Post the request
		Response response = postWithHeaderAndBodyAsFile(headers, file, "/oauth2/v1/clients");
		response.prettyPrint();
		// Verify the Content type
		verifyContentType(response, "JSON");
		// Verify the response status code
		verifyResponseCode(response, 201);	
		// Get the Client_id & name 
		String id = getContentWithKey(response, "client_id");
		String name = getContentWithKey(response, "client_name"); 
		//System.out.println("id :"+id+"   "+ "name is :"+name );
		
		// verify the created client 
		
		Response response1 = getWithHeader(headers,"/oauth2/v1/clients");
		verifyContentsWithKey(response1,"client_id",id);
		verifyContentsWithKey(response1,"client_name",name);
	}
	 
}





