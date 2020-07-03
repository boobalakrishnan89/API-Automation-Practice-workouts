package tests.rest.okta.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class VerifyAppInList extends RESTAssuredBase{
		
		
		
		@BeforeTest
		public void setValues() {
			testCaseName = "VerifyCreatedAppwithLogo (REST)";
			testDescription = "Verify Created App with Logo visible";
			nodes = "Apps";
			authors = "BOO";
			category = "API";
			dataFileName = " ";
			dataFileType = " ";
		}
		
		@Test
		public void verifyApp() {
			Map<String, String> headers = new HashMap<String, String>() ;
			headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
			
			Response response1 = getWithHeader(headers,"/api/v1/apps");
			List<String> name =  getContentsWithKey(response1, "label");
			System.out.println(name.get(0));
			verifyContentsWithKey(response1, "label", "MyApp22620");
			
		}
}
