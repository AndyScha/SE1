package application;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Class to for JavaStream API assignment with methods:
 * - filteredNames()				Aufgabe 2.)
 * - sortedNames(int limit)		Aufgabe 3.)
 * - sortedNamesByLength()			Aufgabe 4.)
 * - priceCalculations()			Aufgabe 5.)
 * - randomCodes(int count)		Aufgabe 6.)
 *
 * @author sgra64
 */
public class PlayStreams {
    //
    final static List<String> names = List.of(
            "Hendricks", "Raymond", "Pena", "Gonzalez", "Nielsen", "Hamilton",
            "Graham", "Gill", "Vance", "Howe", "Ray", "Talley", "Brock", "Hall",
            "Gomez", "Bernard", "Witt", "Joyner", "Rutledge", "Petty", "Strong",
            "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
            "Casey", "Buckner", "Hardin", "Marquez", "Navarro"
    );

    /**
     * Public main() function.
     *
     * @param args arguments passed from command line.
     */
    public static void main(String[] args) {
        var appInstance = new PlayStreams();
        appInstance.run();
    }

    void run() {
        System.out.print("PlayStreams: ");
        //
        randomNumbers();    // Beispiel 1
        rangeNumbers();        // Beispiel 2
        //
        // Aufgabe 2.)
        filteredNames().forEach(n -> System.out.print(n + ", "));
        //
        // Aufgabe 3.)
        sortedNames(8).forEach(n -> System.out.print(n + ", "));
        //
        // Aufgabe 4.)
        sortedNamesByLength().stream().forEach(System.out::println);
        //
        // Aufgabe 5.)
        List<Integer> prices = Arrays.asList(199, 249, 49, 999, 1499, 1999, 789);
        List<Integer> units = Arrays.asList(2, 7, 4, 2, 5, 2, 6);
        int total = priceCalculations(prices, units);
        System.out.print("total value: " + total);
        //
        // Aufgabe 6.)
        randomCodes(10).forEach(rn -> System.out.print(rn + ", "));    // Aufgabe 6.)
    }


    /**
     * Example of a stream of even, random numbers.
     */
    void randomNumbers() {
        System.out.println("\n--- randomNumbers ---");
        Random rand = new Random();
        Stream<Integer> stream = Stream.generate(    // generator for random Integer objects [0, 99]
                () -> Integer.valueOf(rand.nextInt(100))).limit(10);        // stream is limited to 10 Integer objects
        //
        // java.util.function.Predicate<Integer> evenNumbers = (n) -> (n % 2 == 0);
        stream.filter(n -> n % 2 == 0)        // remove odd numbers from Stream
                .sorted((n1, n2) -> Integer.compare(n1, n2))    // sort in descending order
                .forEach(n -> System.out.print(n + ", "));        // consume and print
    }


    /**
     * Example of an IntStream that generates a range of numbers [0..9] that
     * are divisible by 3, ordered and printed.
     */
    void rangeNumbers() {
        System.out.println("\n--- rangeNumbers ---");
        int sum = IntStream.range(0, 10)    // generate numbers 0..9
                .boxed()                        // convert IntStream to Stream<Integer>
                .filter(n -> n % 3 == 0)        // pass only numbers divisible by 3
                .sorted((n1, n2) ->                // sort remaining numbers in descending order
                        Integer.compare(n2, n1)).map(i -> {                        // print remaining number in sorted order
                    System.out.print(i + ", ");
                    return i;                    // pass same number i on
                }).reduce(0, (res, next) -> res + next);    // reduce stream to sum of remaining numbers
        //
        System.out.print("sum: " + sum + "\n");
    }


    /*
     * Bitte implementieren Sie hier die Methoden für Aufgaben 2.) bis 6.)
     */

    /**
     * Aufgabe 2.)
     * <p>
     * Return list of names that end with "ez".
     *
     * @return list of names ending with "ez"
     */
    List<String> filteredNames() {
        System.out.println("\n--- filteredNames ---");
        // create Stream from list, filter and collect() in List
        return names.stream().filter(s -> s.toString().endsWith("ez")).collect(Collectors.toList());
    }


    /**
     * Aufgabe 3.)
     * <p>
     * Return alphabetically sorted list of names.
     *
     * @param limit limits number of returned names
     * @return sorted list of names
     */
    List<String> sortedNames(int limit) {
        System.out.println("\n--- sortedNames ---");
        //
        if (limit < 0) {
            limit = 0;
            System.out.println("Set limit to positive values only.");
        }
        return names.stream().sorted(String::compareTo).limit(limit).collect(Collectors.toList());
    }


    /**
     * Aufgabe 4.)
     * <p>
     * Return list of names that is sorted by length and sorted alphabetically
     * for names of same length.
     *
     * @return sorted list of names
     */
    List<String> sortedNamesByLength() {
        System.out.println("\n--- sortedNamesByLength ---");
        //
        return names.stream()
                .sorted(Comparator.comparing(String::length).thenComparing(String::compareTo))
                .collect(Collectors.toList());
    }


    /**
     * Aufgabe 5.)
     * <p>
     * Calculate value from input lists with: price[i] * unit[i], print each pair [i]
     * and return compound total value.
     *
     * @param prices list of prices[i]
     * @param units  corresponding list of units[i]
     * @return compound total value of all pairs price[i] * unit[i]
     */
    int priceCalculations(final List<Integer> prices, final List<Integer> units) {
        System.out.println("\n--- priceCalculations ---");
        //
        int totalValue = IntStream.range(0, Math.min(prices.size(), units.size())).map(i -> {
            int total = prices.get(i) * units.get(i);
            String s = String.format(" - %4d * %4d = %4d", prices.get(i), units.get(i), total);
            System.out.println(s);
            return total;
        }).reduce(0, (res, next) -> res + next);
        //
        return totalValue;
    }


    /**
     * Aufgabe 6.)
     * <p>
     * Generate list of six-digit, even random numbers in ascending order with
     * prefix "X", examples: "X120198", "X197980", "X277152".
     *
     * @param count limit of numbers returned
     * @return list of random numbers
     * @throws IllegalArgumentException when count is negative
     */
    List<String> randomCodes(int count) {
        System.out.println("\n--- randomNumbers ---");
        int min = 100000;    // [min, max)
        int max = 1000000;    // inclusive min, exclusive max
        if (count < 0) {
            throw new IllegalArgumentException("count negative");
        }
        // source: https://www.baeldung.com/java-generating-random-numbers-in-range
        List<String> result = Stream.generate(() -> (int) ((Math.random() * (max - min)) + min))
                .filter(i -> i % 2 == 0 && i > 0)
                .limit(count)
                .sorted(Integer::compareTo)
                .map(i -> "X" + i).collect(Collectors.toList());
        //
        return result;
    }
}
