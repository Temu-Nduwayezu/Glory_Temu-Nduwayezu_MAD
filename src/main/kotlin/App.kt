import java.util.*

// The number of digits in the generated number
const val NUM_DIGITS = 4

// Compare the guess with generated number and return the result
fun getResult(guess: String, randomGeneratedNumber: String): List<Int> {
    // n= number of correct digits and m= correct positions in the user's guess
    val n = guess.filter { it in randomGeneratedNumber }.length
    val m = guess.withIndex().count { (i, c) -> randomGeneratedNumber[i] == c }
    return listOf(n, m)
}

// Generate random 4-digit number with unique digits
fun generateNumber(): String {
    // next line of code variable used for generating random digits, with this the first digit can be 0.
    val digits = mutableListOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    digits.shuffle()
    val sb = StringBuilder()
    for (i in 0 until NUM_DIGITS) {
        sb.append(digits[i])
        digits.remove(digits[i])
    }
    return sb.toString()
}

fun main() {
    val scanner = Scanner(System.`in`)
    println(" \n Welcome to the Number Guessing Game! \n")
    println("The Information/rules.")
    println("The game generates a $NUM_DIGITS-digit number with unique digits.")
    println("The goal of the game is to guess a $NUM_DIGITS-digit number in as few attempts as possible. \n")

    var playAgain: Boolean
    do {
        // Generate a new random number and prompt the user for guesses until they guess the correct number or quit
        val randomGeneratedNumber = generateNumber()
     //   println("Generated number: $randomGeneratedNumber") //  use only for troubleshooting
        var guessCount = 0
        var guess: String
        do {
            print("Enter your guess (or type 'q' to quit the game): \n")
            guess = scanner.nextLine().trim()
            if (guess == "q") {
                println("The number was $randomGeneratedNumber")
                break
            }
            // check the digits length and check for non-digit characters
            guessCount++
            if (guess.length != NUM_DIGITS || !guess.all { it.isDigit() }) {
                println("Invalid guess! Please enter a $NUM_DIGITS-digit number.")
                continue
            }

            // Check the player guess against the generated number and display the number of correct digits and positions
            val (n, m) = getResult(guess, randomGeneratedNumber)
            println("$n:$m")
        } while (guess != randomGeneratedNumber)

        //when the guessed digits are correct inform the user and display the number of attempts
        if (guess == randomGeneratedNumber) {
            val message = when (guessCount) {
                in 1..8 -> "Super! You guessed the correct number in $guessCount attempt(s)."
                in 9..12 -> "Good! You guessed the correct number in $guessCount attempts."
                in 13..16 -> "Average! You guessed the correct number in $guessCount attempts."
                else -> "Better luck next time. You guessed the correct number in $guessCount attempts."
            }
            println(message)
        }
        //Prompt the user/player to play again or exit the game
        print("Do you want to play again? (type 'y' to play and 'n' to exit): \n")
        playAgain = scanner.nextLine().trim().equals("y", ignoreCase = true)
    } while (playAgain)
    println("Thank you for playing!")
}
