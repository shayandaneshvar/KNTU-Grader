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
    private String id;
    private int maxScore;

    public static MavenTestInvokerProvider prepareInstance(String projectFolder,
                                                           String id,int maxScore) {
        INSTANCE.path = projectFolder + "/pom.xml";
        INSTANCE.id = id;
        INSTANCE.maxScore = maxScore;
        return INSTANCE;
    }

    private MavenTestInvokerProvider() {
    }

    @Override
    public Callable<TestResult> provide(String... input) {
        return new MavenInvokerCallable(path, id,maxScore, input);
    }

    private static class MavenInvokerCallable implements Callable<TestResult> {
        private InvocationOutputHandler handler;
        private InvocationRequest request;
        private Invoker invoker;
        private StringBuilder result;
        private String id;
        private int maxScore;

        MavenInvokerCallable(String path, String id,int maxScore, String... params) {
            this.id = id;
            this.maxScore = maxScore;
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
            TestResult testResult = new TestResult(id, result.toString(),maxScore);
            testResult.extractInfo();
            System.out.println(testResult);
            return testResult;
        }

        public String toString() {
            return "MavenTestInvokerProvider.MavenInvokerCallable(handler=" +
                    this.handler + ", request=" + this.request + ", invoker=" +
                    this.invoker + ", result=" + this.result + ", id=" +
                    this.id + ")";
        }
    }
}
