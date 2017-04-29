
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
	
	public Relation(int [] attr)
	{
		this.attr = attr;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Relation r;
		if(o instanceof Relation)
		{
			r = (Relation)o;
		}
		else return false;
		String s1, s2;
		s1 = this.toString();
		s2 = r.toString();
		if(s1.equals(s2)) return true;
		return false;
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
		toReturn = toReturn.substring(0, toReturn.length()-1);
		return toReturn;
	}
}
