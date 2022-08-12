package Selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {
	public static final String ADD_BUTTON = "//div[text()='%s']//ancestor::div[@class='inventory_item_label']//following-sibling::div//button[text()='Add to cart']";
	public static final String REMOVE_BUTTON = "//div[text()='%s']//ancestor::div[@class='inventory_item_label']//following-sibling::div//button[text()='Remove']";
	public static final String TITLE = "//span[@class='title'][text()='%s']";
	public static final WebDriver driver = new ChromeDriver();
	public static  WebDriverWait wait = null;
	
	public static final String _eltPageTitle = "//span[@class='title']";
	public static final String _eltShoppingCartIcon = "//a[@class='shopping_cart_link']";
	public static final String _eltProductsFilter = "//span[@class='select_container']";
	
	//Button
	public static final String _eltBtnCheckout = "//button[@id='checkout']";
	public static final String _eltRemove = "//button[text()='Remove']";
	public static final String _eltBtnContinueShopping = "//button[@id='continue-shopping']";

	
	public static void navigateToDemoPage() {
		driver.navigate().to("https://www.saucedemo.com/");

	}
	
	public static void Login() {
		//Navigate
		navigateToDemoPage();
		driver.manage().window().maximize();
		
		//Authentication
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
	}
	
	public static Boolean verifyDisplayedElement(WebElement elt, String name) {
		boolean test = true;
		try {
			if (elt.isDisplayed()) {
				
				test = true;
				System.out.println(name +  " is displayed, PASS");
			}
		} catch(Exception e) {
			test = false;
			
			System.out.println(name +  " isn't displayed, FAIL");
			
		}
		return test;
	}
	
	public static WebElement GetWebElement(String xpath) {
		
		WebElement elt = driver.findElement(By.xpath(xpath));
		return elt;
	}
	
	public static void verifyAddACart() {
		addCart("Sauce Labs Backpack");
		verifyAddedCart("Sauce Labs Backpack");
	}
	
	public static void verifyDemoLandingPage() {
		waitForSec(2);
		verifyDisplayedElement(GetWebElement(_eltPageTitle), "Title");
		verifyDisplayedElement(GetWebElement(_eltShoppingCartIcon), "Shopping cart icon");
		verifyDisplayedElement(GetWebElement(_eltProductsFilter), "Products filter");
	}
	
	public static void clickToCartIcon() {
		WebElement eltCart = GetWebElement(_eltShoppingCartIcon);
		eltCart.click();
		waitForSec(2);
	}
	
	public static void verifyCartLandingPage() {
		waitForSec(2);
		verifyDisplayedElement(GetWebElement(_eltPageTitle), "Your Cart");
		verifyDisplayedElement(GetWebElement(_eltBtnCheckout), "Checkout button");
		verifyDisplayedElement(GetWebElement(_eltRemove), "Remove button");
		verifyDisplayedElement(GetWebElement(_eltBtnContinueShopping), "Continue Shopping button");
	}
	
	public static void addCart(String value) {
		driver.findElement(By.xpath(String.format(ADD_BUTTON, value))).click();
		System.out.println(value + " is Clicked");
	}
	
	public static void verifyAddedCart(String value) {
		if (GetWebElement(String.format(REMOVE_BUTTON, value)).isDisplayed()) {
			System.out.println("Product " + value + " is on the cart, DONE");
		}
		else System.out.println("Product " + value + " isn't on the cart. FAIL");
	}
	//wait
	public static void waitForSec(int s)   {
		try {
			Thread.sleep(s*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void waitForLoadingObject(By elt) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(elt));
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", "D:\\Eclipse\\Workspace\\Testing\\chromedriver.exe");
		
		navigateToDemoPage();
		Login();
		//TC01
		System.out.println("--------------TC01-------------------");
		verifyDemoLandingPage();
		//TC02
		System.out.println("--------------TC02-------------------");
		verifyAddACart();
		//TC03
		System.out.println("--------------TC03-------------------");
		clickToCartIcon();
		verifyCartLandingPage();
		
		
		waitForSec(2);
		
		
		driver.close();
		driver.quit();
	}

}
