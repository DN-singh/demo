package scrape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class digiScrape {
	
	static WebDriver driver;
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.godigit.com/health-insurance/digit-cashless-network-hospitals-list");
        driver.manage().window().maximize();
        Thread.sleep(10000);
        File outputFile = new File("/Users/durgaaura/eclipse-workspace/Auranft/digi.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);

        driver.findElement(By.xpath("//label[normalize-space()='State']")).click();
        
        String filePath = "/Users/durgaaura/eclipse-workspace/Auranft/digisate";
        List<String> values = readValueFromFile(filePath);
        
        for (String value : values) {
            WebElement inputField =driver.findElement(By.xpath("//input[@id='state-feild-display']"));
            inputField.sendKeys(value);
            Thread.sleep(10000);
            driver.findElement(By.xpath("//input[@id='state-feild-display']")).sendKeys(Keys.ARROW_DOWN);
            driver.findElement(By.xpath("//input[@id='state-feild-display']")).sendKeys(Keys.ENTER);
            Thread.sleep(10000);
            driver.findElement(By.xpath("//span[@class='search-txt-hospital']")).click();
            Thread.sleep(20000);
            System.out.println("State Name:-" +value);
            List<WebElement> links = driver.findElements(By.xpath("//div[@class='workshop-list-container']/div"));
            for(int i=0; i<links.size();i++) {
            	
            List<WebElement> hn = driver.findElements(By.xpath("//div[@class='workshop-list-container']/div//h3"));
                String name =hn.get(i).getText();
            	System.out.println("Hospital Name:-" +name);
            List<WebElement> paras = driver.findElements(By.xpath("//div[@class='workshop-list-container']/div//p"));
                String adderess =paras.get(i).getText();
                System.out.println("Hospital address :-" + adderess);
            
            
            }
            driver.findElement(By.xpath("//input[@id='state-feild-display']")).clear();
        }
        

}
    
    private static List<String> readValueFromFile(String filePath) throws IOException {
    	List<String> values = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            values.add(line.trim());
        }
       
        reader.close();
        return values;
    }
    public static void clickElementByJS(WebElement element, WebDriver driver) {
    	JavascriptExecutor js = ((JavascriptExecutor)driver);
    	js.executeScript("arguments[0].click();", element);
    }
}
