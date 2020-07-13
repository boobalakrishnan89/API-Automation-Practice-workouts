package test.rest.freshwork;

import static org.hamcrest.Matchers.hasItem;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class Scenario1 extends PreAndTest {
	
	Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
	String started_at = timestamp.toInstant().toString(); 
	
	@BeforeTest
	public void setValue() {
		testCaseName = " Create/update/delete a time Entry";
		testDescription = " Creating updating deleting  a time Entry";
		nodes = "fresh_Books";
		authors = "BK";
		category = "API";
		dataFileName = "createtimeentry";
		dataFileType = "JSON";
	}

	@Test(priority = 1)
	public void createTimeEntry() throws IOException {


		String updatejson = FileUtils.readFileToString(new File("./data/" + "createtimeentry.json"),StandardCharsets.UTF_8);
		updatejson = updatejson.replaceAll("c_id", client_id).replaceAll("p_id", project_id) .replaceAll("s_at",started_at);
		Response createTimeEntry = postWithBodyAsStringFileAndUrl(updatejson,"/timetracking/business/" + bussiness_id + "/time_entries");
		createTimeEntry.prettyPrint();
		String entry = getContentWithKey(createTimeEntry, "time_entry.id");
		System.out.println(entry);
		id = Integer.parseInt(entry);
		verifyResponseCode(createTimeEntry, 200);
	}

	@Test(priority = 2)
	public void getentries() {
		 
		Response getallTimeEntry = get("/timetracking/business/" + bussiness_id + "/time_entries");
		getallTimeEntry.then().assertThat().body("time_entries.id", hasItem(id));
		verifyResponseCode(getallTimeEntry, 200);
	}

	@Test(priority = 3)
	public void updatecreateTimeEntry() throws IOException {

		String updatejson = FileUtils.readFileToString(new File("./data/" + "updatetimeentry.json"),StandardCharsets.UTF_8);
		updatejson = updatejson.replaceAll("c_id", client_id).replaceAll("p_id", project_id).replaceAll("s_at",started_at);

		Response updateTimeEntry = RestAssured.given().contentType(ContentType.JSON).body(updatejson).log().all().when()
				.put("/timetracking/business/" + bussiness_id + "/time_entries/" + id);
		
		verifyResponseCode(updateTimeEntry, 200);
	}
	
	@Test(priority = 4)
	public void deleteTimeEntry()   {
		
		Response deleteTimeEntry = delete("/timetracking/business/" + bussiness_id + "/time_entries/" + id);
		verifyResponseCode(deleteTimeEntry, 204);
	}
}
