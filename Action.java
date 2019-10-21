import java.util.Random;

public class Action {

	private int NUM_ACTIONS = Abstractions.NUM_ACTIONS;
	
	public static final Random random = new Random();
	double[][] regretSum = new double[Abstractions.NUM_PLAYERS][NUM_ACTIONS], 
			strategy = new double[Abstractions.NUM_PLAYERS][NUM_ACTIONS], 
			strategySum = new double[Abstractions.NUM_PLAYERS][NUM_ACTIONS];

	public double[] getStrategy(int player) {
		double normalizingSum = 0;
		for (int a = 0; a < NUM_ACTIONS; a++) {
			strategy[player][a] = regretSum[player][a] > 0 ? regretSum[player][a] : 0;
			normalizingSum += strategy[player][a];
		}
		for (int a = 0; a < NUM_ACTIONS; a++) {
			if (normalizingSum > 0)
				strategy[player][a] /= normalizingSum;
			else
				strategy[player][a] = 1.0 / NUM_ACTIONS;
			strategySum[player][a] += strategy[player][a];
		}
		return strategy[player];
	}


	public int getAction(double[] strategy) {
		double r = random.nextDouble();
		int a = 0;
		double cumulativeProbability =  0;
		while (a < NUM_ACTIONS - 1) {
			cumulativeProbability += strategy[a];
			if (r < cumulativeProbability)
				break;
			a++;
		}
		return a;
	}

	public double[] getAverageStrategy(int player) {
		double[] avgStrategy = new double[NUM_ACTIONS];
		double normalizingSum = 0;
		for (int a = 0; a < NUM_ACTIONS; a++)
			normalizingSum += strategySum[player][a];
		for (int a = 0; a < NUM_ACTIONS; a++) 
			if (normalizingSum > 0)
				avgStrategy[a] = strategySum[player][a] / normalizingSum;
			else
				avgStrategy[a] = 1.0 / NUM_ACTIONS;
		return avgStrategy;
	}


	public int getPayoff(int p1[], int p2[]){
		int v = 0;

		for(int i= 0; i<Abstractions.BATTLEFIELD_VALUES.length; i++){
			if(p1[i]>p2[i])
				v +=Abstractions.BATTLEFIELD_VALUES[i];
			else if(p1[i]==p2[i]) 
				v+=0;
			else
				v-=Abstractions.BATTLEFIELD_VALUES[i];
		}
		if(v>0) return 1; else if(v==0) return 0; else return -1;
	}

}