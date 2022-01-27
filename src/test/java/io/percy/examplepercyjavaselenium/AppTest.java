package io.percy.examplepercyjavaselenium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.percy.selenium.Percy;


public class AppTest {
//    private static final String TEST_URL = "http://localhost:8000";
    private static ExecutorService serverExecutor;
    private static HttpServer server;
    private static WebDriver driver;
    private static Percy percy;
	String folderPath = System.getProperty("user.dir");
	String driverLocation = null;

    @BeforeEach
    public void startAppAndOpenBrowser() throws IOException {
        // Disable browser logs from being logged to stdout
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
        // Create a threadpool with 1 thread and run our server on it.
        serverExecutor = Executors.newFixedThreadPool(1);
        server = App.startServer(serverExecutor);
		driverLocation = folderPath + "/src/main/resources/geckodriver";
		System.out.print("\n-----LOCATION----" + driverLocation);
		System.setProperty("webdriver.gecko.driver", driverLocation);
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
        percy = new Percy(driver);
    }

    @AfterEach
    public void closeBrowser() {
        // Close our test browser.
        driver.quit();
        // Shutdown our server and make sure the threadpool also terminates.
        server.stop(1);
        serverExecutor.shutdownNow();
    }



    @Test
    public void letsYouCheckOffATodo() {
        driver.get("https://k8s.bsstag.com/");
        // Take a Percy snapshot specifying browser widths.
        percy.snapshot("homepage_staging", Arrays.asList(375,480,720,1280,1440,1920));
        driver.navigate().to("https://k8s.bsstag.com/pricing");
        // Take a Percy snapshot specifying browser widths.
        percy.snapshot("pricing_staging", Arrays.asList(375,480,720,1280,1440,1920));
        driver.navigate().to("https://k8s.bsstag.com/integrations/automate");
        // Take a Percy snapshot specifying browser widths.
        percy.snapshot("automate_staging", Arrays.asList(375,480,720,1280,1440,1920));
        driver.get("https://k8s.bsstag.com/docs");
        // Take a Percy snapshot specifying browser widths.
        percy.snapshot("docs_staging", Arrays.asList(375,480,720,1280,1440,1920));
    }
}
