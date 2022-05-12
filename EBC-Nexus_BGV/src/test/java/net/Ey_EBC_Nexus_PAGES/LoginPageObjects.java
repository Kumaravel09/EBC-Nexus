package net.Ey_EBC_Nexus_PAGES;

import org.openqa.selenium.By;

import COMMON_COMPONENTS.SeleniumMethods;

public class LoginPageObjects extends SeleniumMethods {
			
	public static By Username = By.xpath("//input[@formcontrolname='userName']");
	public static By Password = By.xpath("//input[@formcontrolname='password']");
	public static By SignIn = By.xpath("//button[text()='Log In']");

	
	public void Login() throws Exception{
		type(LoginPageObjects.Username,"cr1");
		type(LoginPageObjects.Password,"password");
		click(LoginPageObjects.SignIn , "SignIn Button");
		}
	}
