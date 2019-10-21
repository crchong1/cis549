import java.util.ArrayList;

public class BlottoTrainer {


	public ArrayList<ID> id = new ArrayList<>();
	public int player;
	Action action;



	public void train(int iterations) {
		System.out.println("calculating..");
		for (int i = 0; i < iterations; i++) {

			player = i % 2;
			int opp = 1-player;

			int p1 = action.getAction(action.getStrategy(player));
			int p2 = action.getAction(action.getStrategy(opp));

			int util = action.getPayoff(id.get(p1).b, id.get(p2).b);
			
			for (int a = 0; a < Abstractions.NUM_ACTIONS; a++){
				action.regretSum[player][a] += action.getPayoff(id.get(a).b,
						id.get(p2).b) - util;
			}

		}
	}

	public void create(int r, int[] s){
		s[0] = 0;	
		for(int i=0; i<r+1; i++){
			for(int k=0; k<r+1; k++){
				s[0] = k;
				s[1] = i;
				if(i==r){
					int count = 0;
					for(int c:s){
						count+=c;
					}
					if(count==r)
						id.add(new ID(s));
					s[2]+=1;
					for(int j=1; j<s.length; j++){										
						if(s[j]==r+1){
							if((j+1)==s.length){
								return;
							}
							s[j] = 0;
							s[j+1]+=1;
						}
					}
					create(r, s);
					return;
				}
				int count = 0;
				for(int c:s){
					count+=c;
				}
				if(count==r)
					id.add(new ID(s));		
			}
		}

	}
	
	public void setAbstractions(){
		Abstractions.NUM_ACTIONS = id.size();
	}


	public void printResults(){
		for(int i=0; i<Abstractions.NUM_PLAYERS; i++){
			System.out.println("Results player"+i); 
			for(int j=0; j<Abstractions.NUM_ACTIONS; j++){
				if(action.getAverageStrategy(i)[j]*100>0.5)
					System.out.println("["+id.get(j)+"] "+action.getAverageStrategy(i)[j]*100);
			}
			System.out.println("---");
		}
	}

	public  void printDecisions(){
		for(int i = 0; i<id.size();i++){
			System.out.println(" "+id.get(i));
		}
	}
	
	public void initilize(int r, int[] b){
		create(r, b);
		setAbstractions();
		action = new Action();
	}


	public static void main(String[] args) {
		BlottoTrainer trainer = new BlottoTrainer();		
		trainer.initilize(Abstractions.RESOURCES, new int[Abstractions.BATTLEFIELD_VALUES.length]);	
		trainer.train(10000);
		trainer.printResults();
		
	}
}