package pl.java;

import java.util.Random;
import java.util.function.Consumer;

import pl.java.asynchronous.Product;

public class Util {

    public final static Random random = new Random();

    public static void delayOneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void delayRandom() {
        try {
            Thread.sleep(500 + random.nextInt(2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void timeLog(Consumer<Long> consumer) {
        long startTime = System.currentTimeMillis();
        consumer.accept(startTime);
        long endTime = System.currentTimeMillis();
        System.out.println("time elapsed is " + (endTime - startTime) + " ms");
    }

    public static Product computeCountByPrice(Product product) {
        Double price = product.getPrice();
        if (price < 1) {
            product.setCount(30);
        } else if (price < 2) {
            product.setCount(20);
        } else if (price < 3) {
            product.setCount(10);
        } else {
            product.setCount(5);
        }
        return product;
    }

}
