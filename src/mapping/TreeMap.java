package mapping;


import mapping.tree.BinaryTree;

import java.security.Key;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class TreeMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    protected Integer size=0;
    protected Node<K, V> root; //根节点
    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }
    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * @return 元素的数量
     */
    public int size() {
        return size;
    }

    /**
     * @return  是否为空
     */
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * 清空所有元素
     */
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);
        if (root == null) {
            root = new Node<>(key, value, null);
            afterPut(root);
            size++;
            return null;
        }
        //添加的不是第一个节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        do {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            }else if (cmp < 0) {
                node = node.left;
            }else { //cmp == 0
                node.key = key;
                V oldValue = value;
                node.value = value;
                return oldValue;
            }
        } while (node != null);
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        }else {
            parent.left = newNode;
        }
        size++;
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    private V remove(Node<K, V> node) {
        if (node == null) {
            return null;
        }
        V oldValue = node.value;
        //节点不为null
        if (node.hasTwoChildren()) {//度为2的节点
            Node<K, V> s = successor(node);
//            System.out.println(s);
            node.key = s.key;
            node.value = s.value;
            node = s;
        }
        //node的度为1或者为0
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {//度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null) {//node是度为1的节点并且是根节点
                root = replacement;
            }else if (node == node.parent.left) {
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            afterRemove(replacement);
        }else if (node.parent == null) {//叶子节点同时也是根节点
            root = null;
            afterRemove(node);
        }else {//叶子节点但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            afterRemove(node);
        }
        size--;
        return oldValue;
    }

    protected void afterRemove(Node<K, V> node) {
//        if (isRed(node)) {//如果删除节点为红色，不用处理
//            return;
//        }
        //如果删除节点为红色 或者用来取代node的子节点为红色
        if (isRed(node)) {
            black(node);
            return;
        }
        Node<K, V> parent = node.parent;
        //则删除为黑色叶子节点--根节点
        if (parent == null) {
            return;
        }
        //不是根节点--黑色
        boolean isLeftNull = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = isLeftNull ? parent.right : parent.left;
        if (isLeftNull) { //被删除节点在左边，兄弟节点在右边
            if (isRed(sibling)) {   //兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                sibling = parent.right;
            }
            // 来到这里，兄弟节点为黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点-不可以借东西给你
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            }else {//至少一个红色子节点--可以借元素
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        }else { //被删除节点在右边，兄弟节点在左边
            if (isRed(sibling)) {   //兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling = parent.left;
            }
            // 来到这里，兄弟节点为黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点-不可以借东西给你
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            }else {//至少一个红色子节点--可以借元素
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    /**
     * @return 返回node的前驱节点
     */
    protected Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) {
            return node;
        }
        Node<K, V> pLeft = node.left;
        if (pLeft!= null) {
            //先找第一个左节点，然后找到最后一个右节点

            while (pLeft != null) {
                pLeft = pLeft.right;
            }
            return pLeft;
        }
        //从父节点中寻找
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //循环结束--两种情况
        //1.node.parent == null
        //或者 node.parent.right == node
        return node.parent;
    }

    /**
     * @return 返回node的后继节点
     */
    protected Node<K, V> successor(Node<K, V> node) {
        if (node == null) {
            return node;
        }
        Node<K, V> pRight = node.right;
        if (pRight != null) {
            while (pRight.left != null) {
                pRight = pRight.left;
            }
            return pRight;
        }
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            if (valEquals(value, node.value)) {
                return true;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visistor) {
        if (visistor == null) {
            return;
        }
        traversal(root, visistor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visistor) {
        if (node == null || visistor.stop) {
            return;
        }
        traversal(node.left, visistor);
        if (visistor.stop) {
            return;
        }
        visistor.visit(node.key, node.value);
        traversal(node.right, visistor);
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        if (parent == null) {//如果是添加的根节点，则直接染成黑色后返回
            black(node);
            return;
        }
        if (isBlack(parent)) {//如果父节点是黑色直接返回
            return;
        }
        //判断叔父节点-uncle
        Node<K, V> uncle = parent.sibling();
        Node<K, V> grand = parent.parent;
        if (isRed(uncle)) { //uncle是红色的话
            black(parent);
            black(uncle);
            afterPut(red(grand));   //相当于上溢，就把grand染红当成新节点
            return;
        }
        //叔父节点不是红色
        if (parent.isLeftChild()) {//L
            red(grand);
            if (node.isLeftChild()) { //LL
                black(parent);
//                red(grand);
//                rotateRight(grand);
            }else { //LR
                black(node);
//                red(grand);
                rotateLeft(parent);
//                rotateRight(grand);
            }
            rotateRight(grand);
        }else { //R
            red(grand);
            if (node.isLeftChild()) { //RL
                black(node);
//                red(grand);
                rotateRight(parent);
//                rotateLeft(grand);
            }else { //RR
                black(parent);
//                red(grand);
//                rotateLeft(grand);
            }
            rotateLeft(grand);
        }
    }

    /**
     * @return 判断v1 和 v2 是否相等--防止value为null的情况发生
     */
    private boolean valEquals(V value1, V value2) {
        return value1 == null ? value2 == null : value1.equals(value2);
    }

    /**
     * @param grand 左旋转
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * @param grand 右旋转
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 左右旋转中需要做的事情
     */
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        //让parent成为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        }else {
            //父节点为null 即grand是root节点
            root = parent;
        }
        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }
        //更新grand的parent
        grand.parent = parent;
    }
    /**
     * @param node 为该节点染色
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) {
            return null;
        }
        node.color = color;
        return node;
    }

    /**
     * @param node 将该节点染成红色
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * @param node 将该节点染成黑色
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * @param node 在红黑树中如果是null节点则为黑色，判断是否为null
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * @param node 判断颜色是否属于黑色
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * @param node 判断颜色是否属于红色
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }


    /**
     * @param key 检测所传的参数key是否为null
     */
    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("参数不能为null");
        }
    }

    /**
     * @return 找到节点node
     */
    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) {//则相等
                return node;
            }
            if (cmp > 0) {//element > node.element
                node = node.right;
            }else { //cmp < 0 element < node.element
                node = node.left;
            }
        }
        return null;
    }
    
    /**
     * 比较元素大小
     * @return 返回值等于0，则e1=e2； 返回值大于0，则e1>e2； 返回值小于0，则e1<e2
     */
    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        return ((Comparable<K>) k1).compareTo(k2);
    }
    /**
     * 直接将它当成一颗红黑树来实现，效率会更高
     * @param <K> key
     * @param <V> value
     */
    private static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;

        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * @return 判断是不是叶子节点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * @return 判断是不是有两个子节点
         */
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        /**
         * @return 判断是否为左子树
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * @return 返回兄弟节点
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
}
