package com.example.Learning;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Java3
{

    public static void main(String[] args) throws InterruptedException
    {
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\siddh\\Downloads\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.opencart.com/index.php?route=account/register");

        WebElement drpCountryElement = driver.findElement(By.id("input-country"));
        Select drpCountry = new Select(drpCountryElement);

        drpCountry.selectByVisibleText("Denmark");
        //drpCountry.selectByValue("10");
       // drpCountry.selectByIndex(13);
    }
}







