package lam;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({lam.TestListener.class})
public class LoginTabValidation {

	public static WebDriver driver;

	@Test
	public void testLoginFieldValidation() throws AWTException, InterruptedException {
		driver=new FirefoxDriver();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try{
			driver.get("https://lamvcc.com/Sites/Tahoe/BI/_layouts/15/KPIDashboard/KPIDashboard.aspx#/tileview");
		}catch(Exception e){
			enterCredentialsInHTTPAuthentication("vcc\\ms1", "Gspann123+");  
		}

		List<WebElement> allTiles= driver.findElements(By.xpath("//div[@class='ng-scope kpi-tiles']"));
		Assert.assertTrue(allTiles.size()>0, "Tiles are not present on UI");		
	}

	@AfterMethod
	public void afterTest(){
		driver.quit();
	}

	public void enterCredentialsInHTTPAuthentication(String username,String password) throws AWTException, InterruptedException{
		Thread.sleep(1000);
		StringSelection selec= new StringSelection(username);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selec, selec);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(1000);
		selec= new StringSelection(password);
		clipboard.setContents(selec, selec);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_TAB);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
	}

	public void waitForElementPresent(By by) throws InterruptedException{
		for(int i=0;i<=20;i++){
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

	public static void takeScreenshot(WebDriver driver) throws IOException{
		System.out.println("Taking screenshot");
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String fileName = "LAM_AUTOMATION_"+getDateTime();
		FileUtils.copyFile(file, new File(System.getProperty("user.dir")+"/"+fileName+".png"));
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
