import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

	public static final String fd = "src/test.txt";
	
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
		
		/* Getting the initial functional dependencies.
		 */
		while((line = br.readLine()) != null)
		{
			//System.out.println(line);
			Relation lhs, rhs;
			int lLen, rLen;
			lLen = line.indexOf('-') - 1;
			lhs = new Relation(line.substring(0, lLen));
			rLen = line.indexOf('>') + 2;
			rhs = new Relation(line.substring(rLen, line.length()));
			//System.out.println(lhs);
			//System.out.println(rhs);
			FD temp = new FD(lhs,rhs);
			fdl.insert(temp);
			//System.out.println(lhs);
			//System.out.println(rhs);
			
		}
		//System.out.println(totalAttr);
		System.out.println(fdl.size);
		System.out.println(fdl.getNext());
		fdl.reset();
		System.out.println(fdl.getNext());
		if(br != null) br.close();
		if(fr != null) fr.close();
	}
}
