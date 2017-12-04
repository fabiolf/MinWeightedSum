import java.util.Arrays;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {
		// open file
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] weights = new int[n];
        int[] lenghts = new int[n];
        
        for (int i = 0; i < n; i++) {
            weights[i] = in.nextInt();
            lenghts[i] = in.nextInt();
        }
        

        in.close();
	}

}
