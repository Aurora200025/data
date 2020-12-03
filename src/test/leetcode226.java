package test;


import java.util.LinkedList;
import java.util.Queue;

public class leetcode226 {

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }

//    public TreeNode invertTree(TreeNode root) {
//        if (root == null) {
//            return root;
//        }
//        invertTree(root.left);
//        TreeNode node = root.left;
//        root.left = root.right;
//        root.right = node;
//        invertTree(root.left);
//        return root;
//    }

//    public TreeNode invertTree(TreeNode root) {
//        if (root == null ) {
//            return root;
//        }
//        TreeNode node = root.left;
//        root.left = root.right;
//        root.right = node;
//        invertTree(root.left);
//        invertTree(root.right);
//        return root;
//    }
//
    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
}
