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
function calculatePercentage {
  result=$(($1 * 10000 / $2))                                  # Calculate the percentage, multiplying by 10000 to get 2 decimal places
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

function showSuccess {
  setColor "$GREEN" # Set color to green
  echo "$1"         # Print the success message
  setColor "$NC"    # Reset color
}

function showError {
  setColor "$RED" # Set color to red
  echo "$1"       # Print the error message
  setColor "$NC"  # Reset color
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

  showSuccess "$message set to: $input_value"
}

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
    showError "Invalid username or password"
    exit 1
  fi
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
  clear # Clear the screen
  showSuccess "Users:"

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
  setColor "$BLUE" # Set color to blue
  read -rp "Please, enter the name of the user: " newUsername
  read -rp "Please, enter the password of the user: " newPassword

  clear # Clear the screen
  userExists=false
  # Check if the user already exists
  for user in $users; do
    name=$(echo "$user" | awk -F'.' '{print $1}')

    if [ "$name" == "$newUsername" ]; then
      showError "The user already exists"
      userExists=true
      break
    fi
  done

  if [ "$userExists" == false ]; then
    printf "\n" >>"./users.txt"                                    # Add a new line
    printf "%s / %s" "$newUsername" "$newPassword" >>"./users.txt" # Add the new user to the file
    users=$(cat "./users.txt" | awk -F' / ' '{print $1 "." $2}')   # Update users array

    showSuccess "User created successfully"
  fi

  echo "" # Empty line
}

function explainRegex {

  regexExplanation="Words that "
  if [ "$1" == "1" ]; then
    # Check if the vowel is set, and add it to the explanation
    regexExplanation="$regexExplanation only have the vowel $vowel"
  else
    # Check if the start letter is set, and add it to the explanation
    [ -n "$startLetter" ] && regexExplanation="${regexExplanation}start with $startLetter"
    # Check if the contained letter is set, and add it to the explanation, adding a comma if the start letter is set
    [ -n "$containedLetter" ] && regexExplanation="$regexExplanation${startLetter:+, }contain $containedLetter"
    # Check if the end letter is set, and add it to the explanation, adding a and if the start letter or contained letter is set
    andConnector=""
    [ -n "$startLetter" ] || [ -n "$containedLetter" ] && andConnector=" and "
    [ -n "$endLetter" ] && regexExplanation="${regexExplanation}${andConnector}end with $endLetter"
  fi

  [ "$regexExplanation" == "Words that " ] && regexExplanation="All words" # Check if the explanation is empty, and set it to "All words"

  echo "$regexExplanation"
}

function searchWord {
  clear # Clear the screen

  dicc=$(cat "./diccionario.txt") # Get the words from the file, removing the spaces

  if [ "$2" -eq 1 ]; then
    wordsThatMatch=$(grep -Px "$1" <<<"$dicc") # Get the words that match the regex, -P to use Perl regex, -x to match the whole line
  else
    wordsThatMatch=$(grep -Ex "$1" <<<"$dicc") # Get the words that match the regex, -E to use extended regex, -x to match the whole line
  fi

  showSuccess "Matching words:"
  echo "${wordsThatMatch[@]}" # Print the words that match the regex

  date=$(date +"%Y-%m-%d %H:%M:%S")                            # Get the current date
  cantWords=$(wc -l <<<"$wordsThatMatch")                      # Get the amount of words that match the regex, by counting the lines
  totalWords=$(wc -l <<<"$dicc")                               # Get the total amount of words, by counting the lines
  percentage=$(calculatePercentage "$cantWords" "$totalWords") # Calculate the percentage of words that match the regex

  if [ -z "$wordsThatMatch" ]; then
    clear
    showError "No words match"
  else
    header="       Date         | Amount of words | Total words | Percentage |     User     |          Regex           |    Regex Explained" # Header of the log file
    if [ ! -f "./log.txt" ]; then                                                                                                            # Check if the log file exists
      echo "$header" >>"./log.txt"                                                                                                           # Create the log file with the header
    fi

    regexExplanation=$(explainRegex "$2")
    # Add the log to the file
    echo "$date | $(formatWord "$cantWords" 15) | $(formatWord "$totalWords" 11) | $(formatWord "$percentage" 10) | $(formatWord "$username" 12) | $(formatWord "$1" 24) | $regexExplanation " >>"./log.txt"
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
  export LC_ALL=C.UTF-8 # Set the locale to UTF-8, for perl regex to work
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
      if [ "${#startLetter}" -ne 1 ] && [ -n "$startLetter" ]; then
        clear # Clear the screen
        showError "It must be a single letter"
        startLetter="" # Reset the start letter
        continue       # Exit to the menu
      fi
      ;;
    4)
      requestInput "Please, enter the end letter" endLetter
      if [ "${#endLetter}" -ne 1 ] && [ -n "$endLetter" ]; then
        clear # Clear the screen
        showError "It must be a single letter"
        endLetter="" # Reset the end letter
        continue     # Exit to the menu
      fi
      ;;
    5)
      requestInput "Please, enter the contained letter" containedLetter
      if [ "${#containedLetter}" -ne 1 ] && [ -n "$containedLetter" ]; then
        clear # Clear the screen
        showError "It must be a single letter"
        containedLetter="" # Reset the contained letter
        continue           # Exit to the menu
      fi
      ;;
    6)
      searchWord "^${startLetter}.*${containedLetter}.*${endLetter}$"
      ;;
    7)
      requestInput "Please, enter the vowel" vowel
      if [[ ! "$vowel" =~ ^[aeiou]$ ]]; then
        clear # Clear the screen
        showError "It must be a vowel"
        vowel="" # Reset the vowel
        continue # Exit to the menu
      fi

      ;;
    8)
      if [ -z "$vowel" ]; then
        setColor "$RED" # Set color to red
        echo "A vowel must be set to do this operation"
        setColor "$NC" # Reset color
        continue       # Exit to the menu
      fi
      remainingVowels=$(echo "aeiou" | awk -F"${vowel}" '{print $1$2}') # Get the remaining vowels
      searchWord "(?=\w*${vowel})(?!\w*[${remainingVowels}])\w+" 1
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
      invertedWord=$(revertWord "$word")         # Reverse the word

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