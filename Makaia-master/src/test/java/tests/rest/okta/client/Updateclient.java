package tests.rest.okta.client;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class Updateclient extends RESTAssuredBase{
	

	@BeforeTest
	public void setValues() {
		testCaseName = "updateclient(REST)";
		testDescription = "update client ";
		nodes = "Client";
		authors = "BOO";
		category = "API";
		dataFileName = "updateclient";
		dataFileType = "JSON";
	}

	@Test 
	public void updateClient() throws IOException {	
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		Response response = getWithHeader(headers,"clients");
		List<String> cid = getContentsWithKey(response, "client_id");
		String id=cid.get(0);
		
		//updating the client_name in the created clients 
		String requestData = FileUtils.readFileToString(new File("./data/"+"updateclient.json"), StandardCharsets.UTF_8);
		requestData = requestData.replaceAll("id_value",""+id+"").replaceAll("name_value", "MrBooo");
		Response response1 =	RestAssured
				.given()
				.headers(headers)
				.contentType(ContentType.JSON)
				.request()
				.body(requestData)
				.when()
				.put("/oauth2/v1/clients/"+id);
		
				// verifying the response
				verifyResponseCode(response1, 200);
				//get request
				Response response2 = getWithHeader(headers,"/oauth2/v/1clients");
				//verifying the updated client_name
				verifyContentsWithKey(response2,"client_name","MrBooo");
	}
} 
	
	 





