import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    final static Scanner in = new Scanner(System.in);
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static int[] three_N_plus_one(int n){
        List<Integer> list = new ArrayList<>(); //  сюда пишем числа из текущего цикла
        list.add(n);                            //  добавляем первое число
        while (n!=1){                           //  пока не свелось к 1 повтор цикла
            if (n%2 == 0)                       //      если четное то
                n/=2;                           //      делим на 2
            else n=3*n+1;                       //      иначе умножаем на 3 и прибавляем 1
            list.add(n);                        //      добавляем в лист получившееся число
        }
        int[] array = list.stream().
                mapToInt(i->i).toArray();       //  преобразование листа в массив
        list.clear();
        return array;                           //  возвращает массив (от n до 1)
    }

    public static void IJ_generation_max(){
        int
                maxLength = 0,
                i = 0,
                j = 0;
        boolean fi = false,
                fj = false;

        // пока не будет введено значение в пределах (0; 1 000 000)
        //         будет повторятся ввод
        while (!fi){
            System.out.print("Enter i: "); i = in.nextInt();
            if (i>0&&i<1000000)
                fi = true;
            else
                System.out.println(ANSI_RED+"retry (0 < i < 1 000 000)"+ANSI_RESET);
        }
        while (!fj){
            System.out.print("      j: "); j = in.nextInt();
            if (j>0&&j<1000000)
                fj = true;
            else
                System.out.println(ANSI_RED+"retry (0 < j < 1 000 000)"+ANSI_RESET);
        }


        for (int n = i; n < j; n++)                     //  перебираем все числа от i до j
            if (three_N_plus_one(n).length > maxLength) //      если длинна цикла для текущего числа больше максимального
                maxLength = three_N_plus_one(n).length; //          то длина текущего цикла равна максимальной

        System.out.println("Max len: " + maxLength);
        System.out.println("[" +i + " " + j + " " + maxLength+ "]");
        System.out.println();
    }
    public static void main(String[] args) {
        for(;;) IJ_generation_max();
    }
}