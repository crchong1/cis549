import java.util.*; 

public class GS_Algorithm {
	static int Nodes = 4;  
	// Returns TRUE if user prefers Base Station BS1 over BS2  // users are women
	static boolean uPrefersBS1Over2(int prefer[][], int user, int BS1, int BS2)  {  
	    for (int i = 0; i < Nodes; i++) {  
	        if (prefer[user][i] == BS2) {
	        	return true;  
	        }
	        if (prefer[user][i] == BS1)  {
	        	return false;  
	        }
	    } 
	    return false; 
	}  
	
	static void stableMatch(int prefer[][])  
	{  
	    int freeUsers[] = new int[Nodes];  
	    boolean baseStations[] = new boolean[Nodes];  
	    Arrays.fill(freeUsers, -1);  
	    int numFree = Nodes;  
	    while (numFree > 0)  
	    {  
	        int BS;
	        for (BS = 0; BS < Nodes; BS++)  
	            if (baseStations[BS] == false)  
	                break;   
	        for (int i = 0; i < Nodes && baseStations[BS] == false; i++)  
	        {  
	            int user = prefer[BS][i];
	            if (freeUsers[user - Nodes] == -1) {  
	            	freeUsers[user - Nodes] = BS; 
	            	baseStations[BS] = true;  
	            	numFree--;  
	            }  
	  
	            else
	            {  
	                int BS2 = freeUsers[user - Nodes];  
	                if (uPrefersBS1Over2(prefer, user, BS2, BS) == false)  
	                {  
	                	freeUsers[user - Nodes] = BS2;  
	                	baseStations[BS2] = true;  
	                	baseStations[BS] = false;  
	                }  
	            }
	        }
	    }
	    
	    System.out.println("Users to Base Stations");  
		for (int i = 0; i < Nodes; i++)  { 
		    System.out.print(" ");  
		    System.out.println(i + Nodes + "     " + freeUsers[i]); 
		} 
	}
	
	public static void main(String[] args) {
		int prefer[][] = new int[][]{{7, 5, 6, 4},  
            {5, 4, 6, 7},  
            {4, 5, 6, 7},  
            {4, 5, 6, 7},  
            {0, 1, 2, 3},  
            {0, 1, 2, 3},  
            {0, 1, 2, 3},  
            {0, 1, 2, 3}};  
        stableMatch(prefer);  
	}

}
