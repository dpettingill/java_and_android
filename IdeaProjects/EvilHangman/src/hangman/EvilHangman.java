package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {
        try {
            if (args.length < 3)
            {
                throw (new IOException());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File dictionary = new File(args[0]);
        int wordLength = Integer.valueOf(args[1]);
        int guesses = Integer.valueOf(args[2]);

        EvilHangmanGame game = new EvilHangmanGame();
        try {
            game.startGame(dictionary, wordLength);
        } catch (EmptyDictionaryException e) {
            System.out.println("try giving us a non-empty dictionary or one that will actually produce words!");
        }

        Map<Integer, Character> correctGuesses = new HashMap<>();
        Set<String> myWords = new HashSet();
        boolean correctGuess = false;
        while (guesses > 0)
        {
            System.out.printf("You have %d guesses left\n", guesses);
            System.out.printf("Used letters: %s\n", game.getGuessedLetters().toString());
            wordOutput(game, correctGuesses, wordLength); //word output
            char guess = guessing(game, myWords); //guessing func
            myWords = game.getMyWords();
            correctGuess = guessEvaluation(game, myWords, correctGuesses, guess);//guess evaluation
            if (!correctGuess)
            {
                guesses--;
            }
            if (correctGuesses.size() >= wordLength)
            {
                break;
            }
        }
        if (guesses <= 0 && correctGuesses.size() != wordLength)
        {
            System.out.printf("Sorry you win some and you lose some and you lost this one for sure!\n");
            Iterator it = myWords.iterator();
            System.out.printf("The word was: %s", it.next().toString());
        }
        else
        {
            System.out.printf("Wow. IDK how you pulled that off but nice win!\n");
            Iterator it = myWords.iterator();
            System.out.printf("You guessed the word: %s", it.next().toString());
        }
    }

    private static void wordOutput(EvilHangmanGame game, Map<Integer, Character> correctGuesses, int wordLength)
    {
        System.out.printf("Word: ");
        for (int i = 0; i < wordLength; i++)
        {
            if (correctGuesses.get(i) == null)
            {
                System.out.printf("-");
            }
            else
            {
                System.out.printf("%c", correctGuesses.get(i));
            }
        }
        System.out.printf("\n");
    }

    public static char guessing(EvilHangmanGame game, Set<String> myWords) throws GuessAlreadyMadeException {
        byte valid_input = 0;
        char guess = '\0';
        while (valid_input == 0)
        {
            System.out.printf("Enter Guess: ");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();
            guess = s.charAt(0);
            guess = Character.toLowerCase(guess);
            if (guess >= 'a' && guess <= 'z')
            {
                try {
                    myWords = game.makeGuess(guess);
                    valid_input = 1;

                } catch (GuessAlreadyMadeException e)
                {
                    System.out.printf("Uh already guessed that bro..try again!\n");
                }
            }
            else
            {
                valid_input = 0;
                System.out.printf("Invalid input. Try a letter a-z\n");
            }
        }
        return guess;
    }

    private static boolean guessEvaluation(EvilHangmanGame game, Set<String> myWords, Map<Integer, Character> correctGuesses, char guess)
    {
        int count = 0;
        for (String s : myWords)
        {
            for (int i = 0; i < s.length(); i++)
            {
                if (s.charAt(i) == guess)
                {
                    count++;
                    correctGuesses.put(i, guess);
                }
            }
            break;
        }
        if (count == 0)
        {
            System.out.printf("Sorry there are no %c's!\n\n", guess);
            return false;
        }
        else if (count == 1)
        {
            System.out.printf("Nice! There is 1 %c!\n\n", guess);
            return true;
        }
        else
        {
            System.out.printf("Noice! There are %d %c!\n\n", count, guess);
            return true;
        }
    }




}
