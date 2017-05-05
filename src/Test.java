import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
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
		/*System.out.println("Before removal.");
		fdl.traverse();
		FD fd1, fd2, fd3;
		fd2 = fdl.getNext().data;
		fd3 = fdl.getNext().data;
		fd1 = fdl.getNext().data;
		System.out.println("fd1: " + fd1);
		System.out.println("fd2: " + fd2);
		System.out.println("fd3: " + fd3);
		
		
		System.out.println("Removing middle: " + fd2);
		fdl.remove(fd2);
		System.out.println("Traversing after removal...");
		fdl.traverse();
		System.out.println("Tail: " + fdl.tail);
		System.out.println("Head: " + fdl.head);
		System.out.println("Size: " + fdl.size);
		System.out.println();*/
		
		/*Stack<String> s = new Stack<String>();
		s.push("sally");
		s.push("aunt");
		s.push("dear");
		s.push("my");
		
		System.out.println("Current size: " + s.size);
		System.out.println(s.pop());
		System.out.println("Current size: " + s.size);
		System.out.println(s.pop());
		System.out.println("Current size: " + s.size);
		System.out.println(s.pop());
		System.out.println("Current size: " + s.size);
		System.out.println(s.pop());
		System.out.println("Current size: " + s.size);
		System.out.println(s.pop());
		System.out.println("Current size: " + s.size);*/
		
	}
}