package DynamicProgramming;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Problem:
 * Given a set of (coin values: [1, 2, 5]) and (target value: 11),
 * Find the minimum number of coins needed to reach that target value,
 * assuming you can use an unlimited amount of each coin.
 * Return -1 if reaching the target value is impossible.
 */
public class CoinChange {

    // if we recurse starting from greatest coin -> smallest coin
    // AND cache by min coins for amount>

    // key = amount left, value = min coins
    HashMap<Integer, Integer> lookup = new HashMap<>();

    public int coinChange(int[] coins, int target) {
        Arrays.sort(coins);
        return coinChangeHelper(coins, target);
    }

    public int coinChangeHelper(int[] coins, int rem) {

        if (rem < 0) return -1;
        if (rem == 0) return 0;

        // if we've calculated the min coins needed for this remainder, return it
        if (lookup.containsKey(rem)) {
            return lookup.get(rem);
        }

        // minimum number of coins needed to hit CURRENT remainder
        int currMin = Integer.MAX_VALUE;
        for (int i = coins.length - 1; i >= 0; i--) {
            int minCoinsNeeded = coinChangeHelper(coins, rem - coins[i]);
            if (minCoinsNeeded >= 0 && minCoinsNeeded < currMin) {
                currMin = minCoinsNeeded + 1;
            }
        }

        lookup.put(rem, currMin == Integer.MAX_VALUE ? -1 : currMin);
        return lookup.get(rem);

    }

    @Nested
    @DisplayName("getMVCS() spec")
    class GetMVCSSpec {

        @Test
        void happyPath() {
            int[] coins = new int[]{1, 2, 5};
            int target = 11;
            int expectedAnswer = 3;
            assertEquals(expectedAnswer, coinChange(coins, target));
        }

    }

}
