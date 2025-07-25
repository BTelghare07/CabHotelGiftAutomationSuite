package tests;
 
import org.testng.annotations.*;
import pages.CabBookingPage;
import pages.GiftCardPage;
import pages.HotelBookingPage;
import testBase.BaseTest;
 
public class CabBookingTest extends BaseTest {

 
	@Test(priority = 1)
	public void testCabFare() throws InterruptedException {
		logger.info("***** Starting TC001_AccountRegistrationTest  ****");
		logger.debug("This is a debug log message");
		try {
	           CabBookingPage cabBookingPage = new CabBookingPage(driver);
	           cabBookingPage.bookCab();
	           System.out.println("Cab booking flow executed successfully.");
	       } catch (Exception e) {
	           System.out.println("Test failed due to exception: " + e.getMessage());
	       }

	}


	  @Test(priority = 2)
	    public void testInvalidEmailGiftCard() throws InterruptedException {
	        GiftCardPage giftCardPage = new GiftCardPage(driver);
	        giftCardPage.clickGiftCardLink();
	        Thread.sleep(2000);
	        giftCardPage.selectFirstGiftCard();
	        giftCardPage.enterInvalidEmail("pawarsiddhesh.com");
	        giftCardPage.clickBuyNow();
	        String errorMsg = giftCardPage.getEmailErrorMessage();
	        System.out.println("Email Invalid Message: " + errorMsg);
	    }

//	

	@Test(priority = 3)
    public void testHotelAdultCount() throws InterruptedException {
        HotelBookingPage hotelPage = new HotelBookingPage(driver);
        hotelPage.navigateToHotelSection();
        hotelPage.openGuestSelection();
        hotelPage.openAdultDropdown();
        String totalAdults = hotelPage.getTotalAdultsText();
        System.out.println("Total Number of Adults are: " + totalAdults);


    }

}

 