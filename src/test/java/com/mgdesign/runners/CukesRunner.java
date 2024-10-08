package com.mgdesign.runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber.json",
                "html:target/default-html-reports",
                "rerun:target/rerun.txt"},
       // strict = false,
        features = "src/test/resources/features",
        glue = "com/mgdesign/step_definitions",
        dryRun = false,

           tags = "@all"

        //   tags = "@note"
        //  tags= "@weight"

)
public class CukesRunner {
}
