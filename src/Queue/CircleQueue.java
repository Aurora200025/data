package Queue;

import org.omg.CORBA.Object;

public class CircleQueue<E> {

    private int front; //默认为0
    private int size;
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() // 元素的数量
    {
        return size;
    }

    public boolean isEmpty() // 是否为空
    {
        return size == 0;
    }

    public void clear() // 清空
    {

    }

    public void enQueue(E element) // 入队
    {
        elements[size++] = element;
    }

    public E deQueueFront() // 从队头出队
    {
        return null;
    }

    public void enQueueFront(E element) // 从队头入队
    {
//        list.add(0, element);
    }

    public E deQueueRear() // 从队尾出队
    {
        return null;
    }

    public E front() // 获取队列的头元素
    {
        return elements[front];
    }

    public E rear() // 获取队列的尾元素
    {
        return null;
    }
}
