import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Chromosome {
    private final ArrayList<ArrayList<Integer>> citiesDist = GeneticAlgorithm.citiesDist;
    private final int cityNumber;
    private LinkedList<Integer> path;
    private int pathDist;
    private double fitness;

    public Chromosome() {
        this.cityNumber = citiesDist.size();
        this.path = new LinkedList<>();
    }

    // Create and shuffle the pathway (gene sequence).
    public void createPath() {
        for (int i = 1; i < cityNumber; i++) {
            path.add(i);
        }
        Collections.shuffle(path);
        pathDist = getDistance();
    }

    // Calculate the path length, including distances from and to the zero city.
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

    public LinkedList<Integer> getPath() {
        return DeepCopy.copyList(this.path);
    }

    // Method simultaneously sets a new path and calculates its length.
    public void setPath(LinkedList<Integer> path) {
        this.path = DeepCopy.copyList(path);
        this.pathDist = getDistance();
    }

    public int getPathDist() {
        return this.pathDist;
    }

    public void setPathDist(int pathDist) {
        this.pathDist = pathDist;
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setFitness(double newFitness) {
        this.fitness = newFitness;
    }
}
