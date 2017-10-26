package pl.java.traits;

import java.util.function.Function;

public class Main {

    private static HOF hof = new HOF();

    public static void main(String[] args) {
        higherOrderFunction();
        currying();
    }

    private static void currying() {
        System.out.println("1000 kilometers is equal to " + Currying.convertKmToMi.applyAsDouble(1000) + " miles");
        System.out.println("30 celsius is equal to " + Currying.convertCtoF.applyAsDouble(30) + " fahrenheit");
        System.out.println("30 usd is equal to " + Currying.convertUSDtoGBP.applyAsDouble(30) + " gbp");
    }

    private static void higherOrderFunction() {
        String applied = hof.addHeader.apply("applied");
        System.out.println(applied);

        Function<String, String> hof = Main.hof.addHeader.andThen(Main.hof.addFooter);
        System.out.println(hof.apply("row"));
    }
}
