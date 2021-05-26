package com.sample.code;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class PriceCheck {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","/Users/vamsi.krishna/Documents/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		//Navigate to the URL
		driver.navigate().to("https://www.amazon.com/");
		driver.manage().window().maximize();
		
		//Enter the search string and submit
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("qa testing for beginners");
		driver.findElement(By.id("nav-search-submit-button")).click();
		
		//Find the price of first listing
		WebElement item = 
				driver.findElement(By.xpath("//div[@data-index='0']"));
		String itemPrice = item.findElement(By.xpath("//span[@class='a-price-symbol']")).getText() +
				item.findElement(By.xpath("//span[@class='a-price-whole']")).getText() + "." +
				item.findElement(By.xpath("//span[@class='a-price-fraction']")).getText();
		
		//Click on first element
		item.findElement(By.xpath("//a[@class='a-link-normal a-text-normal']")).click();
		
		//Get the price before adding to cart and verify
		String priceBeforeCarting = driver.findElement(By.id("newBuyBoxPrice")).getText();
		Assert.assertEquals(priceBeforeCarting, itemPrice);
		
		//Add to cart
		driver.findElement(By.id("add-to-cart-button")).click();
		
		//Get the price in cart and verify
		String cartPrice = driver.findElement(By.id("hlb-subcart")).findElement(By.xpath("//span[@class='a-color-price hlb-price a-inline-block a-text-bold']")).getText();
		Assert.assertEquals(cartPrice, itemPrice);
		
		//Proceed to checkout
		driver.findElement(By.id("hlb-ptc-btn-native")).click();
		
		Thread.sleep(5000);
		//Will be prompted for login - Enter email and password
		driver.findElement(By.id("ap_email")).sendKeys("********");
		driver.findElement(By.id("continue")).click();
		driver.findElement(By.id("ap_password")).sendKeys("********");
		driver.findElement(By.id("signInSubmit")).click();
		Thread.sleep(5000);
		
		//Close the pop-up for special instructions and click on delivery address
		driver.findElement(By.id("a-popover-1")).findElement(By.xpath("//button[@class='a-button-close a-declarative']")).click();
		driver.findElement(By.id("address-book-entry-0")).findElement(By.tagName("a")).click();
		Thread.sleep(5000);
		
		//Get the price in checkout and verify
		String checkoutPrice = driver.findElement(By.xpath("//div[@class='shipment-n-of-n ']")).findElement(By.xpath("//span[@class='a-color-price']")).getText();
		Assert.assertEquals(checkoutPrice, itemPrice);
		
		driver.close();
	}

}
