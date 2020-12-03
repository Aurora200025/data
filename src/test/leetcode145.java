package test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class leetcode145 {

    public List<Integer> postorderTraversal(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        postorder(root, list);
//        return list;
        List<Integer> list = new ArrayList<>();
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (root.right == null || root.right == node) {
                list.add(root.val);
                node = root;
                root = null;
            }else {
                stack.push(root);
                root = root.right;
            }
        }
        return list;
    }

    public void postorder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        postorder(node.left, list);
        postorder(node.right, list);
        list.add(node.val);
    }
}
