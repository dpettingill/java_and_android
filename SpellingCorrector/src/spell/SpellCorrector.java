package spell;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    public SpellCorrector() {}
    private Trie myDictionary = new Trie();
    private Set<String> similarWords = new TreeSet<String>();


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
                String str = s2.next().toLowerCase();
                myDictionary.add(str);
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
        similarWords.clear(); //empty the set first
        inputWord = inputWord.toLowerCase();
        if (myDictionary.find(inputWord) != null)
        {
            return inputWord;
        }


        Node tmp;
        boolean edit1 = false;

        //rd 1
        getDistanceWords(inputWord);
        for (String s : similarWords)
        {
            tmp = myDictionary.find(s);
            if (tmp != null) //we have at least 1 edit 1 d word no need to generate more
            {
                edit1 = true;
            }
        }

        if (!edit1) //get edit 2s
        {
            Set<String> temp = new TreeSet<String>(similarWords); //use this to iterate through
            for (Iterator<String> i = temp.iterator(); i.hasNext();) {
                String s = i.next();
                getDistanceWords(s);
            }
        }

        for (Iterator<String> i = similarWords.iterator(); i.hasNext();) {
            String s = i.next();
            tmp = myDictionary.find(s);
            if (tmp == null)
            {
                i.remove(); //remove all words not in dictionary
            }
        }

        //edge cases
        if (similarWords.size() == 1)
        {
            return similarWords.toString().replace("[","").replace("]","");
        }
        else if (similarWords.size() > 1)
        {
            int i = 0, max = 0;
            String myWord = null;
            for (String s : similarWords)
            {
                if (myDictionary.find(s).getValue() > max)
                {
                    max = myDictionary.find(s).getValue();
                    myWord = s;
                }
                i++;
            }
            return myWord;
        }
        else
        {
            return null; //give up
        }
    }

    public void getDistanceWords(String inputWord)
    {
        delDistance(inputWord);
        tranDistance(inputWord);
        altDistance(inputWord);
        insDistance(inputWord);
    }


    public void delDistance(String inputWord)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(inputWord);
        int pos = 0;
        for (int i = 0; i < inputWord.length(); i++)
        {
            sb.deleteCharAt(i);
            similarWords.add(sb.toString()); //add to set
            sb.replace(0, inputWord.length(), inputWord); //reset sb
        }
    }

    public void tranDistance(String inputWord)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(inputWord);
        for (int i = 0; i < (inputWord.length() - 1); i++)
        {
            char c = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(i+1));
            sb.setCharAt((i+1), c);
            similarWords.add(sb.toString());
            sb.replace(0, inputWord.length(), inputWord);
        }
    }

    public void altDistance(String inputWord)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(inputWord);
        for (int i = 0; i < inputWord.length(); i++)
        {
            char c = sb.charAt(i);
            for (int j = 0; j < 26; j++)
            {
                sb.deleteCharAt(i);
                sb.insert(i, (char)((j + 'a')));
                similarWords.add(sb.toString());
            }
            sb.replace(0, inputWord.length(), inputWord); //reset sb
        }
    }

    public void insDistance(String inputWord)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(inputWord);
        for (int i = 0; i < inputWord.length() + 1; i++)
        {
            for (int j = 0; j < 26; j++)
            {
                sb.insert(i, (char)((j + 'a')));
                similarWords.add(sb.toString());
                sb.deleteCharAt(i);
            }
            sb.replace(0, inputWord.length(), inputWord); //reset sb
        }
    }


    public Trie getMyTrie()
    {
        return myDictionary;
    }

}
