package pl.java.asynchronous;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class AbstractService {
    protected List<String> products = Arrays.asList("apples", "bananas", "oranges", "strawberries", "cherries", "unions", "grapefruits", "pomelos", "melons");
    protected final Executor executor = Executors.newFixedThreadPool(Math.min(products.size(), 1000), r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });
    protected ExternalService externalService = new ExternalService();

}
