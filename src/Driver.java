import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	static FDList origList;
	
	public static Relation closure(FD fd, FDList fdl)
	{
		//System.out.println("The first element in fdl; first check: " + fdl.head.data);
		Node<FD> first = fdl.head;
		FD firstFD = fd;
		//System.out.println("************************ENTERING CLOSURE************************");
		FD ptr1 = fd;
		FD ptr2;
		Relation closureR = new Relation();
		
		closureR = new Relation();
		Relation r1 = ptr1.lhs;
		Relation r2 = ptr1.rhs;
		closureR.insert(r1);
		closureR.insert(r2);
		//System.out.println("Current FD: " + ptr1);
		ptr2 = ptr1;
		//System.out.println("Previous FD: " + ptr2);
		ptr1 = fdl.getNext().data;
		//System.out.println("Current closure: " + closureR);
		
		while(ptr1 != null)
		{
			//System.out.println("Current FD: " + ptr1);
			//System.out.println("Previous FD: " + ptr2);
			
			/*Change to finding the intersect and then change contains to find subset.*/
			if(ptr2.rhs.intersect(ptr1.lhs) != null)
			{
				closureR.insert(ptr1.rhs);
			}
			//System.out.println("Current closure: " + closureR);
			ptr2 = ptr1;
			ptr1 = fdl.getNext().data;
			
			if(ptr1.equals(firstFD)) break;
			
		}
		fdl.head = first;
		//System.out.println(fdl.head.data);
		//System.out.println("************************EXITING CLOSURE************************");
		System.out.println("Returning closure: " + closureR);
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
		origList = fdl;
		
		
		
		FDList temp = new FDList();
		temp = fdl;
		System.out.println("***************************************TRAVERSING***************************************");
		fdl.traverse();
		System.out.println("***************************************REMOVING FD***************************************");
		temp.remove(temp.getNext().data);
		System.out.println("***************************************TRAVERSING***************************************");
		fdl.traverse();
		
		
		
		
		DB db = new DB();
		Stack<Relation> s = new Stack<Relation>();
		s.push(r);
		int count = 0;
		
		while(!s.isEmpty())
		{
			System.out.println("**************************ENTERING DECOMPOSITION**************************");
			Relation a = s.pop();
			System.out.println("Current relation: " + a);
			if(count == 12) break;
			boolean violation = false;
			fdl.reset();
			FD fd = new FD();
			FD fdv = new FD(); //Needed because while loop will give next FD after break.
			while((fd = fdl.getNext().data) != null && !violation)
			{
				System.out.println("Current functional dependency: " + fd);

				if(bcnfViolation(a, fd, fdl))
				{
					violation = true;
					fdv = fd;
				}
				count ++;
				if(fd.equals(fdl.tail.data)) break;
			}
			if(!violation)
				db.insert(a);
			else
			{
				/*
				 * This isn't that kind of union and difference...
				 * Should be something like this:
				 * 		Push the attributes that come after the violating fd.
				 * 		Then push the leftover fd's as attributes.
				 * 
				 * We are dealing with not ONE fd here, but rather the fd's that follow
				 * and include the current violating fd...
				 */
				System.out.println("VIOLATING FUNCTIONAL DEPENDENCY: " + fdv);//May have to be the CLOSURE of the fdv...
				Relation union = fdv.lhs.union(fdv.rhs);
				System.out.println("PUSHING UNION: " + union);
				Relation diff = a.diff(fdv.rhs);
				System.out.println("PUSHING DIFFERENCE: " + diff); //Should be whatever is on the rhs that gets removed.
				s.push(union);
				s.push(diff);
			}
			System.out.println("**************************EXITING DECOMPOSITION**************************");
		}
		db.traverse();
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
