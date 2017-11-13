import java.util.*;

public class DealOrNoDealApplication {
	public static Scanner keyboard = new Scanner(System.in);
	private static int offer = 0;
	private static int highestOffer = 0;
	static int userFinalBriefCase;
	private static int offerTaken = 0;
	private static boolean dealTaken = false;
	static BriefCaseMap bcm = new BriefCaseMap();
	static PrizeMoney pm = new PrizeMoney();
	
	/**
     * Method that prompts the user to select their final briefcase. Any non integer values or integers
     * outside of the range 1 - 26 throw an exception
     */
	public static void selectUserBriefCase(){
		while (true){
			try {
				System.out.println("Please select your briefcase");
				userFinalBriefCase = keyboard.nextInt() - 1;
				if (userFinalBriefCase < 0 || userFinalBriefCase > 25 || bcm.getCaseOpened()[userFinalBriefCase] == true) {
					System.err.println("Im sorry, \"" + (userFinalBriefCase + 1) + "\" is not one of the options!");
				} else {
					System.out.println("Congratulations! You have chosen briefcase " + (userFinalBriefCase + 1));
					bcm.setCaseOpened(userFinalBriefCase);
					break;
				}
			} catch (InputMismatchException ime) {
				System.err.println("Please enter an valid integer value!");
				keyboard.next();
			}
		}
	}
	
	/**
	 * Prompts the user to select a briefcase based on the cases that have not been opened yet
	 * @param numberOfBriefCases the initial number of briefcases in the round
	 * @throws InterruptedException
	 */
	public static void pickBriefCasesForRound(int numberOfBriefCases) throws InterruptedException{
		int userBriefCase;
		int remaining = numberOfBriefCases;
		while (true){
			for (int i = 0; i < numberOfBriefCases; i++){
				bcm.Map();
				System.out.println("There are " + remaining + " briefcases remaining in this round");
				System.out.println("Please select a briefcase: ");
				try {
					userBriefCase = keyboard.nextInt() - 1;
					if (userBriefCase < 0 || userBriefCase > 26 || bcm.getCaseOpened()[userBriefCase] == true) {
						System.err.println("Im sorry, \"" + (userBriefCase + 1) + "\" is not one of the options!");
						numberOfBriefCases++;
					} else {
						System.out.println("Opening briefcase " + (userBriefCase + 1) + "...");
						//Thread.sleep(1000);
						System.out.println("Briefcase " + (userBriefCase + 1) + " contains $" + bcm.pm.getNewPrizeMoneyValue(userBriefCase));
						bcm.pm.setValueToZero(userBriefCase);
						bcm.setCaseOpened(userBriefCase);
						remaining--;	
					}
				} catch (InputMismatchException ime) {
					System.err.println("Please enter an integer value!");
					keyboard.next();
				}
			}
			break;
		}
	}
	
	/**
     * 
     * @return an offer based on the number of remaining cases in the game and the total amount of prize money
     * left in play (total prize money / number of cases remaining)
     */
	public static void makeOffer() throws InterruptedException{
		int runningTotal = 0;
		int remainingCases = 1;
		for (int i = 0; i < 26; i++){
			if (bcm.getCaseOpened()[i] == false){
				remainingCases++;
			}
			runningTotal += bcm.pm.getNewPrizeMoneyValue(i);
		}
		
		offer = runningTotal / remainingCases;
		System.out.println("Calculating offer...");
		Thread.sleep(2000);
		if (dealTaken == false) {
			System.out.println("I am willing to offer you $" + offer);
			if (offer > highestOffer){
				highestOffer = offer;
			}
			System.out.println("Deal or no deal?");
			keyboard.nextLine();
			String response = keyboard.nextLine();
			char aChar = response.charAt(0);
			if (aChar == 'D' || aChar == 'd'){
				System.out.println("Congratulations! It's a deal for $" + offer);
				offerTaken = offer;
				dealTaken = true;
			} else {
				System.out.println("NO DEAL!!!");
			}
		} else {
			System.out.println("You would have been offered $" + offer);
		}
		
	}
	
	/**
	 * Reveals the contents of the final two cases
	 * @throws InterruptedException
	 */
	public static void finalTwoCases() throws InterruptedException {
		int case1 = 0, case2 = 0, lastCase = 0;
		for (int i = 0; i < 26; i++){
			if(bcm.getCaseOpened()[i] == false){
				case1 = bcm.pm.getNewPrizeMoneyValue(i);
				lastCase = i + 1;
			}
		}
		case2 = bcm.pm.getNewPrizeMoneyValue(userFinalBriefCase);
		System.out.println("Will your briefcase contain $" + case1 + " or $" + case2 + "?");
		Thread.sleep(1000);
		System.out.println("Let's find out...");
		System.out.println("Briefcase "+ (userFinalBriefCase + 1) + " has....");
		Thread.sleep(4000);
		System.out.println("$" + case2);
		if (dealTaken == false) {
			if (case1 < case2){
				System.out.println("Great deal!!! The other briefcase only had $" + case1);
			} else {
				System.out.println("Bad deal!!! Briefcase " + lastCase + " had $" + case1);
				System.out.println("You were offered $" + highestOffer + " but decided to turn it down");
				System.out.println("You are a greedy person");
			}
		} else {
			if (offerTaken < case2) {
				System.out.println("Bad deal!!! You took a deal for $" + offerTaken);
				System.out.println("Briefcase " + lastCase + " had $" + case2);
			} else {
				System.out.println("Great deal!!! You took a deal for $" + offerTaken);
				System.out.println("Briefcase " + lastCase + " only had $" + case2);
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		bcm.Map();
		selectUserBriefCase();
		for (int i = 0; i < 9; i++){
			if (6 - i > 1){
				pickBriefCasesForRound(6 - i);
			} else {
				pickBriefCasesForRound(1);
			}
			makeOffer();
		}
		finalTwoCases();
		
	}
}

