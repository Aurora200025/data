package priorityQueue;


import priorityQueue.heap.BinaryHeap;

import java.util.Comparator;

/**
 * @author Aurora
 */
public class PriorityQueue<E> {

    private BinaryHeap<E> heap;

    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }

    public PriorityQueue() {
        this(null);
    }

    /**
     * @return 返回元素的数量
     */
    public int size() {
        return heap.size();
    }

    /**
     * @return 判断是否为null， true表示是null，false不是null
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * @param element 入队
     */
    public void enQueue(E element) {
        heap.add(element);
    }

    /**
     * @return 出队
     */
    public E deQueue() {
        return heap.remove();
    }

    /**
     * @return 获取队头元素
     */
    public E front() {
        return heap.replace(heap.get());
    }

    /**
     * 清空队列
     */
    public void clear() {
        heap.clear();
    }
}
