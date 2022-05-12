package net.Ey_EBC_Nexus_REPORT;


import com.relevantcodes.extentreports.ExtentReports;
import COMMON_COMPONENTS.SeleniumMethods;

//OB: ExtentReports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager extends SeleniumMethods {

    private static ExtentReports extent;

    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            extent = new ExtentReports(workingDir+"\\Report\\CareDiscovery"+dateFunc()+".html", true);
        }
        return extent;
    }
}
