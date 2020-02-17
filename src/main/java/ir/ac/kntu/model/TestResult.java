package ir.ac.kntu.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TestResult {
    private String id;
    private final String fullResult;
    private Integer testsPassed = 0;
    private Integer testsFailed = 0;
    private Integer testsRun = 0;
    private boolean extracted;
    private static final Pattern pattern;
    private float mark;
    private final Integer maxScore;

    static {
        pattern = Pattern.compile("Tests run: \\d+, Failures: \\d+, Errors: " +
                "\\d+, Skipped: \\d+");
    }

    public TestResult(String id, String fullResult, int maxScore) {
        this.id = id;
        this.fullResult = fullResult;
        this.maxScore = maxScore;
    }

    public float getMark() {
        return mark;
    }

    public void extractInfo() {
        if (!extracted) {
            extracted = true;
            Matcher matcher = pattern.matcher(fullResult);
            String result = "";
            while (matcher.find()) {
                result = matcher.group(0);
                try {
                    String[] parts = result.split(",");
                    String firstPart = parts[0].substring(parts[0].indexOf("Tests run: ") + 11);
                    String secondPart = parts[1].substring(parts[0].indexOf("Failures: ") + 12);
                    testsRun += Integer.parseInt(firstPart);
                    testsFailed += Integer.parseInt(secondPart);
                    testsPassed = testsRun - testsFailed;
                    mark = (testsPassed / (float) testsRun) * maxScore;
                } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException ex) {
                    System.out.println(ex.getMessage());
                    testsRun = 0;
                    testsFailed = 0;
                    mark = 0;
                    testsPassed = 0;
                }
            }
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

    public String toString() {
        return "TestResult(id=" + this.getId() + ", testsPassed=" +
                this.getTestsPassed() + ", testsFailed=" + this.getTestsFailed()
                + ", testsRun=" + this.getTestsRun() + ", extracted=" +
                this.extracted + ", fullResult=" + this.getFullResult() + ")";
    }
}
