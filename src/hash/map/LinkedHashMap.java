package hash.map;

import java.io.PrintStream;

public class LinkedHashMap<K, V> extends HashMap2<K, V> {

    private LinkedNode<K, V> first;
    private LinkedNode<K, V> last;

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K, V> node = new LinkedNode<>(key, value, parent);
        if (first == null) {
            first = last = node;
        }else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        return node;
    }

    @Override
    public void traversal(Visitor<K, V> visistor) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (visistor.visit(node.key, node.value)) {
                return;
            }
            node = node.next;
        }
    }

    public void afterRemove(Node<K, V> willNode, Node<K, V> removeNode) {
        LinkedNode<K, V> linkedWillNode = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> linkedRemoveNode = (LinkedNode<K, V>) removeNode;
        if (linkedWillNode != linkedRemoveNode) {
            //度为2的节点
            LinkedNode<K, V> temp = linkedWillNode.prev;
            linkedWillNode.prev = linkedRemoveNode.prev;
            linkedRemoveNode.prev = temp;
            if (linkedWillNode.prev == null) {
                first = linkedWillNode;
            }else {
                linkedWillNode.prev.next = linkedWillNode;
            }
            if (linkedRemoveNode.prev == null) {
                first = linkedRemoveNode;
            }else {
                linkedRemoveNode.prev.next = linkedRemoveNode;
            }
            //交换next
            temp = linkedWillNode.next;
            linkedWillNode.next = linkedRemoveNode.next;
            linkedRemoveNode.next = temp;
            if (linkedWillNode.next == null) {
                last = linkedWillNode;
            }else {
                linkedWillNode.next.prev = linkedWillNode;
            }
            if (linkedRemoveNode.next == null) {
                last = linkedRemoveNode;
            }else {
                linkedRemoveNode.next.prev = linkedRemoveNode;
            }
        }
        if (removeNode == null) {
            return;
        }
        LinkedNode<K, V> prevLinkedNode = linkedRemoveNode.prev;
        LinkedNode<K, V> nextLinkedNode = linkedRemoveNode.next;
        if (prevLinkedNode == null) {
            first = nextLinkedNode;
        } else {
            prevLinkedNode.next = nextLinkedNode;
        }
        if (nextLinkedNode == null) {
            last = prevLinkedNode;
        } else {
            nextLinkedNode.prev = prevLinkedNode;
        }
    }

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    private static class LinkedNode<K, V> extends Node<K, V> {

        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
