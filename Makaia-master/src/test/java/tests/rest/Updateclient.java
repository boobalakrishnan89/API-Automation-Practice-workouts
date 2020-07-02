package tests.rest;

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
	public static String id;
	Map<String, String> headers = new HashMap<String, String>() ;


	@BeforeTest
	public void setValues() {
		testCaseName = "updateclient ";
		testDescription = "update client ";
		nodes = "Client";
		authors = "BOO";
		category = "API";
		dataFileName = "updateclient";
		dataFileType = "JSON";
	}

	@Test 
	public void verifyCreatedClient() {	

		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		Response response = getWithHeader(headers,"clients");
		List<String> cid = getContentsWithKey(response, "client_id");
		id=cid.get(0);
	}
	
	@Test ( dependsOnMethods={"verifyCreatedClient"})    
	public void updateCreatedClientName() throws IOException {	

		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		
		//replace the value of client_id and client_name at runtime 
		String requestData = FileUtils.readFileToString(new File("./data/"+"updateclient.json"), StandardCharsets.UTF_8);
		requestData = requestData.replaceAll("id_value",""+id+"").replaceAll("name_value", "MrBooo");
		Response response =	RestAssured
				.given()
				.headers(headers)
				.contentType(ContentType.JSON)
				.request()
				.body(requestData)
				.when()
				.put("clients/"+id);
		
				// verifying the response
				verifyResponseCode(response, 200);
				//get request
				Response response1 = getWithHeader(headers,"clients");
				//verifying the update client_name
				verifyContentsWithKey(response1,"client_name","MrBooo");
	}
}





