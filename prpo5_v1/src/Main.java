import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
    public static boolean doAssign(int Index, List<Integer> Data, List<Boolean> Taken, List<Integer> Results) {
        if (Index == Data.size()) return true;
        int pivotLoc = ((Results.size() - 1) * Results.size())/2;
        for (int i=2; i<Data.size(); i++) {
            if (Index == 2) {
                double Avd = (Data.get(0) + Data.get(1) - Data.get(i))/2.0;
                if (Avd - (int)Avd > 0.00000001) continue;
                Results.add((int)Avd);
                Results.add(Data.get(0) - (int)Avd);
                Results.add(Data.get(1) - (int)Avd);
                Taken.set(i, true);
            } else if (Index == pivotLoc) {
                if (Taken.get(i)) continue;
                Results.add(Data.get(i) - Results.get(0));
                Taken.set(i, true);
            } else {
                pivotLoc = ((Results.size() - 2) * (Results.size()-1))/2;
                if (Taken.get(i)) continue;
                if (Data.get(i) - Results.get(Results.size()-1) != Results.get(Index%pivotLoc)) continue;
                Taken.set(i, true);
            }

            if (doAssign(Index+1, Data, Taken, Results)) return true;

            Taken.set(i, false);
            if (Index == 2) Results.clear();
            if (Index == pivotLoc) Results.remove(Results.size()-1);
        }

        return false;
    }

    public static void main(String[] args) {
        String file = "data.txt";
        dataUploading(file);
        System.out.println("Входные данные загружены из файла: " + file);
        System.out.println("__________________________________________________________________________________");

        for (String datum : Data) {
            String[] strArray;
            strArray = datum.split(" ");
            int[] intArray = new int[strArray.length];
            for (int j = 0; j < intArray.length; j++)
                intArray[j] = Integer.parseInt(strArray[j]);

            int n = intArray[0];
            System.out.print("nums number: " + n + " | ");

            List<Integer> sums = new ArrayList<>();
            System.out.print("sums: ");
            for (int j = 1; j < intArray.length; j++) {
                sums.add(intArray[j]);
                System.out.print(sums.get(j - 1) + " ");
            }
            System.out.println();

            int limit = (n*(n-1)) / 2;

            Collections.sort(sums);

            List<Integer> Results = new ArrayList<>();
            List<Boolean> taken = new ArrayList<>();
            for(int i=0; i<limit; i++) taken.add(false);
            doAssign(2, sums, taken, Results);

            Collections.sort(Results);
            System.out.print("RESULT: ");
            if (Results.size() == 0) System.out.print("Impossible");
            else
                for (Integer result : Results)
                    System.out.print(result + " ");

            System.out.println("\n__________________________________________________________________________________");
        }
    }
}