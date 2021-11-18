package com.browserstack.examples.stepdefs;

import org.openqa.selenium.WebDriver;

import com.browserstack.examples.core.WebDriverFactory;
import com.browserstack.examples.core.WebDriverManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
import org.testng.annotations.Listeners;
import io.cucumber.junit.CucumberOptions;

@CucumberOptions()
@Listeners(MyTestResultListener.class)
public abstract class AbstractBaseSteps {

    private WebDriverManager webDriverManager;
    private WebDriver webDriver;

    public AbstractBaseSteps() {
        this.webDriverManager = WebDriverManager.getInstance();
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            this.webDriver = webDriverManager.getWebDriver();
        }
        return this.webDriver;
    }

    public String getTestEndpoint() {
        return WebDriverFactory.getInstance().getTestEndpoint();
    }
}
