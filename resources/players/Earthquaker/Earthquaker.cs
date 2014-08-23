// Copyright (c) 2014 Ipi http://codegolf.stackexchange.com/users/30685/ipi
using System;
using System.IO;

namespace Earthquaker
{
    class Program
    {
        static void Main(string[] args)
        {
            if (args.Length < 3)
                return;

            int stockPrice = int.Parse(args[0]);
            int money = int.Parse(args[1]);
            int stocks = int.Parse(args[2]);

            bool shouldBuy = true;

            if (stocks != 0)
            {
                StreamReader sr = new StreamReader("brain.txt");
                if (sr.ReadLine() == "B")
                    shouldBuy = false;
                else
                    shouldBuy = true;
                sr.Close();
            }

            if (shouldBuy)
                Console.Write("B" + (money / stockPrice));
            else
                Console.Write("S" + (stocks - 1));

            StreamWriter sw = new StreamWriter("brain.txt", false);
            sw.Write(shouldBuy ? 'B' : 'S');
            sw.Close();
        }
    }
}
