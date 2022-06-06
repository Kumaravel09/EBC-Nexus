package net.Ey_EBC_Nexus_PAGES;
import org.openqa.selenium.By;
public class CR_PageObjects 
		{
		
		public static By CasesTab = By.xpath("//div[text()='Cases']");
		public static By AddNewCase = By.xpath("//mat-icon[@mattooltip='Add New Case']");
		public static By CaseSource = By.xpath("//div[@class='mat-select-arrow ng-tns-c99-31']");
		public static By ListValues = By.xpath("//span[@class='mat-option-text']");
		public static By Client = By.xpath("//span[text()='Select Client']/parent::div/following-sibling::div/div");
		public static By LegalEntity = By.xpath("//span[text()='Select Legal Entity']/parent::div/following-sibling::div/div");
		public static By Package = By.xpath("//span[text()='Select Package']/parent::div/following-sibling::div/div");
		public static By OpenCalendar = By.xpath("//button[@aria-label='Open calendar'][1]");
		public static By TodayDate = By.xpath("//div[@class='mat-datetimepicker-calendar-body-cell-content mat-datetimepicker-calendar-body-today']");
		public static By CurrentHour = By.xpath("//div[@class='mat-datetimepicker-clock-hours active']/div[@class='mat-datetimepicker-clock-cell mat-datetimepicker-clock-cell-selected ng-star-inserted']");
		public static By CurrentMinute = By.xpath("//div[@class='mat-datetimepicker-clock-minutes active']/div[@class='mat-datetimepicker-clock-cell mat-datetimepicker-clock-cell-selected ng-star-inserted']");
		public static By DocumentName = By.xpath("//input[@placeholder='Document Name']");
		public static By CandidateUploadDocument = By.xpath("//button[text()='Browse & Upload']");
		public static By filebrowse = By.xpath("//mat-label[text()='Candidate Document(Only Pdf)']/following-sibling::app-file-upload/input[@value='Browse']");

		
		
		
		
		
		}