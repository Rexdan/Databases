import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	static FDList origList;
	
	public static Relation closure(FD fd, FDList fdl)
	{
		/*For reseting the fdl at the end of the function.*/
		Node<FD> first = fdl.head;
		FD firstFD = fd;
		FD ptr1 = fd;
		FD ptr2;
		Relation closureR = new Relation();
		Relation r1 = ptr1.lhs;
		Relation r2 = ptr1.rhs;
		closureR.insert(r1);
		closureR.insert(r2);
		ptr2 = ptr1;
		ptr1 = fdl.getNext().data;
		
		while(ptr1 != null)
		{
			/*Basically just checking if they share elements. ALL of the lhs needs to appear in in the rhs.*/
			if(ptr1.lhs.subset(ptr2.rhs))
			{
				closureR.insert(ptr1.rhs);
			}
			
			/*if(ptr2.rhs.intersect(ptr1.lhs) != null)
			{
				closureR.insert(ptr1.rhs);
			}*/
			ptr2 = ptr1;
			ptr1 = fdl.getNext().data;
			if(ptr1.equals(firstFD)) break;
		}
		fdl.head = first;
		return closureR;
	}
	
	public static boolean bcnfViolation(Relation r, FD fd, FDList fdl)
	{
		Relation a = closure(fd, origList);
		System.out.println("BCNF Check - Relation: " + r);
		System.out.println("BCNF Check - Closure: " + a);
		System.out.println("BCNF Check - Funcitonal Dependency: " + fd);
		
		Relation intersect = a.intersect(r);
		/*If our closure contains everything from the relation that was fed into the method.*/
		if(intersect.equals(r))
		{
			System.out.println("No BCNF violation.");
			return false;
		}
		else 
		{
			System.out.println("BCNF violation.");
			return true;
		}
	}
	
	public static DB bcnf(Relation r, FDList fdl)
	{
		DB db = new DB();
		Stack<Relation> s = new Stack<Relation>();
		s.push(r);
		
		while(!s.isEmpty())
		{
			System.out.println("**************************ENTERING DECOMPOSITION**************************");
			Relation a = s.pop();
			System.out.println("Current relation: " + a);
			/*Case for when we have no more FD's and need to add everything that we have so far.*/
			if(fdl.size == 0)
			{
				db.insert(a);
				continue;
			}
			boolean violation = false;
			fdl.reset();
			FD fd = new FD();
			FD fdv = new FD(); //Needed because while loop will give next FD after break.
			while((fd = fdl.getNext().data) != null && !violation)
			{
				if(bcnfViolation(a, fd, fdl))
				{
					violation = true;
					fdv = fd;
				}
				if(fd.equals(fdl.tail.data)) break;
			}
			if(!violation)
			{
				db.insert(a);
			}
			else
			{
				System.out.println("VIOLATING FUNCTIONAL DEPENDENCY: " + fdv);//May have to be the CLOSURE of the fdv...
				Relation union = fdv.lhs.union(fdv.rhs);
				Relation diff = a.diff(fdv.rhs);
				System.out.println("PUSHING DIFFERENCE: " + diff);
				System.out.println("PUSHING UNION: " + union); //Should be whatever is on the rhs that gets removed.
				s.push(union);
				s.push(diff);
				fdl.reset();
				fdl.getNext();
				fdl.remove(fdv);/*Needed to remove the functional dependency from the list*/
			}
			System.out.println("**************************EXITING DECOMPOSITION**************************");
		}
		return db;
	}
	public static void main(String[] args) throws IOException{
		
		System.out.println("This driver program will run BCNF decomposition on a set of functional dependencies and a given relation.");
		System.out.println("It will take a file name as its input in the following format:");
		System.out.println("A B C D //This is the relation and the first line of the file.");
		System.out.println("A -> B D //This is the functional dependency and will come after the relation.");
		System.out.println("The file containing this information must be in the *ROOT* of the project folder.");
		System.out.print("Please specify the file name: ");
		
		Scanner sc = new Scanner(System.in);
		String fd = sc.nextLine();
		sc.close();
		
		//String fd = "test2.txt";
		
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
		origList = new FDList();
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
			origList.insert(temp);
		}
		
		DB db = bcnf(totalAttr, fdl);
		System.out.println("Relations Before BCNF Decomposition: " + "{" + totalAttr + "}");
		System.out.print("Relations After BCNF Decomposition: ");
		db.traverse();
		if(br != null) br.close();
		if(fr != null) fr.close();
	}
}
