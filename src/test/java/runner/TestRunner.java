package runner;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")  // path to src/test/resources/features
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "stepdefs") // this must match your steps package
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class TestRunner {
}
