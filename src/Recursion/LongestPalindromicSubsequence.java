package Recursion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LongestPalindromicSubsequence {

    /*
        TODO:
            - Current solution is recursive. Find DP solution.
            - Unit tests.
     */

    /**
     * Returns the longest palindromic subsequence given a String.
     */
    private static String findLPS(String s) {
        return findLPSHelper(s, 0, s.length()-1);
    }

    /**
     * Helper method for {@link #findLPS} that performs the recursive logic for the function.
     */
    private static String findLPSHelper(String s, int start, int end) {

        // base cases:
        // 1. if empty string, return
        if (start > end) return "";
        // 2. if one letter, then it's a palindrome
        if (start == end) return s.charAt(start) + "";

        // recursive cases:
        // 1. if start and end letters are the same, they are part of the palindrome
        if (s.charAt(start) == s.charAt(end)) {
            return s.charAt(start) + findLPSHelper(s, start+1, end-1) + s.charAt(end);
        }
        // 2. if different, then recurse on
        //      2a. start letter removed
        //      2b. end letter removed
        //    and return the longer String
        else {
            String noStart = findLPSHelper(s, start+1, end);
            String noEnd = findLPSHelper(s, start, end-1);
            return noEnd.length() > noStart.length() ? noEnd : noStart;
        }

    }

    /* UNIT TESTS */

    @Nested
    @DisplayName("findLPS() spec")
    class FindLPSSpec {

        @Test
        void oddLengthPalindrome() {
            assertEquals("BCACB", findLPS("ABBDCACB"));
        }

        @Test
        void evenLengthPalindrome() {
            assertEquals("ABBA", findLPS("ABBDCAB"));
        }

    }

}
