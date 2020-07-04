package tests.rest.okta.app;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class CreateApp extends PreAndTest{
	
	
	
	@BeforeTest
	public void setValues() {
		testCaseName = "CreateApp (REST)";
		testDescription = "Create A new App";
		nodes = "Apps";
		authors = "BOO";
		category = "API";
		dataFileName = "createapp";
		dataFileType = "JSON";
	}

	@Test  
	public void createApp() throws IOException {
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		// Post the request
		
		//input values at runtime
		int random = 10000 + new Random(System.currentTimeMillis()).nextInt(20000);
				String requestData = FileUtils.readFileToString(new File("./data/"+"createapp.json"), StandardCharsets.UTF_8);
				requestData = requestData.replaceAll("app_name","MyApp"+random).replaceAll("app_type", "native");
				
		Response response = postWithHeaderAndBodyAsStringFile(headers, requestData, "/api/v1/apps");
		response.prettyPrint();
		// Verify the Content type
		verifyContentType(response, "JSON");
		// Verify the response status code
		verifyResponseCode(response, 200);	
		// Get the Client_id & name 
		String id = getContentWithKey(response, "id");
		String name = getContentWithKey(response, "name"); 
		System.out.println("id :"+id+"   "+ "name is :"+name );
		
		// verify the created client 
		
		Response response1 = getWithHeader(headers,"/api/v1/apps");
		verifyContentsWithKey(response1,"id",id);
		verifyContentsWithKey(response1,"name",name);
	}
	
	}
	 





