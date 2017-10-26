package pl.java.traits;

import java.util.function.Function;

public class HOF {
    Function<String, String> addHeader = arg -> "header " + arg;
    Function<String, String> addFooter = arg -> arg + " footer";
}
