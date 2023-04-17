import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String args[])
    {
        BigDecimal a = new BigDecimal("0");
        BigDecimal b = new BigDecimal("0");
        BigDecimal c = new BigDecimal("0");
        Scanner scanner = new Scanner(System.in);
        for (int i = 0;;i++) {
            System.out.printf("\n___________Test %d___________\n",i);
            System.out.print("Введите кол-во точек n = ");
            a = scanner.nextBigDecimal();

            b = a.multiply(a.subtract(BigDecimal.valueOf(1)));
            b = b.multiply(a.subtract(BigDecimal.valueOf(2)));
            b =  b.multiply(a.subtract(BigDecimal.valueOf(3)));
            b = b.divide(BigDecimal.valueOf(24));
            c = a.multiply(a.subtract(BigDecimal.valueOf(1)));
            c = c.divide(BigDecimal.valueOf(2));
            b = b.add(c);
            b = b.add(BigDecimal.valueOf(1));

            System.out.printf("Кол-во участков земли s = %s\n", b.toString());
        }
    }
}
/*

F(1) = 1
F(2) = 1                        + F(1)
F(3) = 1 + 1                    + F(2)
F(4) = 1 + 2 + 1                + F(3)
F(5) = 1 + 3 + 3 + 1            + F(4)
F(6) = 1 + 4 + 5 + 4 + 1        + F(5)
F(7) = 1 + 5 + 7 + 7 + 5 + 1    + F(6)

 */