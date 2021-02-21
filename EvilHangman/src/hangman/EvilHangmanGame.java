package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {
    private Set<String> availableWords = new HashSet<>();
    private SortedSet<Character> guessedLetters = new TreeSet<Character>();

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        availableWords.clear();
        guessedLetters.clear();

        if (dictionary.length() == 0)
        {
            throw (new EmptyDictionaryException());
        }
        else if (wordLength < 2)
        {
            throw (new EmptyDictionaryException());
        }

        Scanner scanner = new Scanner(dictionary);
        try
        {
            if (scanner == null)
            {
                throw (new IOException());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        while (scanner.hasNext())
        {
            String word = scanner.next();
            word = word.toLowerCase();
            if (word.length() == wordLength)
            {
                availableWords.add(word);
            }
        }

        if (availableWords.size() == 0)
        {
            throw(new EmptyDictionaryException());
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        Map<String, Set<String>> myGroups = new HashMap();
        Set<String> myKeys = new HashSet();
        //check guessed letter
        guess = Character.toLowerCase(guess);
        if (guessedLetters.contains(guess))
        {
            throw (new GuessAlreadyMadeException());
        }
        //sort into groups
        groupSort(myGroups, guess, myKeys);
        //grab the largest group
        sizeSort(myGroups, myKeys);
        //grab the group with fewest guessed letters
        numGuessedLettersSort(myGroups, myKeys);
        //grab the group with farthest right guessed letter
        int pos[] = { 0 };
        while (myKeys.size() > 1)
        {
            guessedLetterPosSort(myGroups, myKeys, pos);
        }

        guessedLetters.add(guess);

        Iterator it = myKeys.iterator();
        availableWords.retainAll(myGroups.get(it.next()));
        return availableWords;
    }

    private void groupSort(Map<String, Set<String>> myGroups, char guess, Set<String> myKeys)
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
            if (myGroups.get(key) == null)
            {
                Set<String> tmp = new HashSet();
                tmp.add(word);
                myGroups.put(key, tmp);
                myKeys.add(key);
            }
            else
            {
                myGroups.get(key).add(word);
            }
            key = "";
        }
    }

    private void sizeSort(Map<String, Set<String>> myGroups, Set<String> myKeys)
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
    }

    private void numGuessedLettersSort(Map<String, Set<String>> myGroups, Set<String> myKeys)
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
            if (first == 1 || count < min_count)
            {
                min_count = count;
                potentials.clear();
                potentials.add(key);
                first = 0;
            }
            else if (count == min_count)
            {
                potentials.add(key);
            }
            count = 0;
        }
        myKeys.retainAll(potentials);
    }

    private void guessedLetterPosSort(Map<String, Set<String>> myGroups, Set<String> myKeys, int pos[])
    {
        int position = 0, max_pos = 0;
        Set<String> potentials = new HashSet<>();
        for (String key : myKeys)
        {
            for (int i = pos[0]; i < key.length(); i++)
            {
                if (key.charAt(i) == '1')
                {
                    position = i;
                    break;
                }
            }
            if (position > max_pos)
            {
                max_pos = position;
                potentials.clear();
                potentials.add(key);
            }
            if (position == max_pos)
            {
                potentials.add(key);
            }
            position = 0;
        }
        myKeys.retainAll(potentials);
        pos[0] = max_pos + 1;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public Set<String> getMyWords()
    {
        return availableWords;
    }

}
