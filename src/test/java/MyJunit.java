import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class MyJunit {
    WebDriver driver;

    @BeforeAll
    public void setup(){
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @DisplayName("Check if Get correct tile")
    @Test
    public void a_getTitle() {
        driver.get("https://demoqa.com");
        String titleActual = driver.getTitle();
        String titleExpected = "DEMOQA";
        Assertions.assertEquals(titleExpected, titleActual);
    }

    @DisplayName("Check if find the Image")
    @Test
    public void b_imageCheck() {
        driver.get("https://demoqa.com");
        boolean imgStatus = driver.findElements(By.tagName("img")).get(1).isDisplayed();
        Assertions.assertTrue(imgStatus);
    }
//    @DisplayName("Check if Form is fill up Properly")
//    @Test
//    public void c_automateForm(){
//        driver.get("https://demoqa.com/text-box");
//
//        driver.findElement(By.id("userName")).sendKeys("Test Rahim");
//        driver.findElement(By.id("userEmail")).sendKeys("Testrahim@gmail.com");
//        scroll();
//        driver.findElement(By.id("currentAddress")).sendKeys("Mirpur,Dhaka");
//        driver.findElement(By.id("permanentAddress")).sendKeys("Bangladesh");
//        scroll();
//        driver.findElement(By.id("submit")).click();
//
//        String nameActual = driver.findElement(By.id("name")).getText();
//        Assertions.assertTrue(nameActual.contains("Rahim"));
//        String emailActual = driver.findElement(By.id("email")).getText();
//        Assertions.assertTrue(emailActual.contains("rahim@"));
//        String curAddActual = driver.findElements(By.id("currentAddress")).get(1).getText();
//        Assertions.assertTrue(curAddActual.contains("Mirpur"));
//        String perAddActual = driver.findElements(By.id("permanentAddress")).get(1).getText();
//        Assertions.assertTrue(perAddActual.contains("Bangla"));
//    }
    @DisplayName("Check if the form Fill automated")
    @Test
    public void c_automateForm() {
        driver.get("https://demoqa.com/text-box");
        driver.findElements(By.className("form-control")).get(0).sendKeys("Mr test");
        driver.findElements(By.className("form-control")).get(1).sendKeys("test@gmail.com");
        driver.findElements(By.className("form-control")).get(2).sendKeys("Rampura Dhaka");
        Utils.scroll(driver,0, 250);
        driver.findElements(By.className("form-control")).get(3).sendKeys("Bangladesh");
        Utils.scroll(driver,0, 500);
        driver.findElement(By.className("btn-primary")).click();

        String nameActual = driver.findElements(By.className("mb-1")).get(0).getText();
        Assertions.assertTrue(nameActual.contains("test"));
        String emailActual = driver.findElements(By.className("mb-1")).get(1).getText();
        Assertions.assertTrue(emailActual.contains("test@"));
        String curAddActual = driver.findElements(By.className("mb-1")).get(2).getText();
        Assertions.assertTrue(curAddActual.contains("Dhaka"));
        String perAddActual = driver.findElements(By.className("mb-1")).get(3).getText();
        Assertions.assertTrue(perAddActual.contains("Bangla"));
    }

    @DisplayName("Check if Checkbox is click")
    @Test
    public void d_checkBox() {
        driver.get("https://demoqa.com/checkbox");
        driver.findElement(By.className("rct-icon-expand-close")).click();//home
        driver.findElements(By.className("rct-icon-expand-close")).get(1).click();//documents
        Utils.scroll(driver,0, 500);
        driver.findElements(By.className("rct-icon-expand-close")).get(2).click();//office
        driver.findElement(By.xpath("//label[@for='tree-node-private']//span[@class='rct-checkbox']//*[name()='svg']")).click();
        driver.findElements(By.className("rct-icon-expand-close")).get(2).click();
        driver.findElement(By.xpath("//label[@for='tree-node-excelFile']//span[@class='rct-checkbox']//*[name()='svg']")).click();

        String firstActualSelect = driver.findElements(By.className("text-success")).get(0).getText();
        Assertions.assertTrue(firstActualSelect.contains("private"));
        String secondActualSelect = driver.findElements(By.className("text-success")).get(1).getText();
        Assertions.assertTrue(secondActualSelect.contains("excel"));
    }

    @DisplayName("Check if handleAleart is working")
    @Test
    public void j_handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        List<WebElement> clickButton = driver.findElements(By.className("btn-primary"));
        clickButton.get(0).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        clickButton.get(1).click();
        Thread.sleep(5000);
        driver.switchTo().alert().accept();

        clickButton.get(2).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
        String actualAction = driver.findElement(By.id("confirmResult")).getText();
        Assertions.assertTrue(actualAction.contains("Ok"));

        clickButton.get(2).click();
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();
        String actual2Action = driver.findElement(By.id("confirmResult")).getText();
        Assertions.assertTrue(actual2Action.contains("Cancel"));

        clickButton.get(3).click();
        Thread.sleep(2000);
        driver.switchTo().alert().sendKeys("nava is bad");
        driver.switchTo().alert().accept();
        String TypeActual = driver.findElement(By.id("promptResult")).getText();
        Assertions.assertTrue(TypeActual.contains("nava"));

        clickButton.get(3).click();
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();


    }

    @DisplayName("Check if datepicker is working")
    @Test
    public void o_datePicker() {
        driver.get("https://demoqa.com/date-picker");
        WebElement dateInputPlace = driver.findElement(By.id("datePickerMonthYearInput"));
        dateInputPlace.click();
        dateInputPlace.sendKeys(Keys.CONTROL + "a");
        dateInputPlace.sendKeys(Keys.BACK_SPACE);
        dateInputPlace.sendKeys("10/16/2023");
        dateInputPlace.sendKeys(Keys.ENTER);

    }

    @DisplayName("check if select menu is automated")
    @Test
    public void p_selectDropdown() throws InterruptedException {
        driver.get("https://demoqa.com/select-menu");
        driver.findElements(By.className("css-1hwfws3")).get(0).click();//Select Value.
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        Utils.scroll(driver,0, 250);

        driver.findElements(By.className("css-1hwfws3")).get(1).click();//Select One.
        Actions actions1 = new Actions(driver);
        actions1.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        Utils.scroll(driver,0, 250);

        Select select = new Select(driver.findElement(By.id("oldSelectMenu")));//Old Style Dropdown
        select.selectByIndex(5);
        Utils.scroll(driver,0, 500);

        driver.findElements(By.className("css-1hwfws3")).get(2).click();//Multiple select ReactDropdown.
        Actions actions2 = new Actions(driver);
        actions2.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
        actions2.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

        Utils.scroll(driver,0, 250);

        Select select1 = new Select(driver.findElement(By.id("cars")));//Standard Multi Select
        select1.selectByVisibleText("Audi");
        select1.selectByVisibleText("Volvo");

    }

    @DisplayName("Check if mouse is hover perfectly")
    @Test
    public void q_mouseHover() {
        driver.get("https://demoqa.com/tool-tips");
        WebElement hoverPoint = driver.findElement(By.id("toolTipButton"));
        Actions actions = new Actions(driver);
        actions.moveToElement(hoverPoint).perform();

    }

    @DisplayName("Check if upload and download")
    @Test
    public void h_uploadDownloadFile() {
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("uploadFile")).sendKeys(System.getProperty("user.dir") + "/src/test/resources/desktop-wallpaper-isaac-newton.jpg");
        String path = driver.findElement(By.id("uploadedFilePath")).getText();
        Assertions.assertTrue(path.contains("desktop-wallpaper-isaac-newton.jpg"));

        driver.findElement(By.id("downloadButton")).click();
    }

    @DisplayName("Check if button click")
    @Test
    public void g_buttonClick() {
        driver.get("https://demoqa.com/buttons");
        List<WebElement> buttonList = driver.findElements(By.className("btn-primary"));
        Actions actions = new Actions(driver);
        actions.doubleClick(buttonList.get(0)).perform();
        String txtActual = driver.findElement(By.id("doubleClickMessage")).getText();
        Assertions.assertTrue(txtActual.contains("double click"));
        Utils.scroll(driver, 0, 250);
        actions.contextClick(buttonList.get(1)).perform();
        String txt2Actual = driver.findElement(By.id("rightClickMessage")).getText();
        Assertions.assertTrue(txt2Actual.contains("right click"));

        Utils.scroll(driver,0, 500);

        actions.click(buttonList.get(2)).perform();
        String txt3Actual = driver.findElement(By.id("dynamicClickMessage")).getText();
        Assertions.assertTrue(txt3Actual.contains("dynamic click"));

    }

    @DisplayName("Check if modal is working")
    @Test
    public void l_modal() throws InterruptedException {
        driver.get("https://demoqa.com/modal-dialogs");
        Utils.scroll(driver,0, 250);
        driver.findElement(By.id("showSmallModal")).click();//Small Modal
        Thread.sleep(1000);
        driver.findElement(By.id("closeSmallModal")).click();
        String smallModaltxt = driver.findElement(By.className("modal-body")).getText();
        Assertions.assertTrue(smallModaltxt.contains("small modal"));

        driver.findElement(By.id("showLargeModal")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("closeLargeModal")).click();
        String largeModal = driver.findElement(By.xpath("//p[contains(text(),'Lorem Ipsum is simply dummy text of the printing a')]")).getText();
        Assertions.assertTrue(largeModal.contains("Lorem Ipsum"));
    }

    @DisplayName("Check if window handle")
    @Test
    public void i_windowHandle() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();//New Tab
        ArrayList<String> w = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(w.get(1));
        Thread.sleep(3000);
        String text = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(text.contains("sample page"));
        driver.close();
        driver.switchTo().window(w.get(0));
        Thread.sleep(1000);

        driver.findElement(By.id("windowButton")).click();//New Window
        String mainWindow = driver.getWindowHandle();
        Set<String> allwindow = driver.getWindowHandles();
        Iterator<String> iterator = allwindow.iterator();

        while (iterator.hasNext()) {
            String childWindow = iterator.next();
            if (!mainWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
                Thread.sleep(1000);
                String textWindow = driver.findElement(By.id("sampleHeading")).getText();
                Assertions.assertTrue(textWindow.contains("sample page"));
            }
        }
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    @DisplayName("Check if web tables edit")
    @Test
    public void f_webEditTable() {
        driver.get("https://demoqa.com/webtables");
        driver.findElement(By.xpath("//span[@id='edit-record-2']//*[name()='svg']")).click();
        Utils.scroll(driver,0, 250);
        driver.findElement(By.id("department")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.id("department")).sendKeys("Software Developer");
        driver.findElement(By.id("submit")).click();
    }

    @DisplayName("Check if frame is working")
    @Test
    public void k_frame() {
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame1");
        String frameText = driver.findElement(By.id("sampleHeading")).getText();
        Assertions.assertTrue(frameText.contains("sample page"));
        driver.switchTo().defaultContent();

    }

    @DisplayName("Check if scrap table data")
    @Test
    public void m_scrapTables() {
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr-group"));
        for (WebElement row : allRows) {
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells) {
                System.out.println(cell.getText());
            }
        }
    }

    @DisplayName("Check if radiobutton press")
    @Test
    public void e_radioButton(){
        driver.get("https://demoqa.com/radio-button");
        List<WebElement> btns = driver.findElements(By.className("custom-control-label"));
        Actions actions = new Actions(driver);
        actions.click(btns.get(1)).perform();

        String actualSelect = driver.findElement(By.className("mt-3")).getText();
        Assertions.assertTrue(actualSelect.contains("Impressive"));
    }

    @DisplayName("Check if accordian is ok")
    @Test
    public void n_accordian() throws InterruptedException {
        driver.get("https://demoqa.com/accordian");
        List<WebElement> textField = driver.findElements(By.tagName("p"));

        driver.findElement(By.id("section1Heading")).click();
        Thread.sleep(1000);
        Utils.scroll(driver,0,250);

        Thread.sleep(1000);
        Utils.scroll(driver,0,250);
        driver.findElement(By.id("section2Heading")).click();
        Thread.sleep(1000);

        Utils.scroll(driver,0,500);
        driver.findElement(By.id("section3Heading")).click();


    }

    @AfterAll
    public void quitBrowser(){
        driver.quit();
    }
}
