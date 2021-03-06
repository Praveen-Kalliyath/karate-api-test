package eon.qa.runner;

import org.junit.jupiter.api.Test;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import eon.qa.testutils.CommonUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;

public class ParallelTestRunner {
	/**
	 * Run All Features In Parallel
	 */
	@Test
	public void runAllInParallel() {
		System.setProperty("karate.env", "test");
		// Runs all features with all tags
		Results results = Runner.path("classpath:").parallel(5);
		generateReport(results.getReportDir());
//		assertTrue(results.getErrorMessages(), results.getFailCount() == 0);
	}

	/**
	 * Run Specific Feature In Parallel With Tags
	 */
	@Test
	public void runSpecificFeatureInParallel() {
		System.setProperty("karate.env", "test");
		// Run Specific feature with specific tag
		Results results = Runner.path("classpath:sample.feature").tags("@javasample").parallel(5);
		generateReport(results.getReportDir());
//		assertTrue(results.getErrorMessages(), results.getFailCount() == 0);
	}

	
	/**
	 * Generate Cucumber Report
	 * 
	 * @param karateOutputPath
	 */
	@SuppressWarnings("unchecked")
	public static void generateReport(String karateOutputPath) {
		Collection<File> jsonFiles = FileUtils.listFiles(new File(karateOutputPath), new String[] { "json" }, true);
		@SuppressWarnings("rawtypes")
		List<String> jsonPaths = new ArrayList(jsonFiles.size());
		jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
		Configuration config = new Configuration(new File("target"), "demo");
		ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
		reportBuilder.generateReports();
	}
}
