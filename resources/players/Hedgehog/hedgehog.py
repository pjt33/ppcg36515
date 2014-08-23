# Copyright (c) 2014 Moop http://codegolf.stackexchange.com/users/21434/moop
from __future__ import print_function
from sys import argv

storage = 'prices.txt'
price,cash,shares = map(long, argv[1:])
turn = 1
buy = lambda x: print('B%d' % long(x))
sell = lambda x: print('S%d' % long(x))
cashtoshares = lambda c: long(c/price)
TURN,PRICE,CASH,SHARES=range(4)

try:   
    data = [map(long, line.split()) for line in open(storage)]
    if data:
        turn = data[-1][TURN] + 1
except IOError:
    pass
with open(storage, 'a') as pricelist:
    pricelist.write('%d %d %d %d\n' % (turn, price, cash, shares))

if turn == 1:
    buy(cashtoshares(cash)) # convert all cash into shares
elif price == 1:
    buy(cashtoshares(cash)) # cannot buy at a better deal
elif price < 10:
    buy(cashtoshares(cash/2))
elif shares < 10:
    buy(cashtoshares(cash/2))
else:
    sell(shares/2)
