package stepdefs;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import io.cucumber.java.After;

import static org.junit.jupiter.api.Assertions.*;


public class RegisterSteps {
    WebDriver driver;

    @Given("I open the registration page")
    public void i_open_the_registration_page() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("I enter valid registration details")
    public void i_enter_valid_registration_details() {

        driver.findElement(By.id("dp")).sendKeys("02/02/1984");
        driver.findElement(By.id("member_firstname")).sendKeys("Saba");
        driver.findElement(By.id("member_lastname")).sendKeys("Mustafa");

        //add time stamp for each execution to avoid duplicate email
        String randomEmail = "sabamustafaali" + System.currentTimeMillis() + "@testmail.com";
        driver.findElement(By.id("member_emailaddress")).sendKeys(randomEmail);
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys(randomEmail);
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Saba1234");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Saba1234");

    }

    @When("I enter registration details with missing last name")
    public void i_enter_registration_details_with_missing_last_name() {
        driver.findElement(By.id("dp")).sendKeys("02/02/1984");
        driver.findElement(By.id("member_firstname")).sendKeys("Saba");
        driver.findElement(By.id("member_lastname")).sendKeys("");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("sabaalimustafa@yahoo.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Saba1234");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Saba1234");
    }

    @When("I enter registration details with mismatched passwords")
    public void i_enter_registration_details_with_mismatched_passwords() {
        driver.findElement(By.id("dp")).sendKeys("02/02/1984");
        driver.findElement(By.id("member_firstname")).sendKeys("Saba");
        driver.findElement(By.id("member_lastname")).sendKeys("Mustafa");
        driver.findElement(By.id("member_emailaddress")).sendKeys("sabaalimustafa@yahoo.com");
        driver.findElement(By.id("member_confirmemailaddress")).sendKeys("sabaalimustafa@yahoo.com");
        driver.findElement(By.id("signupunlicenced_password")).sendKeys("Saba1234");
        driver.findElement(By.id("signupunlicenced_confirmpassword")).sendKeys("Wrong1234");

    }

    @When("I accept terms and conditions")
    public void i_accept_terms_and_conditions() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click the label for "I have read and accepted terms"
        WebElement termsLabel = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='sign_up_25']")));
        termsLabel.click();

        // Click the label for "I am over 18 or have parental responsibility"
        WebElement ageLabel = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='sign_up_26']")));
        ageLabel.click();

        // Click the label for "I agree to Code of Ethics and Conduct"
        WebElement ethicsLabel = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")));
        ethicsLabel.click();
    }


    @When("I do not accept terms and conditions")
    public void i_do_not_accept_terms_and_conditions() {
        WebElement checkbox = driver.findElement(By.id("sign_up_25"));
        if (checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
    }

    @When("I submit the form")
    public void i_submit_the_form() {
        driver.findElement(By.xpath("//input[@type='submit' and @value='CONFIRM AND JOIN']")).click();
    }



    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h2.bold.gray.text-center.margin-bottom-40")));

        String actualText = heading.getText();
        assertTrue(actualText.contains("THANK YOU FOR CREATING AN ACCOUNT"),
                "Confirmation message was not displayed as expected.");
    }





    @Then("I should see an error for last name")
    public void i_should_see_an_error_for_last_name() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        boolean errorDisplayed = driver.getPageSource().contains("Last Name is required");

        assertTrue(errorDisplayed, "Expected 'Last Name is required' error was not displayed.");
    }


    @Then("I should see an error for password mismatch")
    public void i_should_see_an_error_for_password_mismatch() {
        WebElement confirmPasswordInput = driver.findElement(By.id("signupunlicenced_confirmpassword"));
        String validationMessage = confirmPasswordInput.getAttribute("data-val-equalto");
    //assertEqual
        assertTrue(validationMessage.contains("Password did not match"),
                "Expected password mismatch error message.");
    }


    @Then("I should see an error about accepting terms")
    public void i_should_see_an_error_about_accepting_terms() {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean errorDisplayed = driver.getPageSource().contains("You must confirm that you have read and accepted" +
                " our Terms and Conditions");

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
