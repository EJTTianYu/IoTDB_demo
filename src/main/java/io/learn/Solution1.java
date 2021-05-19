package io.learn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution1 {

  static Set<Integer> integerSet = new HashSet<>();
  static List<Integer> integerList = new ArrayList<>();
  static int n;

  static void generate() {
    for (int i = 1; i <= n; i++) {
      if (!integerSet.contains(i)) {
        insert(i);
      }
    }
  }

  static void insert(int num) {
    boolean isAdd = false;
    for (int i = 0; i < integerList.size(); i++) {
      if (num < integerList.get(i)) {
        integerList.add(i, num);
        isAdd = true;
        break;
      }
    }
    if (!isAdd) {
      integerList.add(num);
    }
  }

  static void outputResult() {
    for (int a : integerList) {
      System.out.print(a + " ");
    }
  }

  public static void main(String[] args) {
    System.out.println(Integer.MAX_VALUE);
    Scanner in = new Scanner(System.in);
    n = in.nextInt();
    int m = in.nextInt();
    for (int i = 0; i < m; i++) {
      int currentNum = in.nextInt();
      integerSet.add(currentNum);
      integerList.add(currentNum);
    }
    generate();
    outputResult();
  }
}



