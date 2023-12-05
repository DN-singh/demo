package auraNft.scraping;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadWriteMongodb {

    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://looksrare.org/");
        driver.manage().window().maximize();
        Thread.sleep(10000);

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017"); 
        MongoDatabase database = mongoClient.getDatabase("your_database_name"); 
        MongoCollection<Document> collection = database.getCollection("your_collection_name"); 
        
       List<String> values = readValueFromFile(collection);


        for (String value : values) {
            WebElement inputField = driver.findElement(By.xpath("//input[@placeholder='Collections, Items, Profiles']"));
            inputField.sendKeys(value);
            Thread.sleep(15000);

            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='css-1vowvxp']//div[contains(text(),'Collections')]")));
                WebElement collectionElement = driver.findElement(By.xpath("//div[@class='css-1vowvxp']//div[contains(text(),'Collections')]"));
                String collectionText = collectionElement.getText();
                
                if (collectionText.contains("Collections")) {
                    WebElement collectionLink = driver.findElement(By.xpath("//div[@data-test-id='collection-desktop-results-stack']//div[1]//div[1]//a[1]"));
                    String link = collectionLink.getAttribute("href");
                    System.out.println(value + ":-" + link);
                    
                    // Insert data into MongoDB
                    Document doc = new Document("value", value)
                            .append("link", link);
                    collection.insertOne(doc);

                } else {
                    System.out.println("collection not available");
                }
            } catch (TimeoutException exception) {
                System.out.println(value + ":-" + "Collection not found");
            }

            driver.findElement(By.xpath("//input[@placeholder='Collections, Items, Profiles']")).clear();
        }

        driver.quit();
    }

	private static List<String> readValueFromFile(MongoCollection<Document> collection) {
		List<String> values = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            values.add(line.trim());
        }
       
        reader.close();
        return values;
	}
}
