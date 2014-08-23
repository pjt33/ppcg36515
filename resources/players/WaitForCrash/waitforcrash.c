// Copyright (c) 2014 Optokpper http://codegolf.stackexchange.com/users/30754/optokopper
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    long long int share_price = strtoll(argv[1], NULL, 0);
    long long int money = strtoll(argv[2], NULL, 0);
    long long int shares_owned = strtoll(argv[3], NULL, 0);

    if(shares_owned > 1) {
        printf("S%lld\n", shares_owned - 1);
    } else if (shares_owned == 0 || share_price == 10) {
        printf("B%lld\n", money / share_price);
    } else {
        printf("W\n");
    }

    return 0;
}
