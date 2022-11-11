import java.util.ArrayList;
import java.util.LinkedList;

public class DeepCopy {
    // A method used to deep copy ArrayList objects.
    // In Java, the default is to copy only references (shallow copy),
    // i.e. a change in a value in one causes a change in the value of the other.
    public static ArrayList<ArrayList<Integer>> copyArray(ArrayList<ArrayList<Integer>> array) {
        ArrayList<ArrayList<Integer>> newArray = new ArrayList<>();
        // Transfer values one at a time from one object to another.
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
        // Transfer values one at a time from one object to another.
        for (int i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    public static ArrayList<Integer> copy1DArray(ArrayList<Integer> array) {
        ArrayList<Integer> newArray = new ArrayList<>();
        // Transfer values one at a time from one object to another.
        for (int i = 0; i < array.size(); i++) {
            newArray.add(array.get(i));
        }
        return newArray;
    }
}
