import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	
	public static Relation closure(FD fd, FDList fdl, Relation totalAttr)
	{
		//System.out.println("The first element in fdl; first check: " + fdl.head.data);
		Node<FD> first = fdl.head;
		FD firstFD = fd;
		System.out.println("In closure.");
		FD ptr = fd;
		
		Relation closureR = new Relation();
		
		while(ptr != null)
		{
			System.out.println("Current FD: " + ptr);
			Relation r1 = ptr.lhs;
			Relation r2 = ptr.rhs;
			closureR.insert(r1);
			FD chk = ptr;
			while(chk != null)
			{
				
			}
			ptr = fdl.getNext().data;
			if(ptr.equals(firstFD)) break;
		}
		fdl.head = first;
		return null;
	}
	
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
		/* If r contains everything that is in the lhs of f,
		 * then this passes the first condition.*/
		System.out.println("BCNF Check - Relation: " + r);
		System.out.println("BCNF Check - Funcitonal Dependency: " + fd);
		
		if(!(r.contains(fd.lhs)))
		{
			System.out.println("BCNF violation. Violates first check.");
			return true;
		}
		
		Relation temp = unionR(fd.lhs, fd.rhs);
		/* If everything in f is not a super set of r,
		 * then this passes the second check.
		 */
		if(!(temp.contains(r)))
		{
			System.out.println("BCNF violation. Violates second check.");
			return true;
		}
		System.out.println("Returning false.");
		return false;
	}
	
	public static DB bcnf(Relation r, FDList fdl)
	{
		DB db = new DB();
		Stack<Relation> s = new Stack<Relation>();
		s.push(r);
		int count = 0;
		
		
		while(!s.isEmpty())
		{
			Relation a = s.pop();
			System.out.println("Current relation: " + a);
			if(count == 3) break;
			boolean violation = false;
			fdl.reset();
			FD fd;
			while((fd = fdl.getNext().data) != null && !violation)
			{
				closure(fd, fdl, a);
				if(bcnfViolation(a, fd))
				{
					violation = true;
				}
				count ++;
				if(fd.equals(fdl.tail.data)) break;
			}
			if(!violation)
				db.insert(a);
			else
			{
				Relation union = unionR(fd.lhs, fd.rhs);
				System.out.println(fd);
				s.push(union);
				System.out.println("UNION: " + union);
				Relation diff = diffR(a, fd.rhs);
				s.push(diff);
				System.out.println("DIFFERENCE: " + diff);
			}
		}
		return db;
	}
	public static void main(String[] args) throws IOException{
		
		/*System.out.println("This driver program will run BCNF decomposition on a set of functional dependencies and a given relation.");
		System.out.println("It will take a file name as its input in the following format:");
		System.out.println("A B C D //This is the relation and the first line of the file.");
		System.out.println("A -> B D //This is the functional dependency and will come after the relation.");
		System.out.println("The file containing this information must be in the *ROOT* of the project folder.");
		System.out.print("Please specify the file name: ");
		
		Scanner sc = new Scanner(System.in);
		String fd = sc.nextLine();
		sc.close();*/
		
		String fd = "test.txt";
		
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
		
		if(br != null) br.close();
		if(fr != null) fr.close();
	}
}
