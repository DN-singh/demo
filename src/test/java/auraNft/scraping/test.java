package auraNft.scraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.List;

public class test {
    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://token2049.brella.io/events");
        Thread.sleep(10000);

        File outputFile = new File("/Users/durgaaura/eclipse-workspace/Auranft/token2049.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);

        login();
        driver.findElement(By.xpath("//body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/main[1]/div[1]/div[1]/div[2]/div[1]/div[2]/span[1]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//ul[@role='menu']/li[2]")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//a[normalize-space()='All attendees']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//span[@title='12 / page']")).click();
        driver.findElement(By.xpath("//div[contains(text(),'120 / page')]")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//li[@title='Next 5 Pages']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//li[@title='Next 5 Pages']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//li[@title='Next 5 Pages']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//li[@title='Next 5 Pages']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//li[@title='Next 5 Pages']")).click();
        Thread.sleep(10000);
        driver.findElement(By.xpath("//li[@title='24']")).click();
        Thread.sleep(15000);
        scrapeAttendees(44);
        
        
       // driver.quit();
    }

    public static void login() throws InterruptedException {
        driver.findElement(By.xpath("//button[@data-test='change-login-method-link']")).click();
        driver.findElement(By.xpath("//input[@id='sign-in_email']")).sendKeys("shantanu@auranft.co");
        driver.findElement(By.xpath("//span[normalize-space()='Continue']")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@id='sign-in_password']")).sendKeys("AuraNFT@2023");
        driver.findElement(By.xpath("//span[normalize-space()='Sign In']")).click();
        Thread.sleep(10000);
    }

    public static void scrapeAttendees(int pageCount) {
    	for (int page = 0; page < pageCount; page++) {
        List<WebElement> links = driver.findElements(By.xpath("//div[@class='css-6viwen e4uvspw5']//div//div//div//button[1][@data-test='attendee-card-meeting-button']"));
        
        for(int i = 0; i < links.size(); i++)  {
        	WebElement link = links.get(i);
            if (link.isEnabled()) {
                link.click();
                try {
                    Thread.sleep(10000); // Sleep for 10 seconds (adjust as needed)
                    WebElement header = driver.findElement(By.xpath("//div[@class='css-fyowp3 efx8l0f3']//h2"));
                    String Name = header.getText();
                    System.out.println("Name: " + Name);
                    WebElement span1 =null;
                    try {
                    span1 = driver.findElement(By.xpath("//div[@class='css-fyowp3 efx8l0f3']//span[1]"));
                    String company = span1.getText();
                    System.out.println("Company: " + company);
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        System.out.println("No company");
                    }
                    WebElement span2 = null;
                    try {
                    span2 = driver.findElement(By.xpath("//div[@class='css-fyowp3 efx8l0f3']//span[2]"));
                    String Desig = span2.getText();
                    System.out.println("Designation: " + Desig);
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        System.out.println("No designa");
                    }
                    WebElement element = null;
                    try {
                        element = driver.findElement(By.xpath("//div[@class='css-61vqfz e11zpnbz0']/a"));
                        String slink = element.getAttribute("href");
                        System.out.println("Links: " + slink);
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        System.out.println("No link");
                    }
                    WebElement intro = null;
                    try {
                        intro = driver.findElement(By.xpath("//div[@class='css-ti75j2 ee7qfm1']"));
                        String introduction = intro.getText();
                        System.out.println(introduction);
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        System.out.println("No intro");
                    }
                    WebElement operates = null;
                    try {
                        operates = driver.findElement(By.xpath("//ul[@class='css-undnwh e2ov2j41']"));
                        String operate = operates.getText();
                        System.out.println("Operates in: " + operate);
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        System.out.println("No country");
                    }

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Close']"))).click();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Chat disabled");
            }
        }
        if (page < pageCount - 1) {
            // Click the "Next Page" button if there are more pages
            driver.findElement(By.xpath("//li[@title='Next Page']//button[@type='button']")).click();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    }
}
