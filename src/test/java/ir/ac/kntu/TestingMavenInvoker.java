package ir.ac.kntu;

import org.apache.maven.shared.invoker.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Disabled
public class TestingMavenInvoker {
    @Test
    public void testingInvoker() throws IOException, MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("pom.xml"));
        request.setGoals(Arrays.asList("clean", "test"));
        InvocationOutputHandler handler = s -> System.out.println(s);
        request.setOutputHandler(handler);
        Invoker invoker = new DefaultInvoker();
        InvocationResult result = invoker.execute(request);
    }
}
