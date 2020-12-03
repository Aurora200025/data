package Stack;



public class Stack<E> {

    private ArrayList<E> list = new ArrayList<E>();

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
     * 入栈
     */
    public void push(E element) {
        list.add(element);
    }

    /**
     * @return 出栈，返回该元素
     */
    public E pop() {
        return list.remove(list.size - 1);
    }

    /**
     * @return 获取栈顶元素
     */
    public E top() {
        return list.get(list.size - 1);
    }

    /**
     * 清空栈
     */
    public void clear() {
        list.clear();
    }
}
