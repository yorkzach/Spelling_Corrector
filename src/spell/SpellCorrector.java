package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpellCorrector implements ISpellCorrector {
  ITrie dictionary = new Trie();
  @Override
  public void useDictionary(String dictionaryFileName) throws IOException {
      File file = new File(dictionaryFileName);
      Scanner scanner = new Scanner(file);

      while(scanner.hasNext()){
        String str =scanner.next();
        dictionary.add(str);
      }
  }
  public ArrayList candidateHelper(String inputWord){
    inputWord = inputWord.toLowerCase();
    ArrayList<String> candidates = new ArrayList<String>();
    //Deletion Candidates
    for (int i = 0; i < inputWord.length(); i++) {
      StringBuilder candidate = new StringBuilder(inputWord);
      candidate.deleteCharAt(i);
      candidates.add(candidate.toString());
    }
    //Transposition Candidates
    for (int i = 0; i < inputWord.length() - 1; i++) {
      char[] candidate = inputWord.toCharArray();
      char temp = candidate[i];
      candidate[i] = candidate[i + 1];
      candidate[i + 1] = temp;
      candidates.add(new String(candidate));
    }
    // Alteration Distance
    for (int i = 0; i < inputWord.length(); i++) {
      for (char ch = 'a'; ch <= 'z'; ch++) {
        if (ch != inputWord.charAt(i)) {
          StringBuilder candidate = new StringBuilder(inputWord);
          candidate.setCharAt(i, ch);
          candidates.add(candidate.toString());
        }
      }
    }
    // Insertion Distance
    for (int i = 0; i <= inputWord.length(); i++) {
      for (char ch = 'a'; ch <= 'z'; ch++) {
        StringBuilder candidate = new StringBuilder(inputWord);
        candidate.insert(i, ch);
        candidates.add(candidate.toString());
      }
    }
    return candidates;
  }
  @Override
  public String suggestSimilarWord(String inputWord) {
    inputWord= inputWord.toLowerCase();
    ArrayList<String> suggestion = new ArrayList<String>();
    if(dictionary.find(inputWord)!=null){
      return inputWord;
    }
    ArrayList<String> candidateList = candidateHelper(inputWord);
    ArrayList<String> newCandidateList= new ArrayList<String>();

    for (int i=0; i<candidateList.size();i++){
      String word = candidateList.get(i);
      if(dictionary.find(word)!= null){
        suggestion.add(word);
      }
    }
    if (suggestion.size()==0){
      for (int i=0; i<candidateList.size();i++) {
        String word = candidateList.get(i);
        newCandidateList.addAll(candidateHelper(word));
      }
      for (int i=0; i<newCandidateList.size();i++){
        String word = newCandidateList.get(i);
        if(dictionary.find(word)!= null){
          suggestion.add(word);
        }
      }
    }
    if(suggestion.size()==0){
      return null;
    }

    if(suggestion.size()==1){
      return suggestion.get(0);
    }
    else{
      int lastNodeFreq= 0;
      String mostfreqWord= null;
      for(int i = 0; i< suggestion.size();i++){
        if(dictionary.find(suggestion.get(i)).getValue()>lastNodeFreq){
          lastNodeFreq= dictionary.find(suggestion.get(i)).getValue();
          mostfreqWord= suggestion.get(i);
        }
      }
      return mostfreqWord;
    }
    //check every candidate to see if in the trie
    //if there are multiple run against the rules in the spec
    //create second list, add every candidate from edit 1 to second list

    /**
     * create candidate list by mutating input word using candidateHelper
     * recursively check Trie for each candidate
     * if they are in the Trie create suggestion list
     * return word from suggestion list that has the highest frequency count, if tie alphabetically
     * no match run it again on candidate list to check second layer
     * check trie again and return using the same rules
     *
     * if no match return null
     */
  }
}