package test.rest.paystack;

import static org.hamcrest.Matchers.*;
import java.io.File;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class Scenario1 extends PreAndTest {

	File file = new File("./data/createPaymentPage.json");
	String name;
	@BeforeTest
	public void setValues() {
		testCaseName = " Create a new Payment Page";
		testDescription = " Create a new Payment Page";
		nodes = "payStack";
		authors = "BK";
		category = "API";
	}
	
	@Test(priority = 1)
	public void createpaymentPage() {
		Response createpaymentPage = postWithBodyAsFileAndUrl(file,"/page") ;
		String p_id = getContentWithKey(createpaymentPage, "data.id");
		id = Integer.parseInt(p_id);
		name = getContentWithKey(createpaymentPage, "data.name");
		verifyResponseCode(createpaymentPage, 200);
	}

	@Test(priority = 2)
	public void getallentries() {

		Response getallEntry = get("/page");
		getallEntry.then().assertThat().body("data.id", hasItem(id));
		verifyResponseCode(getallEntry, 200);
	}

	@Test(priority = 3)
	public void getTheCreatedPage()   {

		Response getcreatedEntry = get("/page/"+id);
		getcreatedEntry.then().assertThat().body("data.name", containsString(name));
		verifyResponseCode(getcreatedEntry, 200);
	}
}
