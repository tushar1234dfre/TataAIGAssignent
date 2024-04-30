package com.Amazon.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class amazon {

	public WebDriver driver;

	@Test(priority = 1)
	public void searchingProduct() {

		String productName = "Mobile Phone";

		// Setup the ChromeDriver
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\Chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();

		// Launch the URL
		driver.get("https://www.amazon.in/");

		// Check that Product Search Box is Displayed
		boolean isTrue = driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).isDisplayed();
		System.out.println("Search Box is Displayed : " + isTrue);

		// Enter the Product Name in Search Field
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys(productName);

		// Click on Search Button
		driver.findElement(By.xpath("//input[@id='nav-search-submit-button']")).click();

		WebElement searchResult = driver.findElement(By.xpath("//span[contains(text(),'Phone')]"));
		if (searchResult.isDisplayed()) {
			System.out.println("The Search results are displayed acc/to the Search Product");
		} else {
			System.out.println("The Search results are not displayed acc/to the Search Product");
		}
	}

	@Test(priority = 2)
	public void filteringSearchResult() {
		String minPrice = "1000";
		String maxPrice = "10000";

		// Scroll down the Page
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");

		// Enter Min Price
		WebElement minPrices = driver.findElement(By.xpath("//input[@id='low-price']"));
		boolean isTrue = minPrices.isDisplayed();
		System.out.println("Minimum Price Field is Displayed : " + isTrue);
		minPrices.sendKeys(minPrice);

		// Enter Max Price
		WebElement maxPrices = driver.findElement(By.xpath("//input[@id='high-price']"));
		boolean isTru = maxPrices.isDisplayed();
		System.out.println("Maximum Price Field is Displayed : " + isTru);
		minPrices.sendKeys(maxPrice);

		// Click on Go Button
		driver.findElement(By.xpath("//input[@class='a-button-input']")).click();
		System.out.println("STEP : Clicked on Go Button");

		boolean resultsAreCorrect = true;
		for (WebElement searchResult : driver.findElements(By.xpath("//span[@class='a-price']"))) {
			String priceText = searchResult.getText().replaceAll("[^0-9.]", ""); // Remove non-numeric characters
			if (!priceText.isEmpty()) {
				double price = Double.parseDouble(priceText);
				if (price < Double.parseDouble(minPrice) || price > Double.parseDouble(maxPrice)) {
					resultsAreCorrect = false;
					break;
				}
			}
		}
	}

	@Test(priority = 3)
	public void addProductToShoppingCart() throws InterruptedException {

		// Click on product Link to Navigate to the Product Page
		driver.findElement(By.xpath("(//span[@class='a-truncate-cut'])[2]")).click();
		System.out.println("STEP : Clicked on Product to Navigate to the Product Details Page");

		// Scroll Down the Page
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,250)", "");

		// Add prodcut to Cart
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
		System.out.println("STEP : Product Added to Cart Successfully");

	}

	@Test(priority = 4)
	public void proceedToCheckOut() {
		// Clicked on Proceed to Checkout Button
		driver.findElement(By.xpath("//span[text()='Proceed to checkout (1 item)']//parent::span//preceding-sibling::input")).click();
		System.out.println("STEP : Clicked on Proceed to CheckOut button");
	}
}
