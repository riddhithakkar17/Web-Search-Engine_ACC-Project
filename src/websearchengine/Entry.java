package websearchengine;

import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;

public class Entry {

  private static Scanner sc = new Scanner(System.in);

  public static void main(String args[]) throws Exception {
    // initial delete
    deleteAllFiles("./text_pages");
    deleteAllFiles("./url_pages_crawled");

    System.out.println("Web Search Engine\n\n");

    System.out.println(
      "Enter the way you want to search: \n\nBy word(w) or By URL(u)? \n"
    );
    String searchChoice = sc.next();
    if (searchChoice.equals("u")) {
      System.out.println("Enter the URL you want to Search for: \n");
      String input_URL = sc.next();
      String formed_URL = "https://" + input_URL + "/";

      //Removing whitespaces
      input_URL = input_URL.trim();
    
      //Crawler and HTML to Text Converion
      Crawler craw = new Crawler();
      craw.getLinks(formed_URL);
    } 
    else {
      //HTML to Text Converion
      HTMLToText htmlToText = new HTMLToText();
      HTMLToText.Static_HTMLtoText();
    }
    while (true) {
      System.out.println("\n\nEnter 0 to exit or any number to continue: ");

      if (sc.nextInt() == 0) {
        System.out.println("Thank You\n");
        break;
      }

      System.out.println("Enter a word you want to Search: ");
      String searchWord = sc.next();


      //BoyerMoore Algorithm to search word
      SearchWordInFiles sw = new SearchWordInFiles(searchWord);
      Hashtable<String, Integer> File_List = sw.SearchWord_with_frequency(searchWord);
      if (File_List.isEmpty()) {
      //Edit Distance to get suggestions similar to search word
        System.out.println("No files found as per your serach, \n\nHere are some suggestions: ");
        String[] list = SpellCheck.getWordsuggestions(searchWord);
        if(list.length > 0) {
            if(list[0] != null) {
                for(String item: list){
                    System.out.println(item);
                }
            }else {
                System.out.println("No suggestions found!");
            }
        } else {
            System.out.println("No suggestions found!");
        }
      }
      //Sorting Algorithm
      else {
        System.out.println("Sorted List:");
        SortPages sortPages = new SortPages();
        sortPages.rankWebPages(File_List, File_List.size());

        //Deletion of all crawled and converted files
        deleteAllFiles("./text_pages");
        deleteAllFiles("./url_pages_crawled");

        System.out.println("Thank You");
        break;
      }
    }   
  }

  //Deletion of files in given path
  private static void deleteAllFiles(String filePath) {
    File files = new File(filePath);
    File[] ArrayofFiles = files.listFiles();

    for (int i = 0; i < ArrayofFiles.length; i++) {
      ArrayofFiles[i].delete();
    }
  }
}
