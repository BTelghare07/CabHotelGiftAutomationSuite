package pageObjects;

import java.time.Duration; 
import java.util.List;
import java.util.Arrays;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public class CabBookingPage {
    WebDriver driver;
    Actions actions;

    public CabBookingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        actions = new Actions(driver);
    }
    
    @FindBy(xpath = "//span[@class='commonModal__close']")
    WebElement closeModal;

    @FindBy(linkText = "Cabs")
    WebElement cabsLink;

    @FindBy(id = "fromCity")
    WebElement fromCity;

    @FindBy(xpath = "//input[@placeholder='From']")
    WebElement fromInput;

    @FindBy(xpath = "//div[@role='listbox']//span[text()='Delhi']")
    WebElement fromDelhi;

    @FindBy(xpath = "//input[@id='toCity']")
    WebElement toCity;

    @FindBy(xpath = "//input[@placeholder='To']")
    WebElement toInput;

    @FindBy(xpath = "//span[contains(text(), 'Manali, Himachal Pradesh, India')]")
    WebElement toManali;

    @FindBy(xpath = "//span[normalize-space()='Departure']")
    WebElement departureDate;

    @FindBy(xpath = "//div[@aria-label='Fri Aug 15 2025']")
    WebElement selectDate;

    @FindBy(xpath = "//span[normalize-space()='Pickup-Time']")
    WebElement pickupTime;

    @FindBy(xpath = "//li[@data-cy='HrSlots_81']")
    List<WebElement> hourSlots;

    @FindBy(xpath = "//li[@data-cy='MinSlots_83']")
    List<WebElement> minSlots;

    @FindBy(xpath = "//span[@class='applyBtnText']")
    WebElement applyBtn;

    @FindBy(xpath = "//a[normalize-space()='Search']")
    WebElement searchBtn;

    @FindBy(xpath = "//img[@alt='Close']")
    List<WebElement> popupClose;

    @FindBy(xpath = "//div[@class='filterSection_contentWrapper___IFFB']/div[3]/div[@class='checkbox_checkbox__FA7_p ']")
    WebElement suvFilter;

    @FindBy(xpath = "//span[@class='cabDetailsCard_price__SHN6W']")
    List<WebElement> priceElements;

    @FindBy(xpath = "//span[@class='itemWrapper headerIcons makeFlex hrtlCenter column moreWrapper']")
    WebElement hoverElement;
    
    public void bookCab(){
    	//Close Modal Popup
        actions.moveToElement(closeModal).click().perform();
        
        //Click on Cabs Link
        cabsLink.click();

        //Select Pickup City
        fromCity.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(fromInput));
        fromDelhi.click();

        //Select Drop Location
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement puneOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@role='listbox']//span[text()='Pune']")));
        puneOption.click();
        toCity.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(toInput)).sendKeys("Manali");
        toManali.click();

        //Choose Departure Date
        departureDate.click();
        selectDate.click();

        //Set Pickup Time
        wait.until(ExpectedConditions.elementToBeClickable(pickupTime)).click();

        for (WebElement hour : hourSlots) {
            if (hour.getText().equals("06 Hr")) {
                hour.click();
                break;
            }
        }

        for (WebElement min : minSlots) {
            if (min.getText().equals("30 min")) {
                min.click();
                break;
            }
        }

        //Apply Time Selection
        applyBtn.click();
        
        //Search for Cabs
        searchBtn.click();

        //Handle Optional Popup
        try {
            if (!popupClose.isEmpty()) {
                popupClose.get(0).click();
            }
        } catch (TimeoutException e) {
        	System.out.println("Popup not found, continuing with the test.");
        }

        //Apply SUV Filter
        suvFilter.click();

        //Extract and Sort Prices
        double[] prices = new double[priceElements.size()];
        for (int i = 0; i < priceElements.size(); i++) {
            String priceText = priceElements.get(i).getText().replace("₹", "").replace(",", "").trim();
            try {
                prices[i] = Double.parseDouble(priceText);
            } catch (NumberFormatException e) {
                prices[i] = Double.MAX_VALUE;
            }
        }

        Arrays.sort(prices);
        System.out.println("Lowest SUV price: ₹" + prices[0]);

        //Hover Over Element
        actions.moveToElement(hoverElement).perform();
    }
    
    
}
