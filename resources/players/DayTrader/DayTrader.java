import java.math.BigInteger;

/**
 * Submission for http://codegolf.stackexchange.com/q/36515/18487
 * @author Rainbolt
 */
public class DayTrader {

    /**
     * @param args the command line arguments containing the current 
     * market value, our current money, and our current shares
     */
    public static void main(String[] args) {
        BigInteger marketValue = new BigInteger(args[0]);
        BigInteger myMoney = new BigInteger(args[1]);
        BigInteger myShares = new BigInteger(args[2]);

        // If we have less than or equal to 30 shares, buy as much as possible
        if (myShares.compareTo(new BigInteger("30")) <= 0) {
            System.out.println("B" + myMoney.divide(marketValue).toString());
        // Otherwise, sell as much as possible
        } else {
            System.out.println("S" + myShares.toString());
        }
    }

}
