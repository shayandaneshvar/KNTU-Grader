package ir.ac.kntu;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

    @Disabled
public class TestingExecutorInvocation {

    @Test
    public void testingExecutor() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Callable<Integer> callable = () -> "Hellasdasdo asdfgdas".length() * 10;
        Callable<Integer> callable1 = () ->
                IntStream.iterate(0, x -> x + 1).limit(1000000000).sum();
        Callable<Integer> callable2 = () -> "Hello asdasdasdfgdas".length() * 10;
        Callable<Integer> callable3 = () -> "Hello asdfgdassadas".length() * 10;
        List<Future<Integer>> res = service.invokeAll(Arrays.asList(callable,
                callable1, callable2, callable3));
        res.stream().map(x -> {
            if (x.isDone()) {
                try {
                    return x.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }).forEach(System.out::println);
    }
}
