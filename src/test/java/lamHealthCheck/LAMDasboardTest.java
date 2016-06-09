package lamHealthCheck;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({lamHealthCheck.TestListener.class})
public class LAMDasboardTest {

	public static WebDriver driver;
	static String driverPath = "src/driver/IEDriverServer.exe";

	@Test
	public void testLoginFieldValidation() throws AWTException, InterruptedException {
		//driver=new FirefoxDriver();
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.
				INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		System.setProperty("webdriver.ie.driver", driverPath);
		capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
		driver = new InternetExplorerDriver(capabilities);
		//driver.manage().timeouts().pageLoadTimeout(2000, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		Thread.sleep(2000);

		try{
			((JavascriptExecutor)driver).executeScript("window.focus()");
			driver.get("https://lamvcc.com/Sites/Tahoe/BI/_layouts/15/KPIDashboard/KPIDashboard.aspx#/tileview");
			Thread.sleep(3000);
			//((JavascriptExecutor)driver).executeScript("window.focus()");
			enterCredentialsInHTTPAuthentication("vcc\\ms1", "Gspann123+");
		}catch(Exception e){
			System.out.println("In catch");
			enterCredentialsInHTTPAuthentication("vcc\\ms1", "Gspann123+");  
		}

		Thread.sleep(5000);
		//validate all tiles in dash-board displays properly?
		waitForElementPresent(By.xpath("//section[@class='kpi-content-section']/div"));
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//section[@class='kpi-content-section']/div"))));
		Thread.sleep(3500);
		List<WebElement> allTiles= driver.findElements(By.xpath("//section[@class='kpi-content-section']/div"));
		Assert.assertTrue(allTiles.size()==12, "Total no. of tiles present on UI are not 12");  

	}
	@AfterMethod
	public void afterTest(){
		driver.quit();
	}

	public void enterCredentialsInHTTPAuthentication(String username,String password) throws AWTException, InterruptedException{
		Thread.sleep(2000);
		Robot robot = new Robot();
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyPress(KeyEvent.VK_M);
		Thread.sleep(300);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_M);
		Thread.sleep(300);
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyPress(KeyEvent.VK_1);
		Thread.sleep(300);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_1);

		StringSelection selec= new StringSelection(username);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selec, null);
		Thread.sleep(7000);		
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(1000);
		selec= new StringSelection(password);
		clipboard.setContents(selec, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		Thread.sleep(1000);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
	}

	public void waitForElementPresent(By by) throws InterruptedException{
		for(int i=0;i<=10;i++){
			try{
				driver.findElement(by);
				break;
			}catch(Exception e){
				Thread.sleep(1000);
				System.out.println("WAITING......");
				continue;
			} 
		}

	}

	/**
	 * Returns current Date Time
	 * 
	 * @return
	 */
	public static String getDateTime() {
		String sDateTime = "";
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			String strTime = sdfTime.format(now);
			strTime = strTime.replace(":", "-");
			sDateTime = "D" + strDate + "_T" + strTime;
		} catch (Exception e) {
			System.err.println(e);
		}
		return sDateTime;
	}
}
