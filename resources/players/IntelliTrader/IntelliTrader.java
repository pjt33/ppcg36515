// Copyright (c) 2014 Benny http://codegolf.stackexchange.com/users/26870/benny
import java.io.*;
import java.math.BigInteger;
import java.util.Properties;

public class IntelliTrader {

    private static final String ROUND_NUM = "roundNum";
    private static final String LAST_BUY = "lastBuy";
    private static final String LAST_SELL = "lastSell";
    private static final String FILE = "IntelliTrader/memory.txt";

    private Properties memory;
    private int roundNum;

    private IntelliTrader(Properties memory) {
        this.memory = memory;
        roundNum = new Integer(memory.getProperty(ROUND_NUM, "0"));
    }

    public String evaluate(BigInteger market, BigInteger money, BigInteger shares) {
        String command = "W";
        if (roundNum == 0) {
            if (market.intValue() > 80) {
                command = sell(market, shares);
            } else {
                command = buy(market, money);
            }
        } else {
            if (market.compareTo(new BigInteger(memory.getProperty(LAST_SELL, "0"))) >= 0) {
                command = sell(market, shares);
            } else if (market.compareTo(new BigInteger(memory.getProperty(LAST_BUY, "999999999"))) <= 0) {
                command = buy(market, money);
            }
        }
        return command;
    }

    private String buy(BigInteger cost, BigInteger money) {
        memory.setProperty(LAST_BUY, cost.toString());
        return "B" + money.divide(cost).toString();
    }

    private String sell(BigInteger cost, BigInteger shares) {
        memory.setProperty(LAST_SELL, cost.toString());
        return "S"+shares.toString();
    }


    public static void main(String[] args) {    
        BigInteger marketValue = new BigInteger(args[0]);
        BigInteger myMoney = new BigInteger(args[1]);
        BigInteger myShares = new BigInteger(args[2]);

        Properties memory = new Properties();
        try {
            memory.load(new FileReader(FILE));
        } catch (IOException e) {
            //ignore, file probably doesn't exist yet
        }

        int roundNum = new Integer(memory.getProperty(ROUND_NUM, "0"));
        if (roundNum > 49) {
            roundNum = 0;
            memory.setProperty(ROUND_NUM, "0");
            memory.setProperty(LAST_BUY, "0");
            memory.setProperty(LAST_SELL, "0");
        }

        IntelliTrader it = new IntelliTrader(memory);
        String command = it.evaluate(marketValue, myMoney, myShares);
        System.out.println(command);

        roundNum++;
        memory.setProperty(ROUND_NUM, ""+roundNum);
        try {
            memory.store(new FileWriter(FILE), "IntelliTrader memory properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
