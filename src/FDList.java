public class FDList<FD>
{
	Node<FD> head;
	Node<FD> tail;
	int size;
	
	public FDList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/*Inserts a new functional dependency into the list.*/
	public void insert(FD fd) {
		//System.out.println(fd);
		Node<FD> node = new Node<FD>(fd, head);
		if(size == 0)
		{
			head = node;
			tail = node;
			node.next = head;
		}
		else
		{
			Node<FD> temp = head;
			node.next = temp;
			head = node;
			tail.next = head;
		}
		size++;
	}
	
	/*Returns the next functional dependency in the list.*/
	public Node<FD> getNext()
	{
		return head.next;
	}
	
	public void reset()
	{
		
	}
	
	public int size() {
		return size;
	}

}
