package Recursion;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 3Sum - https://leetcode.com/problems/3sum/
 *
 * @author Ivan Wong
 * @date 4/25/2020
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums, int goal) {

        ArrayList<String> lookup = new ArrayList<>();
        ArrayList<List<Integer>> answers = new ArrayList<>();

        Arrays.sort(nums);

        for (int a = 0; a < nums.length-2; a++) {
            for (int b = a+1; b < nums.length-1; b++) {
                for (int c = b+1; c < nums.length; c++) {
                    // generate key
                    String key = nums[a] + "|" + nums[b] + "|" + nums[c];
                    // if we haven't processed this triplet yet
                    if (!lookup.contains(key)) {
                        // valid triplet, add to answer
                        if (nums[a] + nums[b] + nums[c] == goal) {
                            answers.add(Lists.newArrayList(nums[a], nums[b], nums[c]));
                        }
                        // put into lookup
                        lookup.add(key);
                    }
                }
            }
        }

        return answers;

    }

}
