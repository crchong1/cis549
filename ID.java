import java.util.Arrays;

public class ID {

	int[] b = new int [Abstractions.BATTLEFIELD_VALUES.length];
	
	public ID(int[] a){
		
		for(int i = 0; i<a.length; i++){
			b[i] = a[i];
		}
	}
	public String toString(){
		return Arrays.toString(b);
	}
}