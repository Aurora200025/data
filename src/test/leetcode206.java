package test;

public class leetcode206 {

    public ListNode reverseList(ListNode head) {
        /*
         *递归
         */
//        if (head == null || head.next == null) {
//            return head;
//        }
//        ListNode node = reverseList(head.next);
//        head.next.next = head;
//        head.next = null;
//        return node;
        ListNode listNode = null;
        while (head != null) {
            ListNode node = head.next;
            head.next = listNode;
            listNode = head;
            head = node;
        }
        return head;
    }

    private class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            this.val = x;
        }
    }
}
