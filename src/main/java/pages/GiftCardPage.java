package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GiftCardPage {

    WebDriver driver;

    @FindBy(linkText = "Giftcards")
    WebElement giftCardLink;

    @FindBy(xpath = "//div[@class='all__card__wrap']/ul/li[1]")
    WebElement firstGiftCard;

    @FindBy(name = "senderEmailId")
    WebElement senderEmailInput;

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
}
