#!/bin/bash

export CLASSPATH=$PWD
TESTS="./recogniser_testcases/test*"
RESULTS="./recogniser_testcases/result*"

if [ ! -d "./output" ]
then
	mkdir "./output"
fi

echo "Running compiler on test cases"
OUTPUT="ouput"
for test in $TESTS
do
	TEST_NUM=$( echo "$test" | sed 's/.*test//' )
	OUTPUT_FILE="$OUTPUT$TEST_NUM"
	java VC.vc recogniser_testcases/$( basename "$test") > output/$OUTPUT_FILE
done

echo "comparing output files to control output"
for result in $RESULTS
do
	RESULT_NUM=$( echo "$result" | sed 's/.*result//' )
	echo "Test No: $RESULT_NUM" >> diff.txt
	diff recogniser_testcases/result"$RESULT_NUM" output/ouput"$RESULT_NUM" >> diff.txt
done


