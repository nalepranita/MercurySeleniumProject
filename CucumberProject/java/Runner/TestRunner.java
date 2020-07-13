package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
				features = {"src/test/resources/Features"},
				glue={"stepDefinitions"}, //the path of the step definition files
				plugin= {"pretty","json:target/report.json","html:target/HtmlReports"}, //to generate different types of reporting
				monochrome = true, //displa	y the console output in a proper readable format
				dryRun = true
				//to check the mapping is proper between feature file and step def file
				
		)

public class TestRunner {

}