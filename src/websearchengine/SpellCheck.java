package websearchengine;

import java.io.*;
import java.util.*;

public class SpellCheck {

    private static ArrayList<String> words = new ArrayList<>();

    private static void getVocab() throws IOException {
        String currentDir = System.getProperty("user.dir");
        File textFiles = new File(currentDir+ "/text_pages");

        File[] texts = textFiles.listFiles();

        StringBuilder line = new StringBuilder();
        assert texts != null;
        for (File text : texts) {
            BufferedReader br = new BufferedReader(new FileReader(text));
            String str;
            while ((str = br.readLine()) != null) {
                line.append(str);
            }
            br.close();
        }
        String fullText = line.toString();
        StringTokenizer tokenizer = new StringTokenizer(fullText, "0123456789 ,`*$|~(){}_@><=+[]\\?;/&#-.!:\"'\n\t\r");
        while (tokenizer.hasMoreTokens()) {
            String tk = tokenizer.nextToken().toLowerCase(Locale.ROOT);
            if (!words.contains(tk)) {
                words.add(tk);
            }
        }
    }

    public static String[] getWordsuggestions(String searchWord) throws IOException {
        getVocab();
        HashMap<String, Integer> hashMap = new HashMap<>();
        String[] newWords = new String[10];
        for (String w : words) {
            int editDistance = wordEditDistance(searchWord, w);
            hashMap.put(w, editDistance);
        }
        Map<String, Integer> map = sortByValue(hashMap);

        int rank = 0;
        for (Map.Entry<String, Integer> en : map.entrySet()) {
            if (en.getValue() != 0) {
                newWords[rank] = en.getKey();
                rank++;
                if (rank == 10){ break; }
            }
        }
        return newWords;
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
        List<Map.Entry<String, Integer> > list = new LinkedList<>(map.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static int wordEditDistance(String w1, String w2) {
        int w1Length = w1.length();
        int w2Lenghth = w2.length();
        int[][] distance_Array = new int[w1Length + 1][w2Lenghth + 1];

        for (int i = 0; i <= w1Length; i++) {
        	distance_Array[i][0] = i;
        }
        for (int j = 0; j <= w2Lenghth; j++) {
        	distance_Array[0][j] = j;
        }

        for (int i = 0; i < w1Length; i++) {
            char char1 = w1.charAt(i);
            for (int j = 0; j < w2Lenghth; j++) {
                char char2 = w2.charAt(j);

                if (char1 == char2) {
                	distance_Array[i + 1][j + 1] = distance_Array[i][j];
                } else {
                    int value1 = distance_Array[i][j] + 1;
                    int value2 = distance_Array[i][j + 1] + 1;
                    int value3 = distance_Array[i + 1][j] + 1;

                    int min = Math.min(value1, value2);
                    min = Math.min(value3, min);
                    distance_Array[i + 1][j + 1] = min;
                }
            }
        }
        return distance_Array[w1Length][w2Lenghth];
    }

    public static void main(String[] args) throws IOException{
        String[] list = getWordsuggestions("a");
        System.out.println(list.length+""+ list[0]);
        if(list.length > 0) {
            if(list[0] != null) {
                for(String item: list){
                    System.out.println(item);
                }
            }else {
                System.out.println("No word suggestions found!");
            }
        } else {
            System.out.println("No word suggestions found!");
        }
    }
}
