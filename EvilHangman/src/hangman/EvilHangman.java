package hangman;

import java.io.File;
import java.io.IOException;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException {
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
        EvilHangmanGame playTheGame = new EvilHangmanGame();
        playTheGame.startGame(myFile, wordLength);



    }

    private void gamePrintOut(int length, int guessesLeft)
    {

    }

}
