#!/bin/bash

# Remove all temporary files if exits
for f in *.csv
do
  rm "$f";
done

# Run the drop.sql batch file to drop existing tables
# Inside the drop.sql, you sould check whether the table exists. Drop them ONLY if they exists.
mysql CS144 < drop.sql
echo "Tables dropped"

# Run the create.sql batch file to create the database and tables
mysql CS144 < create.sql
echo "Tables created"

# Compile and run the parser to generate the appropriate load files
ant
ant run-all
ant clean

# If the Java code does not handle duplicate removal, do this now
echo "Start sorting to deduplicate"
for f in *.csv
do
  sort -u "$f" -o "$f";
  echo "    $f sorted"
done
echo "Sort done"

# Run the load.sql batch file to load the data
mysql CS144 < load.sql
echo "Tuples loaded"

# Run the queries.sql batch file to test queries
#mysql CS144 < queries.sql
#echo "queries done"


