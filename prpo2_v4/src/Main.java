import java.util.*;

public class Main {
    static List<Integer> card_keys = new ArrayList<>();
    static List<String> card_values= new ArrayList<>();

    public static void newDeck(){
        List<String> card_suit = new ArrayList<>();     //  масти
        card_suit.add("Clubs");card_suit.add("Diamonds");card_suit.add("Hearts");card_suit.add("Spades");
        List<String> cards = new ArrayList<>();   //  значения
        cards.add("2");cards.add("3");cards.add("4");cards.add("5");cards.add("6");
        cards.add("7");cards.add("8");cards.add("9");cards.add("10");
        cards.add("Jack");cards.add("Queen");cards.add("King");cards.add("Асе");

        card_keys.clear(); card_values.clear();

        int n = 1;
        for (int i = 0; i < card_suit.size(); i++) {
            for (int j = 0; j < cards.size(); j++) {
                String card = cards.get(j) + " of " + card_suit.get(i);
                card_keys.add(n);
                card_values.add(card);
                n++;
            }
        }
    }
    public static void randomSwap(int[]trick){
        Random random = new Random();
        int rnd_card_1 = random.nextInt(52)+1;
        int rnd_card_2 = rnd_card_1;
        while(rnd_card_2==rnd_card_1)
            rnd_card_1 = random.nextInt(52)+1;

        int swap = trick[rnd_card_2];
        trick[rnd_card_2] = trick[rnd_card_1];
        trick[rnd_card_1] = swap;
    }
    public static int[] trick(){
        int []arr = new int[52];
        for (int i = 0; i < arr.length; i++)
            arr[i]=i+1;
        randomSwap(arr);
        return arr;
    }

    public static void printDeck(){
        for (int i = 0; i < card_keys.size(); i++)
            System.out.println(card_keys.get(i)+" <--> "+card_values.get(i));
        System.out.println();
    }

    public static void swapCards(int i,int j){
        i--;j--;
        int card_key_i = card_keys.get(i), card_key_j = card_keys.get(j);
        String card_value_i = card_values.get(i), card_value_j = card_values.get(j);

        card_keys.set(i,card_key_j); card_keys.set(j,card_key_i);
        card_values.set(i,card_value_j); card_values.set(j,card_value_i);

        System.out.println("[" + card_value_i + " <-swap-> " + card_value_j + "]");
    }

    public static void main(String[] args) {
        newDeck();
        printDeck();

        Scanner in = new Scanner(System.in);
        System.out.print("Введите кол-во трюков диллера: ");
        int numberOfTricks = in.nextInt();
        int[][] tricks = new int[numberOfTricks][52];
        for (int i = 0; i < numberOfTricks; i++)
            tricks[i] = trick();

        for (int i = 0; i < numberOfTricks; i++) {
            for (int j = 0; j < 52; j++)
                System.out.print(tricks[i][j]+" ");
            System.out.println();
        }

        List<Integer>  buffer_keys = new ArrayList<>();
        List<String> buffer_values = new ArrayList<>();

        for (int i = 0; i < numberOfTricks; i++){
            for (int j = 0; j < 52; j++){
                buffer_keys.add(card_keys.get(tricks[i][j]-1));
                buffer_values.add(card_values.get(tricks[i][j]-1));
            }
            card_keys.clear();
            card_values.clear();
            for (int k = 0; k < 52; k++) {
                card_keys.add(buffer_keys.get(k));
                card_values.add(buffer_values.get(k));
            }
            buffer_keys.clear();
            buffer_values.clear();
        }
        printDeck();
    }
}