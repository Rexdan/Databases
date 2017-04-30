
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
	
	public Relation()
	{
		attr = new int[26];
	}
	
	public Relation(int [] attr)
	{
		this.attr = attr;
	}
	/*Only being used for one particular method in the driver.*/
	public void insert(Relation r)
	{
		int arr[] = r.attr;
		for(int i = 0; i < attr.length; i++)
		{
			if(arr[i] == 1 && attr[i] == 0)
			{
				attr[i] = 1;
			}
		}
	}
	
	public boolean contains(Object o)
	{
		Relation r;
		if(o instanceof Relation)
		{
			r = (Relation)o;
		}
		else return false;
		
		int [] r1 = this.attr;
		int [] r2 = r.attr;
		int countr1 = 0;
		int countBoth = 0;
		
		for(int i = 0; i < r2.length; i++)
		{
			if(r2[i] == 1) countr1++;
		}
		
		for(int i = 0; i < r1.length; i++)
		{
			if(r1[i] == r2[i]) countBoth++;
		}
		
		if(countr1 > countBoth)	
			return false;
		return true;
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
