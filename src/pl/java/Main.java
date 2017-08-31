package pl.java;

import java.util.List;

import pl.java.asynchronous.DiscountService;
import pl.java.asynchronous.PriceService;

public class Main {

    private static DiscountService discountService;
    private static PriceService priceService;

    static {
        discountService = new DiscountService();
        priceService = new PriceService();
    }

    public static void main(String[] args) {
//        Util.timeLog(e -> {
//            List<String> sequentiallyPrices = main.getSequentiallyPrices();
//            System.out.println(sequentiallyPrices);
//        });
        Util.timeLog(e -> {
            System.out.println("getParallelPrices");
            List<String> parallelPrices = priceService.getParallelPrices();
            System.out.println(parallelPrices);
        });
        Util.timeLog(e -> {
            System.out.println("getCompletableFuturePrices");
            List<String> futurePrices = priceService.getCompletableFuturePrices();
            System.out.println(futurePrices);
        });
        Util.timeLog(e -> {
            System.out.println("getCompletableFutureWithThreadPoolPrices");
            List<String> futurePrices = priceService.getCompletableFutureWithThreadPoolPrices();
            System.out.println(futurePrices);
        });
        Util.timeLog(e -> {
            System.out.println("getParallelPricesWithDiscount");
            List<String> futurePrices = discountService.getParallelPricesWithDiscount();
            System.out.println(futurePrices);
        });

        Util.timeLog(e -> {
            System.out.println("getCompletableFutureWithThreadPoolPricesWithDiscount");
            List<String> futurePrices = discountService.getCompletableFutureWithThreadPoolPricesWithDiscount();
            System.out.println(futurePrices);
        });
    }


}
