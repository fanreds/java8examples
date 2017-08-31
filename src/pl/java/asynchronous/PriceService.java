package pl.java.asynchronous;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PriceService extends AbstractService {

    public List<String> getSequentiallyPrices() {
        return products.stream().map(product -> String.format("%s %.2f", product, externalService.getPrice(product))).collect(toList());
    }

    public List<String> getParallelPrices() {
        return products.parallelStream().map(product -> String.format("%s %.2f", product, externalService.getPrice(product))).collect(toList());
    }

    public List<String> getCompletableFuturePrices() {
        List<CompletableFuture<String>> listOfFuture = products.stream().map(product -> CompletableFuture.supplyAsync(() -> String.format("%s %.2f", product, externalService.getPrice(product)))).collect(toList());
        return listOfFuture.stream().map(CompletableFuture::join).collect(toList());
    }

    public List<String> getCompletableFutureWithThreadPoolPrices() {
        List<CompletableFuture<String>> listOfFuture = products.stream().map(product -> CompletableFuture.supplyAsync(() -> String.format("%s %.2f", product, externalService.getPrice(product)), executor)).collect(toList());
        return listOfFuture.stream().map(CompletableFuture::join).collect(toList());
    }
}
