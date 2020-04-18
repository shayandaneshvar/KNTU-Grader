package ir.ac.kntu.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import java.io.Serializable;

public final class TestResultDTO implements Serializable {
    @CsvBindByName(column = "full name")
    @CsvBindByPosition(position = 0)
    private String id;

    @CsvBindByName(column = "score")
    @CsvBindByPosition(position = 1)
    private Float mark;

    @CsvBindByName(column = "tests run")
    @CsvBindByPosition(position = 2)
    private Integer testsRun;

    @CsvBindByName(column = "tests passed")
    @CsvBindByPosition(position = 3)
    private Integer testsPassed;

    @CsvBindByName(column = "tests failed")
    @CsvBindByPosition(position = 4)
    private Integer testsFailed;


    @CsvBindByName(column = "test errors")
    @CsvBindByPosition(position = 5)
    private Integer testErrors;

    @CsvBindByName(column = "tests skipped")
    @CsvBindByPosition(position = 6)
    private Integer testsSkipped;


    public TestResultDTO(String id, Integer testsPassed, Integer testsFailed,
                         Integer testsRun, Integer testErrors,
                         Integer testsSkipped, Float mark) {
        this.id = id;
        this.testsPassed = testsPassed;
        this.testsFailed = testsFailed;
        this.testsRun = testsRun;
        this.mark = mark;
        this.testErrors = testErrors;
        this.testsSkipped = testsSkipped;
    }

    public Integer getTestErrors() {
        return testErrors;
    }

    public Integer getTestsSkipped() {
        return testsSkipped;
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

    public TestResultDTO setTestErrors(Integer testErrors) {
        this.testErrors = testErrors;
        return this;
    }

    public TestResultDTO setTestsSkipped(Integer testsSkipped) {
        this.testsSkipped = testsSkipped;
        return this;
    }
}
