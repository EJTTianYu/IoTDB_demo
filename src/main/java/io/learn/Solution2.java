package io.learn;// 本题为考试单行多行输入输出规范示例，无需提交，不计分。

import java.util.Arrays;
import java.util.Scanner;

public class Solution2 {

  // 用于统计均分时扔掉的物品价值
  static int valueDropped(int[] nums) {
    int sum = 0;
    for (int num : nums) {
      sum += num;
    }
    boolean[][] dp = new boolean[nums.length][sum + 1];
    for (int i = 0; i < nums.length; i++) {
      dp[i][0] = true;
    }
    dp[0][nums[0]] = true;
    for (int i = 1; i < nums.length; i++) {
      for (int j = 0; j <= sum; j++) {
        if (dp[i - 1][j]) {
          dp[i][j] = true;
          dp[i][j + nums[i]] = true;
        }
      }
    }
    for (int k = sum; k >= 0; k--) {
      if (k % 2 == 0 && dp[nums.length - 1][k] && dp[nums.length - 1][k / 2]) {
        return sum - k;
      }
    }
    return sum;
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int loop = in.nextInt();
    for (int i = 0; i < loop; i++) {
      int n = in.nextInt();
      int[] nums = new int[n];
      for (int j = 0; j < n; j++) {
        nums[j] = in.nextInt();
      }
      //TODO
      System.out.println(valueDropped(nums));
    }
  }
}
