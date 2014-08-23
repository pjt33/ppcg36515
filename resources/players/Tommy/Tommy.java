// Copyright (c) 2014 kaine http://codegolf.stackexchange.com/users/29108/kaine
import java.math.BigInteger;

public class Tommy {
    public static void main(String[] args) {
        BigInteger Value = new BigInteger(args[0]);
        BigInteger Money = new BigInteger(args[1]);
        BigInteger Shares = new BigInteger(args[2]);

       if (Money.compareTo(Value)<1) {
           System.out.print("S" + Shares.toString());
       } else {
           System.out.print("B" + Money.divide(Value).toString());
       }
    }

}
