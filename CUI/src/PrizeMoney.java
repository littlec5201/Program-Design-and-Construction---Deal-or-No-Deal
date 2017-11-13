import java.util.*;

public class PrizeMoney {
	private int BRIEFCASES = 26;
	private int [] initialPrizeMoneyArray = {1, 2, 5, 10, 25, 50, 75, 100, 200, 300, 400, 500, 750, 1000, 
	5000, 10000, 25000, 50000, 75000, 100000, 200000, 300000, 400000, 500000, 750000, 1000000};
	private int [] newPrizeMoneyArray = new int[BRIEFCASES];
	private boolean[] trueFalseArray = new boolean[BRIEFCASES];
	Random randomNumber = new Random();
	
	/**
	 * Method sets the value at the specified index to zero. This is done so that offers for each round
	 * can be made.
	 * @param index the index in the newPrizeMoneyArray that will be set to 0
	 */
	public void setValueToZero(int index){
		for (int i = 0; i < 26; i++){
			if (getNewPrizeMoneyValue(index) == getInitialPrizeMoneyValue(i)){
				this.initialPrizeMoneyArray[i] = 0;
				this.newPrizeMoneyArray[index] = 0;
			}
		}
	}
	
	/**
     * Iterates through each of the 26 indexes in the initialPrizeMoneyArray and randomly assigns each value
     * to a new index in the newPrizeMoneyArray. If an index already has a value associated with it, a new
     * random number is generated until an index without a value is found
     */
	public void initialiseBriefCases(){
		for (int i = 0; i < BRIEFCASES; i++){
			while (true){
				int number = randomNumber.nextInt(26);
				if (trueFalseArray[number] == false){
					newPrizeMoneyArray[number] = initialPrizeMoneyArray[i];
					trueFalseArray[number] = true;
					break;
				}
			}
		}
	}
	
	/**
     * 
     * @param index the index in the initialPrizeMoneyArray
     * @return the value stored at the index in the initialPrizeMoneyArray
     */
	public int getInitialPrizeMoneyValue(int index) {
		return this.initialPrizeMoneyArray[index];
	}
	
	/**
     * 
     * @param index the index in the newPrizeMoneyArray
     * @return the value stored at the index in the newPrizeMoneyArray
     */
	public int getNewPrizeMoneyValue(int index) {
		return this.newPrizeMoneyArray[index];
	}
}