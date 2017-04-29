import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

	public static final String fd = "src/test.txt";
	
	public static Relation diffR(Relation r1, Relation r2)
	{
		int arr1[], arr2[];
		arr1 = r1.attr;
		arr2 = r2.attr;
		
		int arrF[] = new int[26];
		
		for(int i = 0; i < arrF.length; i++)
		{
			if(arr1[i] == 1 && arr2[i] == 0)
			{
				arrF[i] = 1;
			}
		}
		Relation r = new Relation(arrF);
		return r;
	}
	
	public static Relation unionR(Relation r1, Relation r2)
	{
		int arr1[], arr2[];
		arr1 = r1.attr;
		arr2 = r2.attr;
		
		int arrF[] = new int[26];
		
		for(int i = 0; i < arrF.length; i++)
		{
			if(arr1[i] == 1 || arr2[i] == 1)
			{
				arrF[i] = 1;
			}
		}
		Relation r = new Relation(arrF);
		return r;
	}
	
	public static boolean bcnfViolation(Relation r, FD fd)
	{
		
		if(!(r.toString().contains(fd.lhs.toString())))
		{
			System.out.println("BCNF violation. Violates first check.");
			return true;
		}
		
		Relation temp = unionR(fd.lhs, fd.rhs);
		
		if(!temp.equals(r))
		{
			System.out.println("BCNF violation. Violates second check.");
			return true;
		}
		System.out.println("Returning false.");
		return false;
	}
	
	public static DB bcnf(Relation r, FDList fdl)
	{
		DB db = null;
		Stack<Relation> s = new Stack<Relation>();
		s.push(r);
		
		while(!s.isEmpty())
		{
			Relation a = s.pop();
			boolean violation = false;
			fdl.reset();
			FD fd;
			
			while((fd = fdl.getNext().data) != null && !violation)
			{
				if(bcnfViolation(a, fd))
					violation = true;
			}
			
			if(!violation)
			{
				db.insert(a);
			}
			else
			{
				Relation union = unionR(fd.lhs, fd.rhs);
				s.push(union);
				Relation diff = diffR(a, fd.rhs);
				s.push(diff);
			}
		}
		return db;
	}
	public static void main(String[] args) throws IOException{
		BufferedReader br = null; FileReader fr = null;
		
		Relation totalAttr = null;
		
		FDList fdl = new FDList();

		fr = new FileReader(fd);
		br = new BufferedReader(fr);
		
		String line;
		
		/*Getting the list of attributes first.*/
		if((line = br.readLine()) != null)
		{
			totalAttr = new Relation(line);
		}
		
		/* Getting the initial functional dependencies.*/
		while((line = br.readLine()) != null)
		{
			Relation lhs, rhs;
			int lLen, rLen;
			lLen = line.indexOf('-') - 1;
			lhs = new Relation(line.substring(0, lLen));
			rLen = line.indexOf('>') + 2;
			rhs = new Relation(line.substring(rLen, line.length()));
			FD temp = new FD(lhs,rhs);
			fdl.insert(temp);
			
		}
		
		DB db = bcnf(totalAttr, fdl);

		/*Node<FD> ptr = fdl.head;
		int size = fdl.size-2;
		int count = 0;
		while(count < size)
		{
			System.out.println(ptr);
			ptr = fdl.getNext();
			count++;
		}
		
		System.out.println(fdl.head);
		fdl.reset();
		ptr = fdl.getNext();
		System.out.println("Resetting...");
		size = fdl.size;
		count = 0;
		while(count < size)
		{
			System.out.println(ptr);
			ptr = fdl.getNext();
			count++;
		}*/
		
		if(br != null) br.close();
		if(fr != null) fr.close();
	}
}
