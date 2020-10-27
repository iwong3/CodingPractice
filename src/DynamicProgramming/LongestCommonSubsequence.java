package DynamicProgramming;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongestCommonSubsequence {

    /*
     *  TODO:
     *  - code findLCS again for practice - repetition for memory
     *  - optimize findAllLCS()?
     */

    /**
     * Return a valid LCS.
     * Dynamic Programming.
     * Use a matrix to keep track of LCS at every letter between the two Strings.
     */
    private static String findLCS(String string1, String string2) {

        // get LCS matrix
        int[][] lcsMatrix = getLCSMatrix(string1, string2);

        // get a valid LCS
        // start at the last letter of both Strings and backtrack the max LCS for each letter
        StringBuilder validLCS = new StringBuilder();
        int a = lcsMatrix.length - 1;
        int b = lcsMatrix[0].length - 1;

        while  (a > 0 && b > 0) {
            if (string1.charAt(a - 1) == string2.charAt(b - 1)) {
                validLCS.append(string1.charAt(a - 1));
                a--;
                b--;
            }
            else {
                // our for loops automatically decrement b - so just check if we need to go up instead of left
                // if we go up, we need to increment b because the for loop will decrement (we want to go up, not diagonal)
                if (lcsMatrix[a - 1][b] > lcsMatrix[a][b - 1]) {
                    a--;
                } else {
                    b--;
                }
            }
        }

        return validLCS.reverse().toString();
    }

    /**
     * Output all valid LCS.
     * Recursive Dynamic Programming.
     */
    private static List<String> findAllLCS(String string1, String string2) {
        List<String> lcsList = new ArrayList<>();
        StringBuilder validLCS = new StringBuilder();
        findAllLCSHelper(lcsList, string1, string2, validLCS);
        return lcsList;
    }

    /**
     * Helper method to perform the recursive logic for findAllLCS().
     */
    private static void findAllLCSHelper(List<String> lcsList, String string1, String string2, StringBuilder validLCS) {

        // base case - if we're done iterating through either String, add the valid LCS to the list
        if (string1.length() == 0 || string2.length() == 0) {
            lcsList.add(validLCS.reverse().toString());
            return;
        }

        // if the last letters are the same, then it must be in the LCS
        // add letter to LCS, remove from both Strings, and recurse
        if (string1.charAt(string1.length() - 1) == string2.charAt(string2.length() - 1)) {
            validLCS.append(string1.charAt(string1.length() - 1));
            findAllLCSHelper(lcsList, string1.substring(0, string1.length() - 1), string2.substring(0, string2.length() - 1), validLCS);
            return;
        }

        // if the last letters are different, then check LCS for when
        // string1 last letter is removed AND string2 last letter is removed
        // if LCS size is the same for both cases, then recurse on both

        // get LCS matrix
        int[][] lcsMatrix = getLCSMatrix(string1, string2);

        int topCellValue = lcsMatrix[string1.length() - 1][string2.length()];
        int leftCellValue = lcsMatrix[string1.length()][string2.length() - 1];
        if (topCellValue < leftCellValue) {
            findAllLCSHelper(lcsList, string1, string2.substring(0, string2.length() - 1), validLCS);
        }
        else if (leftCellValue < topCellValue) {
            findAllLCSHelper(lcsList, string1.substring(0, string1.length() - 1), string2, validLCS);
        }
        // LCS is same size
        else {
            // make copies of the StringBuilder to avoid a carry-over LCS
            StringBuilder topLCS = new StringBuilder(validLCS.toString());
            StringBuilder leftLCS = new StringBuilder(validLCS.toString());
            findAllLCSHelper(lcsList, string1.substring(0, string1.length() - 1), string2, topLCS);
            findAllLCSHelper(lcsList, string1, string2.substring(0, string2.length() - 1), leftLCS);
        }

    }

    /**
     * Helper method to create a LCS matrix given 2 Strings.
     */
    private static int[][] getLCSMatrix(String string1, String string2) {

        int[][] lcsMatrix = new int[string1.length()+1][string2.length()+1];

        // start with first letter from each String and sequentially fill out the Strings, updating the LCS as we go
        for (int a = 1; a < lcsMatrix.length; a++) {
            for (int b = 1; b < lcsMatrix[0].length; b++) {
                // if same letters, then current LCS equals the LCS prior to adding this letter to both Strings
                if (string1.charAt(a-1) == string2.charAt(b-1)) {
                    lcsMatrix[a][b] = lcsMatrix[a-1][b-1] + 1;
                }
                // if different letters, then the LCS can't increase in size
                // find the max LCS before adding the latest letter to either String
                else {
                    lcsMatrix[a][b] = Math.max(lcsMatrix[a][b-1], lcsMatrix[a-1][b]);
                }
            }
        }

        return lcsMatrix;
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
            assertEquals("MJAU", findLCS("XMJYAUZ", "MZJAWXU"));
        }

        @Test
        void manyLCS() {
            assertEquals("BDAB", findLCS("ABCBDAB", "BDCABA"));
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
            List<String> expected = Lists.newArrayList("MJAU");
            assertEquals(expected, findAllLCS("XMJYAUZ", "MZJAWXU"));
        }

        @Test
        void manyLCS() {
            List<String> expected = Lists.newArrayList("BCAB", "BCBA", "BDAB");
            for (String lcs : findAllLCS("ABCBDAB", "BDCABA")) {
                assertTrue(expected.contains(lcs));
            }
        }

    }

}
