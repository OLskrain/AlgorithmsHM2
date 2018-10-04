/**
 * Home work 1.
 * @author Ievlev Andrey.
 * @version Oct 5, 2018.
 * @Link https://github.com/OLskrain/AlgorithmsHM2.git
 */

public class Main {
    public static void main(String[] args) {
        //создали массив
        Array arr = new Array(5);
        Array arr2 = new Array(10);
        System.out.println(arr);
        System.out.println("_________________________________");

        //заполнили массив
        for (int i = 0; i < 10; i++) {
            arr.append(i);
        }
        System.out.println(arr);
        System.out.println("_________________________________");

        arr2.append(2);
        arr2.append(2);
        arr2.append(1);
        arr2.append(4);
        arr2.append(3);
        arr2.append(8);
        arr2.append(6);
        arr2.append(7);
        arr2.append(2);
        arr2.append(9);

        System.out.println(arr2);
        arr2.sortBubble();
        System.out.println(arr2);
        System.out.println("_________________________________");

        //удалили одно последнее значение
        arr.delete();
        System.out.println("Удаление последнего значения:");
        System.out.println(arr);
        System.out.println("_________________________________");

        //удаление по индексу
        arr.delete(3);
        System.out.println("Удаление по индексу:");
        System.out.println(arr);
        System.out.println("_________________________________");

        //удаление по значению
        arr2.deleteValue(2);
        System.out.println("Удаление по значению:");
        System.out.println(arr2);
        System.out.println("_________________________________");

        //удаление всех элементов
        arr.deleteAll();
        System.out.println("Полное удаление:");
        System.out.println(arr);
    }
}
