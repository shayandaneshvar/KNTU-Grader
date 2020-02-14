package ir.ac.kntu.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TestResult {
    private String id;
    private final String fullResult;
    private Integer testsPassed;
    private Integer testsFailed;
    private Integer testsRun;
    private boolean extracted;
    private static final Pattern pattern;

    static {
        pattern = Pattern.compile("Tests run: \\d+, Failures: \\d+, Errors: " +
                "\\d+, Skipped: \\d+");
    }

    public TestResult(String id, String fullResult) {
        this.fullResult = fullResult;
    }


    public void extractInfo() {
        if (!extracted) {
            extracted = true;
            Matcher matcher = pattern.matcher(fullResult);
            String result = "";
            if (matcher.find()) {
                result = matcher.group(0);
            }
            String[] parts = result.split(",");
            String firstPart = parts[0].substring(parts[0].indexOf("Tests run: ") + 11);
            String secondPart = parts[1].substring(parts[0].indexOf("Failures: ") + 12);
            testsRun = Integer.parseInt(firstPart);
            testsFailed = Integer.parseInt(secondPart);
            testsPassed = testsRun - testsFailed;
        }
    }

    public String getFullResult() {
        return this.fullResult;
    }

    public Integer getTestsPassed() {
        return this.testsPassed;
    }

    public Integer getTestsFailed() {
        return this.testsFailed;
    }

    public Integer getTestsRun() {
        return this.testsRun;
    }

    public String getId() {
        return id;
    }
}
