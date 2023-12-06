package auraNft.scraping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
public class Earnallinac {
	
	static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		
		 WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();
		 driver.manage().window().maximize();
		 driver.get("https://www.earnalliance.com/games");
		 File outputFile = new File("/Users/durgaaura/eclipse-workspace/Auranft/EarnallianceGames.txt");
	        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
	        PrintStream printStream = new PrintStream(fileOutputStream);
	        System.setOut(printStream);
	     Thread.sleep(30000);
	     driver.findElement(By.xpath("//button[@id='accordion-button-:r6:']")).click();
	     List<WebElement> Blockchain = driver.findElements(By.xpath("//div[@id='accordion-panel-:r6:']/label"));
	     for(int i = 4; i < Blockchain.size(); i++)  {
	        	WebElement Blockchains = Blockchain.get(i);
	        	Blockchains.click();
	           String chainName =  Blockchains.getText();
	           System.out.println(chainName);
	           Thread.sleep(10000);
	           JavascriptExecutor jse = (JavascriptExecutor)driver;
	           long intialHeight = (long)(jse.executeScript("return document.body.scrollHeight"));
	           while(true) {
	        	     jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	        	     Thread.sleep(10000);
	        	     long currentHeight = (long)(jse.executeScript("return document.body.scrollHeight"));
	        	     if(intialHeight==currentHeight) {
	        	    	 break;
	        	     }
	        	     intialHeight = currentHeight;
	           }
	      Thread.sleep(20000);
	      List<WebElement> chainGame = driver.findElements(By.xpath("//div[@aria-label='List of Games']//div[3]/p"));
	      for(WebElement chainGames : chainGame) {
	    	  String gameName = chainGames.getText();
	    	  System.out.println(gameName);
	      }
	     
	      jse.executeScript("window.scrollTo(100,0)");
	      Thread.sleep(20000);
	     driver.findElement(By.xpath("//div[@class='css-upazp8']//span[@aria-label='Remove']")).click();
	 }
	}
}