# Copyright (c) 2014 Batman http://codegolf.stackexchange.com/users/30022/batman
import argparse

parser = argparse.ArgumentParser(description="This is a private matter, Master Bruce. Learn how to make your own bed and I will tell you.")
parser.add_argument("Stuff", type=int, nargs='+', help="You don't need to know, Master Bruce.")

args=parser.parseargs()
vals=[]
for x in args:
    vals.append(x)

a=vals[0]
b=vals[1]
c=vals[2]

if c==0:
    x=1
    while x*a<b:
        x+=1
    print "B"+str(x)
    with open("lastval.txt", w) as f:
        f.write(a)

else:
    lastval=next(open("lastval.txt"))
    if a>lastval:print "S10" if c>10 else "S"+str(c)
    else:print 'W'
