# Copyright (c) 2014 Beta Decay http://codegolf.stackexchange.com/users/30525/beta-decay
from sys import argv
import os

price=int(argv[1])
money=int(argv[2])
share=int(argv[3])

if os.path.exists('./buyingprice.txt'):
    f = open('buyingprice.txt', 'r')
    buyingprice=int(f.read())
    f.close()
else:
    buyingprice=0

if share > 0:
    if price > buyingprice*10:
        print('S'+str(share))
    else:
        print('W')
elif money > 0:
    if buyingprice==0:
        print('B10')
        m=open('buyingprice.txt', 'w')
        m.write(str(price))
        m.close()
    elif price <= buyingprice:
        print('B'+str(int(money/price)))
        g=open('buyingprice.txt', 'w')
        g.write(str(price))
        g.close()
    else:
        print('W')
