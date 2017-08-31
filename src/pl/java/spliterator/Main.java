package pl.java.spliterator;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    private static final String SENTENCE = "It is quite a task thinking up great made-up words that are unique, so I created this word generator " +
            "to help you come up with the best fake word ideas. They can be great for naming your website, business, product or project. Fake words or pseudowords " +
            "are words which look like they are real, but actually have no meaning.";

    public static void main(String[] args) {
        int i = countWordsIteratively(SENTENCE);
        System.out.println(i);

        Stream<Character> characterStream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
        System.out.println(countWords(characterStream));
    }


    private static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace)
                    counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    private static int countWords(Stream<Character> stream) {
        return stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine).getCounter();
    }
}
