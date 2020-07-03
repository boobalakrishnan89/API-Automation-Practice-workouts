package tests.rest.okta.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.rest.RESTAssuredBase;

public class UpdateAppwithLogo extends RESTAssuredBase{



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
	public void updataAppwithLogo() {
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT");
		Response response1 = getWithHeader(headers,"/api/v1/apps");
		List<String> aid =  getContentsWithKey(response1, "id");
		String id = aid.get(0);
		System.out.println(id);

		
		Response response =  RestAssured
				.given()
				.header("Authorization", "SSWS00TLzU6N8V0T7oIdHOmWi3dbXOk6CW18GN-80Sx3gT")
				.header("Content-Type", "multipart/form-data")
				.header("_xsrfToken", "b36af29e560f9e8132828a776aa0642476e2e28c381378dfac787b288cd5246c")
				.header("linkId","aln177a159h7Zf52X0g8")
				.headers("Sec-Fetch-Dest","iframe")
				.multiPart("file","./logo.jpg")
				.log()
				.all()
				.post("/admin/app/oidc_client/instance/"+id+"/edit-link")
				.then()
				.log()
				.all().extract().response();
		response.prettyPrint();

	}

}
