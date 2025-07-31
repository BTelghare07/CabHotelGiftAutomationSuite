package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;

import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GiftCardPage {
	
   WebDriver driver;
   
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
   
   public GiftCardPage(WebDriver driver) {
       this.driver = driver;
       PageFactory.initElements(driver, this);
   }
   public void clickGiftCardLink() {
       giftCardLink.click();
   }
   public void selectFirstGiftCard() {
       firstGiftCard.click();
   }
   public void enterInvalidEmail(String email) {
       senderEmailInput.sendKeys(email);
   }
   public void clickBuyNow() {
       buyNowButton.click();
   }
   public String getEmailErrorMessage() {
       return emailErrorMessage.getText();
   }
   
   public void takeScreenshotWithEmailErrorVisible() {
       try {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
           WebElement senderEmailId = driver.findElement(By.name("senderEmailId"));
           WebElement targetElement = wait.until(ExpectedConditions.visibilityOf(senderEmailId));
           ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetElement);
           Thread.sleep(1000);
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
   
   // method to read emails from Excel
   public static String[] readEmailsFromExcel(String filePath) {
       try (FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis)) {
           Sheet sheet = workbook.getSheetAt(0);
           int rowCount = sheet.getPhysicalNumberOfRows();
           String[] emails = new String[rowCount];
           for (int i = 0; i < rowCount; i++) {
               Row row = sheet.getRow(i);
               if (row != null && row.getCell(0) != null) {
                   emails[i] = row.getCell(0).getStringCellValue();
               }
           }
           return emails;
       } catch (Exception e) {
           e.printStackTrace();
           return new String[0];
       }
   }
}
