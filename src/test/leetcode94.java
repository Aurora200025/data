package test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class leetcode94 {

    public List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        inorder(list, root);
//        return list;

        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        TreeNode node = root;
        Deque<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            list.add(node.val);
            node = node.right;
        }
        return list;
    }

    public void inorder(List<Integer> list, TreeNode node) {
        if (node == null) {
            return;
        }
        inorder(list, node.left);
        list.add(node.val);
        inorder(list, node.right);
    }
}
