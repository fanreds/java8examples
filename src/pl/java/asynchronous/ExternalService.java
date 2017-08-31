package pl.java.asynchronous;

import pl.java.Util;

public class ExternalService {
    private static final String FORMAT = "%s count=%d price=%.2f discount=%d";

    public Product getPrice(Product product) {
        product.setPrice(getPrice(product.getName()));
        return product;
    }

    public Double getPrice(String product) {
        Util.delayOneSec();
        return Util.random.nextDouble() * product.length();
    }

    public String checkDiscount(Product product) {
        Util.delayOneSec();
        if (product.getName().length() > product.getCount()) {
            return String.format(FORMAT, product.getName(), product.getCount(), product.getPrice(), Util.random.nextInt(50));
        }
        return String.format(FORMAT, product.getName(), product.getCount(), product.getPrice(), 0);
    }
}
