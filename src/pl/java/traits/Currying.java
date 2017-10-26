package pl.java.traits;

import java.util.function.DoubleUnaryOperator;

public class Currying {
    static DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
    static DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
    static DoubleUnaryOperator convertKmToMi = curriedConverter(0.6214, 0);

    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return x -> x * f + b;
    }
}
