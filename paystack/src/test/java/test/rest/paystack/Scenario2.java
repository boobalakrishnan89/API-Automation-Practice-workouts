package test.rest.paystack;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import lib.rest.PreAndTest;

public class Scenario2 extends PreAndTest {

	String subscription_code;
	String email_token;
	String  customer_code;
	String plan_code;

	@BeforeTest
	public void setValue() {
		testCaseName = " Creating Subscriptions";
		testDescription = " Creating Subscriptions";
		nodes = "paystack";
		authors = "BK";
		category = "API";

	}

	@Test(priority = 1)
	public void createCustomers() throws IOException {

		String updatejson = FileUtils.readFileToString(new File("./data/" + "createCustomer.json"),StandardCharsets.UTF_8);
		Response createCustomers = postWithBodyAsStringFileAndUrl(updatejson,"/customer");
		customer_code = getContentWithKey(createCustomers, "data.customer_code");
		verifyResponseCode(createCustomers, 200);
	}

	@Test(priority = 2)
	public void createPlans() throws IOException {

		String updatejson = FileUtils.readFileToString(new File("./data/" + "createPlan.json"),StandardCharsets.UTF_8);
		Response createPlan = postWithBodyAsStringFileAndUrl(updatejson,"/plan");
		plan_code = getContentWithKey(createPlan, "data.plan_code");
		verifyResponseCode(createPlan, 201);
		System.out.println(plan_code);
	}


	@Test(priority = 3)
	public void createSubscription() throws IOException {

		String updatejson = FileUtils.readFileToString(new File("./data/" + "createSubscription.json"),StandardCharsets.UTF_8);
		updatejson = updatejson.replaceAll("cust_code", customer_code)
				.replaceAll("plan_code", plan_code);
		Response createSubscription = postWithBodyAsStringFileAndUrl(updatejson,"/subscription");
		subscription_code = getContentWithKey(createSubscription, "data.subscription_code");
		email_token = getContentWithKey(createSubscription, "data.email_token");
		verifyResponseCode(createSubscription, 200);
	}


	@Test(priority = 4)
	public void verifycreatedSubscription()   {

		Response getcreatedSubscription = get("/subscription/"+subscription_code);
		verifyResponseCode(getcreatedSubscription, 200);
		getcreatedSubscription.then().assertThat().body("message", containsString("Subscription retrieved successfully"));
	}

	@Test(priority = 5)
	public void disableSubscription() throws IOException   {
		String updatejson = FileUtils.readFileToString(new File("./data/" + "disableSubscription.json"),StandardCharsets.UTF_8);
		updatejson = updatejson.replaceAll("sub_code", subscription_code)
				.replaceAll("e_token", email_token);
		Response disableSubscription = postWithBodyAsStringFileAndUrl(updatejson,"/subscription/disable");
		disableSubscription.then().assertThat().body("message", containsString("Subscription disabled successfully"));
	}

	@Test(priority = 6)
	public void enableSubscription() throws IOException   {
		String updatejson = FileUtils.readFileToString(new File("./data/" + "disableSubscription.json"),StandardCharsets.UTF_8);
		updatejson = updatejson.replaceAll("sub_code", subscription_code)
				.replaceAll("e_token", email_token);
		Response enableSubscription = postWithBodyAsStringFileAndUrl(updatejson,"/subscription/enable");
		enableSubscription.then().assertThat().body("message", containsString("Subscription enabled successfully"));
	}

}
