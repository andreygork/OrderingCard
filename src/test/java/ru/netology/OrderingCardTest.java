package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderingCardTest {
    WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldGetSuccess() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Горькаев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79017054075");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldGetInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Andrey Gorkaev");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79017054075");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldGetValidName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Горькаев-Петров");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79017054075");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldGetInvalidPfone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Горькаев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7(901)7054075");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldGetEmptyCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Горькаев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79017054075");
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldBeFieldOfNameEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79017054075");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldBeFieldOfPhoneEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Андрей Горькаев");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(actual, expected);
    }
}
