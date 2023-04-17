import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Main {
    static class Turtle implements Comparable<Turtle> {
        int w, s;
        public Turtle(int w, int s) {
            this.w = w;
            this.s = s;
        }
        public int compareTo(Turtle other) {
            return Integer.compare(s, other.s);
        }
    }
    static List<Turtle> T = new ArrayList<>();
    static int[] dp = new int[5607];
    public static void solve() {
        Collections.sort(T);

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        int maxNTurtles = 0;

        for (Turtle turtle : T)
            for (int j = maxNTurtles; j >= 0; --j)
                if (turtle.s >= dp[j] + turtle.w && turtle.w + dp[j] < dp[j + 1]) {
                    dp[j + 1] = dp[j] + turtle.w;
                    maxNTurtles = Math.max(maxNTurtles, j + 1);
                }

        System.out.println("Максимальное число черепах в стопке:\n"+maxNTurtles);
    }
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

    public static void main(String[] args) {
        String file = "data.txt";
        dataUploading(file);
        System.out.println("Входные данные загружены из файла: " + file);
        System.out.println(" Вес |  Сила");
        for (String datum : Data){
            int[] numArr = Arrays.stream(datum.split(" ")).mapToInt(Integer::parseInt).toArray();
            int w = numArr[0];
            int s = numArr[1];
            System.out.printf("%4d | %4d\n",w,s);
            T.add(new Turtle(w, s));
        }

        solve();
    }
}