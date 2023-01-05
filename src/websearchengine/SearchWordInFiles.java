package websearchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class SearchWordInFiles {

  private final int R; //The Radix
  private static int[] right; //Bad-character skip array

  //Constructor to take input pattern for Search
  public SearchWordInFiles(String patternString) {
    this.R = 10000;

    //Position of rightmost occurrence of c in the pattern
    right = new int[R];
    for (int c = 0; c < R; c++) 
      right[c] = -1;
    for (int j = 0; j < patternString.length(); j++) 
      right[patternString.charAt(j)] = j;
  }

  public static Hashtable<String, Integer> SearchWord_with_frequency(String word)
    throws IOException {
    Hashtable<String, Integer> listOfFiles = new Hashtable<String, Integer>();
    File fileWithTextData = new File("./text_pages");
    File[] arrayOfTextFiles = fileWithTextData.listFiles();
    int totalFiles = 0;

    for (int i = 0; i < arrayOfTextFiles.length; i++) {
      String text = readFile(arrayOfTextFiles[i].getPath());

      int frequency = searchWord(text, word, arrayOfTextFiles[i].getName());
     
      if (frequency != 0) {
        listOfFiles.put(arrayOfTextFiles[i].getName(), frequency);
        totalFiles++;
      }
    }
    if (totalFiles > 0) {
      System.out.println("\n" + word + "\" found in total " + totalFiles + " files");
    } 
    else {
      System.out.println("\n" + word + "\" could not be found in any file");
    }
    return listOfFiles;
  }

  public static String readFile(String fileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    try {
      StringBuilder strb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        strb.append(line);
        strb.append("\n");
        line = br.readLine();
      }
      return strb.toString();
    } 
    finally {
      br.close();
    }
  }

  public static int search(String pattern, String text) {
    int patLength = pattern.length();
    int textLength = text.length();
    int skip;
    for (int i = 0; i <= textLength - patLength; i += skip) {
      skip = 0;
      for (int j = patLength - 1; j >= 0; j--) {
        if (pattern.charAt(j) != text.charAt(i + j)) {
          skip = Math.max(1, j - right[text.charAt(i + j)]);
          break;
        }
      }
      if (skip == 0) return i; //If word is found
    }
    return textLength; //If word is not found
  }

  public static int searchWord(String data, String word, String fileName) {
    int counter = 0;

    int offset = 0;
    SearchWordInFiles sw = new SearchWordInFiles(word);

    for (int location = 0; location <= data.length(); location += offset + word.length()) {
      offset = sw.search(word, data.substring(location));
      if ((offset + location) < data.length()) {
        counter++;
      }
    }
    return counter;
  }
  //Returns offset of first match; N if no match
}
