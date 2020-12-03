package test;

import java.util.HashSet;
import java.util.Set;

public class leetcode141 {

    public boolean hasCycle(ListNode head) {

        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (head != null || head.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
        /*
        哈希表
         */
//        Set<ListNode> nodeSet = new HashSet<>();
//        while (head != null) {
//            if (!nodeSet.add(head)) {
//                return true;
//            }
//            head = head.next;
//        }
//        return false;
//        if (head == null || head.next == null) {
//            return false;
//        }
//        ListNode slow = head;
//        ListNode fast = head;
//        while (head.next.next != null) {
//            if (fast == null || fast.next == null) {
//                return false;
//            }
//            slow = slow.next;
//            fast = fast.next.next;
//            if (slow.val == fast.val) {
//                return true;
//            }
//        }
//        return false;
    }

    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }
}
