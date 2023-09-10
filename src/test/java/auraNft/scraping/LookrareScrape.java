package auraNft.scraping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LookrareScrape {
	
	static WebDriver driver;
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
//      ChromeOptions options=new ChromeOptions();
//      options.addArguments("headless");
        driver = new ChromeDriver();
        driver.get("https://looksrare.org/");
        driver.manage().window().maximize();
        Thread.sleep(10000);
        File outputFile = new File("/Users/durgaaura/eclipse-workspace/Auranft/link1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
      
        driver.findElement(By.xpath("//div[@class='css-1fesjon']")).click();
        String filePath = "/Users/durgaaura/eclipse-workspace/Auranft/BinanceGame";
        List<String> values = readValueFromFile(filePath);
        
        for (String value : values) {
            WebElement inputField =driver.findElement(By.xpath("//input[@placeholder='Collections, Items, Profiles']"));
            inputField.sendKeys(value);
            Thread.sleep(15000);
            
            try {
	            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='css-1vowvxp']//div[contains(text(),'Collections')]")));
                WebElement collection =driver.findElement(By.xpath("//div[@class='css-1vowvxp']//div[contains(text(),'Collections')]"));
                String collectionText = collection.getText();
                //System.out.println(collectionText);
                
                if (collectionText.contains("Collections")) {
    	           WebElement colletionLink = driver.findElement(By.xpath("//div[@data-test-id='collection-desktop-results-stack']//div[1]//div[1]//a[1]"));
    	           String link = colletionLink.getAttribute("href");
    	           System.out.println(value +":-"+link);

                 } else {
    	             System.out.println("collection not availabel");
                 }
	        } catch(TimeoutException exception){
		        System.out.println(value+":-"+"Collection not found");
	        }
            
            driver.findElement(By.xpath("//input[@placeholder='Collections, Items, Profiles']")).clear();

        }
        driver.quit();
        printStream.close();
        fileOutputStream.close();
     
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

    private static void writeAttributeValueToCSV(String attributeValue, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        writer.append(attributeValue+ "\n" );
        
        writer.flush();
        writer.close();
    } 

    
    
    
}
