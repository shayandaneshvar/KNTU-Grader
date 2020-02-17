package ir.ac.kntu.services;

@FunctionalInterface
public interface Converter<IN, OUT> {
    OUT convert(IN in);
}
