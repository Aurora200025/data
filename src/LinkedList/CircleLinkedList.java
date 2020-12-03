package LinkedList;

/**
 * 双向链表循环链表
 * @param <E>
 */
public class CircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;
    private Node<E> last;
    private static final int ELEMENT_NOT_FOUND = -1;


    /**
     * 返回索引所对应的元素
     * @param index 需要传入的索引
     * @return 返回这个节点的元素值
     */
    public E get(int index) {
        return node(index).element;
    }

    /**
     * 在index位置插入一个元素,替换掉之前的元素
     * @param index 索引位置
     * @param element 要插入的元素
     * @return 返回原来的元素
     */
    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    /**
     * 在某一个位置添加元素
     * @param index 在index插入元素
     * @param element 插入元素值
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);//边界检查
        if (index == size) {
            Node<E> oldLast = last;
            last = new Node<E>(oldLast, element, null);
            if (oldLast == null) {
                //链表添加的第一个元素
                first = last;
                first.next = first;
                first.prev = first;
            }else {
                oldLast.next = last;
                first.prev = last;
            }
        }else {
            Node<E> next = node(index);//该节点为新添加节点的下一个
            Node<E> prev = next.prev; // 新添加节点的上一个
            Node<E> node = new Node<E>(prev, element, next);
            next.prev = node;
            prev.next = node;
            if (next == first) {//判断相当于index == 0
                first = node;
            }
        }
        size++;
    }

    /**
     * 移除这个索引index对应的元素
     * @param index 传入的元素，当做索引
     * @return 返回删除的这个元素值
     */
    public E remove(int index) {
        //防止链表中没有元素时调用remove导致NullPointException异常
        rangeCheck(index);
        Node<E> node = first;
        if (size == 1) {
            first = null;
            last = null;
        }else {
            node = node(index);
//        Node<E> prev = node.prev;
//        Node<E> next = node.next;
//
//        if (prev == null) {
//             first = next;
//        }else {
//            prev.next = next;
//        }
//        if (next == null) {
//              last = prev;
//        }else {
//            next.prev = prev;
//        }
            node.prev.next = node.next;
            node.next.prev = node.prev;
            if (index == 0) { //node = first
                first = node.next;
            }
            if (node == last) {
                last = node.prev;
            }
        }
        size--;
        return node.element;
    }

    /**
     * 查看一个元素在这个动态数组中的位置
     * @param element 传入一个元素
     * @return 返回该元素对应的索引值， 如果不存在该元素，则返回-1
     */
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i=0; i<size; i++) {
                if (node.element == null) {
                    return i;
                }
                node = node.next;
            }
        }else {
            for (int i=0; i<size; i++) {
                if (element.equals(node.element)) {
                    return i;
                }
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    /**
     * 获取链表对应索引的节点
     * @param index 索引
     * @return 返回那个节点
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        if (index < (size >> 2)) {
            Node<E> node = first;
            for (int i=0; i<index; i++) {
                node = node.next;
            }
            return node;
        }else {
            Node<E> node = last;
            for (int i=size-1; i>index; i--) {
                node = node.prev;
            }
            return node;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("size=");
        builder.append(size);
        builder.append(", [");
        Node<E> node = first;
        for (int i=0; i<size; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(node.element);
            node = node.next;
        }
        builder.append("]");
        return builder.toString();
    }

    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> prev;

        public Node(Node<E> prev, E element, Node<E> next) {
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }
}
