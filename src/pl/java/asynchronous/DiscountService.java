package pl.java.asynchronous;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import pl.java.Util;

public class DiscountService extends AbstractService {


    /**
     * For 9 products time for execute method is near 2 sec on 8 cores,
     * external service simulate request and wait 1 sec before return data,
     * for get data is used custom executor
     * 1s for get price (if will be 8 products, time is the same, because is used thread pool)
     * 1s for check discount
     */
    public List<String> getCompletableFutureWithThreadPoolPricesWithDiscount() {
        List<CompletableFuture<String>> listOfFuture = getStreamFutureWithExecutorOfPricesWithDiscount()
                .collect(toList());
        return listOfFuture.stream().map(CompletableFuture::join).collect(toList());
    }

    private Stream<CompletableFuture<String>> getStreamFutureWithExecutorOfPricesWithDiscount() {
        List<Product> productList = products.stream().map(Product::new).collect(toList());
        return productList.stream()
                .map(product -> CompletableFuture.supplyAsync(() -> externalService.getPrice(product), executor))
                .map(future -> future.thenApply(Util::computeCountByPrice))
                .map(future -> future.thenCompose(product -> CompletableFuture.supplyAsync(() -> externalService.checkDiscount(product), executor)));
    }

    /**
     * For 9 products time for execute method is near 4 sec on 8 cores,
     * external service simulate request and wait 1 sec before return data,
     * 2s for get price (if will be 8 products, time is near 1 sec)
     * 2s for check discount
     */
    public List<String> getParallelPricesWithDiscount() {
        List<Product> productList = products.stream().map(Product::new).collect(toList());
        return productList.parallelStream()
                .map(product -> externalService.getPrice(product))
                .map(Util::computeCountByPrice)
                .map(product -> externalService.checkDiscount(product))
                .collect(toList());
    }
}
