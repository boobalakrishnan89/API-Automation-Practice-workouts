package tests.selenium;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class Deleteclient extends RESTAssuredBase{
	public static String id;
	Map<String, String> headers = new HashMap<String, String>() ;


	@BeforeTest
	public void setValues() {
		testCaseName = "deleteclient ";
		testDescription = "delete the created client ";
		nodes = "Client";
		authors = "BOO";
		category = "API";
	}

	@Test 
	public void getclient(){	

		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		Response response = getWithHeader(headers,"clients");
		List<String> cid = getContentsWithKey(response, "client_id");
		id=cid.get(0);
		//delete the created client id 
		Response response1 =deleteWithHeaderAndPathParamWithoutRequestBody(headers,"clients/"+id);
		// verifying the response
		verifyResponseCode (response1, 204);
	}


}





