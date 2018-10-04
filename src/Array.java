public class Array {
    private int arr[];
    private int size;          //текушее наполнение (указатель заполнения)
    private int indexValue;
    private boolean isSorted;  //флажок, отсортирован ли массив
    private boolean noValue;   //значение отсутствует
    private boolean exchangeCccurred; //был ли обмен

    private Array() {          //конструктор (1)
        isSorted = false;
        noValue = false;
        exchangeCccurred = false;
    }

    public Array(int size) {
        this();                  //вызываем конструктор без входяших данных (1)
        this.size = 0;
        this.arr = new int[size];
    }

    public Array(int... args) {  //второй массив, аргумент в котором какой то массив
        this();                  //вызываем конструктор без входяших данных (1)
        this.size = args.length; //длина нового массива равна длине входяшего массива
        this.arr = args;
    }

    public int length() { //метод возврашает длину массива
        return size;
    }

    public int get(int index) {  //геттер для того, чтобы по индексу забрать нужный нам элемент
        if (index >= size)       //если индекс больше чем длина массива(заполнение)
            throw new ArrayIndexOutOfBoundsException(index); //то вылетит ошибка
        return arr[index];       //в противном случае возврашаем элемент
    }

    public void set(int index, int value) { //метод для изменения элемента массива
        if (index >= size)                  //если индекс больше чем длина массива(заполнение)
            throw new ArrayIndexOutOfBoundsException(index);//то вылетит ошибка
        arr[index] = value;                 //изменяем в массиве элемент по индексу
        isSorted = false;
    }

    public void append(int value) {  //метод добавления нового элемента в массив
        if (size >= arr.length) {    //если заполнение больше длины первого массива,то
            int[] temp = arr;        //создаем временный массив
            arr = new int[size * 2]; //увеличиваем изначальный массив в двое(это выбираем сами)
            System.arraycopy(temp, 0, arr, 0, size); //копируем временный массив с позиции 0 в новый увеличенный массив с позиции 0
        }
        arr[size++] = value;         //если заполнение меньге длины первого массива
        isSorted = false;
    }

    /**
     * Deletes the last value in array
     */
    //метод удаления элемента из массива(в пямяти он остаеться, но мы его не видим)
    //Т.е мы расходуем память, но увеличиваем быстродействие (лучше жертвовать памятью, чем скоростью - совет препода)
    boolean delete() {
        if (size == 0) return false;
        size--;
        return true;
    }

    //удаление по интексу
    boolean delete(int index) {
        if (index < 0 || index >= size) {
            throw new RuntimeException("The array does not contain such an index!");
        } else {
            for (int i = 0; i < size; i++) {
                if (index == arr[i]) {
                    indexValue = i;
                    break;
                }
            }
            for (int j = indexValue; j < size - 1; j++) {
                arr[j] = arr[j + 1];
            }
            size--;
            indexValue = 0;
            return true;
        }
    }

    //удаление по значению (все значения)
    boolean deleteValue(int value) {
        int currentSize = size;
        do {
            for (int i = 0; i < size; i++) {
                if (value == arr[i]) {   //если значения совпадают
                    indexValue = i;      //запомнили индекс
                    noValue = true;      //оно сушествует
                    break;
                }
            }
            if (noValue) {               //если сушествует, то смещаем
                for (int j = indexValue; j < size - 1; j++) {
                    arr[j] = arr[j + 1];
                }
                size--;
                noValue = false;
            } else {                     //если не сушествует, то выходим из цикла while
                break;
            }
        } while (indexValue < size);

        indexValue = 0;
        if (currentSize > size) {          //проверяем произошло ли удаление
            return true;
        } else {
            return false;
        }

    }

    //полное удаление
    void deleteAll() {
        size = 0;
    }

    @Override
    public String toString() {        //Переопределили метод то стринг. Наш метод для вывода значений
        if (size == 0) return "[]";   //(это немного упрошенная реализация как в ARRAYS.TOSTRING)
        StringBuilder b = new StringBuilder("[");
        for (int i = 0; ; i++) {
            b.append(arr[i]);         //добавляем к StringBuilder b тек значение в массиве
            if (i == size - 1)        //как дошли до конца массива
                return b.append("]").toString();
            b.append(", ");
        }
    }

    /**
     * Search (поиск)
     */

    //Линейный поиск O(n) элемента неотсортированного массива(единственное что можно для неотсортированного сделать)
    public int find(int value) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == value)
                return i;
        }
        return -1;
    }

    //метод бинарным поиском.(двоичным поиском). сложность O(log n)
    public boolean hasValue(int value) {
        //из трех возможных вариантов (вернуть false, сортировать массив или кинуть Exception - 3 вариант правильны)
        if (!isSorted)
            throw new RuntimeException("Try the 'find' method");
        int l = 0; //левая граница поиска
        int r = size; //правая граница поиска
        int m; //середина диапозона поиска
        while (l < r) {
            // n >> k == n / 2 ^ k
            m = (l + r) >> 1; // 8 = 00001000 >> 1 = 00000100 = 4
            if (value == arr[m])
                return true;
            else if (value < arr[m])
                r = m;
            else
                l = m + 1;
        }
        return false;
    }

    /**
     * Sort (Три сортировки и все они имеют сложность n^2): пузырковая, методом выбора, методом ставки
     */

    //метод для перестановки значения местами. Нужен для сортировки
    private void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //пузырьковая сортировка. сложность O(n^2)
    public void sortBubble() {
        for (int i = size - 1; i > 1; i--) {       //идем с конца, чтобы не не сравнивать отсотированные значения
            for (int j = 0; j < size - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                    exchangeCccurred = true;       //фиксируем перестановку
                }
            }
            if (!exchangeCccurred) {                 //если ни одной перестоновки за проход не было, то выходим из цикла
                System.out.println("сортировать не надо");
                break;
            }
            exchangeCccurred = false;
        }
        isSorted = true;
    }

    //сотировка методом выбора
    public void sortSelect() {
        for (int flag = 0; flag < size; flag++) {         //цикл, который перемешает флажок по массиву
            int cMin = flag; //cMin - текущий минимум
            for (int rem = flag + 1; rem < size; rem++) { //цикл который будет бегать от флажка до конца массива
                if (arr[rem] < arr[cMin])                 //если нашли еще меньшее в остаточке от массива значение чем cMin
                    cMin = rem;
            }
            swap(flag, cMin);
        }
        isSorted = true;
    }

    //Метод сортировки методом вставки(самый эффективный из элементарных сортировок. но это не точно)
    public void sortInsert() {
        int in;
        for (int out = 0; out < size; out++) {
            int temp = arr[out];                    //кладем значение массиво во временное значение
            in = out;
            while (in > 0 && arr[in - 1] >= temp) { //цикл смещения всех элементов на позицию влево
                arr[in] = arr[in - 1];
                in--;
            }
            arr[in] = temp;                         //записываем значение в конец массива
        }
        isSorted = true;
    }
}
