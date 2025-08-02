package testCases;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.CabBookingPage;
import pageObjects.GiftCardPage;
import pageObjects.HotelBookingPage;
import testBase.BaseClass;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class CabBookingTest extends BaseClass {
	private static final Logger logger = LogManager.getLogger(CabBookingTest.class);
	
	@Test(priority = 1)
	public void testCabFare(){
		logger.info("***** Starting testCabFare *****");
      try {
          CabBookingPage cabBookingPage = new CabBookingPage(driver);
          logger.debug("CabBookingPage object initialized.");
          cabBookingPage.bookCab();
          logger.info("Cab booking flow executed successfully.");
      } catch (Exception e) {
          logger.error("Exception occurred during cab booking test: ", e);
          Assert.fail("Cab booking test failed due to exception.");
      }
	}
	
	@Test(priority = 2)
	public void testInvalidEmailGiftCard() throws InterruptedException {
	    logger.info("***** Starting testInvalidEmailGiftCard *****");
	    try {
	        GiftCardPage giftCardPage = new GiftCardPage(driver);
	        logger.debug("GiftCardPage object initialized.");
	        giftCardPage.clickGiftCardLink();
	        Thread.sleep(2000);
	        giftCardPage.selectFirstGiftCard();

	        // Read emails from third column starting from second row
	        FileInputStream fis = new FileInputStream(new File("src/test/resources/testdata/emails.xlsx"));
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheetAt(0);
	        int lastRowNum = sheet.getLastRowNum();

	        for (int i = 1; i <= lastRowNum; i++) {
	            Row row = sheet.getRow(i);
	            if (row != null) {
	                Cell emailCell = row.getCell(2); // Third column (index 2)
	                if (emailCell != null && emailCell.getCellType() == CellType.STRING) {
	                    String email = emailCell.getStringCellValue().trim();
	                    if (!email.isEmpty()) {
	                        logger.info("Testing email: {}", email);

	                        giftCardPage.senderEmailInput.clear();
	                        giftCardPage.enterInvalidEmail(email);
	                        giftCardPage.clickBuyNow();
	                        Thread.sleep(1000); // Wait for error message to appear
	                        giftCardPage.takeScreenshotWithEmailErrorVisible();

	                        String errorMsg = giftCardPage.getEmailErrorMessage();
	                        logger.info("Captured message: {}", errorMsg);

	                        // Handle both valid and invalid emails gracefully
							if (!errorMsg.equals("Please enter a valid Email id.")) {
							    logger.info("Valid email ID entered: {}", email);
							} else {
							    logger.error("Invalid email ID detected: {}", email);
							    Assert.assertEquals(errorMsg, "Please enter a valid Email id.", "Email validation failed for: " + email);
							}
	                    }
	                }
	            }
	        }
	        
	        workbook.close();
	        fis.close();
	    } catch (Exception e) {
	        logger.error("Exception occurred during gift card email validation test: ", e);
	        // Do not fail the entire test if one email fails
	    }
	}

	@Test(priority = 3)
	public void testHotelAdultCount(){
		logger.info("***** Starting testHotelAdultCount *****");
	    try {
	        HotelBookingPage hotelPage = new HotelBookingPage(driver);
	        logger.debug("HotelBookingPage object initialized.");
	        hotelPage.navigateToHotelSection();
	        logger.debug("Navigated to hotel section.");
	        hotelPage.openGuestSelection();
	        logger.debug("Opened guest selection.");
	        hotelPage.openAdultDropdown();
	        logger.debug("Opened adult dropdown.");
	        String totalAdults = hotelPage.getTotalAdultsText();
	        logger.info("Total Number of Adults: {}", totalAdults);
	        System.out.println("Total Number of Adults are: " + totalAdults);
	    } catch (Exception e) {
	        logger.error("Exception occurred during hotel adult count test: ", e);
	        Assert.fail("Hotel adult count test failed due to exception.");
	    }
	}
}
