package spell;

import java.util.Locale;
import java.util.Stack;
import java.lang.String;
import java.lang.Object;

public class Trie implements ITrie{
    public Trie() {}
    private Node head = new Node();
    private int wordCount = 0;
    private int nodeCount = 1;

    /**
     * Adds the specified word to the trie (if necessary) and increments the word's frequency count.
     *
     * @param word the word being added to the trie
     */
    public void add(String word)
    {
        word = word.toLowerCase();
        Node tmp = head;
        int letter = 0;
        for (int i = 0; i < word.length(); i++)
        {
            letter = word.charAt(i) - 'a';
            if (tmp.getChildren()[letter] == null)
            {
                tmp.getChildren()[letter] = new Node();
                nodeCount++;
            }
            tmp = tmp.getChildren()[letter];
        }
        if (tmp.getValue() == 0)
        {
            wordCount++;
        }
        tmp.incrementValue(); //then inc the value on the last letter of the word.
    }

    /**
     * Searches the trie for the specified word.
     *
     * @param word the word being searched for.
     *
     * @return a reference to the trie node that represents the word,
     * 			or null if the word is not in the trie
     */
    public Node find(String word)
    {
        Node tmp = head;
        word = word.toLowerCase();
        int letter = 0;
        for (int i = 0; i < word.length(); i++)
        {
            letter = word.charAt(i) - 'a';
            if (tmp.getChildren()[letter] == null)
            {
                return null;
            }
            tmp = tmp.getChildren()[letter];
        }
        //tmp is pointing at final letter
        if (tmp.getValue() != 0)
        {
            return tmp;
        }
        return null;
    }

    /**
     * Returns the number of unique words in the trie.
     *
     * @return the number of unique words in the trie
     */
    public int getWordCount()
    {
        return wordCount;
    }

    /**
     * Returns the number of nodes in the trie.
     *
     * @return the number of nodes in the trie
     */
    public int getNodeCount()
    {
        return nodeCount;
    }

    /**
     * The toString specification is as follows:
     * For each word, in alphabetical order:
     * <word>\n
     * MUST BE RECURSIVE.
     */
    @Override
    public String toString()
    {
        StringBuilder sb1 = new StringBuilder(); //builds word as we traverse
        StringBuilder sb2 = new StringBuilder(); //collects all words as we traverse
        toStringTraverse(sb1, sb2, head);
        return sb2.toString();
    }

    //recursive function used to traverse for the toString method
    public void toStringTraverse(StringBuilder sb1, StringBuilder sb2, Node tmp)
    {
            int pos = 0;
            char letter = 0;
            for (Node child : tmp.getChildren())
            {
                if (child != null)
                {
                    letter = (char)(pos + 'a');
                    sb1.append(letter);
                    if (child.getValue() > 0)
                    {
                        sb2.append(sb1);
                        sb2.append('\n');
                    }
                    toStringTraverse(sb1, sb2, tmp.getChildren()[pos]);
                    sb1.deleteCharAt(sb1.length() - 1);
                }
                pos++;
        }
    }


    /**
     * Returns the hashcode of this trie.
     * MUST be constant time.
     * @return a uniform, deterministic identifier for this trie.
     */
    @Override
    public int hashCode()
    {
        Node tmp[] = head.getChildren();
        int i = 0;
        int hash = wordCount + nodeCount;
        while (i < tmp.length)
        {
            if (tmp[i] != null)
            {
                hash += i + 1 + tmp[i].getValue();
            }
            i++;
        }
        return hash;
    }

    /**
     * Checks if an object is equal to this trie.
     * MUST be recursive.
     * @param o Object to be compared against this trie
     * @return true if o is a Trie with same structure and node count for each node
     * 		   false otherwise
     */
    @Override
    public boolean equals(Object o)
    {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Trie)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Trie c = (Trie) o;
        return equalsTraverse(this.head.getChildren(), c.head.getChildren());
    }

    //recursive method used to traverse for the equals method
    public boolean equalsTraverse(Node children1[], Node children2[])
    {
        int i = 0;
        boolean equal = true;
        for (Node child1 : children1)
        {
            if (child1 != null && children2[i] != null)
            {
                if (child1.getValue() != children2[i].getValue())
                {
                    return false;
                }
                if (!equalsTraverse(child1.getChildren(), children2[i].getChildren()))
                {
                    return false;
                }
            }
            if ((child1 == null && children2[i] != null) || (child1 != null && children2[i] == null))
            {
                return false;
            }
            i++;
        }
        return true;
    }

}

