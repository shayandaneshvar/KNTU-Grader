package ir.ac.kntu.model;

import com.opencsv.bean.CsvBindByName;

public final class TestResultDTO {
    @CsvBindByName(column = "full name")
    private String id;

    @CsvBindByName(column = "tests passed")
    private Integer testsPassed;

    @CsvBindByName(column = "tests failed")
    private Integer testsFailed;

    @CsvBindByName(column = "tests run")
    private Integer testsRun;

    @CsvBindByName(column = "score")
    private Float mark;

    public TestResultDTO(String id, Integer testsPassed, Integer testsFailed, Integer testsRun, Float mark) {
        this.id = id;
        this.testsPassed = testsPassed;
        this.testsFailed = testsFailed;
        this.testsRun = testsRun;
        this.mark = mark;
    }

    public Float getMark() {
        return mark;
    }

    public TestResultDTO setMark(Float mark) {
        this.mark = mark;
        return this;
    }

    public TestResultDTO() {
    }

    public String getId() {
        return id;
    }

    public TestResultDTO setId(String id) {
        this.id = id;
        return this;
    }

    public Integer getTestsPassed() {
        return testsPassed;
    }

    public TestResultDTO setTestsPassed(Integer testsPassed) {
        this.testsPassed = testsPassed;
        return this;
    }

    public Integer getTestsFailed() {
        return testsFailed;
    }

    public TestResultDTO setTestsFailed(Integer testsFailed) {
        this.testsFailed = testsFailed;
        return this;
    }

    public Integer getTestsRun() {
        return testsRun;
    }

    public TestResultDTO setTestsRun(Integer testsRun) {
        this.testsRun = testsRun;
        return this;
    }
}
