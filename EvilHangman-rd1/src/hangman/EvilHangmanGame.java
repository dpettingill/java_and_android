package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class EvilHangmanGame implements IEvilHangmanGame {
    public EvilHangmanGame() {}
    private Set<String> availableWords = new HashSet<String>();
    private SortedSet<Character> guessedLetters = new TreeSet<Character>();

    @Override
    //okay so how do I do the startGame function again??
    //I think I need to load in the dictionary and check to make sure it is not empty if the length == 0 thing
    //then implement a scanner that adds the words to my public set
    //ahh but I want to get all of the words that are wordLength long
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        availableWords.clear(); //clear btw tests
        guessedLetters.clear();
        Scanner scanner;

        try {
            scanner = new Scanner(dictionary);
        }
        catch (Exception e) {
            throw(new EmptyDictionaryException());
        }

        if (dictionary.length() == 0)
        {
            throw(new EmptyDictionaryException());
        }

        if (wordLength <= 1)
        {
            throw (new EmptyDictionaryException());
        }

        while (scanner.hasNextLine())
        {
            String word = scanner.nextLine();
            word = word.toLowerCase();
            if (word.length() == wordLength)
            {
                availableWords.add(word);
            }
        }

        if (availableWords.isEmpty())
        {
            throw(new EmptyDictionaryException());
        }
    }

    @Override
    //okay so what are the things that I need to do for the makeGuess funcion??
    //this is where the magic happens. We need to do the 3 different checks.
    //group the words together
    //then find the group that is the largest
    //if multiple of the same size then find the one that has letter guess in it the least
    //if still multiple groups then find the one with the rightmost letter and keep checking letters until only 1 group
    //use int pos[] {0}; for this loop
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        //check if already guessed do a try catch in main and output something if caught
        guess = Character.toLowerCase(guess);
        if (guessedLetters.contains(guess))
        {
            throw (new GuessAlreadyMadeException());
        }

        //k we will want to map each set of words (group) to a string of ones and zeros (key)
        Map<String, Set<String>> myGroups = new HashMap<String, Set<String>>();
        Set<String> myKeys = new HashSet(); //now we have a map of groups and a set of keys
        sortIntoGroups(myGroups, guess, myKeys);
        findLargestGroup(myKeys, myGroups);
        if (myKeys.size() != 1)
        {
            findMinCountGuess(myGroups, myKeys); //find the group with the least amount of letters
        }

        int pos[] = {0};
        while (myKeys.size() != 1)
        {
            findRightMostGroup(myGroups, myKeys, pos);//get the rightmost letter group
        }

        //k now we have our one group
        Iterator it = myKeys.iterator();
        availableWords.retainAll(myGroups.get(it.next()));

        guessedLetters.add(guess); //add the guess

        return availableWords;
    }

    //k how will we do this function??
    //loop through and create keys
    //if the key already exists then add the word to the set
    //if it doesn't then create a new set and add it to the map
    private void sortIntoGroups(Map<String, Set<String>> myGroups, char guess, Set<String> myKeys)
    {
        String key = "";
        for (String word : availableWords)
        {
            for (int i = 0; i < word.length(); i++)
            {
                if (word.charAt(i) == guess)
                {
                    key += '1';
                }
                else
                {
                    key += '0';
                }
            }
            if (myGroups.containsKey(key))
            {
                myGroups.get(key).add(word); //add word to the set mapped to that key
            }
            else
            {
                Set<String> tmp = new HashSet();
                tmp.add(word);
                myGroups.put(key, tmp); //add the key and the set to the map
                myKeys.add(key);
            }
            key = "";
        }
    }

    //how to do this guy??
    //I need to loop through each gropu in myGroups and check the size of each
    private Set<String> findLargestGroup(Set<String> myKeys, Map<String, Set<String>> myGroups)
    {
        int max_size = 0;
        Set<String> potentials = new HashSet();
        for (String key : myKeys)
        {
            if (myGroups.get(key).size() > max_size)
            {
                max_size = myGroups.get(key).size();
                potentials.clear();
                potentials.add(key);
            }
            else if (myGroups.get(key).size() == max_size)
            {
                potentials.add(key);
            }
        }
        myKeys.retainAll(potentials);
        return myKeys;
    }

    //alrighty then two more to go. What about this one??
    //loop through and the key with the smallest num of ones wins
    private void findMinCountGuess(Map<String, Set<String>> myGroups, Set<String> myKeys)
    {
        int count = 0, min_count = 0;
        byte first = 1;
        Set<String> potentials = new HashSet();
        for (String key : myKeys)
        {
            for (int i = 0; i < key.length(); i++)
            {
                if (key.charAt(i) == '1')
                {
                    count++;
                }
            }
            if (first == 1)
            {
                min_count = count;
                first = 0;
                potentials.add(key);
            }
            if (count < min_count)
            {
                min_count = count;
                potentials.clear();
                potentials.add(key);
            }
            else if(count == min_count)
            {
                potentials.add(key);
            }
            count = 0;
        }
        myKeys.retainAll(potentials);
    }

    //okay! Last one. let's see...
    private void findRightMostGroup(Map<String, Set<String>> myGroups, Set<String> myKeys, int pos[])
    {
        int rightmost = 0, max_rightmost = 0;
        Set<String> potentials = new HashSet();
        for (String key : myKeys)
        {
            for (int i = pos[0]; i < key.length(); i++)
            {
                if (key.charAt(i) == '1')
                {
                    rightmost = i;
                    break;
                }
            }
            if (rightmost > max_rightmost)
            {
                max_rightmost = rightmost;
                potentials.clear();
                potentials.add(key);
            }
            else if (rightmost == max_rightmost)
            {
                potentials.add(key);
            }
        }
        myKeys.retainAll(potentials);
        pos[0] = max_rightmost + 1;
    }


    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }
}
