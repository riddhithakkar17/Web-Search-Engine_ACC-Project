package websearchengine;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLToText {

  public static void Static_HTMLtoText() throws Exception {
    File file = new File("url_pages_fixed");
    String[] List_of_file1 = file.list();
    for (int i = 0; i < List_of_file1.length; i++) {
    	HtmlToText_Converter(List_of_file1[i], "fixed pages"); //take html files from url_pages_fixed and convert them to text
    }
  }

  public static void Dynamic_HTMLtoText(ArrayList<String> list_of_link)
    throws Exception {
    System.out.println("-------------------Crawled Links------------------- \n");
    int c = 0;
    for (String str : list_of_link) {
      //System.out.println("Link1: "+s);
      c++;
      Document linkdoc = Jsoup.connect(str).get();

      String str_html = linkdoc.html();
      String html_Path = "url_pages_crawled";
      File html_Folder = new File(html_Path);
      String regex = "[a-zA-Z0-9]+";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(str);
      StringBuffer sb = new StringBuffer();
      while (m.find()) {
        sb.append(m.group(0));
      }
      String link_Adress = sb.substring(0);
      System.out.println("Link: " + link_Adress);

      PrintWriter pwout = new PrintWriter(html_Path + "\\" + link_Adress + ".html");
      pwout.println(str_html);
      pwout.close();

      if (c == 20) {
        break;
      }
    }
    File myfile = new File("url_pages_crawled");
    String[] List_of_file2 = myfile.list();
    for (int i = 0; i < List_of_file2.length; i++) {
    	HtmlToText_Converter(List_of_file2[i], "crawled pages"); //take html files from W3C Web Pages and convert them to text
    }
  }

  public static void HtmlToText_Converter(String file, String type)
    throws Exception {
    //System.out.println("File Name: "+file);
    String folder;
    if (type.equals("fixed pages")) {
      folder = "url_pages_fixed";
    } else {
      folder = "url_pages_crawled";
    }
    File f = new File(folder + "\\" + file);
    //Parse the file using JSoup
    Document document = Jsoup.parse(f, "UTF-8");
    //Convert the file to text
    String str = document.text();
    PrintWriter writer = new PrintWriter(
      "text_pages\\" + file.replaceAll(".html", ".txt")
    );
    writer.println(str);
    writer.close();
  }
}
