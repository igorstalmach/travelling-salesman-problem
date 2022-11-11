import java.util.ArrayList;
import java.util.LinkedList;

public class Node {
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
    Node prevNode = null;
    private int value;
    private int key;
    private boolean isVisited;

    Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.isVisited = false;
    }

    public void addToValue(int number) {
        value += number;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void addMatrix(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = DeepCopy.copyArray(matrix);
    }

    public void setVisited(boolean visited) {
        this.isVisited = visited;
    }

    public boolean getVisited() {
        return isVisited;
    }
}
