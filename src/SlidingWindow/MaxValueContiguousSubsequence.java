package SlidingWindow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MaxValueContiguousSubsequence {

    /**
     * Given a list of real numbers, return the MVCS.
     * Only returns one possible MVCS, not all.
     * @return int[], where...
     *   - [0] = MVCS
     *   - [1] = left index
     *   - [2] = right index
     */
    private int[] getMVCS(int[] numbers) {
        int leftIndex = 0;
        int rightIndex = 0;
        int currentSum = 0;
        int maxSum = Integer.MIN_VALUE;
        int maxSumLeftIndex = 0;
        int maxSumRightIndex = 0;

        // get MVCS
        while (rightIndex < numbers.length) {
            // calculate current window sum
            currentSum += numbers[rightIndex++];

            // if current sum is negative, then we'll never need the current window (MVCS would be sum=0, length=0)
            // so we shorten window until sum isn't negative or until window is reset
            while (currentSum < 0 && leftIndex < rightIndex) {
                currentSum -= numbers[leftIndex];
                leftIndex++;
            }

            // update max sum if needed
            if (currentSum > maxSum) {
                maxSum = currentSum;
                maxSumLeftIndex = leftIndex;
                maxSumRightIndex = rightIndex;
            }
        }

        return new int[]{maxSum, maxSumLeftIndex, maxSumRightIndex};
    }

    /* UNIT TESTS */

    @Nested
    @DisplayName("getMVCS() spec")
    class GetMVCSSpec {

        @Test
        void happyPath() {
            int[] numbers = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
            int[] expectedAnswer = new int[]{6, 3, 7};
            assertArrayEquals(expectedAnswer, getMVCS(numbers));
        }

        @Test
        void allNegativeNumbers() {
            int[] numbers = new int[]{-1, -2, -3};
            int[] expectedAnswer = new int[]{0, 1, 1};
            assertArrayEquals(expectedAnswer, getMVCS(numbers));
        }

    }

}
