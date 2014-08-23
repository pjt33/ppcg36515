#!/bin/bash
cd `dirname $0`
cd resources/players
make clean
make
cd ../..
javac Controller.java && java Controller
