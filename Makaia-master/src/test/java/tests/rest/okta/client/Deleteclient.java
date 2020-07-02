package tests.rest.okta.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class Deleteclient extends RESTAssuredBase{

	@BeforeTest
	public void setValues() {
		testCaseName = "deleteclient (REST) ";
		testDescription = "delete the created client ";
		nodes = "Client";
		authors = "BOO";
		category = "API";
		dataFileName = "";
		dataFileType = "";
	}

	@Test 
	public void deleteclient(){	
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		Response response = getWithHeader(headers,"/oauth2/v1/clients");
		List<String> cid = getContentsWithKey(response, "client_id");
		String id=cid.get(0);
		//delete the created client id 
		Response response1 = deleteWithHeaderAndPathParamWithoutRequestBody(headers,"/oauth2/v1/clients/"+id);
		// verifying the response
		verifyResponseCode (response1, 204);
	}
}





