#include <stdio.h>
#include <stdlib.h>

/* BuyAndHold
 * Code revised from OptoKopper's WaitForCrash.c
 * Copyright (c) 2014 Glenn Randers-Pehrson http://codegolf.stackexchange.com/users/17193/glenn-randers-pehrson
 */
int main(int argc, char *argv[]) {
    long long int share_price = strtoll(argv[1], NULL, 0);
    long long int money = strtoll(argv[2], NULL, 0);

    if (money >= share_price) {
        printf("B%lld\n", money / share_price);
    } else {
        printf("W\n");
    }

    return 0;
}
