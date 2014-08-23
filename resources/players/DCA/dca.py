# Copyright (c) 2014 Tim S. http://codegolf.stackexchange.com/users/11333/tim-s
import sys
price, money, shares = [int(arg) for arg in sys.argv[1:]]
target_per_day = 150
buy = round(min(target_per_day, money) / price)
if buy * price > money:
    buy -= 1
if buy > 0:
    print("B" + str(buy))
else:
    print("W")
