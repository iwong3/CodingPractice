package DynamicProgramming;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPath {

    public int getShortestPath(int[][] matrix) {

        // init cost lookup matrix
        int[][] costLookup = new int[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            Arrays.fill(costLookup[row], Integer.MAX_VALUE);
        }
        costLookup[0][0] = matrix[0][0];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {

                // update right cell if not on last col
                if (col < matrix[0].length-1) {
                    int rightCellSum = costLookup[row][col] + matrix[row][col+1];
                    if (costLookup[row][col+1] > rightCellSum) {
                        costLookup[row][col+1] = rightCellSum;
                    }
                }

                // update bottom cell if not on last row
                if (row < matrix.length-1) {
                    int bottomCellSum = costLookup[row][col] + matrix[row+1][col];
                    if (costLookup[row+1][col] > bottomCellSum) {
                        costLookup[row+1][col] = bottomCellSum;
                    }
                }

            }
        }

        return costLookup[matrix.length-1][matrix[0].length-1];

    }

    /**
     * Unit test for {@link #getShortestPath}
     */
    @Nested
    @DisplayName("getShortestPath() spec")
    class GetShortestPathSpec {

        @Test
        void happyPath() {
            int[][] matrix = new int[][]{
                {4,7,8,6,4},
                {6,7,3,9,2},
                {3,8,1,2,4},
                {7,1,7,3,7},
                {2,9,8,9,3}
            };
            assertEquals(36, getShortestPath(matrix));
        }

    }


}
