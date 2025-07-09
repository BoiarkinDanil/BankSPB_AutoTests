package org.example.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.example.steps",
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-html-report.html", "json:target/cucumber-reports/cucumber.json"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        monochrome = true
        // strict = true // <--- УДАЛИТЕ ЭТУ СТРОКУ
        // tags = "@LoginTest"
)
public class CucumberRunner {
    // Этот класс остается пустым.
}