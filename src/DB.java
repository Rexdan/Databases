public class DB
{
	Node<Relation> head;
	Node<Relation> tail;
	int size;
	
	public DB() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/*Inserts a new functional dependency into the list.*/
	public void insert(Relation Relation) {
		//System.out.println(Relation);
		//System.out.println("DB's SIZE: " + size);
		if(!has(Relation))
		{
			//System.out.println("SAFELY ADDED RELATION");
			Node<Relation> node = new Node<Relation>(Relation);
			if(size == 0)
			{
				head = node;
				tail = node;
				node.next = head;
			}
			else
			{
				Node<Relation> n = new Node<Relation>(Relation);
	            tail.next =n;
	            tail=n;
	            tail.next = head;
			}
			size++;
		}
	}
	
	/*Returns the next functional dependency in the list.*/
	public Node<Relation> getNext()
	{
		Node<Relation> toReturn = head.next;
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
	
	public boolean has(Relation r1)
	{
		Node<Relation> temp = head;
		Relation r2;
        if(size<=0){
            return false;
        }else{
            do {
                //System.out.println("Checking...");
                //System.out.println(r1);
                r2 = temp.data;
                //System.out.println(r2);
                if(r1.equals(r2))
                {
                	//System.out.println("Found a relation that's already in the DB");
                	return true;
                }
                temp = temp.next;
            }
            while(temp!=head);
        }
		return false;
	}
	
	public void traverse(){
        Node<Relation> temp = head;
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
