package cop2805;

public class Homework1_BrodyStewart {

	public static void main(String[] args) {
		double amountSold = 1000.00;
		
		System.out.println("Sales\t\tIncome");
		while (amountSold <= 20000.00) {
			System.out.printf("$%.2f", amountSold);					//Prints the amount sold (in $)
			System.out.printf("\t$%.2f%n", computeIncome(amountSold)); //Calculate and prints the projected income
			amountSold += 1000;
		}
	}

	public static double computeIncome(double salesAmount) {
		double comission = 0.00;
		if (salesAmount <= 5000)				//If they didn't sell more than $5,000
			comission += salesAmount * 0.08;
		else if (salesAmount <= 10000)				//If they didn't sell more than $10,000
			comission += 400 + (salesAmount * 0.1);
		else										//If they did superrrr well and sold a ton. 
			comission += 400 + 500 + ((salesAmount- 10000) * 0.12); //The 400 in the last elseif and in the 500 aswell in this one are just the calculations of the last two tiers.
			
		return comission+5000.00; //Add base salary ($5000) before returning
	}
	
}
