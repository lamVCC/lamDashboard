package lamHealthCheck;
import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.ie.InternetExplorerDriver;
	import org.openqa.selenium.remote.CapabilityType;
	import org.openqa.selenium.remote.DesiredCapabilities;
	import org.openqa.selenium.JavascriptExecutor;
public class LAMTest {
	
	 
	 static WebDriver driver;
	 public static void main(String[] args) throws InterruptedException {
	  DesiredCapabilities caps = DesiredCapabilities.internetExplorer();   
	  caps.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
	  caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
	  caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
	  caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	//  caps.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
	  caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	  caps.setJavascriptEnabled(true); 
	  System.setProperty("webdriver.ie.driver", "C:\\Users\\admin\\Desktop\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
	        driver=new InternetExplorerDriver(caps); 
	  driver.manage().window().maximize();
	  Thread.sleep(2500);
	  driver.get("https://lamvcc.com/sites/Tahoe/BI/_layouts/15/VccLam.L2Dashboard/SuccessCriteriaDashboard.aspx");
	  Thread.sleep(2500);
	  ((JavascriptExecutor)driver).executeScript("window.focus();");
	  try {
	   Runtime.getRuntime().exec("C:\\Users\\admin\\Downloads\\WindowsSecurity\\WindowsSecurity.exe");
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  System.out.println("++end++");
	 }
	}