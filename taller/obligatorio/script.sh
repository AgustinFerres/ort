#!/bin/bash
# Agustin Ferres - N° 323408

# variables

users=$(cat "./users.txt" | awk -F' / ' '{print $1 "." $2}') # Array of users from users.txt
isLogged=false                                               # Flag to check if the user is logged
RED='\033[0;31m'                                             # Red
GREEN='\033[0;32m'                                           # Green
BLUE='\033[0;34m'                                            # Blue
GREY='\033[0;37m'                                            # Grey
NC='\033[0m'                                                 # No Color

# Functions
function login {

  read -rp "Please, enter your username: " username
  read -rp "Please, enter your password: " password

  clear # Clear the screen
  # Check if the user is registered

  for user in $users; do
    name=$(echo "$user" | awk -F'.' '{print $1}')
    pass=$(echo "$user" | awk -F'.' '{print $2}')

    if [ "$name" == "$username" ] && [ "$pass" == "$password" ]; then
      echo "Welcome $username"
      isLogged=true
      break
    fi
  done

  # If the user is not registered, exit
  if [ "$isLogged" == false ]; then
    echo -e "${RED}Invalid username or password"
    exit 1
  fi
}

function requestInput {
  message=$(awk -F'the ' '{print $2}' <<<"$1") # Get the success message from the argument
  message=${message^}                          # Capitalize the first letter of the message

  clear                        # Clear the screen
  setColor "$BLUE"             # Set color to blue
  read -rp "$1: " input_value  # Request input with the message passed as argument, and store it in the variable passed as argument
  declare -g "$2=$input_value" # Set the variable passed as argument with the value entered by the user
  setColor "$NC"               # Reset color
  clear                        # Clear the screen

  setColor "$GREEN" # Set color to green
  echo "$message set to: $input_value"
  setColor "$NC" # Reset color
}

function calculatePercentage {
  result=$(($1 * 10000 / $2))                                  # Calculate the percentage
  integer_part=$((result / 100))                               # Get the integer part
  fractional_part=$((result % 100))                            # Get the fractional part, by getting the remainder
  printf "%s.%02d %s\n" "$integer_part" "$fractional_part" "%" # Return the result
}

function calculateAverage {
  result=$(($1 * 100 / $2))         # Calculate the average
  integer_part=$((result / 100))    # Get the integer part
  fractional_part=$((result % 100)) # Get the fractional part, by getting the remainder

  printf "%s.%02d\n" "$integer_part" "$fractional_part"
}

function formatWord {
  formattedWord=$1                       # Get the word to format
  qSpaces=$(($2 - ${#formattedWord}))    # Calculate the amount of spaces to add
  for ((i = 0; i < "$qSpaces"; i++)); do # Loop through the word until the length passed as argument
    formattedWord=" ${formattedWord}"    # Add a space before the word
  done

  echo "$formattedWord" # Return the formatted word
}

function setColor {
  echo -en "$1" # Set color to the argument passed
}

function generateMenu {
  setColor "$GREY"
  echo "----------------------------------------------   Menu   ------------------------------------------------------"
  echo "1. List users           |  2. Create user   |  3. Set start letter  |  4. Set end letter"
  echo "5. Set contained letter |  6. Search word   |  7. Input vowel       |  8. Search words that only contain vowel"
  echo "9. Algorithm 1          |  10. Algorithm 2  |  11. Exit"
  echo "--------------------------------------------------------------------------------------------------------------"
  echo -e "${BLUE}" # Set color to blue
  read -rp "Please, select an option: " option
  echo -e "${NC}" # Reset color
}

function listUsers {
  setColor "$GREEN"
  echo "Users:"
  setColor "$NC"

  # Print users
  for user in $users; do
    awk -F'.' '{print "Username: " $1 "\nPassword: " $2}' <<<"$user" # format user into "user: xxxx pass: xxxx"
    setColor "$GREEN"
    echo "----------------------------------------------"
    setColor "$NC"
  done

  echo "" # Empty line
}

function createUser {
  setColor "BLUE" # Set color to blue
  read -rp "Please, enter the name of the user: " newUsername
  read -rp "Please, enter the password of the user: " newPassword

  clear # Clear the screen
  userExists=false
  # Check if the user already exists
  for user in $users; do
    name=$(echo "$user" | awk -F'.' '{print $1}')

    if [ "$name" == "$newUsername" ]; then
      setColor "$RED" # Set color to red
      echo "The user already exists"
      setColor "$NC" # Reset color
      userExists=true
      break
    fi
  done

  if [ "$userExists" == false ]; then
    printf "\n" >>"./users.txt"                                    # Add a new line
    printf "%s / %s" "$newUsername" "$newPassword" >>"./users.txt" # Add the new user to the file
    users=$(cat "./users.txt" | awk -F' / ' '{print $1 "." $2}')   # Update users array

    setColor "$GREEN" # Set color to green
    echo "User created successfully"
    setColor "$NC" # Reset color
  fi

  echo "" # Empty line
}

function searchWord {
  clear # Clear the screen

  dicc=$(cat "./diccionario.txt") # Get the words from the file

  if [ "$2" -eq 1 ]; then
    wordsThatMatch=$(grep -P "$1" <<<"$dicc") # Get the words that match the regex
  else
    wordsThatMatch=$(grep -E "$1" <<<"$dicc") # Get the words that match the regex
  fi

  setColor "$GREEN" # Set color to green
  echo "Matching words:"
  setColor "$NC"              # Reset color
  echo "${wordsThatMatch[@]}" # Print the words that match the regex

  date=$(date +"%Y-%m-%d %H:%M:%S")                            # Get the current date
  cantWords=$(wc -l <<<"$wordsThatMatch")                      # Get the amount of words that match the regex
  totalWords=$(wc -l <<<"$dicc")                               # Get the total amount of words
  percentage=$(calculatePercentage "$cantWords" "$totalWords") # Calculate the percentage of words that match the regex

  if [ -z "$wordsThatMatch" ]; then
    setColor "$RED" # Set color to red
    echo "No words match"
    setColor "$NC" # Reset color
  else
    header="       Date         | Amount of words | Total words | Percentage |     User     |   Regex    " # Header of the log file
    if [ ! -f "./log.txt" ]; then                                                                          # Check if the log file exists
      echo "$header" >>"./log.txt"                                                                         # Create the log file with the header
    fi

    # Add the log to the file
    echo "$date | $(formatWord "$cantWords" 15) | $(formatWord "$totalWords" 11) | $(formatWord "$percentage" 10) | $(formatWord "$username" 12) | $1 " >>"./log.txt"
  fi
}

function revertWord {
  word=$1
  invertedWord=""

  for ((i = 0; i < ${#word}; i++)); do       # Loop through the word
    invertedWord="${word:$i:1}$invertedWord" # Add the letter to the beginning of the word
  done

  echo "$invertedWord"
}

function executeMain {
  export LC_ALL=C.UTF-8 # Set the locale to UTF-8
  login                 # Call the login function
  while true; do        # Infinite loop to show the menu after each option
    # Print menu
    generateMenu

    case $option in
    1)
      listUsers
      ;;
    2)
      createUser
      ;;
    3)
      requestInput "Please, enter the start letter" startLetter
      ;;
    4)
      requestInput "Please, enter the end letter" endLetter
      ;;
    5)
      requestInput "Please, enter the contained letter" containedLetter
      ;;
    6)
      searchWord "\b^${startLetter}.*${containedLetter}.*${endLetter}$\b"
      ;;
    7)
      requestInput "Please, enter the vowel" vowel
      ;;
    8)
      remainingVowels=$(echo "aeiou" | awk -F'i' '{print $1$2}')
      searchWord "\b(?=\w*${vowel})(?!\w*[${remainingVowels}])\w+\b" 1
      ;;
    9)
      requestInput "Please, enter the quantity of inputs" qNumbers # Request the quantity of inputs

      requestInput "Please, enter a number" number # Request the first number
      maxNumber=$number                            # Set the maximum number to the first number
      minNumber=$number                            # Set the minimum number to the first number
      sum=$number                                  # Set the sum to the first number

      for ((i = 1; i < qNumbers; i++)); do
        requestInput "Please, enter a number" number
        if [ "$number" -gt "$maxNumber" ]; then
          maxNumber=$number
        fi
        if [ "$number" -lt "$minNumber" ]; then
          minNumber=$number
        fi
        sum=$((sum + number))
      done

      clear

      echo "The average is: $(calculateAverage "$sum" "$qNumbers")"
      echo "The maximum number is: $maxNumber"
      echo "The minimum number is: $minNumber"
      ;;
    10)
      requestInput "Please, enter the word" word # Request word to check
      invertedWord=$(revertWord "$word")             # Reverse the word

      clear

      if [ "$word" == "$invertedWord" ]; then
        setColor "$GREEN"
        echo "True"
      else
        setColor "$RED"
        echo "False"
      fi

      setColor "$NC"
      ;;
    11)
      setColor "$GREEN"
      echo "Goodbye $username"
      exit 0
      ;;
    *)
      echo "Invalid option"
      ;;
    esac
  done
}

executeMain