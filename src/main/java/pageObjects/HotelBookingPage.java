package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelBookingPage {

    WebDriver driver;
    
    public HotelBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Hotels")
    WebElement hotelLink;

    @FindBy(xpath = "//label[@for='guest']")
    WebElement guestLabel;

    @FindBy(xpath = "//p[text()='Adults']/following::div[@class='gstSlctCont']")
    WebElement adultDropdown;

    @FindBy(xpath = "//p[text()='Adults']/following::div[@class='gstSlctCont']//ul/li[40]")
    WebElement totalAdultsElement;

    // Navigates to the hotel section 
	public void navigateToHotelSection() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.elementToBeClickable(hotelLink));
	    Actions actions = new Actions(driver);
	    actions.moveToElement(hotelLink).click().perform();
	}

	// Opens the guest selection panel 
    public void openGuestSelection() {
        guestLabel.click();
    }

    // Opens the dropdown to select number of adults
    public void openAdultDropdown() {
        adultDropdown.click();
    }


    // Returns the text of total number of adults from the dropdown
    public String getTotalAdultsText() {
        return totalAdultsElement.getText();
    }
}
