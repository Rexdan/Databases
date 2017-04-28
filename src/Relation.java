
public class Relation
{
	/*A relation is a set of attributes and 
	 * can be implemented easily using an 
	 * array A of integers with values of 0 or 1. 
	 * If A[i]=1 means that the attribute with 
	 * ASCII i is in the relation.*/
	int [] attr;
	
	public Relation(String input)
	{
		this.attr = new int[26];
		for(int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			if(Character.isLetter(c))
			{
				int index = c - 65;
				attr[index] = 1;
			}
		}
	}
	
	@Override
	public String toString()
	{
		String toReturn = new String();
		for(int i = 0; i < attr.length; i++)
		{
			if(attr[i] == 1)
			{
				char c = (char)(i + 65);
				toReturn += c;
				toReturn += ' ';
			}
		}
		return toReturn;
	}
}
