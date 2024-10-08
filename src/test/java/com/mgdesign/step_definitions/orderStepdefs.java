package com.mgdesign.step_definitions;


import com.mgdesign.pages.OrderPage;
import com.mgdesign.utilities.BrowserUtils;
import com.mgdesign.utilities.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class orderStepdefs {
    OrderPage orderPage = new OrderPage();

    @When("the user click onboard")
    public void the_user_click_onboard() {

       // BrowserUtils.clickWithWait(By.className("dropdown-toggler-content-XHHDfD3"), 5);
        //orderPage.onboardClick();

        // WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
        //WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated( By.className("dropdown-toggler-content-XHHDfD3")));
        //button.click();
    }


    @When("the user click orders")
    public void theUserClickOrders() {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
        BrowserUtils.clickWithWait(By.linkText("Orders"), 3);


    }

    @Then("the user should be view Awaiting shipment")
    public void theUserShouldBeViewAwaitingShipment() {
        WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
        Driver.get().findElements(By.className("view-name-biN1OPA"));

    }

    @When("I enter the following data:")
    public void i_enter_the_following_data(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            String id = row.get("id");
            String weight = row.get("weight");

            BrowserUtils.waitFor(3);
            // Button elementini bulmak için XPath
            BrowserUtils.clickWithWait(By.xpath("//button[@class='button-link' and @type='button']//div[text()='" + id + "']"), 3);
            // WebElement button = Driver.get().findElement(By.xpath("//button[@class='button-link' and @type='button']//div[text()='"+id+"']"));
            //button.click();  // Butona tıklama işlemi


            // Input elementini XPath ile buluyoruz
            WebElement inputFieldLbs = Driver.get().findElement(By.xpath("//input[@aria-labelledby='order-details-weight pounds' and @type='number']"));

            // Mevcut değeri temizliyoruz
            inputFieldLbs.clear();

            // Yeni değeri giriyoruz (örneğin 0)
            inputFieldLbs.sendKeys("0");

            // Input elementini XPath ile buluyoruz
            WebElement inputFieldOz = Driver.get().findElement(By.xpath("//input[@aria-labelledby='order-details-weight ounces' and @type='number']"));


            // Mevcut değeri temizliyoruz

            // Tüm metni seç ve sil
            inputFieldOz.sendKeys(Keys.CONTROL, "a");
            inputFieldOz.sendKeys(Keys.DELETE);

            // Yeni değeri gir (weight bir String veya Number olmalı)
            inputFieldOz.sendKeys(String.valueOf(weight));

            // Tab tuşuna bas
            inputFieldOz.sendKeys(Keys.TAB);


            System.out.println("Entering data: id = " + id + ", weight = " + weight);
        }
    }

    @Then("the data should be submitted successfully")
    public void the_data_should_be_submitted_successfully() {
        // Code to verify successful submission, e.g. checking confirmation message
        System.out.println("notes write successfully");
    }


    @When("the user click batch")
    public void theUserClickBatch() {
        WebElement element = Driver.get().findElement(By.xpath("//p[@class='paragraph-XUqSpQH' and text()='Batch']"));
        element.click();

    }


    @When("the user click {string} batch")
    public void theUserClickBatch(String arg0) {
        WebElement element = Driver.get().findElement(By.xpath("//div[@class='batch-name-_SzuZr2' and text()='105110']"));
        element.click();

    }

    @When("I enter the following notes:")
    public void i_enter_the_following_notes(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            String id = row.get("id");
            String notes = row.get("notes");


            // Button elementini bulmak için XPath
            BrowserUtils.clickWithWait(By.xpath("//button[@class='button-link' and @type='button']//div[text()='" + id + "']"), 5);

            //Notes click
            // XPath ile div elementini bulup tıklıyoruz
            BrowserUtils.clickWithWait(By.xpath("//div[@class='collapsible-list-item-header-content-dzCplCS']//h2[@class='h4-yAR2Zwb order-details-section-title-nfe6D27' and text()='Notes']"), 1);



            // Textarea elementini XPath ile bulup tıklıyoruz
            WebElement textarea = Driver.get().findElement(By.id("Internal"));
            textarea.click();

            // Yeni değeri gir (notes bir String olmalı)
            // Tüm metni seç ve sil
            textarea.sendKeys(Keys.CONTROL, "a");
            textarea.sendKeys(Keys.DELETE);

            // Yeni değeri gir (weight bir String veya Number olmalı)
            textarea.sendKeys(String.valueOf(notes));


            // Tab tuşuna bas
            textarea.sendKeys(Keys.TAB);





            System.out.println("Entering data: id = " + id + ", notes = " + notes);
        }
    }

    @When("the user click batch notes")
    public void theUserClickBatchNotes() {
        // WebElement'i XPath ile buluyoruz
        WebElement element = Driver.get().findElement(By.xpath("//div[@class='dropdown-toggler-content-XHHDfD3']//div[@class='header-button-vnQAq7Z']"));

        // JavaScript Executor kullanarak sayfanın yukarısına kaydırıyoruz
        JavascriptExecutor js = (JavascriptExecutor) Driver.get();
        js.executeScript("arguments[0].scrollIntoView(true);", element);

        // Tıklama işlemini gerçekleştiriyoruz
        element.click();

    }

    @When("the user click close order details")
    public void theUserClickCloseOrderDetails() {
        // WebElement'i XPath ile buluyoruz
        WebElement button = Driver.get().findElement(By.xpath("//button[@class='button-unstyled icon-button-tpaS1B7' and @aria-label='Close Order Details']"));

// Tıklama işlemini gerçekleştiriyoruz
        button.click();

    }

}
