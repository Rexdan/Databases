
public class Relation
{
	/*A relation is a set of attributes and 
	 * can be implemented easily using an 
	 * array A of integers with values of 0 or 1. 
	 * If A[i]=1 means that the attribute with 
	 * ASCII i is in the relation.*/
	int [] arr;
	
	public Relation()
	{
		
	}

	public Relation(int size)
	{
		/*Allocating dynamic memory for the 'relation'*/
		this.arr = new int[size];
	}

}
