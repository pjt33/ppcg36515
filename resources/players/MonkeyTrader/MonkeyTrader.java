// Copyright (c) 2014 Bob Genom http://codegolf.stackexchange.com/users/19545/bob-genom
import java.math.BigInteger;
import java.util.Random;

public class MonkeyTrader {
    /**
     * @param args the command line arguments containing the current 
     * market value, our current money, and our current shares
     */
    public static void main(String[] args) {
        BigInteger marketValue = new BigInteger(args[0]);
        BigInteger myMoney = new BigInteger(args[1]);
        BigInteger myShares = new BigInteger(args[2]);
        Random random=new Random();

        switch (random.nextInt(2)) {
        case 0:
            System.out.println("B" + myMoney.divide(marketValue));
            break;
        case 1:
            System.out.println("S" + myShares);
            break;
        }
    }
}
