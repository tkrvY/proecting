import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Shoemaker {
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
    }   //  загрузка данных

    public static void main(String[] args) {
        String file = "Orders.txt";
        dataUploading(file);
        System.out.println("Входные данные загружены из файла: " + file);
        System.out.print("Кол-во тестов: ");
        int numTests = Integer.parseInt(Data.get(0));                   System.out.println(numTests);
        int numStr = 1;

        for (int t = 0; t < numTests; t++) {
            System.out.printf ("________________Тест №%d________________\n",t+1);
            System.out.print("Кол-во заказов: ");
            int numOrders = Integer.parseInt(Data.get(numStr));         System.out.println(numOrders);
            numStr++;

            System.out.println("ДНИ   |   ШТРАФ");
            Order[] orders = new Order[numOrders];
            for (int j = 0; j < numOrders; j++) {
                String[] time_penalty;
                time_penalty = Data.get(numStr).split(" ", 2);
                numStr++;

                int time = Integer.parseInt(time_penalty[0]);           System.out.printf("%2s",time);
                int penalty = Integer.parseInt(time_penalty[1]);        System.out.printf("    |   %3s\n",penalty);
                orders[j] = new Order(time, penalty, j);
            }
            numStr++;

            // сортируем заказы по убыванию прибыли за день
            Arrays.sort(orders);

            // определяем порядок выполнения заказов
            int[] result = new int[numOrders];
            int index = 0;
            for (Order order : orders) {
                result[index] = order.index;
                index++;
            }

            // выводим порядок выполнения заказов
            System.out.println();
            System.out.println("Последовательность выполнения заказов,\nвидущая к минимальному штрафу:");
            for (int j = 0; j < numOrders; j++)
                System.out.print(" " + (result[j]+1));
            System.out.println();
            System.out.println("_______________________________________");
        }
    }
}