package pageObjects;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import org.openqa.selenium.*;

import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GiftCardPage {
	
   WebDriver driver;
   
   public GiftCardPage(WebDriver driver) {
       this.driver = driver;
       PageFactory.initElements(driver, this);
   }
   
   @FindBy(linkText = "Giftcards")
   WebElement giftCardLink;
   
   @FindBy(xpath = "//div[@class='all__card__wrap']/ul/li[1]")
   WebElement firstGiftCard;
   
   @FindBy(name = "senderEmailId")
   public WebElement senderEmailInput;
   
   @FindBy(xpath = "//button[text()='BUY NOW']")
   WebElement buyNowButton;
   
   @FindBy(xpath = "//input[@name='senderEmailId']/following::p")
   WebElement emailErrorMessage;

   // Navigates to the Giftcards section
   public void clickGiftCardLink() {
       giftCardLink.click();
   }
   
   // Selects the first gift card from the list
   public void selectFirstGiftCard() {
       firstGiftCard.click();
   }

   // Inputs an invalid email address.
   public void enterInvalidEmail(String email) {
       senderEmailInput.sendKeys(email);
   }
   
   // Clicks the "BUY NOW" button.
   public void clickBuyNow() {
       buyNowButton.click();
   }
   
   // Returns the error message for invalid email input
   public String getEmailErrorMessage() {
       return emailErrorMessage.getText();
   }

   // Takes a screenshot with the email error message in view.
   public void takeScreenshotWithEmailErrorVisible() {
       try {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
           WebElement senderEmailId = driver.findElement(By.name("senderEmailId"));
           WebElement targetElement = wait.until(ExpectedConditions.visibilityOf(senderEmailId));
           ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetElement);
           wait.until(ExpectedConditions.elementToBeClickable(targetElement));
           File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
           String randomFileName = "fullpage_" + UUID.randomUUID() + ".png";
           File destination = new File(System.getProperty("user.dir") + "\\screenshotfolder\\" + randomFileName);
           FileHandler.copy(screenshot, destination);
           System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
       } 
       catch (IOException e) {
           System.out.println("Error saving screenshot: " + e.getMessage());
       } catch (Exception e) {
           System.out.println("Error capturing screenshot: " + e.getMessage());
       }
   }
}
