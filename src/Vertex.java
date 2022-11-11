import java.util.ArrayList;

public class Vertex {
    // A class representing a vertex object.
    ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
    Vertex prevVertex = null;
    private int value;
    private final int key;
    private boolean isVisited;

    Vertex(int key, int value) {
        this.key = key;
        this.value = value;
        this.isVisited = false;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

    public void setMatrix(ArrayList<ArrayList<Integer>> matrix) {
        this.matrix = DeepCopy.copyArray(matrix);
    }

    public void setVisited(boolean visited) {
        this.isVisited = visited;
    }

    public boolean getVisited() {
        return isVisited;
    }
}
