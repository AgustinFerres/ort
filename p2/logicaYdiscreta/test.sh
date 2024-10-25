#!/bin/bash

file_path=$1   # path to module
func_name=$2   # name of function to test
var_count=$3   # number of variables (True | False)

# Calculate the total number of rows in the truth table
total_rows=$((2 ** var_count))

# Create a temporary file
temp_file=$(mktemp)


# Generate the truth table and write to the temporary file
for (( i=0; i<total_rows; i++ ))
do
    row=$func_name" "
    for (( j=var_count-1; j>=0; j-- ))
    do
        if [ $(( (i >> j) & 1 )) -eq 1 ]
        then
            row+="True "
        else
            row+="False "
        fi
    done
    # Write each row to the temporary file, at start of file
    { echo "$row"; cat "$temp_file"; } > temp && mv temp "$temp_file"
done

# Log table, removing function name
echo -en "\033[0;34m Table: " 
echo -e "\n--------------------------------"
cat "$temp_file" | awk -F $func_name '{print $2}' | column -t
echo -e "--------------------------------"
echo -en "\033[0m" 

ghci -D  "$file_path" < "$temp_file"



# Clean up
rm "$temp_file"

