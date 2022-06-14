package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


public class PlayNumbers {
    private final int[] numbers = {-2, 4, 9, 4, -3, 4, 9, 5};
    private final int[] numbers_2 = {8, 10, 2, 14, 4};


    public static void main(String[] args) {
        var appInstance = new PlayNumbers();
        appInstance.run();
    }

    void run() {
        //
        System.out.println("numbers: " + Arrays.toString(numbers));

        // Tests für Aufgaben 1.) und 2.)
        int result = sum(numbers);
        System.out.println(String.format(" - sum(numbers) -> %d", result));
        //
        test0("sum", () -> sum(numbers));    // Aufruf von sum() über test-Methode
        test0("sumPositiveEvenNumbers", () -> sumPositiveEvenNumbers(numbers));

        // Tests für Aufgabe 3.)
        List.of(4, -3, 1).stream()
                .filter(x -> true)        // auf true setzen für Ausgaben
                .forEach(x -> {
                    System.out.println(String.format("\ntesting: x=%d", x));
                    test1("findFirst", x, (x1) -> findFirst(x1, numbers));
                    test1("findLast", x, (x1) -> findLast(x1, numbers));
                    test2("findAll", x, (x1) -> findAll(x1, numbers));
                });

        // Tests für Aufgabe 4.)
        Arrays.stream(new int[][]{
                        {4, 9}, {9, 4}, {2, 3}
                })
                .filter(x -> true)        // auf true setzen für Ausgaben
                .forEach(t -> {
                    int x = t[0];
                    int y = t[1];
                    System.out.println(String.format("\ntesting: (x=%d, y=%d)", x, y));
                    test3("findAllAdjacent", x, y, (x1, y1) -> findAllAdjacent(x1, y1, numbers));
                });

        // Tests für Aufgabe 5.)
        int[][] results = findSums(10, numbers_2);
        System.out.println(String.format("\nfindSums(%d, %s)", 10, Arrays.toString(numbers_2)));
        for (int i = 0; i < results.length; i++) {
            System.out.println(String.format(" - %s", Arrays.toString(results[i])));
        }
        //
        List.of(10, 12, 15, 18, 36).stream()
                .filter(sum -> true)    // auf true setzen für Ausgaben
                .forEach(sum -> {
                    test4("findSums", sum, numbers_2, (s, nbs) -> findSums(s, nbs));
                });

        // Tests für Aufgabe 6.)
        List.of(14, 15, 20, 32).stream()
                .filter(sum -> true)    // auf true setzen für Ausgaben
                .forEach(sum -> {
                    test4("findAllSums", sum, numbers_2, (s, nbs) -> findAllSums(s, nbs));
                });
    }


    /*
     * Bitte implementieren Sie die Methoden aus der Aufgabe.
     *
     * Aufgaben 1.) und 2.)
     */
    int sum(int[] numbers) {
        int result = 0;
        //calculate sum of numbers
        for (int i = 0; i < numbers.length; i++) {
            result += numbers[i];
        }
        return result;
    }

    // calculate sum of all positive even numbers
    int sumPositiveEvenNumbers(int[] numbers) {
        int result = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] % 2 == 0 && numbers[i] > 0) {
                result += numbers[i];
            }
        }
        return result;
    }


    /*
     * Aufgabe 3.)
     */
    int findFirst(int x, int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == x) {
                return i;
            }
        }
        return -1;
    }

    // find last index of x
    int findLast(int x, int[] numbers) {
        int result = -1;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] == x) {
                return i;
            }
        }
        return -1;
    }

    int[] findAll(int x, int[] numbers) {
        List<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == x) {
                result.add(i);
            }
        }

        // convert List<Integer> to int[]
        return result.stream().mapToInt(Integer::intValue).toArray();
    }


    /*
     * Aufgabe 4.)
     * find all adjacent numbers
     */
    int[][] findAllAdjacent(int x, int y, int[] numbers) {
        List<Integer> temp = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == x && numbers[i + 1] == y) {
                temp.add(i);
                temp.add(i+1);
            }
        }
        int length = temp.size() /2;
        int[][] result = new int[length][2];
        for (int a = 0; a < length; a++) {
            result[a][0] = temp.remove(0);
            result[a][1] = temp.remove(0);

        }
        return result;
    }


    /*
     * Aufgabe 5.)
     */
    int[][] findSums(int sum, int[] numbers) {
        List<int[]> result = new ArrayList<int[]>();    // list of tuples (int[])
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == sum) {
                    result.add(new int[]{numbers[i], numbers[j]});
                }
            }
        }
        return result.stream()    // convert List<int[]> to int[][]
                .map(t -> new int[]{t[0], t[1]})
                .toArray(int[][]::new);
    }


    /*
     * Aufgabe 6.)
     */
    int[][] findAllSums(int sum, int[] numbers) {	// list of tuples (int[])
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> smallList = new ArrayList<>();
        int temp = 0;
        for (int i = 0; i < (1<<numbers.length); i++) {
            for (int j = 0; j < numbers.length; j++) {
                if ((i & (1 << j)) > 0) {
                    smallList.add(numbers[j]);
                }
            }
            for (int integer : smallList) {
                temp += integer;
            }
            if (temp == sum) {
                list.add(smallList);
            }
            temp = 0;
            smallList = new ArrayList<>();
        }
        return list.stream()
                .map(t -> t.stream().mapToInt(i -> i).toArray())
                .toArray(int[][]::new);
    }


    /*
     * Test methods that wrap functions, run them and print results.
     * No need to change.
     */
    void test0(String funcName, Supplier<Integer> func) {
        int i = func.get();            // invoke function
        System.out.println(String.format(" - %s(numbers) -> %d", funcName, i));
    }

    void test1(String funcName, int x, Function<Integer, Integer> func) {
        int i = func.apply(x);        // invoke function
        System.out.println(String.format(" - %s(%d, numbers) -> %d", funcName, x, i));
    }

    void test2(String funcName, int x, Function<Integer, int[]> func) {
        int[] all = func.apply(x);    // invoke function
        System.out.println(String.format(" - %s(%d, numbers) -> %s", funcName, x, Arrays.toString(all)));
    }

    void test3(String funcName, int x, int y, BiFunction<Integer, Integer, int[][]> func) {
        StringBuffer sb = new StringBuffer("[");
        int[][] all = func.apply(x, y);                // invoke function
        Arrays.stream(all).map(t -> Arrays.toString(t)/*.replace("[", "(").replace("]", ")")*/)
                .forEach(t -> sb.append(sb.length() > 1 ? ", " + t : t));
        sb.append("]");
        System.out.println(String.format(" - %s(%d, %d, numbers) -> %s", funcName, x, y, sb.toString()));
    }

    void test4(String funcName, int sum, int[] numbers, BiFunction<Integer, int[], int[][]> func) {
        int[][] results = func.apply(sum, numbers);    // invoke function
        System.out.println(String.format("\n%s(%d, %s)", funcName, sum, Arrays.toString(numbers)));
        for (int i = 0; i < results.length; i++) {
            System.out.println(String.format(" - %s", Arrays.toString(results[i])));
        }
        if (results.length == 0) {
            System.out.println(" - []");
        }
    }
}
