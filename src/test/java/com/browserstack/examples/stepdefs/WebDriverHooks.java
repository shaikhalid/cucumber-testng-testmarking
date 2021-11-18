package com.browserstack.examples.stepdefs;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.TestListenerAdapter;
import org.testng.ITestResult;

import com.browserstack.examples.core.WebDriverFactory;
import com.browserstack.examples.core.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;


/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverHooks{

    private static final String TEST_STATUS_SCRIPT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}";
    @After
    public void afterScenario(Scenario scenario) {
        String status = "passed";
        String reason = "Scenario Passed";
        if (scenario.isFailed()) {
            status = "failed";
            reason = "Scenario failed";
        }
        // System.out.print(scenario.exception.message);
        markAndCloseWebDriver(status, reason);
    }

    private void markAndCloseWebDriver(String status, String reason) {
        WebDriver webDriver = WebDriverManager.getInstance().getWebDriver();
        if (webDriver == null) {
            return;
        }
        try {
            // if (webDriver instanceof RemoteWebDriver && WebDriverFactory.getInstance().isCloudDriver()) {
            //     ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, status, reason));
            // }
        } finally {
            WebDriverManager.getInstance().quitDriver();
        }
    }




}
