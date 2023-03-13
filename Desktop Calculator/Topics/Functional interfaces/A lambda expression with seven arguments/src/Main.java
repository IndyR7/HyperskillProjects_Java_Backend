class Seven {
    public static SeptenaryStringFunction fun = str -> String.join("", str).toUpperCase();
}

@FunctionalInterface
interface SeptenaryStringFunction {
    String apply(String... str);
}