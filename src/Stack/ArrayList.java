package Stack;


import LinkedList.AbstractList;

public class ArrayList<E> extends AbstractList<E> {

    int size = 0;   //元素的数量
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;//初始容量
    private static final int ELEMENT_NOT_FOUND = -1;//常量， 索引异常时返回

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];
    }
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 统计元素数量
     * @return 返回元素数量
     */
    public int size() {
        return size;
    }

    /**
     * 判断这个动态数组是否为null
     * @return 返回true表示为null， false则不为null
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含这个元素
     * @param element 传入需要比较的元素
     * @return 返回true表示包含 ， false表示不包含
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 获取这个索引位置的元素
     * @param index 传入索引
     * @return 返回index对应的元素
     */
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 在index位置插入一个元素,替换掉之前的元素
     * @param index 索引位置
     * @param element 要插入的元素
     * @return 返回原来的元素
     */
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 添加元素到尾部
     * @param element 需要添加的元素
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 在某一个位置添加元素
     * @param index 在index插入元素
     * @param element 插入元素值
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size+1);
        for (int i=size-1; i>=index; i--) {
            elements[i+1] = elements[i];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 移除这个索引index对应的元素
     * @param index 传入的元素，当做索引
     * @return 返回删除的这个元素值
     */
    public E remove(int index) {
        rangeCheck(index);
        E oldElement = elements[index];
        for (int i=index+1; i<=size-1; i++) {
            elements[i - 1] = elements[i];
        }
        elements[size--] = null;

        trim();
        return oldElement;
    }

    /**
     * 查看一个元素在这个动态数组中的位置
     * @param element 传入一个元素
     * @return 返回该元素对应的索引值， 如果不存在该元素，则返回-1
     */
    public int indexOf(E element) {
        if (element == null) {
            for (int i=0; i<size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }else {
            for (int i=0; i<size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        for (int i=0; i<size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("size=");
        builder.append(size);
        builder.append(", [");
        for (int i=0; i<size; i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append(elements[i]);
        }
        builder.append("]");
        return builder.toString();
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
        throw new IndexOutOfBoundsException("数组下标溢出："+"index="+index+"，size="+size);
    }

    /**
     * 扩容
     * @param capacity 容量
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) {
            return;
        }
        int newCapacity = oldCapacity >> 1 + oldCapacity;//每次扩容1.5倍
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i=0; i<size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    /**
     * 锁容机制，防止占用内存
     */
    private void trim() {
        int capacity = elements.length;

        if ((size >= capacity >> 2) && capacity <= DEFAULT_CAPACITY) {
            return ;
        }
        //剩余空间挺多 ,直接缩小为原来一半
        int newCapacity = capacity >> 1;
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i=0; i<size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}
