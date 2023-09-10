package auraNft.scraping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AuraGameData {
	
	static WebDriver driver;
	public static void main(String[] args) throws FileNotFoundException {
		
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        File outputFile = new File("/Users/durgaaura/eclipse-workspace/Auranft/link1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintStream printStream = new PrintStream(fileOutputStream);
        System.setOut(printStream);
        String filePath = "/Users/durgaaura/eclipse-workspace/Auranft/auragames.txt";
     // Create a list to store the URLs
        List<String> urls = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            // Read URLs from the text file and add them to the list
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                urls.add(line);
            }
            bufferedReader.close();
           // Iterate through the list of URLs and navigate to each one
            for (String url : urls) {
                driver.get(url);
                Thread.sleep(10000);
             // Add your web scraping or other actions here as needed
                WebElement name = driver.findElement(By.xpath("//p[@class='wa-overview__title']"));
               String Gamename = name.getText();
               System.out.println(Gamename);
                WebElement totalvisit =driver.findElement(By.xpath("//div[@class='app-page-navigation__section']//div[@class='engagement-list']//div[1]/p[2]"));
                String count = totalvisit.getText();
                System.out.println("Totalvisit :-"+ count);
                List<WebElement> elements = driver.findElements(By.xpath("//div[@class='wa-geography__legend wa-geography__chart-legend']/div/div[2]"));
             // Iterate through the list and get text from each element
                for (WebElement element : elements) {
                    String text = element.getText();
                    System.out.println(text);
                }
                
                
                driver.quit();
                driver = new ChromeDriver();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
       
                
        }
	}
	
	


