package LinkedList;

/**
 * 单向链表
 * @param <E>
 */
public class SigLinkedList<E> extends AbstractList<E> {

    private Node<E> first;
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
        if (index == 0) {
            first = new Node<E>(element, first);
        }else {
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
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
        if (index == 0) {
            first = first.next;
        }else {
            Node<E> prev = node(index-1);
            node = prev.next;
            prev.next = prev.next.next;
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
    }

    /**
     * 获取链表对应索引的节点
     * @param index 索引
     * @return 返回那个节点
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
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

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
