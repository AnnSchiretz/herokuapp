import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HerokuAppTest {
    @Test
    public void addRemoveElements(){
        By addManually = By.xpath("//button[text()='Add Element']");
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdrivers/chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/add_remove_elements/");
        driver.findElement(addManually).click();
        driver.findElement(addManually).click();
        driver.findElement(By.className("added-manually")).click();
        int numberOfDeleteButtons = driver.findElements(By.className("added-manually")).size();
        assertEquals(numberOfDeleteButtons,1,"Число кнопок не верно");
        driver.quit();
    }
}
