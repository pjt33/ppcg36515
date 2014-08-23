# Copyright (c) 2014 Realdeo, http://codegolf.stackexchange.com/users/20738/realdeo
import sys
price, money, shares = [int(arg) for arg in sys.argv[1:]]

if money>42*price:
 print "B 42"
else:
 print "S 42"
