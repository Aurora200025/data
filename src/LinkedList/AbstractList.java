package LinkedList;

public abstract class AbstractList<E> implements List<E>{

    protected int size = 0;//元素的数量

    /**
     * 统计元素数量
     *
     * @return 返回元素数量
     */
    public int size() {
        return size;
    }

    /**
     * 判断该链表是否为null
     *
     * @return 返回true代表链表是null， false则不为null
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断链表包不包含该元素
     *
     * @param element 该元素的值
     * @return 返回true代表包含，false代表不包含
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 向元素尾部添加一个节点
     * @param element 添加节点的值
     */
    public void add(E element) {
        add(size, element);
    }
    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("数组下标溢出：" + "index=" + index + "，size=" + size);
    }
}
