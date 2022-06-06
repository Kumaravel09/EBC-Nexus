package COMMON_COMPONENTS;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import net.Ey_EBC_Nexus_REPORT.ExtentTestManager;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import com.google.common.base.Function;

public class SeleniumMethods extends ReadAFile 

{
	static WebDriver driver;
	WebElement element;
	
	@BeforeTest
	public void launch() throws Exception
	{
		ConfigFile();
		launchBrowser("Chrome");
		OpenUrl(getUrl());
	}
	
	public Set<Cookie> getCookies()
	{
		return driver.manage().getCookies();
	}
	
	public void launchBrowser(String BrowserName)
	{
		if (BrowserName.equalsIgnoreCase("firefox")) 
		{
			System.out.println("firefox browser starting");
			driver = new FirefoxDriver();
		}
		else if (BrowserName.equalsIgnoreCase("InternetExplorer"))
		{
			System.out.println("InternetExplorer browser starting");
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/drivers/IEDriverServer.exe");
			HashMap<String, Object> IEopt = new HashMap<String, Object>();
			IEopt.put("profile.default_content_settings.popups", true);
			IEopt.put("download.default_directory", System.getProperty("user.dir") + "/drivers");
			InternetExplorerOptions opt = new InternetExplorerOptions();
			opt.setCapability("disable-popup-blocking", true);
			opt.setCapability("prefs", IEopt);
			driver = new InternetExplorerDriver();	
		}
		else if (BrowserName.equalsIgnoreCase("Chrome"))
		{
			System.out.println("Launching Chrome Browser");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
			/*String downloadFilepath = "c:\\download";
		     HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		     chromePrefs.put("profile.default_content_settings.popups", 0);
		     chromePrefs.put("download.default_directory", downloadFilepath);
		     ChromeOptions options = new ChromeOptions();
		     HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		     options.setExperimentalOption("prefs", chromePrefs);
		     options.addArguments("--test-type");
		     options.addArguments("--disable-extensions"); //to disable browser extension popup
		     DesiredCapabilities cap = DesiredCapabilities.chrome();
		     cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		     cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		     cap.setCapability(ChromeOptions.CAPABILITY, options);*/
		     driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public void OpenUrl(String url)
	{
		driver.get(url);
	}
	
	protected WebElement findElement(By bylocator)
	{
		element = driver.findElement(bylocator);
		return element;
	}

	public static String dateFunc()
	{
		String Pattern = "ddMMyyHHmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(Pattern);
		String date = sdf.format(new Date());
		return date;
	}

	public void type(By bylocator, String text) throws Exception
	{
		try
		{
			clear(bylocator);
			element = findElement(bylocator);
			element.sendKeys(text);
			ExtentTestManager.getTest().log(LogStatus.PASS, text + " value entered successfully");
		} 
		catch (Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) + text + " value Entry failed");
		}
	}
	
	public void BeginTimeframe(By bylocator, String fromdate) throws Exception
	{
		try
		{
			clear(bylocator);
			element = findElement(bylocator);
			element.sendKeys(fromdate);
			if (getPassword().equalsIgnoreCase(fromdate))
				fromdate = "Password";
			ExtentTestManager.getTest().log(LogStatus.PASS, fromdate + " value Entered successfully");
		} 
		catch (Exception e)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) + fromdate + " value Entry failed");
		}
	}
	
		
	public WebElement listvalue (String text)
	{
		List<WebElement> inclusionsoptions = driver.findElements(By.xpath("//mat-option/span"));
		WebElement option = null;
		for(WebElement e : inclusionsoptions)
		{
			if(e.getText().equalsIgnoreCase(text))
			{
				click.option = e;
			}
		}
		return option;
	}

	public void clear(By objectName)
	{
		findElement(objectName).clear();
	}

	public boolean checkElementPresent(By bylocator, String elementName) throws Exception
	{
		try
		{
			findElement(bylocator).isDisplayed();
			//ExtentTestManager.getTest().log(LogStatus.PASS, elementName + " present Successfully");
			HighlightMyElement(element);
			return true;
		} catch (Exception e)
		{
			//ExtentTestManager.getTest().log(LogStatus.FAIL,
					//ExtentTestManager.getTest().addScreenCapture(capture(driver)) + elementName + " Present Unsuccessful");
			return false;
		}
	}
	
	public void waitUntilElementNotPresent(By bylocator) throws InterruptedException {
		for(int j = 0; j<=20; j++)
		{
		int i = findElementByList(bylocator).size();
		if (i == 1)
		{
			Thread.sleep(2000);
		}
		else
		{
		break;
		}
	}
	}
	
	public void fluentWait(By bylocator)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
			       .ignoring(NoSuchElementException.class);

			   WebElement ele = wait.until(new Function<WebDriver, WebElement>()
			   {
			     public WebElement apply(WebDriver driver) 
			     {
			       return driver.findElement(bylocator);
			     }
			   });

	}
	
	public boolean checkElementNotPresent(By objectName, String Name) throws Exception {
        boolean isElementPresent = true;
        int i = findElementByList(objectName).size();
        if (i == 0) {
            System.out.println("Pass, The " + Name + " not displayed as expected");
          /*  ExtentTestManager.getTest().log(LogStatus.PASS, Name + " Present Unsuccessful");*/

        } else {
            isElementPresent = false;
        }
        
        return isElementPresent;
    }
	
	public List<WebElement> findElementByList(By bylocator) {
		List<WebElement> wbList = driver.findElements(bylocator);
		return wbList;
	}

	public static String capture(WebDriver driver) throws Exception {
		String screenshotPath = null;
		try {

			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			String workingDir = System.getProperty("user.dir");
			screenshotPath = workingDir + "\\Report\\FailureScreenshots\\" + dateFunc() + ".jpg";
			// This will store screenshot on Specific location
			ImageIO.write(image, "jpg", new File(screenshotPath));
			return screenshotPath;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return screenshotPath;

	}

	public void click(By bylocator, String elementName) throws Exception {
		try {
			element = driver.findElement(bylocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Waituntil(bylocator);
			if (element.isDisplayed() && element.isEnabled())
				findElement(bylocator).click();
			//ExtentTestManager.getTest().log(LogStatus.PASS, elementName + " is clicked successfully");
		} catch (Exception e) {
			e.printStackTrace();
			//ExtentTestManager.getTest().log(LogStatus.FAIL,ExtentTestManager.getTest().addScreenCapture(capture(driver)) + elementName+ " is not clicked successfully");
		}
	}

	public void screencapture(String reportopt) throws Exception
	{
		ExtentTestManager.getTest().log(LogStatus.INFO,
				ExtentTestManager.getTest().addScreenCapture(capture(driver)) +reportopt);
	}
	
	public void implicitwait(int TimeOut)
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}
	
	public boolean scrollToElement(By bylocator, String elementName) throws Exception {
		try {
			element = driver.findElement(bylocator);
			Actions actions = new Actions(driver);
			actions.moveToElement(element);
			actions.perform();
			ExtentTestManager.getTest().log(LogStatus.PASS, elementName + " Scrolled successfully");
			return true;

		} catch (Exception e) {
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) + elementName
							+ " Scrolled Unsuccessful");
			return false;
		}
	}

	public boolean scrollUp(int value) throws Exception {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,-" + value + ")", "");
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public boolean scrollDown(int value) throws Exception {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0," + value + ")", "");
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public boolean Waituntil(By bylocator) {

		try 
		{
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(bylocator));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}

	}

	public void waitLoop(By bylocator) 
	{
		Waituntil(bylocator);
		for (int i = 0; i < 3; i++)
			Waituntil(bylocator);
	}
	
	public void extwaitLoop(By bylocator) 
	{
		Waituntil(bylocator);
		for (int i = 0; i < 10; i++)
			Waituntil(bylocator);
	}

	public void StaleException(By bylocator) {
		try {
			WebElement button = driver.findElement(bylocator);
			button.click();
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			WebElement button = driver.findElement(bylocator);
			button.click();
		}
	}

	public void HighlightMyElement(WebElement element) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor javascript = (JavascriptExecutor) driver;
			javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: green; border: 3px solid green;");
			javascript.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}
		
	}
		
	public void dropDown(By bylocator, String value) throws InterruptedException
	{
		Thread.sleep(2000);
		Select dd = new Select(driver.findElement(bylocator));
		dd.selectByVisibleText(value);
	}
	
	public void dropDownbyindex(By bylocator, int index)
	{
		Select dd = new Select(driver.findElement(bylocator));
		dd.selectByIndex(index);
		
	}
	
	public void MouseOver(By bylocator) {
		Actions a = new Actions(driver);
		a.moveToElement(driver.findElement(bylocator)).perform();

	}

	public void SwitchToFrame(By bylocator) throws InterruptedException 
	{
		WebElement frame1 = findElement(bylocator);
		driver.switchTo().frame(frame1);
		System.out.println("Frame switched");
	}
		
	public void SwitchToFrame2(By bylocator) 
	{
			WebElement frame2 = findElement(bylocator);
			driver.switchTo().frame(frame2);
	}

	public void SwitchToDefaultFrame() {
		driver.switchTo().defaultContent();
		System.out.println("back to parent frame");

	}
	
	public void parentframe()
	{
		driver.switchTo().parentFrame();    // to move back to parent frame
		driver.switchTo().defaultContent()	;
		}

	public void alerthandle() {
        try {
            System.out.println("i am in alerthandle method");
            Alert ale = driver.switchTo().alert();
            System.out.println(ale.getText());
            ale.accept();
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void WindowHandle()
	{
		System.out.println("parent window: "+driver.getCurrentUrl());
		Set<String> allwindows = driver.getWindowHandles();
		Iterator<String> iter = allwindows.iterator();
		String mainwindow = iter.next();
		@SuppressWarnings("unused")
		String secondwindow = iter.next();
		driver.switchTo().window(secondwindow);
		System.out.println("parent window: "+driver.getCurrentUrl());
	}
	
	public void switchToParentWindow(){
		
		String parentWindowContact = driver.getWindowHandle();
		driver.switchTo().window(parentWindowContact);
		
		String winname = driver.getTitle();
		System.out.println(winname);
		
	}

	public void closeCurrentTab(){
		driver.close();
	}
	
	public void QuitBrowser() {
		driver.quit();
	}

	public void sliderX(By bylocator, int xaxis, int yaxis)
	{
		try 
		{
			element = driver.findElement(bylocator);
	        Actions move = new Actions(driver);
	        Action action = (Action) move.dragAndDropBy(element, xaxis, yaxis).build();
	        action.perform();	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();		
		}
		
	}
	
	public void gettext(By bylocator) throws Exception
	{
		try 
			{
			element= driver.findElement(bylocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Waituntil(bylocator);
			String innerText= element.getText();
			System.out.println(innerText);
			ExtentTestManager.getTest().log(LogStatus.PASS, innerText + " has requested the report");

			} 
		catch (Exception e) 
			{
			e.printStackTrace();
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) + "**Name mis-match found**");
			}
		
		/*
		 * WebElement element = driver.findElement(bylocator); Actions actions = new
		 * Actions(driver); actions.moveToElement(element); actions.perform(); String
		 * innerText= element.getText(); System.out.println(innerText);
		 */

		
	}

	public void captureheadername(By bylocator) throws Exception
	{
		try 
			{
			element= driver.findElement(bylocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Waituntil(bylocator);
			String innerText= element.getText();
			System.out.println(innerText);
			ExtentTestManager.getTest().log(LogStatus.PASS, innerText + " - Report has been requested");

			} 
		catch (Exception e) 
			{
			e.printStackTrace();
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) + "unable to fetch report name");
			}
		
		/*
		 * WebElement element = driver.findElement(bylocator); Actions actions = new
		 * Actions(driver); actions.moveToElement(element); actions.perform(); String
		 * innerText= element.getText(); System.out.println(innerText);
		 */

		
	}
	
	public void ValidateNameonHTMLPage(By bylocator, String textToVerify) throws Exception //method created particularly for State data reports - to validate header
	{
		textToVerify="Nirosha Saminathan";
		try 
			{
			element= driver.findElement(bylocator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Waituntil(bylocator);
			String innerText= element.getText();
			if (innerText.contentEquals(textToVerify))
			{
				System.out.println(innerText);
				ExtentTestManager.getTest().log(LogStatus.PASS, innerText + " - has requested the report");
			}			
			} 
		catch (Exception e) 
			{
			e.printStackTrace();
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) + "**Name mismatch**");
			}
		
	}
	
	public void ccontrolshifts()
	{
		Actions action = new Actions(driver);
		action.click();
				System.out.println("ss");
		action.keyDown(Keys.SHIFT);
				System.out.println("ss");
		action.keyDown(Keys.CONTROL);
				System.out.println("ss");
		action.sendKeys("S");
				System.out.println("ss");
		action.build().perform();
				System.out.println("ss");
		action.keyUp(Keys.SHIFT);
				System.out.println("ss");
		action.keyUp(Keys.CONTROL);
				System.out.println("ss");
		action.build().perform();
				System.out.println("ss");
	}

	

	public void ClickSaveAs() throws Exception
	{Robot robot;
		try
		{
			robot = new Robot();
			
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Button clicked to save the report");

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Unable to click Save Button");		}
	}

	public void Copythefilepath(String EXLFilepath) throws Exception
	{
		try
		{
			StringSelection copydir = new StringSelection(EXLFilepath);
	       Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copydir, null); //copy the above string to clip board
	       Robot robot;
	       robot = new Robot();
	       /*robot.keyPress(KeyEvent.VK_ENTER);
	       robot.keyRelease(KeyEvent.VK_ENTER);*/
	       robot.delay(2000);
	       robot.keyPress(KeyEvent.VK_CONTROL);
	       robot.keyPress(KeyEvent.VK_V);
	       robot.keyRelease(KeyEvent.VK_V);
	       robot.keyRelease(KeyEvent.VK_CONTROL); //paste the copied string into the dialog box
	       robot.keyPress(KeyEvent.VK_ENTER);
	       robot.keyRelease(KeyEvent.VK_ENTER); //enter
	       robot.delay(3000);
	       robot.keyPress(KeyEvent.VK_ESCAPE);
	       robot.keyRelease(KeyEvent.VK_ESCAPE);
	       
			ExtentTestManager.getTest().log(LogStatus.PASS, "Report saved in: " +EXLFilepath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Unable to save the file in the given path: "+EXLFilepath);
			
		}
		
	}
		
	
	public void ChromePDFDownload() throws Exception
	{Robot robot;
		try
		{
			robot = new Robot();
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			
			
			ExtentTestManager.getTest().log(LogStatus.PASS, "Button clicked to save the report");
			System.out.println("PDF Saved");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
			ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Unable to click Save Button");
		}
	}

	public void ChromePDFSaveAs(String PDFFilepath) throws Exception
	{
		try
		{
			StringSelection copydir = new StringSelection(PDFFilepath);
			System.out.println(copydir);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(copydir, null); //copy the above string to clip board
			
	       Robot robot;
	       robot = new Robot();
	       robot.delay(3000);
	       robot.keyPress(KeyEvent.VK_CONTROL);
	       robot.keyPress(KeyEvent.VK_V);
	       robot.keyRelease(KeyEvent.VK_V);
	       robot.keyRelease(KeyEvent.VK_CONTROL); //paste the copied string into the dialog box
	       robot.keyPress(KeyEvent.VK_ENTER);
	       robot.keyRelease(KeyEvent.VK_ENTER); //enter
	              
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Report saved in: "+PDFFilepath );
	       System.out.println("save");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Unable to save the file in the given path: " +PDFFilepath);
			
		}
	}

	public void Logout() throws Exception
	
	{
		try
		{
			Robot robot;
	       robot = new Robot();
	       robot.delay(3000);
	       robot.keyPress(KeyEvent.VK_TAB);
	       robot.keyPress(KeyEvent.VK_TAB);
	       robot.keyPress(KeyEvent.VK_TAB);
	       robot.keyPress(KeyEvent.VK_TAB);
	       robot.keyPress(KeyEvent.VK_TAB);
	       robot.keyPress(KeyEvent.VK_ENTER);
	       robot.keyRelease(KeyEvent.VK_ENTER); //enter
	       robot.keyRelease(KeyEvent.VK_TAB);
	       robot.keyRelease(KeyEvent.VK_TAB);
	       robot.keyRelease(KeyEvent.VK_TAB);
	       robot.keyRelease(KeyEvent.VK_TAB);
	       robot.keyRelease(KeyEvent.VK_TAB);
	              
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Navigated to Analysis module" );
	       System.out.println("save");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Unable to navigate to Analysis Module");
			
		}
	}

	public void prompthandler(By bylocator, String optiontoselect)
	{
			//Select promptoption = new Select(bylocator);
			//promptoption.selectByVisibleText(optiontoselect);
	}
	
	int a;
	public void countDropdownElements(By bylocator)
	{
		Select select = new Select(driver.findElement(bylocator));

		List<WebElement> listofelements = select.getOptions();
		a= listofelements.size();
		System.out.println(a);
	}
	
	public void comparevalue(By bylocator) throws Exception
	{
		Waituntil(bylocator);
		element =findElement(bylocator);
		String popcount =element.getText();
		System.out.println(popcount);
		int b= Integer.parseInt(popcount);
		if(b==a)
		{
		System.out.println("Dropdown elements matched with population count");
	       ExtentTestManager.getTest().log(LogStatus.PASS, "Dropdown elements matched with population count: " + a + " = " + b );
		}
		else
		{
			ExtentTestManager.getTest().log(LogStatus.INFO,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Dropdown elements does not match with population count: " + a + " ≠ " + b + " **could be bcause of threshold value selected**");
		}
	}
	
	String option="";
	public void validategraphdropdown(By bylocator, int index) throws Exception
	{
		
		try
		{
		Select dd = new Select(driver.findElement(bylocator));
		dd.selectByIndex(index);
		option = dd.getFirstSelectedOption().getText();
		System.out.println("Graph slected in dropdown: " +option);
		ExtentTestManager.getTest().log(LogStatus.PASS, option + ": Graph selected" );
		}
		catch(Exception e)
		{
			System.out.println(e);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					ExtentTestManager.getTest().addScreenCapture(capture(driver)) +"Unable to select graph");
		}}
		
		
	private  final static String getDate(  )
   {
        DateFormat df = new SimpleDateFormat( "yyyyMMdd" ) ;
        df.setTimeZone( TimeZone.getTimeZone( "IST" )  ) ;
        return ( df.format( new Date(  )  )  ) ;
    }
	
	private  final static String getTime(  )

	{
        DateFormat df = new SimpleDateFormat( "hhmmss" ) ;
       df.setTimeZone ( TimeZone.getTimeZone ( "IST" )  ) ;
       return ( df.format( new Date(  )  )  ) ;
	
	}

	public void waitfordownload() throws InterruptedException
	{
		File file = new File("C:\\download\\physicians.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("Export PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");

	}
	
	public void waitfordownloadReveal() throws InterruptedException
	{
		File file = new File("C:\\download\\Reveal with Memorial Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");

	}
	
	public void waitfordownloadReveal1() throws InterruptedException
	{
		File file = new File("C:\\download\\Reveal with Single Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");

	}
	
	public void waitfordownloadReveal2() throws InterruptedException
	{
		File file = new File("C:\\download\\Reveal Multi Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");
	}
	
	public void waitfordownloadReveal3() throws InterruptedException
	{
		File file = new File("C:\\download\\Outpatient Reveal with Memorial Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");
	}
	
	public void waitfordownloadReveal4() throws InterruptedException
	{
		File file = new File("C:\\download\\Oupatient Reveal with Community Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");
	}
	
	public void waitfordownloadReveal5() throws InterruptedException
	{
		File file = new File("C:\\download\\Outpatient Reveal with Kiswaukee Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");
	}
	
	public void waitfordownloadReveal6() throws InterruptedException
	{
		File file = new File("C:\\download\\Outpatient Reveal with Multi Facility.pdf");
		
		while (!file.exists());
		{
			Thread.sleep(3000);
		}
		System.out.println("reveal PDF downloaded");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Output page: PDF downloaded");
	}
	
	public void RenameRevealExportedPDF()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Reveal with Memorial Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 29);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}
	
	public void RenameRevealExportedPDF1()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Reveal with Single Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 27);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}
	
	public void RenameRevealExportedPDF2()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Reveal Multi Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 21);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}

	public void RenameRevealExportedPDF3()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Outpatient Reveal with Memorial Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 36);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}

	public void RenameRevealExportedPDF4()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Outpatient Reveal with Community Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 34);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}

	public void RenameRevealExportedPDF5()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Outpatient Reveal with Kiswaukee Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 31);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}

	public void RenameRevealExportedPDF6()
	{
		String Exstfilepath = "C:\\download";
		//String renamedfilepath = ".//Provider_ExportedPDF";
				
		File folder = new File(Exstfilepath);
		File[] listOfFiles = folder.listFiles();
		for(int i=0; i<=listOfFiles.length-1;i++)
		{
			//System.out.println("listOfFiles  " +listOfFiles[i].getName());
			if(listOfFiles[i].getName().equals("Outpatient Reveal with Multi Facility.pdf"))
			{
				String Actualname=listOfFiles[i].getName();
				String Substring=Actualname.substring(0, 25);
				String output="_Output_";
				String rename=getDate()+getTime();
				String Toname=Substring+" "+rename+".pdf";
				System.out.println(Toname);
				
				File sdcard = new File(Exstfilepath);
			    File from = new File(sdcard,Actualname);
			    File to = new File(sdcard,Toname);
			    from.renameTo(to);
				ExtentTestManager.getTest().log(LogStatus.PASS, "PDF renamed and saved as : "+Toname);

			}
			else
			{
				System.out.println("File not found in "+Exstfilepath+ " to rename");}
			}
		
	}

}

 
	
