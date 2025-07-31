package testBase;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
 
public class BaseClass {
	public WebDriver driver;
	public Logger logger;
 
	@BeforeClass
	public void setup() {
		logger=LogManager.getLogger(this.getClass());//Log4j
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
	}
	
	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
			System.out.println("Browser closed and WebDriver quit successfully.");
		}	
	}
}
 
