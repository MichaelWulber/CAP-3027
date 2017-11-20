package Plant;

public class Node<T> {
    public Node next = null;
    public T value;

    public Node(T value){
        this.value = value;
    }
}
