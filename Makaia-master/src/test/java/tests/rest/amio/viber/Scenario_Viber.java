package tests.rest.amio.viber;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class Scenario_Viber  extends PreAndTest{

	@BeforeTest
	public void setValue() {
		testCaseName = "Send File message /Validate ViberBot (REST)";
		testDescription = " Send File message /Validate  messenge in Viber channel on Amio.io";
		nodes = "channel_Viber";
		authors = "BK";
		category = "API";
		dataFileName = "createvibermessage";
		dataFileType = "JSON";
	}

	@Test
	public void scenario1_Viber() throws IOException {

		Map<String, String> headers = new HashMap<String, String>() ;
		headers.put("Authorization", "Bearer"+" "+OAUTH_TOKEN);

		
		// get contact from allcontacts
		Response getContactList = getWithHeader(headers,"/v1/channels/"+viber_id+"/contacts");
		getContactList.prettyPrint();
		List<String> contactlList = getContentsWithKey(getContactList, "id");
		String contact_id = contactlList.get(0);

		// validating the status code 
		verifyResponseCode(getContactList, 200);
		
		
		

		// send text message using  viber id and contact id 

		String messagetext = FileUtils.readFileToString(new File("./data/"+"createvibermessage.json"), StandardCharsets.UTF_8);
		messagetext = messagetext.replaceAll("channel_id", viber_id).replaceAll("contact_id",contact_id);

		// post request with url and body as file 
		Response createvibermessage = postWithHeaderAndBodyAsStringFile(headers, messagetext,"/v1/messages");
		createvibermessage.prettyPrint();
		String text_id = getContentWithKey(createvibermessage, "id");
		System.out.println(text_id);
		
		// validating the status code  for text message creation
		verifyResponseCode(createvibermessage, 201);

		
		// send file message using  viber id and contact id 

		String messagefile = FileUtils.readFileToString(new File("./data/"+"createviberfilemessage.json"), StandardCharsets.UTF_8);
		messagefile = messagefile.replaceAll("channel_id", viber_id).replaceAll("contact_id",contact_id);

		// post request with url and body as file 
		Response createviberfilemessage = postWithHeaderAndBodyAsStringFile(headers, messagefile,"/v1/messages");
		createviberfilemessage.prettyPrint();
		String filemsg_id = getContentWithKey(createviberfilemessage, "id");
		verifyResponseCode(createvibermessage, 201);
		

		// validating the created  file message is available in the message list 

		Response getviberMessageList = getWithHeader(headers,"/v1/channels/"+viber_id+"/contacts/"+contact_id+"/messages");
		List<String> messageIdList = getContentsWithKey(getviberMessageList, "id");
		for (String messageId : messageIdList) {
			if(messageId.equalsIgnoreCase(filemsg_id)) {
				System.out.println("File messages exist in the MessageList");
			}
		}
		verifyResponseCode(getviberMessageList, 200);	
	}
}

