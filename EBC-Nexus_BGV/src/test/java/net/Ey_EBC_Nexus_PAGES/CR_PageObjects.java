package net.Ey_EBC_Nexus_PAGES;
import org.openqa.selenium.By;
public class CR_PageObjects 
		{
		
		public static By AdhocModule = By.xpath("//span[text()='Ad Hoc']");
		public static By CreateANewReport = By.xpath("//a[@class='thickbox saveButton multilineButton']");
		public static By IFrame = By.id("TB_iframeContent");
		public static By Iframeheader = By.xpath("(//div[@class='popupTitle']/text())[2])");
		public static By IframePackage = By.xpath("//input[@id='name']");
		public static By IframeContinueButton = By.xpath("//a[text()='Continue']");
		public static By IframeContinueButton1 = By.xpath("//a[@class='continue']");
		public static By SizeWarningContinue = By.xpath("//div[@class='continue']");
		public static By IframeHeaderSelectAPopulation = By.xpath("(//div[@class='selectPopulationTitle']/text())[2]");
		public static By IframeTopic = By.xpath("(//select[@class='filterItems columnSelect'])[2]");

}