package test;

public class leetcode237 {

    public void deleteNode(ListNode node) {

        int listNode = node.val;
        while (node != null) {
            if (node.val == listNode) {
                node.val = node.next.val;
                node.next = node.next.next;
            }
            node = node.next;
        }
    }

    private class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
}
