package DynamicProgramming;

import java.util.Arrays;

public class HouseRobber {

        /**
         1. Figure out the recursive solution.
         Starting at the last house, the robber has 2 choices:
         1. Rob the house + max loot of i-2 houses
         2. Don't rob the house + max loot of i-1 houses
         Base case is when the index is less than 0 (no more houses to consider)

         2. Improve recursive solution by memoizing.
         Whenever we figure out the max loot of a house, record it.
         Then, before we jump into the recursive step, check our lookup.
         That way, when we hit a repeat case because of recursion, we can just look it up instead of going down that rabbit hole path.

         3. Change from recursive to iterative.
         Now, let's think about how to approach this iteratively.
         We can start at the first house and apply the same principles from the recursive solution.
         At every house, we have 2 choices:
         1. Rob the house + max loot of i-2 houses
         2. Don't rob the house + max loot of i-1 houses
         The difference is we'll need to initialize the first 2 houses so we can always compare to the i-2 house.
         Max loot from first house is just the first house's loot.
         Max loot from the second house is the GREATER between the first and the second house's loot. (choose to skip second if first loot is greater).
         */

        int[] lookup;

        public int rob(int[] nums) {

            lookup = new int[nums.length];
            Arrays.fill(lookup, -1);
            // return robHelper(nums, nums.length - 1);

            return robIter(nums);

        }

        public int robHelper(int[] nums, int i) {

            if (i < 0) {
                return 0;
            }

            if (lookup[i] >= 0) {
                return lookup[i];
            }

            int currentHouseNotRobbed = robHelper(nums, i-1);
            int currentHouseRobbed = nums[i] + robHelper(nums, i-2);
            int betterHouse = Math.max(currentHouseRobbed, currentHouseNotRobbed);
            lookup[i] = betterHouse;

            return betterHouse;

        }

        public int robIter(int[] nums) {

            if (nums.length == 0) return 0;
            if (nums.length == 1) return nums[0];
            if (nums.length == 2) {
                if (nums[0] > nums[1]) {
                    return nums[0];
                }
                return nums[1];
            }

            int[] lookup = new int[nums.length];
            lookup[0] = nums[0];
            lookup[1] = nums[0] > nums[1] ? nums[0] : nums[1];

            for (int i = 2; i < nums.length; i++) {

                int robHouse = nums[i] + lookup[i-2];
                int dontRobHouse = lookup[i-1];
                lookup[i] = Math.max(robHouse, dontRobHouse);

            }

            int maxRobbing = 0;
            for (int i = 0; i < lookup.length; i++) {
                if (lookup[i] > maxRobbing) {
                    maxRobbing = lookup[i];
                }
            }

            return maxRobbing;

        }

        // space optimization
        // instead of keep a lookup array (size: O(n)), we can just store the relevant variables (last 2 variables)
        public int robIter2(int[] nums) {

            if (nums.length == 0) return 0;

            int prev1 = 0;
            int prev2 = 0;
            for (int num : nums) {
                int tmp = prev1;
                prev1 = Math.max(prev2 + num, prev1);
                prev2 = tmp;
            }

            return prev1;

        }

}
