package Queue;

public class TestMain {

    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<Integer>();
        queue.enQueueFront(11);
        queue.enQueueFront(22);
        queue.enQueueFront(33);
        queue.enQueueFront(44);
        while (!queue.isEmpty()) {
            System.out.print(queue.deQueueRear()+"\t");
        }
    }
}
