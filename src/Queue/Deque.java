package Queue;

public class Deque<E> {

    private LinkedList<E> list = new LinkedList<E>();
    public int size() // 元素的数量
    {
        return list.size;
    }

    public boolean isEmpty() // 是否为空
    {
        return list.isEmpty();
    }

    public void clear() // 清空
    {
        list.clear();
    }

    public void enQueueRear(E element) // 从队尾入队
    {
        list.add(element);
    }

    public E deQueueFront() // 从队头出队
    {
        return list.remove(0);
    }

    public void enQueueFront(E element) // 从队头入队
    {
        list.add(0, element);
    }

    public E deQueueRear() // 从队尾出队
    {
        return list.remove(list.size - 1);
    }

    public E front() // 获取队列的头元素
    {
        return list.get(0);
    }

    public E rear() // 获取队列的尾元素
    {
        return list.get(list.size - 1);
    }
}
