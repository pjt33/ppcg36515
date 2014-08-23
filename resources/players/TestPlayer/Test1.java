// Copyright (c) 2014 spocot http://codegolf.stackexchange.com/users/30169/spocot
public class Test1 {

    public static void main(String[] args){

		int marketValue = Integer.parseInt(args[0]);
        int myMoney = Integer.parseInt(args[1]);
        int myShares = Integer.parseInt(args[2]);

        //Buy 10 if we don't have any.
        if(myShares <= 0){
            System.out.println("B10");
        }else{
            System.out.println("S1");
        }
    }
}
