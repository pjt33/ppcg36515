// Copyright (c) 2014 Peter Taylor http://codegolf.stackexchange.com/users/194/peter-taylor
import java.math.BigInteger;

public class LeesonLearnt {
	private static final BigInteger THRESHOLD = new BigInteger("100");

	public static void main(String[] args){
		BigInteger price = new BigInteger(args[0]);
		BigInteger capital = new BigInteger(args[1]);
		BigInteger shareholding = new BigInteger(args[2]);

		BigInteger affordable = capital.divide(price);

		// In the long run, the shares will probably lose all their value.
		// But if they're cheap, buying them will pump them and they can be sold at a profit.
		// The target amount of our value held in shares varies exponentially with their price.
		BigInteger targetShareholding = price.compareTo(THRESHOLD) > 0
			? BigInteger.ZERO
			: affordable.add(shareholding).shiftRight(price.intValue() - 1);
		if (targetShareholding.compareTo(shareholding) <= 0) {
			System.out.println("S" + shareholding.subtract(targetShareholding));
		}
		else {
			BigInteger diff = targetShareholding.subtract(shareholding);
			System.out.println("B" + diff.min(affordable));
		}
	}
}
