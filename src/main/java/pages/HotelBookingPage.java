package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.interactions.Actions;

public class HotelBookingPage {

    WebDriver driver;

    @FindBy(linkText = "Hotels")
    WebElement hotelLink;

    @FindBy(xpath = "//label[@for='guest']")
    WebElement guestLabel;

    @FindBy(xpath = "//p[text()='Adults']/following::div[@class='gstSlctCont']")
    WebElement adultDropdown;

    @FindBy(xpath = "//p[text()='Adults']/following::div[@class='gstSlctCont']//ul/li[40]")
    WebElement totalAdultsElement;

    public HotelBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToHotelSection() throws InterruptedException {
        Thread.sleep(1000); // Replace with explicit wait in real framework
        Actions actions = new Actions(driver);
        actions.moveToElement(hotelLink).click().perform();
    }

    public void openGuestSelection() {
        guestLabel.click();
    }

    public void openAdultDropdown() {
        adultDropdown.click();
    }

    public String getTotalAdultsText() {
        return totalAdultsElement.getText();
    }
}
