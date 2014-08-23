# Copyright (c) 2014 Sean D http://codegolf.stackexchange.com/users/30919/sean-d
egrep -o '[bc][0-9]+' <<< $(sha512sum <<< $@) | sed "s/c/S/;s/b/B/" | head -1
