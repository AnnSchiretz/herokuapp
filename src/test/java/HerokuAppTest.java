import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;


public class HerokuAppTest {
    private WebDriver driver;

    @BeforeTest
    public void setUP() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void addRemoveElements() {
        driver.get("http://the-internet.herokuapp.com/add_remove_elements/");
        By addManually = By.xpath("//button[text()='Add Element']");
        driver.findElement(addManually).click();
        driver.findElement(addManually).click();
        driver.findElement(By.className("added-manually")).click();
        int numberOfDeleteButtons = driver.findElements(By.className("added-manually")).size();
        assertEquals(numberOfDeleteButtons, 1, "Число кнопок не верно");
    }

    @Test
    public void checkCheckboxes() {
        driver.get("http://the-internet.herokuapp.com/checkboxes");
        By checkboxes = By.tagName("input");
        List<WebElement> checkBoxes = driver.findElements(checkboxes);
        assertEquals(checkBoxes.get(0).isSelected(), false, "Первый чек-бокс выбран");
        checkBoxes.get(0).click();
        assertEquals(checkBoxes.get(0).isSelected(), true, "Первый чек-бокс не выбран");
        assertEquals(checkBoxes.get(1).isSelected(), true, "Второй чек-бокс не выбран");
        checkBoxes.get(1).click();
        assertEquals(checkBoxes.get(1).isSelected(), false, "Второй чек-бокс выбран");
    }

    @Test
    public void dropdownListElement() {
        driver.get("http://the-internet.herokuapp.com/dropdown");
        By dropdownOptions = By.xpath("//select/option");
        List<WebElement> dropdownListElement = driver.findElements(dropdownOptions);
        String firstOption = dropdownListElement.get(0).getText();
        assertEquals(firstOption, "Please select an option", "Текст неверный, либо элемента нет в списке");
        dropdownListElement.get(1).click();
        String secondOption = dropdownListElement.get(1).getText();
        assertEquals(secondOption, "Option 1", "Не совпадает текст, либо нет такого элемента");
        dropdownListElement.get(2).click();
        String thirdOption = dropdownListElement.get(2).getText();
        assertEquals(thirdOption, "Option 2", "Не совпадает текст, либо нет такого элемента");

    }

    @Test
    public void actionsWithHovers() {
        driver.get("http://the-internet.herokuapp.com/hovers");
        By hoverElement = By.xpath("//div[@id=\"content\"]/div/div");
        WebElement firstHover = driver.findElement(hoverElement);
        Actions builder = new Actions(driver);
        builder.moveToElement(firstHover).perform();
        String firstUser = driver.findElement(By.cssSelector(".figcaption h5")).getText();
        assertEquals(firstUser, "name: user1", "Не совпадает текст");
        driver.findElement(By.cssSelector(".figcaption a")).click();
        String resultFirstPage = driver.findElement(By.xpath("//h1")).getText();
        assertEquals(resultFirstPage, "Not Found", "Не верный текст или не сработал клик по кнопке");
        driver.navigate().back();

        By secondHoverElement = By.xpath("//div[@id=\"content\"]/div/div[2]");
        WebElement secondHover = driver.findElement(secondHoverElement);
        builder.moveToElement(secondHover).build().perform();
        String secondUser = driver.findElement(By.xpath("//div[@id=\"content\"]/div/div[2]/div/h5")).getText();
        assertEquals(secondUser, "name: user2", "Не совпадает текст");
        driver.findElement(By.xpath("//div[@id=\"content\"]/div/div[2]/div/a")).click();
        String resultSecondPage = driver.findElement(By.xpath("//h1")).getText();
        assertEquals(resultSecondPage, "Not Found", "Не верный текст или не сработал клик по кнопке");
        driver.navigate().back();

        By thirdHoverElement = By.xpath("//div[@id=\"content\"]/div/div[3]");
        WebElement thirdHover = driver.findElement(thirdHoverElement);
        builder.moveToElement(thirdHover).build().perform();
        String thirdUser = driver.findElement(By.xpath("//div[@id=\"content\"]/div/div[3]/div/h5")).getText();
        assertEquals(secondUser, "name: user2", "Не совпадает текст");
        driver.findElement(By.xpath("//div[@id=\"content\"]/div/div[3]/div/a")).click();
        String resultThirdPage = driver.findElement(By.xpath("//h1")).getText();
        assertEquals(resultThirdPage, "Not Found", "Не верный текст или не сработал клик по кнопке");
    }

    @Test
    public void inputsWork() {
        driver.get("http://the-internet.herokuapp.com/inputs");
        driver.findElement(By.xpath("//input")).sendKeys(Keys.ARROW_UP);
        String firstResult = driver.findElement(By.xpath("//input")).getAttribute("value");
        assertEquals(firstResult, "1");
        driver.findElement(By.xpath("//input")).sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
        String secondResult = driver.findElement(By.xpath("//input")).getAttribute("value");
        assertEquals(secondResult, "-2");
        driver.findElement(By.xpath("//input")).sendKeys("fwiojfwi");
        String thirdResult = driver.findElement(By.xpath("//input")).getAttribute("value");
        assertEquals(thirdResult, "-2");
    }

    @Test
    public void notificationMessage() {
        driver.get("http://the-internet.herokuapp.com/notification_message_rendered");
        driver.findElement(By.cssSelector(".example a")).click();
        String message = driver.findElement(By.cssSelector(".flash")).getText();
        assertEquals(message, "Action unsuccesful, please try again\n" + "×", "не сработал клик по кнопке, либо не верное сообщение");
    }

    @Test
    public void tableContains() {
        driver.get("http://the-internet.herokuapp.com/tables");
        List<WebElement> elements = driver.findElements(By.cssSelector(".tablesorter thead tr th"));
        for (WebElement element : elements) {
            assertEquals(elements.get(0).getText(), "Last Name");
            assertEquals(elements.get(1).getText(), "First Name");
            assertEquals(elements.get(2).getText(), "Email");
            assertEquals(elements.get(3).getText(), "Due");
            assertEquals(elements.get(4).getText(), "Web Site");
            assertEquals(elements.get(5).getText(), "Action");
        }
        List<WebElement> johnSmith = driver.findElements(By.xpath("//tbody/tr[1]/td"));
        for (WebElement element : johnSmith) {
            assertEquals(johnSmith.get(0).getText(), "Smith");
            assertEquals(johnSmith.get(1).getText(), "John");
            assertEquals(johnSmith.get(2).getText(), "jsmith@gmail.com");
            assertEquals(johnSmith.get(3).getText(), "$50.00");
            assertEquals(johnSmith.get(4).getText(), "http://www.jsmith.com");
            assertEquals(johnSmith.get(5).getText(), "edit delete");
        }
        List<WebElement> bachFrank = driver.findElements(By.xpath("//tbody/tr[2]/td"));
        for (WebElement element : bachFrank) {
            assertEquals(bachFrank.get(0).getText(), "Bach");
            assertEquals(bachFrank.get(1).getText(), "Frank");
            assertEquals(bachFrank.get(2).getText(), "fbach@yahoo.com");
            assertEquals(bachFrank.get(3).getText(), "$51.00");
            assertEquals(bachFrank.get(4).getText(), "http://www.frank.com");
            assertEquals(bachFrank.get(5).getText(), "edit delete");
        }

        List<WebElement> doeJason = driver.findElements(By.xpath("//tbody/tr[3]/td"));
        for (WebElement element : doeJason) {
            assertEquals(doeJason.get(0).getText(), "Doe");
            assertEquals(doeJason.get(1).getText(), "Jason");
            assertEquals(doeJason.get(2).getText(), "jdoe@hotmail.com");
            assertEquals(doeJason.get(3).getText(), "$100.00");
            assertEquals(doeJason.get(4).getText(), "http://www.jdoe.com");
            assertEquals(doeJason.get(5).getText(), "edit delete");
        }
        List<WebElement> timConway = driver.findElements(By.xpath("//tbody/tr[4]/td"));
        for (WebElement element : timConway) {
            assertEquals(timConway.get(0).getText(), "Conway");
            assertEquals(timConway.get(1).getText(), "Tim");
            assertEquals(timConway.get(2).getText(), "tconway@earthlink.net");
            assertEquals(timConway.get(3).getText(), "$50.00");
            assertEquals(timConway.get(4).getText(), "http://www.timconway.com");
            assertEquals(timConway.get(5).getText(), "edit delete");
        }
    }

    @Test
    public void checkSpelling() {
        driver.get("http://the-internet.herokuapp.com/typos");
        String expectedResult = "Sometimes you'll see a typo, other times you won't.";
        String actualResult = "";
        for (int i = 0; i < 6; i++) {
            driver.navigate().refresh();
            actualResult = driver.findElement(By.xpath("//div/p[2]")).getText();
            assertEquals(expectedResult,actualResult, "Найдена ошибка");
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}
