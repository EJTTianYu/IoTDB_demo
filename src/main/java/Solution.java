import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class Solution {

  public int maximalRectangle(char[][] matrix) {
    if (matrix.length == 0) {
      return 0;
    }
    int ans = 0;
    int[][] dp = new int[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        int left = j;
        while (left >= 0 && matrix[i][left] == '1') {
          left--;
        }
        dp[i][j] = j - left;
      }
    }
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == '0') {
          continue;
        }
        int currMax = 0;
        int lowRow = i;
        int minCol = dp[i][j];
        while (lowRow >= 0 && dp[lowRow][j] != 0) {
          minCol = Math.min(minCol, dp[lowRow][j]);
          currMax = Math.max(currMax, minCol * (i - lowRow + 1));
          lowRow--;
        }
        ans = Math.max(ans, currMax);
      }
    }

    return ans;
  }

//  public static void main(String[] args) {
//    char[][] nums = new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'},
//        {'1', '1', '1', '1', '1'}, {'1', '0', '0', '1', '0'}};
////    System.out.println(new Solution().maximalRectangle(nums));
//    List<String> testMap = new ArrayList<>();
//    testMap.add("test");
//    System.out.println(testMap.get(2));
//  }

  public static void main(String[] args) {
    PriorityQueue<Integer> integerPriorityQueue = new PriorityQueue<>();
    integerPriorityQueue.add(10);
    integerPriorityQueue.add(1);
    while(true){
      System.out.println(integerPriorityQueue.poll());
    }
  }
}
