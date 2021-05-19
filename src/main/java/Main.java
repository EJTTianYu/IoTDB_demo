

public class Main {


  public TreeNode changeTree(TreeNode root) {
    if (root == null) {
      return null;
    }
    TreeNode left = changeTree(root.left);
    TreeNode right = changeTree(root.right);
    if (left != null) {
      left.left = root;
      left.right = null;
    }
    if (right != null) {
      right.left = root;
      right.right = null;
    }

    root.left = null;
    root.right = null;

    return root;
  }

  public static void main(String[] args) {
    // 深度为2
    // root = null
    // root.left = null && root.right = TreeNode
    // root.right = null && root.left = TreeNode
    // 深度为3

    TreeNode root = new TreeNode(1);
    TreeNode left = new TreeNode(2);
    TreeNode right = new TreeNode(3);
    root.left = left;
    root.right = right;
    new Main().changeTree(root);
  }

}
