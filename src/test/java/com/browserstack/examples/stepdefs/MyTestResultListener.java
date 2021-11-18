package com.browserstack.examples.stepdefs;

// import org.apache.http.NameValuePair;
// import org.apache.http.client.entity.UrlEncodedFormEntity;
// import org.apache.http.client.methods.HttpPut;
// import org.apache.http.impl.client.HttpClientBuilder;
// import org.apache.http.message.BasicNameValuePair;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

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

public class MyTestResultListener extends TestListenerAdapter {
    private static final String TEST_STATUS_SCRIPT = "browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}";
    @Override
    public void onTestFailure(ITestResult result) {

        String msg;
        

        String status = "passed";
        String reason = "Scenario Passed";
        
        if (null != result.getThrowable()) {
            reason = result.getThrowable().getMessage();
        }
        status = "failed";
            
        
        // System.out.print(scenario.exception.message);
        markAndCloseWebDriver(status, reason);

    }

    private void markAndCloseWebDriver(String status, String reason) {
        WebDriver webDriver = WebDriverManager.getInstance().getWebDriver();
        if (webDriver == null) {
            return;
        }
        try {
            if (webDriver instanceof RemoteWebDriver && WebDriverFactory.getInstance().isCloudDriver()) {
                ((JavascriptExecutor) webDriver).executeScript(String.format(TEST_STATUS_SCRIPT, status, reason));
            }
        } finally {
            WebDriverManager.getInstance().quitDriver();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // do what you want to do
    }
}