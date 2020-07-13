package stepDefinitions;

import java.util.concurrent.TimeUnit;

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
	
    

	@Given("User has Logged into MercuryTours with \"(.*)\" and \"(.*)\"")
	public void user_has_logged_into_mercury_tours_with_username_and_password(String username, String password) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
		driver= new ChromeDriver();

		//1. Open http://newtours.demoaut.com/ in chrome browser
		driver.navigate().to("http://newtours.demoaut.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


		//Verify mercury tours on home page 
		WebElement element = driver.findElement(By.xpath(".//font[contains(text(),'Mercury Tours')]"));
		if(element.isDisplayed()){ 
			System.out.println("Success!!Mercury Home Page is Opened");
		}
		else{    
			System.out.println("This is not the right page,Please Check the URL again");   
		}
		driver.findElement(By.name("userName")).sendKeys(username);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("login")).click();
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		//Verify Flight Finder page opened
		WebElement FlighFindelement = driver.findElement(By.xpath("//img[@src = '/images/masts/mast_flightfinder.gif']"));
		//Assert.assertTrue("Login Failed", b_result);
		if(FlighFindelement.isDisplayed()){ 
			System.out.println("Success!!Flight Finder Page is Opened");
		}
		else{    
			System.out.println("This is not the right page!!");   
		}
		

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

		boolean bn_result = driver.findElement(By.xpath("//img[@src = '/images/masts/mast_selectflight.gif']")).isDisplayed();
		Assert.assertTrue("Flight Booking Failed", bn_result);

		throw new io.cucumber.java.PendingException();
	}

	@And("Enter Payment Details")
	public void enter_payment_details() {
		//Click Secure Purchase   
		driver.findElement(By.name("reserveFlights")).click();
		//Enter Passenger Details
		driver.findElement(By.name("passFirst0")).sendKeys("Jane");
		driver.findElement(By.name("passLast0")).sendKeys("Doe");
		//Enter card Details
		driver.findElement(By.name("creditnumber")).sendKeys("1123");
		driver.findElement(By.name("buyFlights")).click();

		throw new io.cucumber.java.PendingException();
	}
	@Then("Check The FLight Confirmation Details")
	public void check_the_f_light_confirmation_details() {

		//Capture Flight Confirmation       
		WebElement FlightConfirmation = driver.findElement(By.xpath("//font[contains(text(),'Flight')]"));
		String FlightId = FlightConfirmation.getText();
		Assert.assertTrue("Failed to Book Flight", (FlightId.contains("Your itinerary has been booked!")));
		
		//Departing 
		String DepartDetails = driver.findElement(By.xpath("//tbody//tr[3]/td[1]/font[1]/b[1]")).getText();
		System.out.println(DepartDetails);

		//returning
		String ArrivingDetails = driver.findElement(By.xpath("//tbody//tr[3]/td[1]/font[1]/b[1]")).getText();
		System.out.println(ArrivingDetails);
		
		//Passenger Details
		String PassengerDetails = driver.findElement(By.xpath("//tr//tr//tr//tr//tr[7]//td[1]")).getText();  
		System.out.println(PassengerDetails);


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



