package tests.rest.okta.client;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class CreateClient extends PreAndTest{
	
	
	
	@BeforeTest
	public void setValue() {
		testCaseName = "Createclient (REST)";
		testDescription = "Create A new Client";
		nodes = "Client";
		authors = "BOO";
		category = "API";
		dataFileName = "createClient";
		dataFileType = "JSON";
	}

	@Test (dataProvider ="fetchData") 
	public void createClient(File file) throws IOException {
		
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		String requestData = FileUtils.readFileToString(new File("./data/"+"createClient.json"), StandardCharsets.UTF_8);
		requestData = requestData.replaceAll("name_value", "kris"+random());
		// Post the request
		Response response = postWithHeaderAndBodyAsStringFile(headers, requestData, "/oauth2/v1/clients");
		response.prettyPrint();
		
		// Verify the Content type
		verifyContentType(response, "JSON");
		
		// Verify the response status code
		verifyResponseCode(response, 201);	
		
		// Get the Client_id & name 
		  id = getContentWithKey(response, "client_id");
		
		
		// verify the created client 
		
		Response response1 = getWithHeader(headers,"/oauth2/v1/clients");
		List<String> cids = getContentsWithKey(response1, "client_id");
		for (String clientid : cids) {
			if(clientid.equalsIgnoreCase(id)) {
				System.out.println("success"+" "+clientid);
				Assert.assertEquals(clientid, id);
				break;
			}
		}
	}
}





