package COMMON_COMPONENTS;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadAFile {
	
	static String URL;
	static String USERNAME;
	static String PASSWORD;
	public static Properties prop = new Properties();
	static InputStream input = null;
	
	public static String getUrl()
	{
 		return URL;
 	}
 	
 	public static void setUrl(String url){
 		URL = url;
 	}
 	
 	public static String getUsername()
	{
 		return USERNAME;
 	}
 	
 	public static void setUsername(String username){
 		USERNAME = username;
 	}
	
 	
 	public static String getPassword(){
	 return PASSWORD;
 	}
 	
 	public static void setPassword(String password){
 		PASSWORD = password;
 	}
 	
 	 	
	
	public static void ConfigFile() {
		try{
			input = new FileInputStream(System.getProperty("user.dir") + "/resource/Cred.properties");
			// load a properties file
			prop.load(input);
			setUrl(prop.getProperty("Url"));
			setUsername(prop.getProperty("cr1"));
			setPassword(prop.getProperty("password"));
			
		  } catch (IOException ex) {
				ex.printStackTrace();
		    }
		   
		}
}






