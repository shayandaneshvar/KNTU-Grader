package ir.ac.kntu;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestingDelete {
    @Test
    public void testSimpleDelete() throws IOException {
        Path pathToBeDeleted = Paths.get("C:\\Users\\moein\\IdeaProjects\\KNTU Grader\\src\\test\\java\\ir\\ac\\kntu\\asd");
        delete(pathToBeDeleted);
    }

    @Test
    public void testFindDirectories() throws IOException, URISyntaxException {
        File file = new File("src\\test\\java\\ir\\ac\\kntu\\add");
        List<File> directories = Arrays.asList(file.listFiles(File::isDirectory));
        for (File f : directories) {
            delete(Paths.get(f.getAbsolutePath() /*+ "/testing"*/));
        }
    }

    @Test
    public void testCopyDirectories() throws IOException, URISyntaxException {
        File file = new File("src\\test\\java\\ir\\ac\\kntu\\add");
        List<File> directories = Arrays.asList(file.listFiles(File::isDirectory));
        for (File f : directories) {
            copyDirectory(Paths.get(f.getAbsolutePath()),
                    Paths.get("src\\test\\java\\ir\\ac\\kntu\\desti/testy"));
        }
    }

    private void delete(Path path) throws IOException {
        if (path.toFile().exists()) {
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private void copyDirectory(Path srcPath, Path destination) throws IOException {
        FileUtils.copyDirectory(srcPath.toFile(), destination.toFile());
    }
}
