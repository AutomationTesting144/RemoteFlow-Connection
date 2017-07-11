package com.example.a310287808.ifttt_appium;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.android.AndroidDriver;


/**
 * Created by 310287808 on 5/30/2017.
 */

public class RemoteConnectionFlow  {
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;
    String whiteListString;
    String [] parts ;
    int counter=0;
    String iftttSubString;
    String url;

    public void remoteConnection(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

//Opening IFTTT app by clicking on different welcome screens
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("indicator")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.id("indicator")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Logging in with gmail account
        driver.findElement(By.id("continue_btn")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //Clicking on random pop ups
        driver.findElement(By.id("com.ifttt.ifttt:id/ok")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //clicking on search field to search for hue applet
        WebElement abc4 = driver.findElement(By.xpath("//android.widget.TextView[@bounds='[407,1775][493,1809]']"));
        abc4.click();
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).click();
        driver.findElement(By.id("com.ifttt.ifttt:id/boxed_edit_text")).sendKeys("hue" + "\n");
        // clicking on the searched applet
        driver.findElement(By.xpath("//android.widget.TextView[@text='Press a button to make your Hue lights color loop']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // switching on the applet
        driver.findElement(By.id("com.ifttt.ifttt:id/toggle_container")).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //clicking on the button to login to hue page
        driver.findElement(By.xpath("/*//*[@resource-id='com.ifttt.ifttt:id/positive_button']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //clicking and entering user's email
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@bounds='[140,844][1060,926]']")));
        driver.findElement(By.xpath("//android.widget.EditText[@bounds='[140,844][1060,926]']")).click();
        driver.findElement(By.xpath("//android.widget.EditText[@bounds='[140,844][1060,926]']")).clear();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//android.widget.EditText[@bounds='[140,844][1060,926]']")).sendKeys("ifttt1automation@gmail.com");
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@bounds='[140,844][1060,926]']")));
        //clicking and entering user's Password
        driver.findElement(By.xpath("//android.widget.EditText[@bounds='[140,1012][1060,1094]']")).click();
        driver.findElement(By.xpath("//android.widget.EditText[@bounds='[140,1012][1060,1094]']")).clear();
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@bounds='[140,1012][1060,1094]']")));
        driver.findElement(By.xpath("//android.widget.EditText[@bounds='[140,1012][1060,1094]']")).sendKeys("automation1"+"\n");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.LinearLayout[@bounds='[224,192][976,905]']")));
        // making the connection
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@bounds='[1088,64][1184,160]']")));
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        boolean success=driver.findElement(By.xpath("//android.widget.TextView[@text='Configure']")).isEnabled();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,176]']")).click();
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[16,48][128,176]']")).click();
        driver.navigate().back();
        driver.navigate().back();

        if(success==true){
            Status = "1";
            ActualResult = "Remote connection with Hue bridge is successful";
            Comments = "NA";
            ExpectedResult= "User should be able to make new bridge connection from IFTTT";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }
        else{
            Status = "0";
            ActualResult = "Remote connection with Hue bridge is not successful";
            Comments = "Fail: User is not able to make connection with the bridge";
            ExpectedResult= "User should be able to make new bridge connection from IFTTT";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);

        }
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
    }
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel(String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult
            ,String resultAPIVersion, String resultSWVersion) throws IOException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS aa");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber=workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell((short)0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell((short)1);
        r2c2.setCellValue("11");

        HSSFCell r2c3 = row2.createCell((short)2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell((short)3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell((short)4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell((short)5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell((short)6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =
                new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();


    }
}
