package stepDefinitions;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;




public class FlightBooking {

	
	WebDriver driver=null;
	//Capture the logs
		
		Logger log = Logger.getLogger(FlightBooking.class);

		//Reading OR from properties files
		Properties prop = new Properties();
		FileInputStream inp = new FileInputStream("src/main/resources/config.properties");
		prop.load(inp);

    

	@Given("User has Logged into MercuryTours with \"(.*)\" and \"(.*)\"")
	public void user_has_logged_into_mercury_tours_with_username_and_password(String username, String password) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriver_path"));
		driver= new ChromeDriver();

		//1. Open http://newtours.demoaut.com/ in chrome browser
		driver.navigate().to(prop.getProperty("URL"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


		
		
		//Verify mercury tours on home page 
		WebElement HomeLbl = driver.findElement(By.xpath(prop.getProperty("homePage_xpath")));
		Assert.assertEquals(true,HomeLbl.isDisplayed());
		
		driver.findElement(By.name("userName")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).click();
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		//Verify Flight Finder page opened
		WebElement FlighFindelement = driver.findElement(By.xpath(prop.getProperty("flightFinder_xpath")));
		//Assert.assertTrue("Login Failed", b_result);
		Assert.assertEquals(true,FlighFindelement.isDisplayed());

		throw new io.cucumber.java.PendingException();
	}


	@When("User Enters the Flight Details as \"(.*)\" and \"(.*)\" and \"(.*)\" and \"(.*)\" and \"(.*)\"")
	public void user_enters_the_flight_details_as(String typeOfFlight,String passengers,String departingFrom,String departingTo,String serviceClass) {

		//----------------------------------- code for inputting data -----------------------------------------------
		
		//Flight Type
		String strtypeOfFlight = typeOfFlight;
		driver.findElement(By.xpath("//input[@value='**" + strtypeOfFlight + "**']")).click(); 
		
		//Select Passengers
		Select Passangers= new Select(driver.findElement(By.cssSelector("select[name='passCount']")));
		Passangers.selectByVisibleText(passengers);


		//Departing from
		Select Departingfrom = new Select(driver.findElement(By.cssSelector("select[name='fromPort']")));
		Departingfrom.selectByVisibleText(departingFrom);


		//Arring In
		Select ArrivingIn = new Select(driver.findElement(By.cssSelector("select[name='toPort']")));
		ArrivingIn.selectByVisibleText(departingTo);


		//Service Class
		String strserviceClass = serviceClass;
		driver.findElement(By.xpath("//input[@value='**" + strserviceClass + "**']")).click();

		//Airline Preference
		Select AirlinePreference = new Select(driver.findElement(By.cssSelector("select[name='airline']")));
		AirlinePreference.selectByVisibleText("No Preference");

		//Click Continue
		driver.findElement(By.name("findFlights")).click();

		boolean bn_result = driver.findElement(By.xpath(prop.getProperty("selectFlight_xpath"))).isDisplayed();
		Assert.assertTrue("Flight Booking Failed", bn_result);

		throw new io.cucumber.java.PendingException();
	}

	@And("Enter Payment Details")
	public void enter_payment_details() {
		//Click Secure Purchase   
		driver.findElement(By.name("reserveFlights")).click();
		//Enter Passenger Details
		driver.findElement(By.name("passFirst0")).sendKeys(prop.getProperty("firstName1"));
		driver.findElement(By.name("passLast0")).sendKeys(prop.getProperty("lastName"));
		//Enter card Details
		driver.findElement(By.name("creditnumber")).sendKeys(prop.getProperty("creditNumber"));
		driver.findElement(By.name("buyFlights")).click();

		throw new io.cucumber.java.PendingException();
	}
	@Then("Check The FLight Confirmation Details")
	public void check_the_f_light_confirmation_details() {

		//Capture Flight Confirmation       
		WebElement FlightConfirmation = driver.findElement(By.xpath(prop.getProperty("flightConfirmation_xpath")));
		String FlightId = FlightConfirmation.getText();
		Assert.assertTrue("Failed to Book Flight", (FlightId.contains("Your itinerary has been booked!")));
		
		//Departing 
		String DepartDetails = driver.findElement(By.xpath(prop.getProperty("departDetails_xpath")).getText();
		log.info(DepartDetails);

		//returning
		String ArrivingDetails = driver.findElement(By.xpath(prop.getProperty("arrivingDetails_xpath"))).getText();
		log.info(ArrivingDetails);
		
		//Passenger Details
		String PassengerDetails = driver.findElement(By.xpath(prop.getProperty("passengerDetails_xpath"))).getText();  
		log.info(PassengerDetails);


		throw new io.cucumber.java.PendingException();
	}
	@And("Logout of MercuryTours")
	public void logout_of_mercury_tours() {
		
		driver.findElement(By.linkText("SIGN-OFF")).click();
        driver.close(); 
        driver.quit();
		throw new io.cucumber.java.PendingException();
	}




}//closing of Login Class



