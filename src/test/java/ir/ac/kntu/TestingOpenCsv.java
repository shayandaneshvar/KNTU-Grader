package ir.ac.kntu;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ir.ac.kntu.model.TestResultDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
@Disabled
public class TestingOpenCsv {

    @Test
    public void write() throws IOException {
        String[] array = {"1", "2", "3"};
        String[] array2 = {"11", "22", "33"};
        String[] array3 = {"111", "222", "333"};
        String[] array4 = {"1111", "2222", "3333"};
        List<String[]> list = Arrays.asList(array, array2, array3, array4);
        Path path = Paths.get("C:\\Users\\TOP\\Desktop\\AP Assignments\\kntu-grader\\src\\test\\java\\ir\\ac\\kntu");
        CSVWriter csvWriter = new CSVWriter(new FileWriter(path.toString()));
        csvWriter.writeAll(list);

    }

    @Test
    public void writeBean() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        TestResultDTO dto = new TestResultDTO()
                .setId("ali")
                .setTestsRun(10)
                .setTestsPassed(8)
                .setTestsFailed(2)
                .setMark(0.8f);

        TestResultDTO dto2 = new TestResultDTO()
                .setId("moein")
                .setTestsRun(10)
                .setTestsPassed(5)
                .setTestsFailed(5)
                .setMark(0.5f);

        List<TestResultDTO> list = Arrays.asList(dto,dto2);

        Path path = Paths.get("C:\\Users\\TOP\\Desktop\\AP " +
                        "Assignments\\kntu-grader\\src\\test\\java\\ir\\ac\\kntu\\marks" +
                ".csv");

        Writer writer = new FileWriter(path.toString());
        StatefulBeanToCsv sbc = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();

        sbc.write(list);
        writer.close();
    }
}
