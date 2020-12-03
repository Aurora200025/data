package Queue;

public class Queue<E> {

    private LinkedList<E> list = new LinkedList<E>();
    /**
     * @return 返回元素的数量
     */
    public int size() {
        return list.size();
    }

    /**
     * @return 判断是否为null， true表示是null，false不是null
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * @param element 入队
     */
    public void enQueue(E element) {
        list.add(element);
    }

    /**
     * @return 出队
     */
    public E deQueue() {
        return list.remove(0);
    }

    /**
     * @return 获取队头元素
     */
    public E front() {
        return list.get(0);
    }

    /**
     * 清空队列
     */
    public void clear() {
        list.clear();
    }
}
