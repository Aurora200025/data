package priorityQueue.heap;


import BinaryPile.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 默认为最大堆（大顶堆）
 * @param <E>
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < capacity; i++) {
                this.elements[i] = elements[i];
            }
            heapify();
        }
    }
    public BinaryHeap(E[] elements) {
        this(elements, null);
    }
    public BinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }
    public BinaryHeap() {
        this(null, null);
    }


    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        //让最后一位元素上滤
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        //堆顶元素
        E root = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return root;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            elements[0] = element;
            size++;
        }else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return root;
    }

    //====================================

    /**
     * 批量建队
     */
    private void heapify() {
        //第一种--自上而下的上滤--效率慢
        //第一种--自下而上的下滤
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is null");
        }
    }

    /**
     * @param index index处的元素下滤
     */
    private void siftDown(int index) {
        E element = elements[index];
        //非叶子节点索引
        int half = size >> 1;

        while (index < half) {
            // index位置为非叶子节点
            //左子节点的索引
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];
            int rightIndex = childIndex + 1;
            if (rightIndex < size && compare(elements[rightIndex], child) >= 0) {
                child = elements[childIndex = rightIndex];
            }

            if (compare(element, child) >= 0) {
                break;
            }
            //来到这儿说明子节点比父节点大
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = element;
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
        //每次扩容1.5倍
        int newCapacity = (oldCapacity >> 1) + oldCapacity;
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i=0; i<size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    /**
     * @param index index位置的元素上滤
     */
    private void siftUp(int index) {
        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];
            if (compare(element, parent) <= 0) {
                break;
            }
            //说明父节点小于element
            elements[index] = parent;
            //重新赋值index
            index = parentIndex;
        }
        elements[index] = element;
    }

    //-------------------------------------------
    //-------------实现打印接口--------------------------
    //-------------------------------------------

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = (int) node;
        index = (index << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = (int) node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        int index = (int) node;
        return elements[index];
    }
}
