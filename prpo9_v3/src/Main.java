import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    final static List<String> Data = new ArrayList<>();
    public static void dataUploading(String path){
        try (FileReader fr= new FileReader(path)){
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine())
                Data.add(scan.nextLine());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void printHashMap(HashMap<String, Integer> hashMap) {
        for (String key : hashMap.keySet())
            System.out.println(key + ": " + hashMap.get(key));
    }

    public static void main(String[] args) {
        HashMap<String, Integer> L = new HashMap<>();
        int maxLength = 0;

        String file = "data.txt";
        dataUploading(file);
        System.out.println("Входные данные загружены из файла: " + file);

        for (String word : Data){
            int length = 1;

            // Insert c before word[i].
            for (int i = 0; i <= word.length(); ++i)
                for (char c = 'a'; c <= 'z'; ++c) {
                    StringBuilder wordNew = new StringBuilder(word);
                    wordNew.insert(i, c);
                    if (wordNew.toString().compareTo(word) > 0)
                        break;
                    if (L.containsKey(wordNew.toString()))
                        length = Math.max(length, L.get(wordNew.toString()) + 1);
                }

            // Delete word[i].
            for (int i = 0; i < word.length(); ++i) {
                StringBuilder wordNew = new StringBuilder(word);
                wordNew.deleteCharAt(i);
                if (L.containsKey(wordNew.toString()))
                    length = Math.max(length, L.get(wordNew.toString()) + 1);
            }

            // Change word[i].
            for (int i = 0; i < word.length(); ++i)
                for (char c = 'a'; c <= 'z' && c != word.charAt(i); ++c) {
                    StringBuilder wordNew = new StringBuilder(word);
                    wordNew.setCharAt(i, c);
                    if (wordNew.toString().compareTo(word) > 0)
                        break;
                    if (L.containsKey(wordNew.toString()))
                        length = Math.max(length, L.get(wordNew.toString()) + 1);
                }

            L.put(word, length);

            maxLength = Math.max(maxLength, length);
        }

        System.out.println("Количество слов в самой большой \n" +
                "лесенке ступенек редактирования: "+maxLength+"\n");
        printHashMap(L);
    }
}