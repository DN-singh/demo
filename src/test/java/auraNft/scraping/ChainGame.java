package auraNft.scraping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChainGame {
	 static WebDriver driver;

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-dev-shm-usage");
       
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");
       

        
        driver = new ChromeDriver(options);
       // File path for the text file containing the URL
        String filePath = "/Users/durgaaura/eclipse-workspace/Auranft/dapplink.txt";
        
        try {
            // Create a FileReader to read the text file
            FileReader fileReader = new FileReader(filePath);
            
            // Create a BufferedReader to read lines from the text file
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            // Read the URL from the text file
            String url = bufferedReader.readLine();
            
            // Navigate to the URL using WebDriver
            driver.get(url);
            Thread.sleep(10000);
            driver.findElement(By.xpath("//h1[normalize-space()='Top WAX Games']"));
            // Close the BufferedReader and the WebDriver
            bufferedReader.close();
           // driver.quit();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

       
}
}
