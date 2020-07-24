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

	public static int id;
	public static String auth;
	public static String token_value;
	
  

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
		RestAssured.baseURI = "https://"+prop.getProperty("server");
		 RestAssured.authentication = RestAssured.oauth2("sk_test_f83951eeb3f7ccb38c06e8cb9343c721037a9b46");
		auth = prop.getProperty("auth");
		token_value= prop.getProperty("token_value");
		 
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
