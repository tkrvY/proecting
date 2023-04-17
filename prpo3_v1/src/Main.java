import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main  {
    private static Map<String, Set<String>> graph;
    final static List<String> dictionary = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        dictionaryEntry("dictionary.txt");
        String[] words = new String[dictionary.size()];
        for (int i = 0; i < words.length; i++)
            words[i]=dictionary.get(i);

        graph = new HashMap<>();
        buildGraph(words);

        System.out.print("Введите количество пар слов: ");
        int N = scanner.nextInt();

        for (int i = 0; i < N; i++) {
            System.out.print("А"+(i+1)+": ");
            String start = scanner.next();
            System.out.print("В"+(i+1)+": ");
            String end = scanner.next();
            List<String> path = findShortestPath(start, end);
            if (path != null)
                for (String word : path)
                    System.out.print(word + " ");

            else
                System.out.println("No path found");
            System.out.println();
        }
    }
    private static void buildGraph(String[] words) {
        for (int i = 0; i < words.length; i++)
            for (int j = i + 1; j < words.length; j++)  // нет смысла обходить во внутреннем цикле все слова, матрица смежности
                if (isDoublet(words[i], words[j])) {
                    addEdge(words[i], words[j]);    // соединяем i c j
                    addEdge(words[j], words[i]);    // соединяем j c i
                }
    }// строим граф, вершинах которого - слова, явл дуплетом хотябы для еще одного слова; иначе слово нет смысла включать в граф
    private static void addEdge(String from, String to) {   //  Map<String, Set<String>> graph
        if (!graph.containsKey(from))   //  если в графе еще нет такой вершины
            graph.put(from, new HashSet<>());   //  добавляем новую вершину

        graph.get(from).add(to);        //  добавляет "to" в множество вершины "from"
    }
    private static boolean isDoublet(String word1, String word2) {
        if (word1.length() != word2.length())
            return false;

        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++)
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
                if (diffCount > 1)
                    return false;
            }
        return diffCount == 1;
    }   //  является ли пара слов дуплетом (true/false)
    private static List<String> findShortestPath(String start, String end) {
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start); //  начало автоматически посещено

        while (!queue.isEmpty()) {  //  пока не обойдем в queue все элементы
            String current = queue.remove();    //  извлекаем из всего списка в текущую веришину
            if (current.equals(end))            //  если текущая вершина = концу, то
                return buildPath(parentMap, start, end);    //  путь завершен

            for (String neighbor : graph.getOrDefault(current, new HashSet<>())) {
                /*                            /\
                                              ||
                    Возвращает значение, с которым сопоставлен указанный ключ, или значение по умолчанию,
                    если данная карта не содержит сопоставления для данного ключа.
                */

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return null;
    }
    private static List<String> buildPath(Map<String, String> parentMap, String start, String end) {
        List<String> path = new ArrayList<>();// хранение вершин в пути
        String current = end;
        while (current != null) {
            path.add(current);// добавляем текущую вершину в путь
            current = parentMap.get(current);   //  присваиваем текущую вершину своему родителю (из которой вышла текущая)
        }
        Collections.reverse(path);// т.к. мы шли из конца, то нужно перевернуть путь
        // если первый элемент списка path равен start, то возвращаем path, иначе , вернуть null (для проверки)
        return path.get(0).equals(start) ? path : null;
    }

    public static void dictionaryEntry(String path){
        try (FileReader fr= new FileReader(path)){
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine())
                dictionary.add(scan.nextLine());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        for (String s : dictionary)
            System.out.println(s);

    }  //  заполнение и печать словаря в консоль
}