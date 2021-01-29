package com.ing.payments;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty"},
        strict = true,
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        features = "classpath:features")

public class PaymentExecutorTest {
    /***
     *
     */
}
