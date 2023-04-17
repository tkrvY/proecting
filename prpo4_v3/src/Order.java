class Order implements Comparable<Order> {
    int time;
    int penalty;
    int index;
    public Order(int time, int penalty, int index) {
        this.time = time;
        this.penalty = penalty;
        this.index = index;
    }
    public int compareTo(Order other) {

        int profit1 = this.penalty/this.time;

        int profit2 = other.penalty/other.time;

        if (profit1 != profit2)
            return Integer.compare(profit2, profit1);
        else
            return Integer.compare(this.index, other.index);
    }
}


/*
    Сортировка по убыванию прибыли за день происходит в методе compareTo, который реализует интерфейс Comparable.

    1.  Вычисляется прибыль за день для текущего заказа (this) и для другого заказа (other).
            Прибыль за день вычисляется как разница между штрафом и временем выполнения заказа.
    2.  Происходит сравнение прибыли за день для текущего и другого заказов.
            Сравнение:
            Если они не равны, то возвращается результат сравнения, который определяет порядок сортировки (
            - значение > 0 - текущий заказ должен быть после другого,
            - значение < 0 - текущий заказ должен быть перед другим,
            - значение = 0 - порядок не имеет значения).
    3.  Если прибыль за день для текущего и другого заказов равны, то происходит сравнение по индексу заказа.
            Это нужно для того, чтобы при одинаковой прибыли за день заказы сортировались по возрастанию индекса
                (то есть в порядке, в котором они были заданы во входных данных).
*/