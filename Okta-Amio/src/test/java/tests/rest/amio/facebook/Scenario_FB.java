package tests.rest.amio.facebook;

import static org.hamcrest.Matchers.hasItems;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class Scenario_FB  extends PreAndTest{


	@BeforeTest
	public void setValue() {
		testCaseName = "Create/Validate/Delete FBChannel (REST)";
		testDescription = "Create / validate / delete  Facebook messenger channel on Amio.io";
		nodes = "channel_FaceBookMessanger";
		authors = "BK";
		category = "API";
		dataFileName = "createfbchannel";
		dataFileType = "JSON";
	}

	@Test
	public void scenario1_fb() throws IOException {
		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "Bearer"+" "+OAUTH_TOKEN);

		//adding   value to the json obj during execution
		String updatefile = FileUtils.readFileToString(new File("./data/"+"createfbchannel.json"), StandardCharsets.UTF_8);
		updatefile = updatefile.replaceAll("name_text", "krisFB_messenger"+random()).replaceAll("app_id",App_id).replaceAll("fb_access_token", access_token);

		// post request with url and body as file 
		Response createFBchannel = postWithHeaderAndBodyAsStringFile(headers, updatefile,"v1/channels");
		createFBchannel.prettyPrint();

		// validating the status code 
		verifyResponseCode(createFBchannel, 201);	

		// get the created channel id 
		id = getContentWithKey(createFBchannel, "id");

		// get the created fb channel from the list of available channels
		Response getChannelList = getWithHeader(headers,"/v1/channels");
		//getChannelList.prettyPrint();

		// validating the status code 
		verifyResponseCode(getChannelList, 200);
		
		//validating with hamcrest.Matchers 
		getChannelList.then().assertThat().body("id",hasItems(id));

		//Deleting the created channel 
		Response deleteChannel = deleteWithHeaderAndPathParamWithoutRequestBody(headers,"/v1/channels/"+id);

		// validating the status code after deletion
		verifyResponseCode(deleteChannel, 204);
	}
}
