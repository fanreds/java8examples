package pl.java;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.java.asynchronous.ExternalService;
import pl.java.asynchronous.Product;

public class Main {

    private static Main main = new Main();
    private List<String> products = Arrays.asList("apples", "bananas", "oranges", "strawberries", "cherries", "unions", "grapefruits", "pomelos", "melons");
    private final Executor executor = Executors.newFixedThreadPool(Math.min(products.size(), 1000), r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });
    private ExternalService externalService = new ExternalService();

    public static void main(String[] args) {
//        Util.timeLog(e -> {
//            List<String> sequentiallyPrices = main.getSequentiallyPrices();
//            System.out.println(sequentiallyPrices);
//        });
        Util.timeLog(e -> {
            System.out.println("getParallelPrices");
            List<String> parallelPrices = main.getParallelPrices();
            System.out.println(parallelPrices);
        });
        Util.timeLog(e -> {
            System.out.println("getCompletableFuturePrices");
            List<String> futurePrices = main.getCompletableFuturePrices();
            System.out.println(futurePrices);
        });
        Util.timeLog(e -> {
            System.out.println("getCompletableFutureWithThreadPoolPrices");
            List<String> futurePrices = main.getCompletableFutureWithThreadPoolPrices();
            System.out.println(futurePrices);
        });
        Util.timeLog(e -> {
            System.out.println("getParallelPricesWithDiscount");
            List<String> futurePrices = main.getParallelPricesWithDiscount();
            System.out.println(futurePrices);
        });

        Util.timeLog(e -> {
            System.out.println("getCompletableFutureWithThreadPoolPricesWithDiscount");
            List<String> futurePrices = main.getCompletableFutureWithThreadPoolPricesWithDiscount();
            System.out.println(futurePrices);
        });
    }

    private List<String> getSequentiallyPrices() {
        return products.stream().map(product -> String.format("%s %.2f", product, externalService.getPrice(product))).collect(toList());
    }

    private List<String> getParallelPrices() {
        return products.parallelStream().map(product -> String.format("%s %.2f", product, externalService.getPrice(product))).collect(toList());
    }

    /**
     * For 9 products time for execute method is near 4 sec on 8 cores,
     * external service simulate request and wait 1 sec before return data,
     * 2s for get price (if will be 8 products, time is near 1 sec)
     * 2s for check discount
     */
    private List<String> getParallelPricesWithDiscount() {
        List<Product> productList = products.stream().map(Product::new).collect(toList());
        return productList.parallelStream()
                .map(product -> externalService.getPrice(product))
                .map(Util::computeCountByPrice)
                .map(product -> externalService.checkDiscount(product))
                .collect(toList());
    }

    private List<String> getCompletableFuturePrices() {
        List<CompletableFuture<String>> listOfFuture = products.stream().map(product -> CompletableFuture.supplyAsync(() -> String.format("%s %.2f", product, externalService.getPrice(product)))).collect(toList());
        return listOfFuture.stream().map(CompletableFuture::join).collect(toList());
    }

    /**
     * For 9 products time for execute method is near 2 sec on 8 cores,
     * external service simulate request and wait 1 sec before return data,
     * for get data is used custom executor
     * 1s for get price (if will be 8 products, time is the same, because is used thread pool)
     * 1s for check discount
     */
    private List<String> getCompletableFutureWithThreadPoolPricesWithDiscount() {
        List<Product> productList = products.stream().map(Product::new).collect(toList());
        List<CompletableFuture<String>> listOfFuture = productList.stream()
                .map(product -> CompletableFuture.supplyAsync(() -> externalService.getPrice(product), executor))
                .map(future -> future.thenApply(Util::computeCountByPrice))
                .map(future -> future.thenCompose(product -> CompletableFuture.supplyAsync(() -> externalService.checkDiscount(product), executor)))
                .collect(toList());
        return listOfFuture.stream().map(CompletableFuture::join).collect(toList());
    }

    private List<String> getCompletableFutureWithThreadPoolPrices() {
        List<CompletableFuture<String>> listOfFuture = products.stream().map(product -> CompletableFuture.supplyAsync(() -> String.format("%s %.2f", product, externalService.getPrice(product)), executor)).collect(toList());
        return listOfFuture.stream().map(CompletableFuture::join).collect(toList());
    }
}
