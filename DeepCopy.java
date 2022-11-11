import java.util.ArrayList;
import java.util.LinkedList;

public class DeepCopy {
    // Metoda służąca do głębokiego kopiowania (deep copy) obiektów ArrayList.
    // W języku Java domyślnie dokonywana jest kopia jedynie referencji (shallow copy),
    // przez co zmiana wartości w jednej powoduje zmianę wartości w drugiej.
    public static ArrayList<ArrayList<Integer>> copyArray(ArrayList<ArrayList<Integer>> array) {
        ArrayList<ArrayList<Integer>> newArray = new ArrayList<>();
        // Przeniesienie pojedynczo wartości z jednego obiektu do drugiego.
        for (int i = 0; i < array.size(); i++) {
            ArrayList<Integer> tempArray = new ArrayList<>();
            for (int j = 0; j < array.get(i).size(); j++) {
                tempArray.add(array.get(i).get(j));
            }
            newArray.add(tempArray);
        }
        return newArray;
    }

    public static LinkedList<Integer> copyList(LinkedList<Integer> list) {
        LinkedList<Integer> newList = new LinkedList<>();
        // Przeniesienie pojedynczo wartości z jednego obiektu do drugiego.
        for (int i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static ArrayList<Integer> copy1DArray(ArrayList<Integer> array) {
        ArrayList<Integer> newArray = new ArrayList<>();
        // Przeniesienie pojedynczo wartości z jednego obiektu do drugiego.
        for (int i = 0; i < array.size(); i++) {
            newArray.add(array.get(i));
        }
        return newArray;
    }
}
