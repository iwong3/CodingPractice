package DynamicProgramming;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongestCommonSubstring {

    /**
     * Return the longest common *substring*.
     * The difference between substring and subsequence is that
     * we only set a non-zero value in a cell if the letters are the same.
     */
    public static String findLCS(String string1, String string2) {

        // initialize variables
        int[][] lcsMatrix = new int[string1.length()+1][string2.length()+1];
        int maxVal = 0;
        int maxValCol = 0;
        int maxValRow = 0;

        // generate LCS matrix and keep track of max value + position at the same time
        for (int a = 1; a < lcsMatrix.length; a++) {
            for (int b = 1; b < lcsMatrix[0].length; b++) {
                // only update if the letters are the same
                if (string1.charAt(a - 1) == string2.charAt(b - 1)) {
                    int cellVal = lcsMatrix[a-1][b-1] + 1;
                    lcsMatrix[a][b] = cellVal;
                    if (cellVal > maxVal) {
                        maxVal = cellVal;
                        maxValCol = a;
                        maxValRow = b;
                    }
                }
            }
        }

        // backtrack through LCS matrix starting at max val position
        // only go top-left because we must return a substring
        StringBuilder lcs = new StringBuilder();
        int a = maxValCol;
        int b = maxValRow;

        while (lcsMatrix[a][b] > 0) {
            lcs.append(string1.charAt(a-1));
            a--;
            b--;
        }

        return lcs.reverse().toString();

    }

    /**
     * Return all LCSubstrings.
     * Planning:
     * - Use a list to keep track of all non-zero cells (val, col, row) and the overall max lcs length
     * - Iterate through list, finding the LCSubstring for each max lcs length entry
     */
    public static List<String> findAllLCS(String string1, String string2) {

        // initialize variables
        int[][] lcsMatrix = new int[string1.length()+1][string2.length()+1];
        ArrayList<ArrayList<Integer>> lcsCellValues = new ArrayList<>();
        int maxVal = 0;

        // generate lcs matrix and keep track of non-zero cells + position at the same time
        for (int a = 1; a < lcsMatrix.length; a++) {
            for (int b = 1; b < lcsMatrix[0].length; b++) {
                // only update if the letters are the same
                if (string1.charAt(a - 1) == string2.charAt(b - 1)) {
                    // update lcs matrix
                    int cellVal = lcsMatrix[a-1][b-1] + 1;
                    lcsMatrix[a][b] = cellVal;

                    // keep track of max value
                    if (cellVal > maxVal) {
                        maxVal = cellVal;
                    }

                    // add entry to list
                    ArrayList<Integer> cellValues= Lists.newArrayList(cellVal, a, b);
                    lcsCellValues.add(cellValues);
                }
            }
        }

        // get all LCSubstrings
        ArrayList<String> validLCSubstrings = new ArrayList<>();

        // find all max values in list of non-zero cells
        for (ArrayList<Integer> entry : lcsCellValues) {
            // found a max lcs entry
            if (maxVal == entry.get(0)) {
                StringBuilder lcs = new StringBuilder();
                int a = entry.get(1);
                int b = entry.get(2);

                while (lcsMatrix[a][b] > 0) {
                    lcs.append(string1.charAt(a-1));
                    a--;
                    b--;
                }

                validLCSubstrings.add(lcs.reverse().toString());
            }
        }

        return validLCSubstrings;

    }

    /* UNIT TESTS */

    /**
     * Unit test for {@link #findLCS}
     */
    @Nested
    @DisplayName("findLCS() spec")
    class FindLCSSpec {

        @Test
        void oneLCS() {
            assertEquals("DAB", findLCS("ABCDABCBA", "XYZDABZYX"));
        }

        @Test
        void manyLCS() {
            assertEquals("ABA", findLCS("ABAB", "BABA"));
        }

    }

    /**
     * Unit test for {@link #findAllLCS}
     */
    @Nested
    @DisplayName("findAllLCS() spec")
    class FindAllLCSSpec {

        @Test
        void oneLCS() {
            List<String> expected = Lists.newArrayList("DAB");
            assertEquals(expected, findAllLCS("ABCDABCBA", "XYZDABZYX"));
        }

        @Test
        void manyLCS() {
            List<String> expected = Lists.newArrayList("ABA", "BAB");
            for (String lcs : findAllLCS("ABAB", "BABA")) {
                assertTrue(expected.contains(lcs));
            }
        }

    }

}
