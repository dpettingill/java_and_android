package hangman;

public class EmptyDictionaryException extends Exception {
    public EmptyDictionaryException() {}
	//Thrown when dictionary file is empty or no words in dictionary match the length asked for
}
