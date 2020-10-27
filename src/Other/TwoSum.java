package Other;

import java.util.ArrayList;
import java.util.HashMap;

/*
    LESSON LEARNED:
    - For improving efficiency, think of different ways you can SAVE what you've iterated through.
    - Think for better efficiency (O(N) runthroughs, iterating one list), what can you save and what info do you need per iteration?

    First thought is brute force.
    - Double for loop to iterate through all pair possibilities, return when found.
    - Not terribly efficient. Better than n^2, but can we do better?

    Second thought was to see if we could use DP - save pairs that we've encountered?
    - This doesn't really make anything faster as we already need to check a pair at the start
      (nums[a] + nums[b] == target?)

    Third approach - save all numbers we've encountered
    - That way, we can just do a O(2N) == O(N) pass through.
    - First run is to see all available numbers and save it in a list/map.
    - Second run is to check the current number and to see if other number is available in the list/map.
 */
public class TwoSum {

    public static void main(String[] args) {

        // HashMaps are substantially faster than ArrayList, even though the data fits ArrayList better in a way.
        // Size: 5000
        // HashMap Time: 3
        // ArrayList Time: 17

        int[] numbers = new int[5000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (int) (Math.random() * 5000);
        }
        int target = (int) (Math.random() * 5000);

        long startTime = System.currentTimeMillis();
        twoSumMap(numbers, target);
        long endTime = System.currentTimeMillis();
        System.out.println("HashMap: " + (endTime - startTime));

        startTime = System.currentTimeMillis();
        twoSumList(numbers, target);
        endTime = System.currentTimeMillis();
        System.out.println("List: " + (endTime - startTime));

    }

    /**
     * Given a list of numbers and a target, return the indicies of 2 numbers that sum to the target.
     * There is exactly 1 solution and you cannot use the same element twice.
     * @params nums = [2, 7, 11, 15], target = 9
     * @return = [0, 1]
     */
    public static int[] twoSumMap(int[] nums, int target) {

        int[] ans = new int[2];
        HashMap<Integer, Boolean> mapLookup = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (!mapLookup.containsKey(nums[i])) {
                mapLookup.put(nums[i], true);
            }
        }

        int a = 0;
        int b = 0;
        while (a < nums.length) {
            int numNeeded = target - nums[a];
            if (mapLookup.containsKey(numNeeded)) {
                ans[0] = a;
                b = a + 1;
                    while (b < nums.length) {
                        if (nums[b] == numNeeded) {
                            ans[1] = b;
                            return ans;
                        }
                        b++;
                    }
            }
            a++;
        }

        return ans;

    }

    public static int[] twoSumList(int[] nums, int target) {

        int[] ans = new int[2];
        ArrayList<Integer> lookupList = new ArrayList<>(0);

        for (int i = 0; i < nums.length; i++) {
            if (!lookupList.contains(nums[i])) {
                lookupList.add(nums[i]);
            }
        }

        int a = 0;
        int b = 0;
        while (a < nums.length) {
            int numNeeded = target - nums[a];
            if (lookupList.contains(numNeeded)) {
                ans[0] = a;
                b = a + 1;
                while (b < nums.length) {
                    if (nums[b] == numNeeded) {
                        ans[1] = b;
                        return ans;
                    }
                    b++;
                }
            }
            a++;
        }

        return ans;

    }

}
