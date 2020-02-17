package ir.ac.kntu.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ir.ac.kntu.model.TestResultDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

public final class CSVWriteUtil {
    private CSVWriteUtil() {
    }

    public static <T> void writeAll(Path path, List<T> list) throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        System.err.println(path.toFile().toString() + "\n\n\n\n\n");
        Writer writer = new FileWriter(path.toFile().toString());
        StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder(writer)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
        sbc.write(list);
        writer.close();
    }
}
