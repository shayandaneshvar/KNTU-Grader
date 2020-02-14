package ir.ac.kntu.services;

@FunctionalInterface
public interface Provider<IN, OUT> {
    OUT provide(IN input);
}
