import java.util.LinkedList;
import java.util.Queue;

class ListNode {

  int val;
  ListNode next;

  ListNode(int val) {
    this.val = val;
  }
}

class Utils {

  static ListNode transform(int[] nums) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    for (int num : nums) {
      current.next = new ListNode(num);
      current = current.next;
    }
    return dummy.next;
  }

  static void printListNode(ListNode head) {
    while (head != null) {
      System.out.print(head.val + ",");
      head = head.next;
    }
  }
}

class TreeNode {

  TreeNode left;
  TreeNode right;
  int val;

  TreeNode(int val) {
    this.val = val;
  }

}

public class Solution {

//  boolean isPinghengBinaryTree(TreeNode root) {
//    if (root == null) {
//      return true;
//    }
//    int leftDepth = depth(root.left);
//    int rightDepth = depth(root.right);
//    return Math.abs(leftDepth - rightDepth) <= 1 && isPinghengBinaryTree(root.left)
//        && isPinghengBinaryTree(root.right);
//  }
//
//  int depth(TreeNode root) {
//    if (root == null) {
//      return 0;
//    }
//    return 1 + Math.max(depth(root.left), depth(root.right));
//  }


}

class Stack {

  Queue<Integer> queue1 = new LinkedList<>();
  Queue<Integer> queue2 = new LinkedList<>();

  void put(int k) {
    if (queue1.isEmpty()) {
      transfrom(queue2, queue1);
    }
    queue1.add(k);
  }

  int peek() {
    if (queue2.isEmpty()) {
      transfrom(queue1, queue2);
    }
    return queue2.peek();
  }

  void transfrom(Queue<Integer> q1, Queue<Integer> q2) {
    while (!q1.isEmpty()) {
      q2.add(q1.poll());
    }
  }
}
