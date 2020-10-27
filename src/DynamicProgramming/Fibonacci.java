package DynamicProgramming;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Fibonacci {

    /**
     * Return the Fibonacci sum given the number of iterations.
     */
    private static int getFibonacci(int iterations) {

        // edge cases
        if (iterations == 0) return 0;
        if (iterations == 1) return 1;

        // store the Fibonacci sequence in an array
        int[] sequence = new int[iterations];
        sequence[0] = 1;
        sequence[1] = 1;
        int index = 2;

        while (index < iterations) {
            sequence[index] = sequence[index-2] + sequence[index-1];
            index++;
        }

        return sequence[iterations-1];

    }

    /**
     * Return the Fibonacci sum given the number of iterations and the two starting numbers.
     */
    private static int getFibonacciWithStartingNumbers(int iterations, int num1, int num2) {

        // edge cases
        if (iterations == 0) return 0;
        if (iterations == 1) return num1;

        // store the Fibonacci sequence in an array
        int[] sequence = new int[iterations];
        sequence[0] = num1;
        sequence[1] = num2;
        int index = 2;

        while (index < iterations) {
            sequence[index] = sequence[index-2] + sequence[index-1];
            index++;
        }

        return sequence[iterations-1];

    }

    /* UNIT TESTS */

    /**
     * Unit test for {@link #getFibonacci}
     */
    @Nested
    class getFibonacciSpec {

        @Test
        void happyCases() {
            assertEquals(getFibonacci(5), 5);
            assertEquals(getFibonacci(11), 89);
        }

        @Test
        void edgeCases() {
            assertEquals(getFibonacci(0), 0);
            assertEquals(getFibonacci(1), 1);
            assertEquals(getFibonacci(2), 1);
        }

    }

    @Nested
    class getFibonacciGivenStartingNumbersSpec {

        @Test
        void happyCase() {
            assertEquals(23, getFibonacciWithStartingNumbers(4, 7, 8));
        }

        @Test
        void negativeNumbers() {
            assertEquals(-7, getFibonacciWithStartingNumbers(5, 1, -3));
            assertEquals(-26, getFibonacciWithStartingNumbers(6, -2, -4));
        }

        @Test
        void edgeCases() {
            assertEquals(0, getFibonacciWithStartingNumbers(0, 7, 8));
            assertEquals(7, getFibonacciWithStartingNumbers(1, 7, 8));
            assertEquals(8, getFibonacciWithStartingNumbers(2, 7, 8));
        }

    }

}
