package com.mgdesign.step_definitions;

import com.mgdesign.utilities.BrowserUtils;
import com.mgdesign.utilities.Driver;
import com.mgdesign.utilities.ExcelReader;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class NotesSteps {

    private static final Logger logger = LogManager.getLogger(NotesSteps.class);

    @When("program reads output excel")
    public void programReadsOutputExcel() {
        int count=0;
        try (FileWriter logFile = new FileWriter("src/test/resources/logs/notes_log.txt", true)) {

        ExcelReader excelReader = new ExcelReader();
        List<String[]> notesData;

        // Excel'den veri okuma işlemi (dosya yolunuza göre güncelleyin)
        String excelFilePath = "src/test/resources/testdata/output.xlsx";
        notesData = excelReader.readExcelData(excelFilePath);
        logFile.write("Excel alındı\n");


            // Excel verilerini test adımlarında kullanabilirsiniz
            for (String[] row : notesData) {
                String id = row[0];
                String notes = row[1];
                logFile.write("ID: " + id + ", Notes: " + notes + "\n");


                // Button elementini bulmak için XPath
                BrowserUtils.clickWithWait(By.xpath("//button[@class='button-link' and @type='button']//div[text()='" + id + "']"), 3);

                try {
                    // notes'i buluyoruz(1)
                   //  ((JavascriptExecutor) Driver.get()).executeScript("window.scrollTo(0, document.body.scrollHeight);");

                    // notes (2)
                    WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(5));
                    WebElement notesHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='collapsible-list-item-header-content-dzCplCS']//h2[@class='h4-yAR2Zwb order-details-section-title-nfe6D27' and text()='Notes']")));
                    ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'nearest' });", notesHeader);

                    // sadece ilk tıklamada notes başlığını açsın
                    if(count==0){
                        BrowserUtils.clickWithJS(notesHeader);
                            ++count;
                    }


                    // Textarea elementini XPath ile buluyoruz ve elementin görünür olmasını bekliyoruz

                    WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Internal")));

                    // JavaScript ile sayfayı elemente kaydırma ve tıklama işlemi
                    ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'nearest' });", textarea);
                    textarea.click();

                    // Metni girme işlemleri
                    textarea.sendKeys(Keys.CONTROL, "a");
                    textarea.sendKeys(Keys.DELETE);
                    textarea.sendKeys(notes);

                    // Tab tuşuna bas
                    textarea.sendKeys(Keys.TAB);


                    logFile.write(id.concat(" note update successfully\n"));
                } catch (Exception e) {

                    logFile.write("Error updating notes for ID: " + id + " - " + e.getMessage() + "\n");
                    continue;
                }

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
                    logger.error("Error   ID: " + id + " - " + e.getMessage());
                    logFile.write("Error   ID: " + id + " - " + e.getMessage() + "\n");
                }
            }

            // Verinin başarıyla kaydedildiğini doğrulama işlemleri burada yapılabilir

            logFile.write("All Notes have been updated successfully.\n");
        } catch (IOException e) {
            logger.error("Error writing to log file - " + e.getMessage());

        }
    }
}