package sit.ogp;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Run {

	static WebDriver driver =null;
	static WebDriverWait wait=null;
	static ElementOperators eo;
	static OGPMethods ogp;
	static Properties properties=null;
	static BufferedReader reader;
	static String OGPURL;
	static Select dropDown;
	static String suid;
	static String giftDate;
	static String pledgeAmount;
	static String awardNumber;
	static String payentAmount;
	static String receiptNumber;
	static List<Map<String,String>>testDataAllRows=null;
	private static JPanel pane;
	private static JFrame frame;
	private static JTextField usrName;
	private static JPasswordField pswrd;

	static String userName; 
	static String password;



	@SuppressWarnings("deprecation")
	public static void getUserNamePassword()
	{
		pane = new JPanel();
		pane.setLayout(new GridLayout(0,2,2,2));

		usrName=new JTextField();
		pswrd=new JPasswordField();

		pane.add(new JLabel("User Name"));
		pane.add(usrName);


		pane.add(new JLabel("Password"));
		pane.add(pswrd);

		int option=JOptionPane.showConfirmDialog(frame, pane, "Please enter StanFord user name and password", JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);

		if(option==0)
		{
			userName=usrName.getText();
			password=pswrd.getText();

		}
		else
		{
			System.exit(0);
		}

	}

	public static void setProperties() throws Exception
	{
		String propertyFilePath= "Utilities\\elementLocators.properties";
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e)
		{
			System.out.println("Failed to read properties file");
			throw new Exception(e.getMessage());
		}

	}

	public static String getDateAsString(String format)
	{
		String dateInFormat = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format); 
		LocalDateTime now = LocalDateTime.now();  
		dateInFormat=dtf.format(now).toString();
		return dateInFormat;

	}


	public static void main(String[] args) throws Exception {

		testDataAllRows=new ArrayList<Map<String,String>>();
		ReadFromExcel excelData=new ReadFromExcel();
		eo=new ElementOperators();
		ogp=new OGPMethods();

		testDataAllRows=excelData.getTestDataInMap();
		System.out.println(testDataAllRows);



		System.out.println("create new pledge on OGP"); 
		setProperties();
		OGPURL=properties.getProperty("OGPURL"); //Login to Stanford
		ogp.stanfordLogin();

		ogp.createManualDeposit();


		for(Map map:testDataAllRows)
		{
			try {
				giftDate=map.get("GiftDate").toString(); 
				pledgeAmount=map.get("PLEDGEAMOUNT").toString(); 
				suid=map.get("SUID").toString();
				awardNumber=map.get("AWARDNUMBER").toString(); 
				payentAmount=map.get("PAYMENTAMOUNT").toString();

				ogp.createPledge();

				ogp.addJointDonor();

				ogp.sendForEvalOGP();

				ogp.assignPledge();

				eo.clickElement(properties.getProperty("workBenchTab"), "workBenchTab");
				Thread.sleep(4000);


			}catch(Exception e)
			{
				System.out.println("Breaking loop for SUID" +suid);
				break;
			}

		}
		System.out.println("++++++++++++++++++++Ends ++++++++++++++++");
	}

}
