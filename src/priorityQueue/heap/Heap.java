package priorityQueue.heap;

/**
 * 二叉堆的基本接口设计
 */
public interface Heap<E> {

    int size(); //元素的数量

    boolean isEmpty();  //是否为null

    void clear();       //清空

    void add(E element);    //添加元素

    E get();    //获得堆顶元素

    E remove(); //删除堆顶元素

    E replace(E element);   //删除堆顶元素的同时插入一个新元素
}
