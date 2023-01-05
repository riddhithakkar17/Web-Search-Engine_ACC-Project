package websearchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

public class SortPages {
	private static final int CUTOFF = 3;
	
	//Collection Sort for ranking of the pages
	public static void rankWebPages(Hashtable<?, Integer> files, int numberOfFiles) {

		ArrayList<Map.Entry<?, Integer>> list_of_files = new ArrayList<Map.Entry<?, Integer>>(files.entrySet());
		
		Comparator<Map.Entry<?, Integer>> compareByOccurence=
				(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2)->o1.getValue().compareTo(o2.getValue());
		//Ranking of list in descending Order 
		Collections.sort(list_of_files, compareByOccurence.reversed());		
				
	    System.out.println("\nRanked URLs are:\n");
		int sr_no=1;
		for(int i=0;i<list_of_files.size();i++)
		{
			if(list_of_files.get(i).getKey()!=null) {
				System.out.println("(" + sr_no + ") " + list_of_files.get(i).getKey() + " - Occurence of Word: "+ list_of_files.get(i).getValue());
				sr_no++;
			}
		}
	}   
}
