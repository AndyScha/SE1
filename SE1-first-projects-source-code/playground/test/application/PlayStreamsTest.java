package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;


/**
 * Test class to for PlayStreams class.
 *
 * @author sgra64
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayStreamsTest {
    //
    // test object, created for each test-run that executes one @Test method
    final PlayStreams ps = new PlayStreams();
    //
    final static List<String> sortedNames = List.of(
            "Bernard", "Brock", "Buckner",
            "Case", "Casey", "Cleveland", "Crane",
            "Duncan",
            "Gill", "Gomez", "Gonzalez", "Graham",
            "Hall", "Hamilton", "Hardin", "Hendricks", "Howe",
            "Joyner",
            "Lott",
            "Marquez",
            "Navarro", "Nielsen",
            "Pena", "Petty",
            "Ray", "Raymond", "Richardson", "Rutledge",
            "Soto", "Strong",
            "Talley",
            "Vance",
            "Witt"
    );
    //
    final static List<String> sortedNamesByLength = List.of(
            "Ray",
            "Case", "Gill", "Hall", "Howe", "Lott", "Pena", "Soto", "Witt",
            "Brock", "Casey", "Crane", "Gomez", "Petty", "Vance",
            "Duncan", "Graham", "Hardin", "Joyner", "Strong", "Talley",
            "Bernard", "Buckner", "Marquez", "Navarro", "Nielsen", "Raymond",
            "Gonzalez", "Hamilton", "Rutledge",
            "Cleveland", "Hendricks",
            "Richardson"
    );
    //
    final static List<Integer> prices = Arrays.asList(
            199, 249, 49, 999, 1499, 1999, 789
    );
    final static List<Integer> units = Arrays.asList(
            2, 7, 4, 2, 5, 2, 6
    );


    @Test
    @Order(200)
    void test200_filteredNames() {
        List<String> result = ps.filteredNames();
        assertNotNull(result);
        assertEquals(List.of("Gonzalez", "Gomez", "Marquez"), result);
    }

    @Test
    @Order(300)
    void test300_sortedNames() {
        List<String> result = ps.sortedNames(8);
        assertNotNull(result);
        assertEquals(List.of(
                "Bernard", "Brock", "Buckner", "Case", "Casey",
                "Cleveland", "Crane", "Duncan"), result);
        //
        assertEquals(sortedNames.subList(0, 8), result);
        //
        // test full list
        result = ps.sortedNames(sortedNames.size());
        assertNotNull(result);
        assertEquals(sortedNames, result);
    }

    @Test
    @Order(310)
    void test310_sortedNamesEmptyList() {
        int limit = 0;
        List<String> result = ps.sortedNames(limit);
        assertNotNull(result);
        assertEquals(List.of(), result);
    }

    @Test
    @Order(320)
    void test320_sortedNamesNegativeLimit() {
        int limit = -1;
        List<String> result = ps.sortedNames(limit);
        assertNotNull(result);
        assertEquals(List.of(), result);
        //
        limit = -100;
        result = ps.sortedNames(limit);
        assertEquals(List.of(), result);
        //
        limit = Integer.MIN_VALUE;
        result = ps.sortedNames(limit);
        assertEquals(List.of(), result);
    }

    @Test
    @Order(400)
    void test400_sortedNamesByLength() {
        List<String> result = ps.sortedNamesByLength();
        assertNotNull(result);
        assertEquals(sortedNamesByLength, result);
    }

    @Test
    @Order(500)
    void test500_priceCalculations() {
        //
        int result = ps.priceCalculations(prices, units);
        assertEquals(20562, result);
        //
        result = ps.priceCalculations(prices.subList(0, 3), units.subList(0, 3));
        assertEquals(2337, result);
        //
        result = ps.priceCalculations(Arrays.asList(
                1499, 1999, 699), Arrays.asList(
                120, 430, 298)
        );
        assertEquals(1247752, result);
    }

    @Test
    @Order(510)
    void test510_priceCalculationsEmptyLists() {
        //
        int result = ps.priceCalculations(prices, List.of());
        assertEquals(0, result);
        //
        result = ps.priceCalculations(List.of(), units);
        assertEquals(0, result);
        //
        result = ps.priceCalculations(List.of(), List.of());
        assertEquals(0, result);
    }

    private List<Integer> verifyNumbers(List<String> rand) {
        return rand.stream()
                // test string for format "X123456"
                .filter(s -> s != null && s.length() == "X123456".length())
                .map(s -> s.substring(1))        // 120198
                .map(s -> Integer.parseInt(s))
                .filter(i -> i % 2 == 0)        // test number is even
                .collect(Collectors.toList());
    }

    @Test
    @Order(600)
    void test600_randomCodes() {
        int testSize = 1000;
        List<String> result = ps.randomCodes(testSize);
        assertNotNull(result);
        assertEquals(testSize, result.size());
        //
        List<Integer> numbers = verifyNumbers(result);
        List<Integer> sorted = new ArrayList<Integer>(numbers);
        Collections.sort(sorted);        // verify ascending order
        assertEquals(numbers, sorted);
        //
        List<Integer> numbers_2 = verifyNumbers(ps.randomCodes(testSize));
        assertNotEquals(numbers, numbers_2);    // make sure new numbers
    }

    @Test
    @Order(610)
    void test610_randomCodesNegativeCount() {
        //
        List<String> result = ps.randomCodes(0);
        assertEquals(result, List.of());
        //
        // must throw IllegalArgumentException for negative count parameter
        Throwable thr = assertThrows(IllegalArgumentException.class, () -> {
            ps.randomCodes(-1);
        });
        String msg = thr.getMessage();    // test exception message
        assertEquals("count negative", msg);
    }

}
