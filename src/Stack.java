import java.util.NoSuchElementException;

class StackNode<T> {
	T data;
	StackNode<T> next;
	
	public StackNode(T data, StackNode<T> next) {
		this.data = data;
		this.next = next;
	}
	
	public String toString() {
		return data.toString();
	}
}

public class Stack<T> {

	StackNode<T> front;
	int size;
	
	public Stack() {
		front = null;
		size = 0;
	}
	
	public void push(T item) {
		front = new StackNode<T>(item, front);
		size++;
	}
	
	public T pop() 
	throws NoSuchElementException {
		if (front == null) {
			throw new NoSuchElementException();
		}
		T temp = front.data;
		front = front.next;
		size--;
		return temp;
	}
	
	public boolean isEmpty() {
		return front == null;
	}
	
	public int size() {
		return size;
	}
	
	public void clear() {
		front = null;
		size = 0;
	}
	
}
