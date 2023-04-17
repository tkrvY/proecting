import java.util.*;
import java.io.*;

public class Main {
    static int[][] a = new int[55][55];
    static int[] du = new int[55];
    static List<Edge> my = new ArrayList<>();
    static void jie(int u) {
        Edge t;
        for (int v = 1; v <= 50; v++) {
            if (a[u][v] > 0) {
                a[u][v]--;
                a[v][u]--;
                t = new Edge(u, v);
                my.add(t);
                jie(v);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        dataUploading("data.txt");

        int t = Integer.parseInt(Data.get(0));
        int curIndex = 1;

        int n, k = 1, u=1, v;
        while (t-- > 0) {

            n = Integer.parseInt(Data.get(curIndex));

            for (int i = 1; i <= 50; i++) {
                Arrays.fill(a[i], 0);
                du[i] = 0;
            }
            for (int i = 1; i <= n; i++) {
                curIndex++;
                String[] input = Data.get(curIndex).split(" ");
                u = Integer.parseInt(input[0]);
                v = Integer.parseInt(input[1]);
                a[u][v]++;
                a[v][u]++;
                du[u]++;
                du[v]++;
            }

            boolean flag = true;

            for (int i = 1; i <= 50; i++)
                if (du[i] % 2 != 0) {
                    flag = false;
                    break;
                }

            if (flag) {
                my.clear();
                jie(u);

                // является ли граф связным и первый граф связным.
                if (my.size() != n || my.get(0).x != my.get(my.size() - 1).y) flag = false;

            }
            curIndex++;

            System.out.println("Test #" + k);
            k++;
            if (!flag)
                System.out.println("impossible");
            else
                for (Edge edge : my) System.out.println(edge.x + " " + edge.y);

            System.out.println();
        }
    }
    final static List<String> Data = new ArrayList<>();
    public static void dataUploading(String path){
        try (FileReader fr= new FileReader(path)){
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine())
                Data.add(scan.nextLine());
            System.out.println("Входные данные загружены из файла: " + path);
            System.out.println();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}