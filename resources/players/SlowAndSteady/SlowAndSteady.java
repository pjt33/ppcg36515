// Copyright (c) 2014 Red Shadow http://codegolf.stackexchange.com/users/29711/red-shadow
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SlowAndSteady{
    public static void main(String[] args) {
        BigInteger price = new BigInteger(args[0]);
        BigInteger cash= new BigInteger(args[1]);
        long shares= Long.parseLong(args[2]);
        BigInteger number = new BigInteger("165");
        String count = "0";


        try {
            count = new String(Files.readAllBytes(Paths.get("counter.txt")));
        } catch (IOException e) {

        }

        int c = Integer.parseInt(count)+1;

        if (c >= 50)
        {
            System.out.println("S" + shares);
            c=0;
        }

        else if(cash.compareTo(number) > 0)     System.out.println("B" + (number.divide(price)));

        else System.out.println("S" + shares);


        try {
            Writer wr = new FileWriter("counter.txt");
            wr.write(Integer.toString(c));
            wr.close();
        } catch (IOException e) {
        }
   }
}
