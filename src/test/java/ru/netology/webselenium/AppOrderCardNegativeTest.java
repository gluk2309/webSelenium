package ru.netology.webselenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppOrderCardNegativeTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void InvalidName() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Vera Mihailovna");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71999999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    public void InvalidPhone() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Надежда Ворон");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+719999999991");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);

    }

    @Test
    public void checkboxNotChecked() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Надежда Ворон");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71999999999");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());   //Проверяем не нажатый checkbox assertTrue

    }

    @Test
    public void emptyNameField() {

        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+71999999999");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);

    }

    @Test
    public void emptyPhoneField() {

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Вера Ворон");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", text);
    }


}
