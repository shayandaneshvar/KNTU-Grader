package ir.ac.kntu.services;

import ir.ac.kntu.model.TestResult;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class MavenTestInvokerProvider implements Provider<String[],
        Callable<TestResult>> {
    private static final MavenTestInvokerProvider INSTANCE =
            new MavenTestInvokerProvider();
    private String path;

    public static MavenTestInvokerProvider prepareInstance(String path) {
        INSTANCE.path = path;
        return INSTANCE;
    }

    private MavenTestInvokerProvider() {
    }


    @Override
    public Callable<TestResult> provide(String... input) {
        return new MavenInvokerCallable(path, input);
    }

    private static class MavenInvokerCallable implements Callable<TestResult> {
        private InvocationOutputHandler handler;
        private InvocationRequest request;
        private Invoker invoker;
        private StringBuilder result;

        MavenInvokerCallable(String path, String... params) {
            result = new StringBuilder();
            request = new DefaultInvocationRequest();
            request.setPomFile(new File(path));
            request.setGoals(Arrays.asList(params));
            handler = result::append;
            request.setOutputHandler(handler);
            invoker = new DefaultInvoker();
        }

        @Override
        public TestResult call() throws Exception {
            invoker.execute(request);
            TestResult testResult = new TestResult(result.toString());
            testResult.extractInfo();
            return testResult;
        }
    }
}
