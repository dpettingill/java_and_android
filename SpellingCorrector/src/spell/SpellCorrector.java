package spell;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    public SpellCorrector() {}
    private Trie myTrie = new Trie();


    /**
     * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
     * for generating suggestions.
     * @pre SpellCorrector will have had empty-param constructor called, but dictionary has nothing in it.
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException If the file cannot be read
     * @post SpellCorrector will have dictionary filled and be ready to suggestSimilarWord any number of times.
     */
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        File myFile = new File(dictionaryFileName);
        Scanner myScanner = null;
        try {
            myScanner = new Scanner(myFile);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        while (myScanner.hasNextLine()) {
            Scanner s2 = new Scanner(myScanner.nextLine());
            while (s2.hasNext()) {
                String str = s2.next();
                myTrie.add(str);
            }
        }
    }


    /**
     * Suggest a word from the dictionary that most closely matches
     * <code>inputWord</code>.
     * @param inputWord the word we are trying to find or find a suggestion for
     * @return the suggestion or null if there is no similar word in the dictionary
     */
    public String suggestSimilarWord(String inputWord)
    {

        return "hello";
    }

    public Trie getMyTrie()
    {
        return myTrie;
    }

}
