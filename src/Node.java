// generic Node class - T is an Object type, cannot be a primitive
public class Node<T> {
	T data;
	Node<T> next;
	
	public Node(T data) {
		this.data = data;
		this.next = null;
	}
	
	public String toString() {
		return data.toString();
	}
}


