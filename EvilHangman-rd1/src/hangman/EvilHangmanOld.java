package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class EvilHangmanOld {


    //alrighty and now for the
    //I'll need to throw exceptions or wrap in an if statement or something for all of my checks
    //instead of just printing out to the console
    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {
        if (args.length < 3)
        {
            System.out.println("give the proper inputs!");
        }

        File dictionary = new File(args[0]);
        int wordLength = Integer.valueOf(args[1]);
        int guesses = Integer.valueOf(args[2]);

        if (wordLength <= 1)
        {
            System.out.println("give the proper inputs!");
            throw (new IOException());
        }
        if (guesses <= 0)
        {
            System.out.println("give the proper inputs!");
        }

        EvilHangmanGame game = new EvilHangmanGame();
        try {
            game.startGame(dictionary, wordLength);
        }
        catch (EmptyDictionaryException e) {
            System.out.println("Bad dictionary!");
        }

        playTheGame(game, guesses, wordLength);
    }

    //ok what are we going to do here??
    private static void playTheGame(EvilHangmanGame game, int guesses, int wordLength) throws GuessAlreadyMadeException {
        Map<Integer, Character> correctGuesses = new HashMap();
        Set<String> words = new HashSet();
        byte guess_check = 0;
        while (guesses > 0)
        {
            System.out.printf("You have %d guesses left\n", guesses);
            System.out.printf("%s\n", game.getGuessedLetters().toString());
            wordOutput(correctGuesses, wordLength);
            char guess = '\0';
            while (guess_check == 0)
            {
                try {
                    guess = guessing();
                    words = game.makeGuess(guess);
                    guess_check = 1;
                }
                catch (GuessAlreadyMadeException e)
                {
                    System.out.println("You've already guessed that! Try again");
                    guess_check = 0;
                }
            }

            guessCheck(words, guess, correctGuesses);//checks for if there are guess or not here
            if (correctGuesses.size() >= wordLength)
            {
                System.out.printf("Congrats! You won the game\n");
                Iterator it = words.iterator();
                System.out.printf("You guessed the word: %s", it.next());
                break;
            }
            guesses--;
            guess_check = 0;
        }
        if (guesses <= 0 && correctGuesses.size() < wordLength)
        {
            System.out.printf("So I don't want to be the one to say it but you lost :'(\n");
            Iterator it = words.iterator();
            System.out.printf("the correct word was: %s", it.next());
        }
    }

    //how do I want to do this?? I will get the list of words returned to me each time I make a guess
    //I could go through and find the letter guess in those words
    //or i could just keep a set of correctly guessed letters and output those
    //map<int pos, char guess> where
    private static void wordOutput(Map<Integer, Character> correctGuesses, int wordLength)
    {
        if (correctGuesses.size() == 0)
        {
            for (int i = 0; i < wordLength; i++)
            {
                System.out.printf("-");
            }
        }
        else
        {
            for (int i = 0; i < wordLength; i++)
            {
                if (correctGuesses.containsKey(i))
                {
                    System.out.printf("%c", correctGuesses.get(i));
                }
                else
                {
                    System.out.printf("-");
                }
            }
        }
        System.out.printf("\n");
    }


    //how do I just get the next char from my scanner??
    private static char guessing()
    {
        byte valid_input = 0;
        char guess = '\0';
        while (valid_input != 1)
        {
            System.out.printf("Enter guess: ");
            Scanner s = new Scanner(System.in);
            String str = s.next(); //need to make sure just hitting enter doesn't break my program
            guess = str.charAt(0);
            guess = Character.toLowerCase(guess);
            if (guess < 'a' || guess > 'z')
            {
                System.out.printf("invalid input! ");
            }
            else
            {
                valid_input = 1;
            }
            //try my ignore line here?? Idk what it was though Scanner.ignore("/r/n|[/n/r stuff]);
        }
        return guess;
    }

    private static void guessCheck(Set<String> words, char guess, Map<Integer, Character> correctGuesses)
    {
        Iterator it = words.iterator();
        String word = (String)it.next();
        int count = 0;
        for (int i = 0; i < word.length(); i++)
        {
            if (word.charAt(i) == guess)
            {
                count++;
                correctGuesses.put(i, guess);
            }
        }
        if (count == 0)
        {
            System.out.printf("Sorry, there are no %c\n", guess);
        }
        else if (count == 1)
        {
            System.out.printf("Yes there is 1 %c\n", guess);
        }
        else
        {
            System.out.printf("Yes there are %d %c\n", count, guess);
        }
    }

}
