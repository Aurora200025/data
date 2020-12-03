package trie;


import java.util.HashMap;

/**
 * @author Aurora
 */
public class Trie<V> {

    /**
     * 根节点
     */
    private Node<V> root ;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root.getChildren().clear();
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.word;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    public V add(String key, V value) {
        keyCheck(key);
        if (root == null) {
            root = new Node<>(null);
        }
        Node<V> node = root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            char c = key.charAt(i);
            //判断有没有子节点
            boolean emptyChild = node.children == null;
            Node<V> newNode = emptyChild ? null : node.getChildren().get(c);
            if (newNode == null) {
                newNode = new Node<>(node);
                newNode.character = c;
                node.children = emptyChild ? new HashMap<>() : node.children;
                node.getChildren().put(c, newNode);
            }
            node = newNode;
        }
        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key) {
        Node<V> node = node(key);
        //如果不是单词结尾，不用做任何处理
        if (node == null || !node.word) {
            return null;
        }
        size--;
        V oldValue = node.value;
        //如果还有子节点的话
        if (node.children != null && !node.children.isEmpty()) {
            node.word = false;
            node.value = null;
            return oldValue;
        }
        //则没有子节点
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            if (!parent.children.isEmpty()) {
                break;
            }
            node = parent;
        }

        return oldValue;
    }

    public boolean starsWith(String prefix) {
        return node(prefix) != null;
    }

    //====================================

    /**
     * @param key 根据key找到对应的节点
     * @return V value
     */
    private Node<V> node(String key) {
        if (root == null) {
            return null;
        }
        keyCheck(key);
        Node<V> node = root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            //如果根节点都是null的，就没必要拿到字符了
            if (node == null || node.children == null || node.children.isEmpty()) {
                return null;
            }
            char c = key.charAt(i);
            node = node.getChildren().get(c);
        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    /**
     * 字典树 (前缀树)
     * @param <V>
     */
    private static class Node<V> {

        //每一个节点的子节点
        HashMap<Character, Node<V>> children;
        V value;
        //判断是否为单词的结尾
        boolean word;
        Node<V> parent;
        Character character;

        public Node(Node<V> parent) {
            this.parent = parent;
        }

        public HashMap<Character, Node<V>> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }
}
