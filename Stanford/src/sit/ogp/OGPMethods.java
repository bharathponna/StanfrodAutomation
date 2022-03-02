package sit.ogp;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OGPMethods extends Run {
	
	public void stanfordLogin() throws Exception
	{
		
		getUserNamePassword();
		eo.launchBrowser("Chrome", OGPURL);
		
		
		eo.waitForElementVisible(properties.getProperty("OGPheader"),"OGP Header ", 5);

		eo.EnterText(properties.getProperty("userNameTextBox"), userName, " User Name ");

		eo.EnterText(properties.getProperty("passwordTexxtBox"), password, " Pass Word " );


		eo.clickElement(properties.getProperty("loginButton"), " Login Button ");

		eo.waitForElementVisible(properties.getProperty("headerTwoStepVerification")," Two Step Header ", 5);

		eo.switchFrameswithID("duo_iframe");

		eo.clickElement(properties.getProperty("callmebutton")," Call me Button");

		driver.switchTo().defaultContent();
		Thread.sleep(5000);

	}
	
	
	@SuppressWarnings("deprecation")
	public void createPledge() throws Exception
	{
		try {

			//Thread.sleep(5000);

			eo.waitForElementVisible(properties.getProperty("headerGiftProcessing"),"OGP Gift Process Header ", 10);

			eo.waitForElementClickable(properties.getProperty("createPledgeButton"),"createPledgeButton ", 10);

			eo.clickElement(properties.getProperty("createPledgeButton"), " Create Pledge Button ");

			eo.waitForElementVisible(properties.getProperty("pledgeDate"),"GiftDate ", 10);

			eo.EnterText(properties.getProperty("pledgeDate"), giftDate, " GiftDate " );

			eo.EnterText(properties.getProperty("pledgeAmount"), pledgeAmount, " Pledge Amount " );

			eo.clickElement(properties.getProperty("saveButton"), " Save Button ");

			eo.waitForElementVisible(properties.getProperty("suidTextBox"),"suid Text Box ", 10);

			eo.EnterText(properties.getProperty("suidTextBox"), suid, " suidTextBox " );
			
			driver.findElement(By.xpath(properties.getProperty("suidTextBox"))).sendKeys(Keys.ENTER);

			Thread.sleep(5000);

			eo.EnterText(properties.getProperty("awardNumber"), awardNumber, " awardNumber " );
			
			driver.findElement(By.xpath(properties.getProperty("awardNumber"))).sendKeys(Keys.ENTER);

			eo.waitForElementVisible(properties.getProperty("donorName"),"donor Name ", 10);

			Thread.sleep(5000);

			eo.clickElement(properties.getProperty("saveButton"), " Save Button ");

			eo.waitForElementVisible(properties.getProperty("suidTextBox"),"suid Text Box ", 10);
			//replace xpath
			eo.waitForElementVisible("//div[@class='a-GV-w-scroll']","div for schedule ", 10);

			WebElement toScrollSchedule = driver.findElement(By.xpath("//div[@class='a-GV-w-scroll']"));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].offsetWidth", toScrollSchedule);

			eo.waitForElementClickable(properties.getProperty("scheduleIcon"),"scheduleIcon ", 10);

			eo.clickElement(properties.getProperty("scheduleIcon"), " scheduleIcon ");

			eo.waitForElementVisible(properties.getProperty("schedulerDialog"),"scheduler Dialog ", 5);

			eo.switchFrameswithXpath(properties.getProperty("schedulerIframe"));

			eo.waitForElementVisible(properties.getProperty("PayfrequencyDropDown"),"PayfrequencyDropDown ", 5);

			eo.selectDropDown(properties.getProperty("PayfrequencyDropDown"), "Monthly", "PayfrequencyDropDown");

			eo.EnterText(properties.getProperty("payentAmount"), payentAmount, " payentAmount " );

			eo.EnterText(properties.getProperty("FirstPaymentDate"), giftDate, " First Payment Date " );

			eo.clickElement(properties.getProperty("scheduleButton"), " scheduleButton ");

			Thread.sleep(9000);

			eo.waitForElementClickable(properties.getProperty("saveScheduleButton"), " saveScheduleButton ", 10);

			eo.clickElement(properties.getProperty("saveScheduleButton"), " saveScheduleButton ");	

			driver.switchTo().defaultContent();

			eo.clickElement(properties.getProperty("closeScheduler"), " closeScheduler ");

			eo.clickElement(properties.getProperty("saveButton"), " Save Button ");

			receiptNumber=driver.findElement(By.xpath(properties.getProperty("receiptNumber"))).getText();

			System.out.println("Pledge creation is complete for SUID "+suid+" and receipit number is " +receiptNumber);
		}catch(Exception e)
		{
			eo.takeScreenShot();
			System.out.println("Failed to create Pledge");
			throw new Exception(e.getMessage());
		}

	}
	
	
	

	public void sendForEvalOGP() throws Exception
	{
		try {

			WebElement toScrollSchedule = driver.findElement(By.xpath(properties.getProperty("sideColumnDiv")));

			((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", toScrollSchedule);

			eo.waitForElementClickable(properties.getProperty("saveAndEvalButton"), "saveAndEvalButton", 5);

			eo.clickElement(properties.getProperty("saveAndEvalButton"), "saveAndEvalButton");

			eo.waitForElementClickable(properties.getProperty("okButton"), "saveAndEvalButton", 5);

			eo.clickElement(properties.getProperty("okButton"), "okButton");

			System.out.println("Save and Eval Done");

		}catch(Exception e)
		{
			eo.takeScreenShot();
			System.out.println("Could not save and send the pledge for Eval " +e.getMessage());
			throw new Exception(e.getMessage());
		}


	}

	public void addJointDonor() throws Exception
	{
		try {
			
			Thread.sleep(5000);
						
			eo.clickElement(properties.getProperty("addJointDonor"), "addJointDonor");

			Thread.sleep(5000);

			eo.switchFrameswithXpath(properties.getProperty("jointDonoriFrame"));

			Thread.sleep(3000);

			if(!driver.findElement(By.xpath(properties.getProperty("noJointDonotFoundText"))).isDisplayed())
			{
				eo.waitForElementVisible(properties.getProperty("jointDonorName"),"jointDonorName", 20);

				String JointDonrName=driver.findElement(By.xpath(properties.getProperty("jointDonorName"))).getText();

				eo.clickElement(properties.getProperty("jointDonorConfirmButton"), "jointDonorConfirmButton");

				eo.waitForElementVisible(properties.getProperty("confirmJointDonor").replace("{TEXT}", JointDonrName),"jointDonorName", 20);

				if(driver.findElement(By.xpath(properties.getProperty("confirmJointDonor").replace("{TEXT}", JointDonrName))).isDisplayed())
				{
					System.out.println("Joint Donor is added as expected"+JointDonrName);
				}
			}else
			{
				driver.switchTo().defaultContent();
				System.out.println("No joint donot associated with the SUID "+suid);
				driver.findElement(By.xpath(properties.getProperty("closeJointDonor"))).click();
			}
		}catch(Exception e)
		{
			eo.takeScreenShot();
			System.out.println("Failed to add joint donor");
			throw new Exception(e.getMessage());
		}


	}
	
	public void assignPledge() throws Exception
	{
		try {
			Thread.sleep(5000);
			eo.waitForElementClickable(properties.getProperty("assignButton"), "assignButton", 5);

			eo.clickElement(properties.getProperty("assignButton"), "assignButton");

			eo.waitForElementClickable(properties.getProperty("assignDialog"), "assignDialog", 5);

			eo.switchFrameswithXpath(properties.getProperty("assigneeFrame"));

			eo.waitForElementClickable(properties.getProperty("assigneeNameDropDown"), "assigneeNameDropDown", 5);

			eo.clickElement(properties.getProperty("assigneeNameDropDown"), "assigneeNameDropDown");
			
			//pass from excel sheet
			
			String assigneeName="bharathp";
			
			Thread.sleep(4000);
			
			 driver.switchTo().defaultContent();
			
			 eo.EnterText(properties.getProperty("assigneeNameTextBox"), assigneeName, "assigneeNameTextBox");
			
			driver.findElement(By.xpath(properties.getProperty("assigneeNameTextBox"))).sendKeys(Keys.ENTER);
			 
			 
			eo.switchFrameswithXpath(properties.getProperty("assigneeFrame"));

			eo.clickElement(properties.getProperty("submitAssigneeButton"), "submitAssigneeButton");
			 
			
			 Thread.sleep(5000);
			 driver.switchTo().defaultContent();
			 
			 eo.clickElement(properties.getProperty("saveAndSubmitButton"), "saveAndSubmitButton");
			 
			 Thread.sleep(3000);
			 
			 
			
		}catch(Exception e)
		{
			System.out.println("Failed to Assign the Pledge");
		}

	}
	
	public void createManualDeposit() throws Exception
	{
		int depositAmount=200;
		
		eo.waitForElementVisible(properties.getProperty("createManualDepositButton"),"createManualDepositButton", 10);
		
		eo.clickElement(properties.getProperty("createManualDepositButton"), "createManualDepositButton");
		
		eo.waitForElementVisible(properties.getProperty("depositHeader"), "depositHeader", 8);
		String depositRef=getDateAsString("yyMMddHHmm");
		
		eo.EnterText(properties.getProperty("depositReferrencetextBox"), "auto"+depositRef , "depositReferrencetextBox");
		
		eo.EnterText(properties.getProperty("depostDateTextBox"), getDateAsString("MM/dd/yyyy") , "depostDateTextBox");
		
		eo.clickElement(properties.getProperty("createPledgeButton"), "createButton");
		
		Thread.sleep(9000);
		
		eo.clickElement(properties.getProperty("addNewGiftButton"), "addNewGiftButton");
		
		eo.waitForElementVisible(properties.getProperty("newGiftHeader"), "newGiftHeader", 5);
		
		eo.EnterText(properties.getProperty("amountTextBox"), String.valueOf(depositAmount) , "amountTextBox");
		
		eo.clickElement(properties.getProperty("saveButton"), "saveButton");
		
		eo.waitForElementVisible(properties.getProperty("suidTextBox"), "suidTextBox", 8);
		
		//scrollToElement(properties.getProperty("suidTextBox"));
		
		eo.EnterText(properties.getProperty("suidTextBox"), "0000453662" , "suidTextBox");
		
		driver.findElement(By.xpath(properties.getProperty("suidTextBox"))).sendKeys(Keys.ENTER);
		
		Thread.sleep(3000);
		
		eo.clickElement(properties.getProperty("saveButton"), "saveButton");
		
		Thread.sleep(8000);
		
		//scrollToElement(properties.getProperty("associatePledgeButton"));
		
		eo.clickElement(properties.getProperty("associatePledgeButton"), "associatePledgeButton");
		
		eo.waitForElementVisible(properties.getProperty("associatedPledgeDialog"), "associatedPledgeDialog", 5);
		
		eo.switchFrameswithXpath(properties.getProperty("associatedPledageDialogFrame"));
		
		if(!eo.isElementPresent(properties.getProperty("nextButton")))
		{
			eo.clickElement(properties.getProperty("selectLinkForAssciatedPledge"), "selectLinkForAssciatedPledge");
			
		}
		Thread.sleep(4000);
		
		String tempText=driver.findElement(By.xpath("//div[@id='ui-id-4']/p")).getText();
		String tempOk=driver.findElement(By.xpath("//div[@role='alertdialog']//button[contains(@class,'confirm')]")).getText();
		
		if(eo.isElementPresent(properties.getProperty("okButtonOnconfirmation")))
		{
			eo.clickElement(properties.getProperty("okButtonOnconfirmation"), "okButtonOnconfirmation");
		}
		
		eo.scrollToElement(properties.getProperty("depositHeader"));
		
		eo.clickElement(properties.getProperty("backtoDeposit"), "backtoDeposit");
		
		eo.EnterText(properties.getProperty("countTextBox"), eo.getElementText(properties.getProperty("giftsTextBox")) , "countTextBox");
		
		eo.EnterText(properties.getProperty("controlAmount"), eo.getElementText(properties.getProperty("giftAmountTextBox")) , "controlAmount");
		
		eo.clickElement(properties.getProperty("saveButton"), "saveButton");
		
		eo.clickElement(properties.getProperty("searchIconTransactions"), "searchIconTransactions");
		
		eo.waitForElementVisible(properties.getProperty("giftTrasactionHeader"), "giftTrasactionHeader", 5);

		
	}

}
