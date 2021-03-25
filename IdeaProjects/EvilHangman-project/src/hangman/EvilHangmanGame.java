package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    public EvilHangmanGame() {}
    private Set<String> myWords = new HashSet<String>();
    private SortedSet<Character> guessedLetters = new TreeSet<Character>();
    public String key = null;

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        Scanner myScanner = null;
        myWords.clear();
        guessedLetters.clear();
        //check if the file exists
        try {
            myScanner = new Scanner(dictionary);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //check if the file is empty
        if (dictionary.length() == 0)
        {
            throw new EmptyDictionaryException();
        }

        while (myScanner.hasNextLine()) {
            Scanner s2 = new Scanner(myScanner.nextLine());
            while (s2.hasNext()) {
                String str = s2.next().toLowerCase();
                //add all words of length wordLength
                if (str.length() == wordLength)
                {
                    myWords.add(str);
                }
            }
        }
        if (myWords.isEmpty())
        {
            throw new EmptyDictionaryException();
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if (guessedLetters.contains(guess))
        {
            throw new GuessAlreadyMadeException();
        }

        HashMap<String, Set<String>> wordGroups = findWordGroups(guess); //sort words into groups
        Set<String> keysToPossibleGroups = sortByWordCount(wordGroups);
        if (keysToPossibleGroups.size() != 1) //then sort by minimum letter count
        {
            keysToPossibleGroups = sortByLetterCount(keysToPossibleGroups);
        }

        int start_pos[] = { 0 }; //an array so that functions can change it's value
        while (keysToPossibleGroups.size() != 1) //then narrow by right most letter
        {
            keysToPossibleGroups = sortByLetterPosition(keysToPossibleGroups, start_pos);
        }

        //could I probably just assign myWords to the wordGroup? Probably. I feel safer doing this though
        Iterator it = keysToPossibleGroups.iterator();
        key = (String) it.next();
        try
        {
            myWords.retainAll(wordGroups.get(key));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //oh and don't forget to add the guess!
        guessedLetters.add(guess);

        return myWords;
    }


    //sorts through the possibleGroups based on right most guess letter position
    private Set<String> sortByLetterPosition(Set<String> possibleGroups, int start_pos[])
    {
        int pos = 0, max_pos = 0;
        Set<String> newPossibleGroups = new HashSet<String>();
        for (String key : possibleGroups)
        {
            for (int i = start_pos[0]; i < key.length(); i++)
            {
                if (key.charAt(i) == '1')
                {
                    pos = i;
                    break;
                }
            }
            if (pos > max_pos)
            {
                max_pos = pos;
                newPossibleGroups.clear();
                newPossibleGroups.add(key);
            }
            else if (pos == max_pos)
            {
                newPossibleGroups.add(key);
            }
            pos = 0;
        }

        start_pos[0] = max_pos + 1; //update start_pos
        return newPossibleGroups;
    }

    //Used to sort through the map of groups and find those that are largest
    private Set<String> sortByWordCount(Map<String, Set<String>> wordGroups)
    {
        Set<String> possibleGroups = new HashSet<String>();
        int size = 0;
        for (Map.Entry<String, Set<String>> wordGroup : wordGroups.entrySet())
        {
            if (wordGroup.getValue().size() > size)
            {
                size = wordGroup.getValue().size();
                possibleGroups.clear();
                possibleGroups.add(wordGroup.getKey());
            }
            else if (wordGroup.getValue().size() == size)
            {
                possibleGroups.add(wordGroup.getKey());
            }

        }
        return possibleGroups;
    }

    //used to return a set of the keys linked to the groups with the minimum guessed letter count
    private Set<String> sortByLetterCount(Set<String> possibleGroups)
    {
        //count the number of letter guess in each family left
        Set<String> newPossibleGroups = new HashSet<String>();
        int count = 0, min = 0;
        byte first = 1;
        for (String key : possibleGroups)
        {
            for (int i = 0; i < key.length(); i++)
            {
                if (key.charAt(i) == '1')
                {
                    count++;
                }
            }
            if (first == 1) //first time set min to count
            {
                min = count;
                first = 0;
                newPossibleGroups.add(key);
            }
            else if (count < min) //if we find a new min then save it and the key for that group
            {
                min = count;
                newPossibleGroups.clear();
                newPossibleGroups.add(key);
            }
            else if (count == min) //if we matched the min the add to set of possible groups
            {
                newPossibleGroups.add(key);
            }
            count = 0; //reset count every run
        }
        return newPossibleGroups;
    }


    private HashMap<String, Set<String>> findWordGroups(char guess)
    {
        HashMap<String, Set<String>> wordGroups = new HashMap<String, Set<String>>(); //aha!
        String key = "";
        for (String s : myWords)
        {
            //I could loop through each letter and look at charAt(i) == guess
            // String key += 1 else key += 0;
            for (int i = 0; i < s.length(); i++)
            {
                if (s.charAt(i) == guess)
                {
                    key += '1';
                }
                else
                {
                    key += '0';
                }
            }
            if (wordGroups.containsKey(key))
            {
                wordGroups.get(key).add(s); //add the string to that group of words
            }
            else //create a new set mapped to this key
            {
                Set<String> tmpSet = new HashSet<String>();
                tmpSet.add(s);
                wordGroups.put(key, tmpSet);
            }
            key = ""; //reset key
        }
        return wordGroups;
    }


    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public String getCurrentKey()
    {
        return key;
    }

    public String getWord()
    {
        Iterator it = myWords.iterator();
        return it.next().toString();
    }

}
