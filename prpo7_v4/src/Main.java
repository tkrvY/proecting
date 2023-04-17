import java.util.*;

class Main
{
    public static void main (String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();                   //  кол во текстовых блоков
        while (T-- > 0) {
            int N = in.nextInt();               //  кол во человек
            int[] W = new int[N + 1];           //  массив с весами
            for (int i = 1; i <= N; ++i)
                W[i] = in.nextInt();            //  вес человека

            int sum = Arrays.stream(W).sum();   //  сумма весов

            long[] dp = new long[450 * 100 + 5];
            Arrays.fill(dp, 0);
            dp[0] = 1;

            for (int i = 1; i <= N; ++i)
                for (int j = sum; j >= 0; --j)
                    if (dp[j] != 0)
                        dp[j + W[i]] |= dp[j] << 1;

            int minDiff = 450 * 100;
            int teamOneWeight = 0, teamTwoWeight = 0;
            for (int i = 0; i <= sum; ++i)
                if ((dp[i] & (1L << (N / 2))) != 0) {
                    int diff = Math.abs(i - (sum - i));
                    if (diff < minDiff) {
                        minDiff = diff;
                        teamOneWeight = Math.min(i, sum - i);
                        teamTwoWeight = Math.max(i, sum - i);
                    }
                }
            System.out.println(teamOneWeight + " " + teamTwoWeight);
            if (T > 0)
                System.out.println();
        }
    }
}