public class DB<Relation>
{
	Node<Relation> front;
	Node<Relation> first;
	int size;
	
	public DB() {
		front = null;
		size = 0;
	}
	
	/*Inserts a new functional dependency into the list.*/
	public void insert(Relation relation) {
		front = new Node<Relation>(relation, front);
		size++;
		if(size == 1)
		{
			first = front;
		}
	}
	
	/*Returns the next functional dependency in the list.*/
	public Node<Relation> getNext()
	{
		return front.next;
	}
	
	public void reset()
	{
		front = first;
	}
	
	public int size() {
		return size;
	}

}
