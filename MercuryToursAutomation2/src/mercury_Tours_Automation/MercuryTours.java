package mercury_Tours_Automation;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import ExcelUtility.ReadExcelFile;
import org.openqa.selenium.io.FileHandler;

public class MercuryTours {
	
	
	static WebDriver driver;
	static ReadExcelFile reader = new ReadExcelFile("src\\testdata\\TestData.xlsx");
	
	public static void main(String[] args) throws Exception{
	
    MercuryTours objClassOject = new MercuryTours();
    //Reading username and password from Testdata file
    //Get the rowcount to iterate through all data present in Testdata sheet
    int RowCount = reader.getRowCount(0);
    int iterator;
    for(iterator=0; iterator<=RowCount; iterator++){
    
	String username = reader.getData(0, iterator, 1);
	String password = reader.getData(0, iterator, 2);
	//calling Launch and Login function
    objClassOject.func_LaunchBrowser();
    try {
		objClassOject.func_Login(username,password);
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
    
    objClassOject.func_FlightBook(iterator);
    objClassOject.func_FlightConfirmationPage(iterator);
    objClassOject.func_Logout();
   
    }
	
	}//closing main method
//========================================================================================	
	public void func_LaunchBrowser(){
		
		//Initiate the browser
		 //WebDriver driver;
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\585969\\Desktop\\Selenium\\chromedriver\\chromedriver.exe");
		 driver= new ChromeDriver();
		 
		//1. Open http://newtours.demoaut.com/ in chrome browser
		driver.navigate().to("http://newtours.demoaut.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
		
		//Verify mercury tours on home page 
		WebElement element = driver.findElement(By.xpath(".//font[contains(text(),'Mercury Tours')]"));
		if(element.isDisplayed()){ 
		   System.out.println("Success!!Mercury Home Page is Opened");
		   }
		else{    
		    System.out.println("This is not the right page,Please Check the URL again");   
		}
		//verify today's date should be in <Mon Day, Year> format e.g. Nov 21, 2019
		
		// Create object of SimpleDateFormat class and decide the format
		 DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		 
		 //get current date time with Date()
		 Date date = new Date();
		 
		 // Now format the date  
		 String date1= dateFormat.format(date);
		 
		 WebElement DateElement = driver.findElement(By.xpath(".//font[contains(text(),"+ date1 +"]"));
		 if(DateElement.isDisplayed()){ 
			   System.out.println("Date is in correct format");
			   }
			else{    
			    System.out.println("Date is not in correct format");   
			}
	
	}
//========================================================================================		
	public void func_Login(String Username,String Password) throws InterruptedException{
		
		
		 driver.findElement(By.name("userName")).sendKeys(Username);
	     driver.findElement(By.name("password")).sendKeys(Password);
	     driver.findElement(By.name("login")).click();
		//Sync
		Thread.sleep(5000);
		
		
		//Check for any Object
				boolean b_result = func_verifyElements(driver.findElement(By.xpath("//img[@src = '/images/masts/mast_flightfinder.gif']")));
				if(b_result==true){
					System.out.println("Login is Successfull.");
				}else{
					System.out.println("Login Failed.");
				}
				
		
		
	}//closing of login
//========================================================================================		
	//Function to check the existence of the Web Element
		public boolean func_verifyElements(WebElement o_obj){
			if(o_obj!= null){
				//System.out.println("Element is Present");
				return true;
			}else{
				//System.out.println("Element is Absent");
				return false;
			}		
		}
//=======================================================================================
		
public void func_FlightBook(int iterator) throws InterruptedException{
	
	//----------------------------------- code for inputting data -----------------------------------------------
	//Select Passengers
	 Select Passangers= new Select(driver.findElement(By.cssSelector("select[name='passCount']")));
     Passangers.selectByVisibleText(reader.getData(0, iterator, 4));
     
     //Flight Type
     String typeOfFlight = reader.getData(0, iterator, 3);
      driver.findElement(By.xpath("//input[@value='**" + typeOfFlight + "**']")).click(); 
      
     //Departing from
     Select Departingfrom = new Select(driver.findElement(By.cssSelector("select[name='fromPort']")));
     Departingfrom.selectByVisibleText(reader.getData(0, iterator, 5));
     
     //On
     Select FromMonth = new Select(driver.findElement(By.cssSelector("select[name='fromMonth']")));
     FromMonth.selectByVisibleText(reader.getData(0, iterator, 8));
     //Day
     Select fromDay = new Select(driver.findElement(By.cssSelector("select[name='fromDay']")));
     fromDay.selectByVisibleText(reader.getData(0, iterator, 9));
     
     //Arring In
     Select ArrivingIn = new Select(driver.findElement(By.cssSelector("select[name='toPort']")));
     ArrivingIn.selectByVisibleText(reader.getData(0, iterator, 6));
     
     //To month
     Select ToMonth = new Select(driver.findElement(By.cssSelector("select[name='toMonth']")));
     ToMonth.selectByVisibleText(reader.getData(0, iterator, 11));
     
     Select ToDay = new Select(driver.findElement(By.cssSelector("select[name='toDay']")));
     ToDay.selectByVisibleText(reader.getData(0, iterator, 10));
     
     //Service Class
     String serviceClass = reader.getData(0, iterator, 7);
     driver.findElement(By.xpath("//input[@value='**" + serviceClass + "**']")).click();
     
     //Airline Preference
     Select AirlinePreference = new Select(driver.findElement(By.cssSelector("select[name='airline']")));
     AirlinePreference.selectByVisibleText("No Preference");
     
     //Click Continue
     driver.findElement(By.name("findFlights")).click();
    //----------------------------------------------------------------------------------------------------------- 
     
     //Verify Select Flight page opened
     boolean b_result = func_verifyElements(driver.findElement(By.xpath("//img[@src = '/images/masts/mast_selectflight.gif']")));
		if(b_result==true){
			System.out.println("Flight Booking Passed");
		}else{
			System.out.println("Flight Booking Failed.");
		}
     

//----------------------------Book the costliest Flight---------------------------------------------------
		    List<String> all_elements_text=new ArrayList<>();
		    //If you want to get all elements text into array list

		    for(int i=4; i<=10; i+=2){

		        all_elements_text.add(driver.findElement(By.xpath("//table[2]//tbody[1]//tr[" + i + "]//td[1]//font[1]//font[1]//b[1]")).getText());
		        //loading text of each element in to array all_elements_text
		        //System.out.println(myList.get(i).getText());
		    }
		    Object obj = Collections.max(all_elements_text);
		    System.out.println(obj);
		    String str = (String) obj;  
			String Newstr = str.substring(7, 11);
			   
		    driver.findElement(By.xpath("'//input[@type='radio'][contains(@value," + Newstr + ")]'")).click();
		    
		    
		    
	//--------------------------------------------------------------------------------------------------------------------	    
		 //Click Secure Purchase   
		 driver.findElement(By.name("reserveFlights")).click();
		 
		 b_result = func_verifyElements(driver.findElement(By.xpath("//img[@src = '/images/masts/mast_book.gif']")));
	  		if(b_result==true){
	  			System.out.println("Flight page opened");
	  		}else{
	  			System.out.println("Flight page Failed to opened .");
	  		}
	  			
             // Enter Passenger Details 
	  		String numberofPassengers =reader.getData(0, iterator, 4);
	  	     if(numberofPassengers == "1"){
	  	         driver.findElement(By.name("passFirst0")).sendKeys("Jane");
		         driver.findElement(By.name("passLast0")).sendKeys("Doe");
	  		 }
	  	     else if(numberofPassengers == "2"){
	  	    	 driver.findElement(By.name("passFirst1")).sendKeys("Mane");
	  	    	 driver.findElement(By.name("passLast1")).sendKeys("Doe");}
	  	     else if(numberofPassengers == "3"){
	  	    	 driver.findElement(By.name("passFirst3")).sendKeys("Sane");
	  	    	 driver.findElement(By.name("passLast3")).sendKeys("Doe");}
	  	     
	  	     	 driver.findElement(By.name("creditnumber")).sendKeys("1123");
	  	     	 driver.findElement(By.name("buyFlights")).click();
			
	         Thread.sleep(5000);
	      
	     	
}//closing fligtbook


public void func_FlightConfirmationPage(int iterator){

    
	//Capture Flight Confirmation       
	WebElement FlightConfirmation = driver.findElement(By.xpath("//font[contains(text(),'Flight')]"));
	String FlightId = FlightConfirmation.getText();
	if (FlightId.contains("Your itinerary has been booked!")){
		reader.setCellData(1, 12, iterator, FlightId);
		reader.setCellData(1, 13, iterator, "Pass");
	}
	else{
		reader.setCellData(1, 13, iterator, "Fail");
	}
	//Departing 
	String DepartDetails = driver.findElement(By.xpath("//tbody//tr[3]/td[1]/font[1]/b[1]")).getText();
    
    		
    if(DepartDetails.contains(reader.getData(0, iterator, 5))){
		System.out.println("Depart City is correct");
	}else{
		System.out.println("Depart City is incorrect");
	}
	//Dates
	//returning
	String ArrivingDetails = driver.findElement(By.xpath("//tbody//tr[3]/td[1]/font[1]/b[1]")).getText();
	if(ArrivingDetails.contains(reader.getData(0, iterator, 6))){
		System.out.println("Arrving City is correct");
	}else{
		System.out.println("Arriving City is incorrect");
	}
	//Passenger Details
	String PassengerDetails = driver.findElement(By.xpath("//tr//tr//tr//tr//tr[7]//td[1]")).getText();  
	if(PassengerDetails.contains(reader.getData(0, iterator, 4))){
		System.out.println("Passenger Details correct");
	}else{
		System.out.println("Passenger Details incorrect");
	}
	
	//Capture the screenshot for this page
	TakesScreenshot ts=(TakesScreenshot)driver; 
    try {
        FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), new File("C:\\Users\\585969\\workspace\\Results\\FlightConfirmation.png"));
    } catch (WebDriverException e) {
 
    } catch (IOException e) {
 
    }
 
		
				 
	}//closing of FlightconfirmationPage

	public void func_Logout()
	{
        driver.findElement(By.linkText("SIGN-OFF")).click();
        driver.close(); 
        driver.quit();
	}//closing of Logout
	
	}//closing of class

