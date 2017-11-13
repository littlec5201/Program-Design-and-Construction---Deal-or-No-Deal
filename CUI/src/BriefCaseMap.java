
public class BriefCaseMap {
	private boolean [] caseOpened = new boolean[26];
	PrizeMoney pm = new PrizeMoney();
	
	public BriefCaseMap(){
		pm.initialiseBriefCases();
	}
	
	public boolean[] getCaseOpened() {
		return caseOpened;
	}

	public void setCaseOpened(int caseOpened) {
		this.caseOpened[caseOpened] = true;
	}

	public void Map(){
		System.out.println("+---------------------------------------+");
		System.out.println("|            DEAL OR NO DEAL            |");
		System.out.println("+---------------------------------------+");
		for (int i = 1; i < 27; i++){
			if (!getCaseOpened()[i - 1]){
				if (i < 10){
					System.out.print("[ " + i + " ] ");
				}
				if (9 < i && i < 20){
					System.out.print("[1 " + (i - 10) + "] ");
				}
				if (19 < i && i < 27){
					System.out.print("[2 " + (i - 20) + "] ");
				}
			} else {
				System.out.print("[   ] ");
			}
			
			if (i == 7 || i == 14 || i == 21 || i == 26){
				
				System.out.print("   ");
				
				if (i == 26){
					System.out.print("      ");
					i = i + 2;
				}
	
				for (int j = i - 7; j < i; j++){
					if (pm.getInitialPrizeMoneyValue(j) == 0){
						String output = String.format("%-10s", "");
						System.out.print(output);
						if (j == 25){
							break;
						}
					} else {
						if (i < 29){
							String output = String.format("%-10s", "$" + pm.getInitialPrizeMoneyValue(j));
							System.out.print(output);
							if (j == 25){
								break;
							}
						} else { 
							break;
						}
					}
				}
				System.out.println();
				System.out.println();
				if (i == 21){
					System.out.print("      ");
				}
			}
		}
	}
}
