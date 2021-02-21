package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {
        if (args.length < 3)
        {
            System.out.println("Looks like you didn't give enough input params. Try again with dictionary wordLength guesses");
        }
        File myFile = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        if (wordLength < 2)
        {
            System.out.println("Hey. Try giving a word length of 2 or longer");
        }
        int guesses = Integer.parseInt(args[2]);
        if (guesses < 1)
        {
            System.out.println("Hey. Try giving at least 1 guess");
        }
        EvilHangmanGame myGame = new EvilHangmanGame();
        myGame.startGame(myFile, wordLength);
        gamePrintOut(myGame, wordLength, guesses);
    }

    public static void gamePrintOut(EvilHangmanGame game, int length, int guessesLeft) throws IOException, GuessAlreadyMadeException {
        char guess = '\0';
        boolean bad_user_input = true, correctGuess = false;
        int countCorrectGuesses = 0, totalCorrectGuesses = 0;
        Map<Integer, Character> correctGuesses = new HashMap<Integer, Character>();
        String key = null;
        while (guessesLeft > 0)
        {
            System.out.printf("You have %d guesses remaining\n", guessesLeft);
            System.out.printf("Used Letters: %s\n", game.getGuessedLetters().toString());
            wordOutput(game, correctGuesses, guess, length);
            guess = guessOutputInput(game);
            key = game.getCurrentKey();
            for (int i = 0; i < key.length(); i++)
            {
                if (key.charAt(i) == '1')
                {
                    countCorrectGuesses++;
                    totalCorrectGuesses++;
                    correctGuess = true;
                }
            }
            if (correctGuess)
            {
                if (countCorrectGuesses == 1)
                {
                    System.out.printf("Yes, there is %d %c: \n\n", countCorrectGuesses, guess);
                }
                else
                {
                    System.out.printf("Yes, there are %d %c: \n\n", countCorrectGuesses, guess);
                }
            }
            else
            {
                System.out.printf("Sorry, there are no %c's\n\n", guess);
            }

            //check if they won or lost here
            if (totalCorrectGuesses == length)
            {
                System.out.println("Congrats! You beat the system!! :')\n\n\n");
                break;
            }
            guessesLeft--;
            countCorrectGuesses = 0;
            correctGuess = false;
        }
        if (guessesLeft <= 0)
        {
            System.out.println("I don't wanna say you suck, but you did lose...:'(\n\n\n");
            System.out.printf("The word was: %s\n", game.getWord());
        }

    }

    public static void wordOutput(EvilHangmanGame game, Map<Integer, Character> correctGuesses, char guess, int length)
    {
        String key = game.getCurrentKey();
        System.out.printf("Word: ");
        if (key != null)
        {
            for (int i = 0; i < key.length(); i++)
            {
                if (correctGuesses.containsKey(i))
                {
                    System.out.print(correctGuesses.get(i));
                }
                else if (key.charAt(i) == '0')
                {
                    System.out.print('-');
                }
                else
                {
                    System.out.print(guess);
                    correctGuesses.put(i, guess); //add this to my map
                }
            }
        }
        else
        {
            for (int i = 0; i < length; i++)
            {
                System.out.print('-');
            }
            System.out.print('\n');
        }
    }

    public static char guessOutputInput(EvilHangmanGame game)
    {
        boolean bad_user_input = true;
        char guess = '\0';
        System.out.printf("Enter Guess: ");
        while (bad_user_input)
        {
            Scanner scanner = new Scanner(System.in);
            guess = scanner.next().charAt(0);
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
            guess = Character.toLowerCase(guess);
            if (guess >= 'a' && guess <= 'z')
            {
                try
                {
                    game.makeGuess(guess);
                    bad_user_input = false;
                }
                catch (GuessAlreadyMadeException e)
                {
                    System.out.println("Hey so you already guessed that letter...try another one");
                    bad_user_input = true;
                }
            }
            else
            {
                System.out.println("So uh how about guessing a letter in the alphabet?");
            }
        }
        return guess;
    }


}
