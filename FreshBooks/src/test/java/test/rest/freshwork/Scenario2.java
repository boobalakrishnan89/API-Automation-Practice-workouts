package test.rest.freshwork;

import static org.hamcrest.Matchers.containsString;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class Scenario2 extends PreAndTest {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	LocalDateTime date = LocalDateTime.now();  
	String create_date = dtf.format(date).toString();
	String updated_country;


	@BeforeTest
	public void setValue() {
		testCaseName = " Create/update/Verify Estimates";
		testDescription = " Creating updating Verify Estimates";
		nodes = "fresh_Books";
		authors = "BK";
		category = "API";
		 
	}

	@Test(priority = 1)
	public void createEstimates() throws IOException {

		String updatejson = FileUtils.readFileToString(new File("./data/" + "createEstimates.json"),StandardCharsets.UTF_8);
		updatejson = updatejson.replaceAll("c_id", client_id).replaceAll("cur_data", create_date) ;
		
		Response createEstimaates = postWithBodyAsStringFileAndUrl(updatejson,"/accounting/account/"+account_id+"/estimates/estimates");
		
		String entry = getContentWithKey(createEstimaates, "response.result.estimate.estimateid");
		id = Integer.parseInt(entry);
		
		verifyResponseCode(createEstimaates, 200);
	}


	@Test(priority = 2)
	public void updateEstimates() throws IOException {

		String updatejson = FileUtils.readFileToString(new File("./data/" + "updateEstimates.json"),StandardCharsets.UTF_8);
		
		Response updateEstimates = RestAssured.given().contentType(ContentType.JSON).body(updatejson).log().all().when()
				.put("/accounting/account/"+account_id+"/estimates/estimates/" + id);
		
		verifyResponseCode(updateEstimates, 200);
		
		updated_country = getContentWithKey(updateEstimates, "response.result.estimate.country");
	}

	@Test(priority = 3)
	public void verifyEstimates()   {

		Response getEstimate = get("/accounting/account/"+account_id+"/estimates/estimates/"+id);
		
		verifyResponseCode(getEstimate, 200);
		
		getEstimate.then().assertThat().body("response.result.estimate.country", containsString(updated_country));
	}
}
