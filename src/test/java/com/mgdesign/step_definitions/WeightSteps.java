package com.mgdesign.step_definitions;

import com.mgdesign.utilities.BrowserUtils;
import com.mgdesign.utilities.Driver;
import com.mgdesign.utilities.ExcelReader;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class WeightSteps {
    private static final Logger logger = LogManager.getLogger(NotesSteps.class);

    @When("program reads weight excel")
    public void programReadsWeightExcel() {
        ExcelReader excelReader = new ExcelReader();
        List<String[]> notesData;

        // Excel'den veri okuma işlemi (dosya yolunuza göre güncelleyin)
        String excelFilePath = "src/test/resources/testdata/weight.xlsx";
        notesData = excelReader.readExcelData(excelFilePath);
        logger.info("Excel alındı");

        try (FileWriter logFile = new FileWriter("src/test/resources/logs/weight_log.txt", true)) {
            // Excel verilerini test adımlarında kullanabilirsiniz
            for (String[] row : notesData) {
                String id = row[0];
                String weight = row[1];
                logger.info("ID: " + id + ", Weight: " + weight);
                logFile.write("ID: " + id + ", Weight: " + weight + "\n");

                WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(10));
                // Button elementini bulmak için XPath
                BrowserUtils.clickWithWait(By.xpath("//button[@class='button-link' and @type='button']//div[text()='" + id + "']"), 3);

                // Input elementini XPath ile buluyoruz
                WebElement inputFieldLbs = Driver.get().findElement(By.xpath("//input[@aria-labelledby='order-details-weight pounds' and @type='number']"));

                // Mevcut değeri temizliyoruz
                inputFieldLbs.sendKeys(Keys.CONTROL, "a");
                inputFieldLbs.sendKeys(Keys.DELETE);

                // TAB giriyoruz
                inputFieldLbs.sendKeys(Keys.TAB);

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

                logger.info(id.concat(" weight update successfully"));
                logFile.write(id.concat(" weight update successfully\n"));

                try {
                    // Batch'i XPath ile buluyoruz
                    WebElement batch = Driver.get().findElement(By.xpath("//div[@class='dropdown-toggler-content-XHHDfD3']//div[@class='header-button-vnQAq7Z']"));

                    // JavaScript Executor kullanarak sayfanın yukarısına kaydırıyoruz ve tıklıyoruz
                    JavascriptExecutor js = (JavascriptExecutor) Driver.get();
                    js.executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", batch);

                    // 105110 nolu batch Tıklama işlemini gerçekleştiriyoruz
                    WebElement ilkbatch = Driver.get().findElement(By.xpath("//div[@class='batch-name-_SzuZr2' and text()='105110']"));
                    ilkbatch.click();

                    // order details kapatmayı XPath ile buluyoruz
                    WebElement order_details_exit = Driver.get().findElement(By.xpath("//button[@class='button-unstyled icon-button-tpaS1B7' and @aria-label='Close Order Details']"));

                    // Tıklama işlemini gerçekleştiriyoruz
                    order_details_exit.click();
                } catch (Exception e) {
                    logger.error("Error interacting with batch or order details for ID: " + id + " - " + e.getMessage());
                    logFile.write("Error interacting with batch or order details for ID: " + id + " - " + e.getMessage() + "\n");
                }
            }

            // Verinin başarıyla kaydedildiğini doğrulama işlemleri burada yapılabilir
            logger.info("All weight have been updated successfully.");
            logFile.write("All weight have been updated successfully.\n");
        } catch (IOException e) {
            logger.error("Error writing to log file - " + e.getMessage());
        }
    }
}