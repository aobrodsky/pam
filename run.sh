#!/bin/bash

PERIODS="1000 2000 3000 4000 5000 6000 7000 8000 9000 10000"
CONTAINERS="java.util.TreeSet java.util.HashSet java.util.LinkedList java.util.ArrayList"
RUNS="1 2 3 4 5"

javac Pam.java 

for c in $CONTAINERS; do
  echo $c","
  for p in $PERIODS; do
    echo -n $p","
    for r in $RUNS; do
      echo -n `java Pam $c $p |grep "Total time" | sed 's/^.*: //' | sed 's/ms$/,/'`
    done
    echo ""
  done
done
 

