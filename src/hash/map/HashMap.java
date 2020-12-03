package hash.map;

import hash.printer.BinaryTreeInfo;
import hash.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V>[] table; //数组里放红黑树的根节点
    private static final int DEFAULT_CAPACITY = 1 << 4;


    public HashMap() {
        table = new Node[DEFAULT_CAPACITY]; //2^4==16
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }
        //清空这课数组
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Node<K, V> root = table[index]; //取出红黑树的根节点
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        //能来到这里就是根节点不为null--新增的key有hash冲突
        Node<K, V> parent = root;
        Node<K, V> node = root;
        K key1 = key;
        int cmp = 0;
        int hash1 = key1 == null ? 0 : key1.hashCode();
        Node<K, V> result = null;
        boolean searched = false;   //标记是否已经搜索过key
        while (node != null) {
            parent = node;
            K key2 = node.key;
            int hash2 = node.hash;
            if (hash1 > hash2) {
                cmp = 1;
            } else if (hash1 < hash2) {
                cmp = -1;
            } else if (Objects.equals(key1, key2)) {//hash值相等
                cmp = 0;
            }else if (
                    key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && (cmp = ((Comparable) key1).compareTo(key2)) != 0
            ) {
            } else if (!searched) { //hash不等，不具备可比较性，不equals
                //先扫描，在根据内存地址决定方向
                if (node.left != null && (result = node(node.left, key1)) != null
                        || node.right != null && (result = node(node.right, key2)) != null) {
                    //已经存在该key
                    node = result;
                    cmp = 0;
                } else {
                    searched = true;
                    //不存在该key
                    cmp = System.identityHashCode(key1) - System.identityHashCode(key2);
                }
            }else { //searched = true
                cmp = System.identityHashCode(key1) - System.identityHashCode(key2);
            }
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
        }
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

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) {
            return false;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visistor) {
        if (visistor == null || size == 0) {
            return;
        }
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visistor.visit(node.key, node.value)) {
                    return;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }
    //--------------------------------------
    //-------------私有方法------------------
    //--------------------------------------

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
            node.hash = s.hash;
            node = s;
        }
        //node的度为1或者为0
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);
        if (replacement != null) {//度为1的节点
            replacement.parent = node.parent;
            if (node.parent == null) {//node是度为1的节点并且是根节点
                table[index] = replacement;
            }else if (node == node.parent.left) {
                node.parent.left = replacement;
            }else {
                node.parent.right = replacement;
            }
            afterRemove(replacement);
        }else if (node.parent == null) {//叶子节点同时也是根节点
            table[index] = null;
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


    private Node<K, V> node(K key) {
        Node<K, V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }

    private Node<K, V> node(Node<K, V> node, K key1) {
        int hash1 = key1 == null ? 0 : key1.hashCode();
        Node<K, V> result = null;
        int cmp = 0;
        while (node != null) {
            int hash2 = node.hash;
            K key2 = node.key;
            //先比较hash值看存储位置
            if (hash1 > hash2) {
                node = node.right;
            } else if (hash1 < hash2) {
                node = node.left;
            } else if (Objects.equals(key1, key2)) {
                return node;
            } else if (
                    key1 != null && key2 != null
                    && key1.getClass() == key2.getClass()
                    && key1 instanceof Comparable
                    && (cmp = ((Comparable) key1).compareTo(key2)) != 0
            ) { //hash一样
                node = cmp > 0 ? node.right : node.left;
            } else if (
                    node.right != null
                    && (result = node(node.right, key1)) != null
            ) { //hash相等，不具备可比较性
                return result;
            }else {
                node = node.left;
            }
//            else if (
//                    node.left != null
//                    && (result = node(node.left, key1)) != null
//            ) {
//                return result;
//            }else {
//                return null;
//            }
        }
        return null;
    }
    /**
     *
     * @param k1 key1
     * @param k2 key2
     * @param hash1 key1的hashcode 码
     * @param hash2 key2的hashcode 码
     */
    private int compare(K k1, K k2, int hash1, int hash2) {
        int result = hash1 - hash2;
        if (result != 0) {
            return result;
        }
        //hash值相等--比较equals
        if (Objects.equals(k1, k2)) {//key相等，则覆盖
            return 0;
        }
        //来到这里意味着hash相等，但是key不一样
        if (k1 != null && k2 != null
                && k1.getClass() == k2.getClass()
                && k1 instanceof Comparable) {
            String key1Class = k1.getClass().getName();
            String key2Class = k2.getClass().getName();
            result = key1Class.compareTo(key2Class);
            if (result != 0) {//类名不相等
                return result;
            }
            //来到这里即为同一种类型
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }
        //同一种类型但是不具备可比较性
        //或者其中某个key为null
        //System.identityHashCode()--利用内存地址计算出来的hashcode
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    /**
     * @param key 根据key生成对应的索引
     * @return 返回这个索引
     */
    private int index(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        hash = hash ^ (hash >>> 16);
        return hash & (table.length - 1);
    }
    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
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
            table[index(grand)] = parent;
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


    //=======================================
    //=======================================
    //=======================================

    public void print() {
        if (size == 0) {
            return;
        }
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index= "+i+" 】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }
            });
            System.out.println("------------------------");
        }
    }

    /**
     * 直接将它当成一颗红黑树来实现，效率会更高
     * @param <K> key
     * @param <V> value
     */
    protected static class Node<K, V> {

        int hash;
        K key;
        V value;
        boolean color = RED;

        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
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

        @Override
        public String toString() {
            return "key=" + key +
                    "---value=" + value;
        }
    }
}
