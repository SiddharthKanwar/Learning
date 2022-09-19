package com.example.Learning;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Java2
{

    public static void main(String[] args)
    {

//		System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
//		WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        String baseUrl = "https://carviahomestay.listingfy.in/";
        String expectedTitle = "Welcome: Mercury Tours";
        String actualTitle = "";

        driver.get(baseUrl);
        actualTitle = driver.getTitle();

        if (actualTitle.contentEquals(expectedTitle))
        {
            System.out.println("Test Passed!");
        }
        else
        {
            System.out.println("Test Failed");
        }
        driver.close();

    }

}