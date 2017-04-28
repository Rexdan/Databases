public class FDList
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
		Node<FD> node = new Node<FD>(fd);
		if(size == 0)
		{
			head = node;
			tail = node;
			node.next = head;
		}
		else
		{
			Node<FD> n = new Node<FD>(fd);
            tail.next =n;
            tail=n;
            tail.next = head;
		}
		size++;
	}
	
	/*Returns the next functional dependency in the list.*/
	public Node<FD> getNext()
	{
		Node<FD> toReturn = head.next;
		head = head.next;
		return toReturn;
	}
	
	/* Resets the CLL so that our tail is now set to the head.
	 * Calling getNext will result in choosing the first element.
	 */
	public void reset()
	{
		head = tail;
		System.out.println(head);
	}
	
	public void traverse(){
        Node<FD> temp = head;
        if(size<=0){
            System.out.print("List is empty");
        }else{
            do {
                System.out.println(temp.data);
                temp = temp.next;
            }
            while(temp!=head);
        }
    }
}
