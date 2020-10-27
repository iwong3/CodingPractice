package Recursion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnapsackProblem {

    /**
     * Given a list of items that have a weight and value,
     * return the maximum value we can get from the items without breaking the weight limit.
     */
    private int getMaxValueKnapsack(int[] values, int[] weights, int weightLimit) {
        long recurseStart = System.currentTimeMillis();
        int recursion = getMaxValueKnapsackHelper(values, weights, weightLimit, 0);
        long recurseEnd = System.currentTimeMillis();

        long dpStart = System.currentTimeMillis();
        int dp = getMaxValueKnapsackHelperDP(values, weights, weightLimit, 0, new HashMap<>());
        long dpEnd = System.currentTimeMillis();

        System.out.println(recurseStart + ", " + recurseEnd + ", " + dpStart + ", " + dpEnd);
        System.out.println("Recursion Time Elapsed: " + (recurseEnd - recurseStart));
        System.out.println("DP Time Elapsed: " + (dpEnd - dpStart));

        return dp;
    }

    /**
     * Helper method to perform the recursive logic for {@link #getMaxValueKnapsack}
     */
    private int getMaxValueKnapsackHelper(int[] values, int[] weights, int weightLimit, int index) {

        // base cases:
        // 1. when we're at the last item...
        if (index == values.length-1) {
            // 1a. ...and it doesn't put us over the weight limit
            if (weightLimit >= weights[index]) {
                return values[index];
            }
            // 1b. ...and we can't use the item due to the weight limit
            return 0;
        }

        // recursive cases:
        // 1. we *can* use the item (must be under the weight limit)
        if (weightLimit >= weights[index]) {
            // then pick the max between...
            // 1a. using the item
            int withItem = values[index] + getMaxValueKnapsackHelper(values, weights, weightLimit - weights[index], index+1);
            // 1b. not using the item
            int withoutItem = getMaxValueKnapsackHelper(values, weights, weightLimit, index+1);
            return Math.max(withItem, withoutItem);
        }
        // 2. we can't use the item (would put us over the weight limit)
        else {
            return getMaxValueKnapsackHelper(values, weights, weightLimit, index+1);
        }

    }

    /**
     * Helper method to perform the recursive logic for {@link #getMaxValueKnapsack}
     */
    private int getMaxValueKnapsackHelperDP(int[] values, int[] weights, int weightLimit, int index, HashMap<String, Integer> lookup) {

        // base cases:
        // 1. when we're at the last item...
        if (index == values.length-1) {
            // 1a. ...and it doesn't put us over the weight limit
            if (weightLimit >= weights[index]) {
                return values[index];
            }
            // 1b. ...and we can't use the item due to the weight limit
            return 0;
        }

        // store this scenario if we haven't come across it yet
        String key = index + "|" + weightLimit;
        if (!lookup.containsKey(key)) {
            // recursive cases:
            // 1. we *can* use the item (must be under the weight limit)
            if (weightLimit >= weights[index]) {
                // then pick the max between...
                // 1a. using the item
                int withItem = values[index] + getMaxValueKnapsackHelper(values, weights, weightLimit - weights[index], index+1);
                // 1b. not using the item
                int withoutItem = getMaxValueKnapsackHelper(values, weights, weightLimit, index+1);
                lookup.put(key, Math.max(withItem, withoutItem));
            }
            // 2. we can't use the item (would put us over the weight limit)
            else {
                int withoutItem = getMaxValueKnapsackHelper(values, weights, weightLimit, index+1);
                lookup.put(key, withoutItem);
            }
        }

        // if we have come across it (or after we finish processing it), return it by lookup
        return lookup.get(key);

    }

    /* UNIT TESTS */

    @Nested
    @DisplayName("getMaxValueKnapsack() spec")
    class GetMaxValueKnapsackSpec {

        @Test
        void happyPath() {
            int[] values = new int[]{20, 5, 10, 40, 15, 25};
            int[] weights = new int[]{1,2, 3, 8, 7, 4};
            int weightLimit = 10;
            int expectedValue = 60;
            assertEquals(expectedValue, getMaxValueKnapsack(values, weights, weightLimit));
        }

    }

}
