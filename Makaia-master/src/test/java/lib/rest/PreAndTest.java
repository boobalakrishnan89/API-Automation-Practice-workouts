package lib.rest;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;


import io.restassured.RestAssured;
import lib.utils.DataInputProvider;

public class PreAndTest extends RESTAssuredBase{

	public String dataFileName, dataFileType;	

	public static String id;
	public static String OAUTH_TOKEN;
	public static String App_id;
	public static String access_token;
	public static String viber_id;

	public static int  random() {
		int random = 10000 + new Random(System.currentTimeMillis()).nextInt(20000);
		return random;
	}

	@BeforeSuite
	public void beforeSuite() {
		startReport();
	}

	@BeforeTest
	public void beforeTest() {

	}


	@BeforeClass
	public void beforeClass() {
		startTestCase(testCaseName, testDescription);		
	}


	@BeforeMethod
	public void beforeMethod() throws FileNotFoundException, IOException {
		//for reports		
		svcTest = startTestModule(nodes);
		svcTest.assignAuthor(authors);
		svcTest.assignCategory(category);

		Properties prop = new Properties();
		prop.load(new FileInputStream(new File("./src/test/resources/config.properties")));

		//RestAssured.baseURI = "https://"+prop.getProperty("serverokta")+"/"+prop.getProperty("resourcesuser")+"/";
		RestAssured.baseURI = "https://"+prop.getProperty("serveramio");
		//RestAssured.authentication = RestAssured.basic(prop.getProperty("username"), prop.getProperty("password"));
		OAUTH_TOKEN = prop.getProperty("amio_OAUTH_Token");
		App_id = prop.getProperty("fb_app_id");
		access_token = prop.getProperty("fb_access_Tok");
		viber_id = prop.getProperty("viber_id");
	}

	@AfterMethod
	public void afterMethod() {

	}

	@AfterClass
	public void afterClass() {

	}

	@AfterTest
	public void afterTest() {

	}

	@AfterSuite
	public void afterSuite() {
		endResult();
	}

	@DataProvider(name="fetchData")
	public  Object[][] getData(){
		if(dataFileType.equalsIgnoreCase("Excel"))
			return DataInputProvider.getSheet(dataFileName);	
		else if(dataFileType.equalsIgnoreCase("JSON")){
			Object[][] data = new Object[1][1];
			data[0][0] = new File("./data/"+dataFileName+"."+dataFileType);
			System.out.println(data[0][0]);
			return data;
		}else {
			return null;
		}

	}

	@Override
	public long takeSnap() {
		return 0;
	}	



}
