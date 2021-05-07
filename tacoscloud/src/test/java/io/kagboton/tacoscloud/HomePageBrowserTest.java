package io.kagboton.tacoscloud;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    @LocalServerPort
    private int port;
    private static WebDriver browser;


    @Before
    public void setUp(){
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void teardown(){
        browser.quit();
    }

    @Test
    public void testHomePage(){
        String homePage = "http://localhost:" + port;

        browser.get(homePage);

        String titleText = browser.getTitle();
        Assert.assertEquals("Tacos Cloud", titleText);

        String h1Text = browser.findElement(By.tagName("h1")).getText();
        Assert.assertEquals("Welcome to...", h1Text);

        String imgSrc = browser.findElement(By.tagName("img")).getAttribute("src");
        Assert.assertEquals(homePage + "/images/TacoCloud.png", imgSrc);
    }


}
