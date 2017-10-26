package pl.java.lazy;

public class Main {
    static LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n + 1));
    }

    public static void main(String[] args) {
        LazyList<Integer> numbers = from(1);
        System.out.println(numbers.tail().tail().head());
        System.out.println(numbers.tail().head());
    }
}
