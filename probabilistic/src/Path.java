import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

// A class representing a path. Collects all the necessary information about the path,
// and contains helper methods to perform calculations on it.
public class Path {
    // Variables storing values associated with a path.
    private final ArrayList<ArrayList<Integer>> citiesDist;
    private final int cityNumber;
    private LinkedList<Integer> path;
    private LinkedList<Integer> previousPath;
    private int pathDist;

    public Path(LinkedList<Integer> path, ArrayList<ArrayList<Integer>> citiesDist) {
        this.path = DeepCopy.copyList(path);
        this.citiesDist = citiesDist;
        this.cityNumber = citiesDist.size();
        this.pathDist = getDistance();
    }

    public Path(ArrayList<ArrayList<Integer>> citiesDist) {
        this.path = new LinkedList<>();
        this.citiesDist = citiesDist;
        this.cityNumber = citiesDist.size();
        this.pathDist = 0;
    }

    // A method that calculates the length of the path, taking into account the start and return from city 0.
    public int getDistance() {
        int dist = 0;

        dist += citiesDist.get(0).get(path.getFirst());
        dist += citiesDist.get(path.getLast()).get(0);

        for (int i = 0; i < path.size() - 1; i++) {
            dist += citiesDist.get(path.get(i)).get(path.get(i + 1));
        }

        pathDist = dist;
        return dist;
    }

    // A method that swaps cities according to a random index within neighborhood boundaries.
    public void swapCities() {
        int index_1 = (int) Math.floor(Math.random() * path.size());
        int index_2 = (int) Math.floor((Math.random() * path.size()));

        previousPath = DeepCopy.copyList(path);
        int city_1 = path.get(index_1);
        int city_2 = path.get(index_2);

        path.set(index_1, city_2);
        path.set(index_2, city_1);
    }

    // Revert city swaps by index.
    public void revertSwap() {
        path = DeepCopy.copyList(previousPath);
        pathDist = getDistance();
    }

    // Create an initial and random route.
    public void createStartingPath() {
        for (int i = 1; i < cityNumber; i++) {
            path.add(i);
        }
        Collections.shuffle(path);
    }

    public LinkedList<Integer> getPath() {
        return DeepCopy.copyList(this.path);
    }

    public void setPath(LinkedList<Integer> path) {
        this.path = DeepCopy.copyList(path);
        this.pathDist = getDistance();
    }

    public int getPathDist() {
        return this.pathDist;
    }
}
