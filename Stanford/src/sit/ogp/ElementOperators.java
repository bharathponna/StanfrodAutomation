package sit.ogp;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class ElementOperators extends Run {


	public void takeScreenShot() throws Exception
	{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String fileName=getDateAsString("yyyyMMddHHmmss");
		FileUtils.copyFile(scrFile, new File("ScreenShots\\"+fileName+".png"));

	}
	public void selectDropDown(String xpath, String visibleText, String name) throws Exception
	{
		try {
			dropDown= new Select(driver.findElement(By.xpath(xpath)));
			dropDown.selectByVisibleText(visibleText);
		}catch(Exception e)
		{
			System.out.println("Failed to select"+visibleText+" from drop down "+name);
			throw new Exception(e.getMessage());
		}


	}


	public void switchFrameswithID(String frameID) throws Exception
	{
		try {
			driver.switchTo().frame(frameID);
			System.out.println("Switched to Iframe "+frameID);
		}catch (Exception e)
		{
			System.out.println("Failed to Switch to iFrame");
			throw new Exception(e.getMessage());
		}


	}

	public void switchFrameswithXpath(String xpath) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.xpath(xpath));
			driver.switchTo().frame(ele);
			System.out.println("Switched to Iframe "+xpath);
		}catch (Exception e)
		{
			System.out.println("Failed to Switch to iFrame");
			throw new Exception(e.getMessage());
		}


	}

	@SuppressWarnings("deprecation")
	public void waitForElementVisible(String xpath, String name, int seconds) throws Exception
	{
		try {
			int milliSeconds=seconds*1000;
			Thread.sleep(milliSeconds);
			wait=new WebDriverWait(driver, seconds);
			WebElement ele=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			System.out.println("<b>"+name+ "</b> Element found ");
		}catch(Exception e)
		{
			System.out.println("Could not find element after waiting for"+seconds+" "+e.getMessage());
		}

	}



	public void waitForElementClickable(String xpath, String name, int seconds) throws Exception
	{
		try {
			int milliSeconds=seconds*1000;
			Thread.sleep(milliSeconds);
			wait=new WebDriverWait(driver, seconds);
			WebElement ele=wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			System.out.println("<b>"+name+ "</b> Element found ");
		}catch(Exception e)
		{
			System.out.println("Could not find element after wait " +e.getMessage());

		}

	}

	public static void waitForElementInvisible(String xpath, String name, int seconds) throws Exception
	{
		try {
			int milliSeconds=seconds*1000;
			Thread.sleep(milliSeconds);
			wait=new WebDriverWait(driver, seconds);
			boolean flag=wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
			if(flag)
			{
				waitForElementInvisible(xpath, name, seconds);
			}
		}catch(Exception e)
		{
			System.out.println("Could not find element after wait " +e.getMessage());

		}

	}

	public static String getElementText(String xpath) throws Exception
	{
		String text=null;
		try {
			WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			text=element.getText();

		}catch(Exception e)
		{
			System.out.println("Failed to get text"+e.getMessage());
			throw new Exception(e.getMessage());
		}
		return text;

	}


	public void EnterText(String xpath, String text, String name) throws Exception
	{
		try {
			WebElement element=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			element.clear();
			element.sendKeys(text);
			if(name.toLowerCase().contains("password"))
			{
				//code to encrypt password or user specific data
				text="**********";
			}
			System.out.println("Text "+text+" entered in element "+name);
		}catch(Exception e)
		{
			System.out.println("Failed to enter text in text box"+e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	public void clickElement(String xpath, String name) throws Exception
	{
		try {
			WebElement element=driver.findElement(By.xpath(xpath));
			element.click();
			System.out.println("Clicked on "+name);
		}catch(Exception e)
		{
			System.out.println("Failed to click the element "+e.getMessage());
			throw new Exception(e.getMessage());
		}

	}

	public boolean isElementPresent(String xpath)
	{
		boolean flag=false;
		try {
			if(!driver.findElement(By.xpath(xpath)).isDisplayed())
			{
				flag=true;
			}

		}catch(Exception e)
		{
			flag=false;
		}
		return flag;

	}



	public void scrollToElement(String xpath) throws Exception
	{
		WebElement element = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

	}


	public void launchBrowser(String browser, String url) throws Exception
	{
		try {
			if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver","WebDriver\\chromedriver.exe");
				driver= new ChromeDriver();
			}
			driver.get(url);
			driver.manage().window().maximize();

		}catch(Exception e)
		{
			System.out.println("Failed to launch browser" +e.getMessage());
			throw new Exception();
		}

	}

}
