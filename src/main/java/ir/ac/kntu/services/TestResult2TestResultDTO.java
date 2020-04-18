package ir.ac.kntu.services;

import ir.ac.kntu.model.TestResult;
import ir.ac.kntu.model.TestResultDTO;

import java.util.Optional;

public class TestResult2TestResultDTO implements Converter<TestResult, TestResultDTO> {
    private static final TestResult2TestResultDTO INSTANCE =
            new TestResult2TestResultDTO();

    private TestResult2TestResultDTO() {
    }

    public static TestResult2TestResultDTO getInstance() {
        return INSTANCE;
    }

    @Override
    public TestResultDTO convert(TestResult testResult) {
        TestResultDTO dto = new TestResultDTO();
        Optional.ofNullable(testResult).ifPresent(x ->
                dto
                        .setId(x.getId())
                        .setMark(x.getMark())
                        .setTestsRun(x.getTestsRun())
                        .setTestsFailed(x.getTestsFailed())
                        .setTestsPassed(x.getTestsPassed())
                        .setTestErrors(x.getTestErrors())
                        .setTestsSkipped(x.getTestsSkipped()));
        return dto;
    }
}
