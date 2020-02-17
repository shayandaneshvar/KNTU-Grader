package ir.ac.kntu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestingRegex {
    @Test
    public void testOutput() {
        String text = new String("fja;kjfjlk;adjdl Tests run: 12, Failures: 50, " +
                "Errors: 10, Skipped: 210 fklasf;lkjfaj");
        Pattern pattern = Pattern.compile("Tests run: \\d+, Failures: \\d+, Errors: " +
                "\\d+, Skipped: \\d+");
        Matcher matcher = pattern.matcher(text);
        String result = "";
        if (matcher.find()) {
            result = matcher.group(0);
        }
        String[] parts = result.split(",");
        String firstPart = parts[0].substring(parts[0].indexOf("Tests run: ") + 11);
        String secondPart = parts[1].substring(parts[0].indexOf("Failures: ") + 12);
        String thirdPart = parts[2].substring(parts[0].indexOf("Errors: ") + 10);
        String fourthPart = parts[3].substring(parts[0].indexOf("Skipped: ") + 11);
        Integer testsRun = Integer.parseInt(firstPart);
        Integer failures = Integer.parseInt(secondPart);
        Integer errors = Integer.parseInt(thirdPart);
        Integer skipped = Integer.parseInt(fourthPart);
        System.out.println(testsRun);
        System.out.println(secondPart);
        System.out.println(thirdPart);
        System.out.println(fourthPart);
        Assertions.assertEquals(testsRun,12);
        Assertions.assertEquals(failures,50);
        Assertions.assertEquals(errors,10);
        Assertions.assertEquals(skipped,210);
    }
    @Test
    public void testingMultiple(){
        String text = "fja;kjfjlk;adjdl Tests run: 12, Failures: 50, Errors: 10, Skipped: 210 fklasf;lkjfaj " +
                "Tests run: 206, Failures: 0, Errors: 0, Skipped: 0";
        Pattern pattern = Pattern.compile("Tests run: \\d+, Failures: \\d+, Errors: " +
                "\\d+, Skipped: \\d+");
        Matcher matcher = pattern.matcher(text);
        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
        System.out.println(matcher.find());
    }
}
