#!/usr/bin/python

import os
import sys
from sys import argv

#output = open('shadeParSortB.txt')
#sample = open('shadeParSortA.txt')
getSet = open('test1.txt')

filearg = sys.argv[1]

# Test used primarily to ensure program is implemented in parallel, by comparing the order of the trees executed on.
# Also used for other small tests (timings, shade values)
def testOne():
    count = 0
    print(filearg) #test1

    for b in output:
        for a in sample:

            if a != b:
                print("line: ", count)
                print("output: ", b)
                print("sample: ", a)
                count = count+1
                break
            else:
                break

            count = count+1
    
    if count == 0:
        print("no errors")
    else:
                print(str(count) + " errors")


def testTwo():
    
    print(filearg) #test2

    totalSample = 0.000
    totalOutput = 0.000

    output.readline()
    output.readline()
    for c in output:
        totalOutput = totalOutput + float(c)

    sample.readline()
    sample.readline()
    for d in sample:
        totalSample = totalSample + float(d)

    print("My Output Average: ")
    print(totalOutput)
    print("Sample Output Average: ")
    print(totalSample)

# BAD INTERLEAVING TEST
# method checks that a land block is set straight after get
# else if there are more than one 'gets' before a 'set', it means that a new tree is accessing a shade value before the previous tree accessing the same shade value has reset it as a result of its shade
def getSetTest():
    
    print(filearg) #getSetTest
    
    getSetArr = []
    for i in range (0, 3000):
        new = []
        for j in range (0, 3000):
            new.append("empty")
        getSetArr.append(new)
    
    errorCount=0
    
    getSet.readline() #ignore first two lines
    getSet.readline()
    
    for i in getSet:
        line = i.split(',')
        method=line[0]
        x=int(float(line[1]))
        y=int(float(line[2]))
        
        if getSetArr[x][y]=="get":
            if method=="set":
                getSetArr[x][y]="set"
            else:
                print("new get before set!")
                errorCount=errorCount+1
        elif getSetArr[x][y]=="set":
            if method=="get":
                getSetArr[x][y]="get"
            else:
                print("new set after set?")
                errorCount=errorCount+1
        else:
            getSetArr[x][y]=method

    print("Total Errors: " + str(errorCount))


if (filearg == "test1"):
    testOne()
elif (filearg == "test2"):
    testTwo()
elif (filearg == "getSetTest"):
    getSetTest()





