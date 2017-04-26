public class FDList<FD>
{
	Node<FD> front;
	int size;
	
	public FDList() {
		front = null;
		size = 0;
	}
	
	/*Inserts a new functional dependency into the list.*/
	public void insert(FD fd) {
		front = new Node<FD>(fd, front);
		size++;
	}
	
	/*Returns the next functional dependency in the list.*/
	public Node<FD> getNext()
	{
		return front.next;
	}
	
	public int size() {
		return size;
	}

}
