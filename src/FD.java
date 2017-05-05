public class FD
{
	Relation lhs;
	Relation rhs;
	
	public FD()
	{
		this.lhs = null;
		this.rhs = null;
	}

	public FD(Relation lhs, Relation rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}
	
	@Override
	public String toString()
	{
		return lhs + "-> " + rhs;
	}
}
