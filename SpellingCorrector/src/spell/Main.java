package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {

		String dictionaryFileName = args[0];
//		String inputWord = args[1];

		System.out.println("Hello World\n");
		SpellCorrector myCorrector = new SpellCorrector();

		myCorrector.useDictionary(dictionaryFileName);
		Trie mainTrie = myCorrector.getMyTrie();
		System.out.printf("word count: %d\n", mainTrie.getWordCount());
		System.out.printf("node count: %d\n", mainTrie.getNodeCount());

		Node check = mainTrie.find("yea");
		if (check != null) System.out.println("found");
		else System.out.println("not found");
		check = mainTrie.find("floating");
		if (check != null) System.out.println("found");
		else System.out.println("not found");

		int hash = mainTrie.hashCode();
		System.out.printf("my hash: %d", hash);
//		String suggestion = corrector.suggestSimilarWord(inputWord);
//		if (suggestion == null) {
//		    suggestion = "No similar word found";
//		}
//
//		System.out.println("Suggestion is: " + suggestion);
	}

}
