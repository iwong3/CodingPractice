package Recursion;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RodCuttingProblem {

    private ArrayList<String> finalCuts;

    /**
     * Given rod cuts+prices and rod length available, calculate the max price possible.
     * Also output all cuts that give the max price.
     */
    private int maximizeRodCutting(int[] lengths, int[] prices, int rodLength) {
        // get max price and all final rod cuts
        HashMap<String, Integer> lookup = new HashMap<>();
        int maxRodValue = maximizeRodCuttingHelper(lengths, prices, rodLength, 0, new ArrayList<>(), 0, lookup);

        // get all max price cuts from list of final cuts
        ArrayList<String> maxPriceCuts = new ArrayList<>();
        lookup.forEach((cut, price) -> {
            if (price == maxRodValue) {
                maxPriceCuts.add(cut);
            }
        });
        this.finalCuts = maxPriceCuts;

        return maxRodValue;
    }

    /**
     * Helper method to perform recursive logic for {@link #maximizeRodCutting}.
     * @param rodLength - the rod length left at the current iteration
     * @param index - the rod cut being considered at the current iteration
     * @param cuts - all cuts made at the current iteration
     * @param totalPrice - total price of current cut
     * @param lookup - used to keep track of final cuts
     * @return int - the total price for the current cut
     */
    private static int maximizeRodCuttingHelper(int[] lengths, int[] prices, int rodLength, int index, ArrayList<Integer> cuts, int totalPrice, HashMap<String, Integer> lookup) {

        // base cases - also record the final cut and total price
        // 1. there's only one more type of rod cut we can try...
        if (index == lengths.length-1) {
            // 1a. ...and we have enough rod length left to try it
            if (rodLength >= lengths[index]) {
                ArrayList<Integer> cutsWithRod = Lists.newArrayList(cuts);
                cutsWithRod.add(lengths[index]);
                lookup.put(generateCutKey(cutsWithRod), totalPrice + prices[index]);
                return prices[index];
            }
            // 1b. ...but we don't have enough rod length left to try it
            ArrayList<Integer> cutsWithoutRod = Lists.newArrayList(cuts);
            lookup.put(generateCutKey(cutsWithoutRod), totalPrice);
            return 0;
        }
        // 2. no more rod length left
        if (rodLength <= 0) {
            ArrayList<Integer> cutsWithoutRod = Lists.newArrayList(cuts);
            lookup.put(generateCutKey(cutsWithoutRod), totalPrice);
            return 0;
        }

        // recursive cases:
        // 1. we have enough rod length left to try the current rod cut...
        if (rodLength >= lengths[index]) {

            // 1a. ...and we use the current rod cut
            ArrayList<Integer> cutsWithRod = Lists.newArrayList(cuts);
            cutsWithRod.add(lengths[index]);
            int withRod = prices[index] + maximizeRodCuttingHelper(lengths, prices, rodLength - lengths[index], index, cutsWithRod, totalPrice + prices[index], lookup);

            // 1b. ...and we don't use the current rod cut
            ArrayList<Integer> cutsWithoutRod = Lists.newArrayList(cuts);
            int withoutRod = maximizeRodCuttingHelper(lengths, prices, rodLength, index+1, cutsWithoutRod, totalPrice, lookup);

            return Integer.max(withRod, withoutRod);
        }
        // 2. we don't have enough rod length left to try the current rod cut
        else {
            ArrayList<Integer> cutsWithoutRod = Lists.newArrayList(cuts);
            return maximizeRodCuttingHelper(lengths, prices, rodLength, index+1, cutsWithoutRod, totalPrice, lookup);
        }

    }

    /**
     * Helper method to generate a key given cuts.
     */
    private static String generateCutKey(ArrayList<Integer> cuts) {
        StringBuilder keyBuilder = new StringBuilder();
        for (int cut : cuts) {
            keyBuilder.append(cut).append("|");
        }
        if (keyBuilder.length() > 0) {
            keyBuilder.deleteCharAt(keyBuilder.length()-1);
        }
        return keyBuilder.toString();
    }

    /* UNIT TESTS */

    @Nested
    @DisplayName("maximizeRodCutting() spec")
    class MaximizeRodCuttingSpec {

        @Test
        void oneMaxPriceCut() {
            int[] lengths = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
            int[] prices = new int[]{1, 5, 8, 9, 10, 17, 17, 20};
            int rodLength = 4;

            int expectedMaxPrice = 10;
            assertEquals(expectedMaxPrice, maximizeRodCutting(lengths, prices, rodLength));
            ArrayList<String> expectedFinalCuts = Lists.newArrayList("2|2");
            finalCuts.forEach((finalCut) -> {
                assertTrue(expectedFinalCuts.contains(finalCut));
            });
        }

        @Test
        void manyMaxPriceCuts() {
            int[] lengths = new int[]{1, 2, 3};
            int[] prices = new int[]{1, 2, 3};
            int rodLength = 4;

            int expectedMaxPrice = 4;
            assertEquals(expectedMaxPrice, maximizeRodCutting(lengths, prices, rodLength));
            ArrayList<String> expectedFinalCuts = Lists.newArrayList("1|1|1|1", "1|1|2", "1|3", "2|2");
            finalCuts.forEach((finalCut) -> {
                assertTrue(expectedFinalCuts.contains(finalCut));
            });
        }

    }

}
