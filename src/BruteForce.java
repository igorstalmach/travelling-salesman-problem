import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.commons.collections4.iterators.PermutationIterator;

public class BruteForce {
    public static void BF(String filePath) {
        // Reading from a file.
        System.out.println("Info: Reading from file");
        ArrayList<ArrayList<Integer>> cities = ReadFromFile.readFile(filePath);
        int cityNumber = cities.get(0).get(0);
        cities.remove(0);

        // Generate a list of numbers for permutation.
        System.out.println("Info: Started permutations");
        LinkedList<Integer> toPermute = new LinkedList<>();
        for (int i = 1; i < cityNumber; i++) {
            toPermute.add(i);
        }

        // Generate all possible permutations.
        PermutationIterator<Integer> permutations = new PermutationIterator<>(toPermute);

        // Variables storing the final path and its length.
        int length = 0;
        ArrayList<Integer> path = new ArrayList<>();

        // The main loop of the algorithm.
        System.out.println("Info: Started bruteforce");
        while (permutations.hasNext()) {
            // Variables storing current permutation, previous city
            // and the temporary length of the path calculated with the given permutation.
            ArrayList<Integer> currPerm = (ArrayList<Integer>) permutations.next();
            int prev = 0;
            int tempLength = 0;

            // Adding the distance of each city in a given permutation sequentially.
            for (Integer currNum : currPerm) {
                tempLength += cities.get(currNum).get(prev);
                // The current city becomes the previous one.
                prev = currNum;
            }
            // Adding a return distance to the starting city.
            tempLength += cities.get(0).get(prev);

            // Updating path and permutation when the condition is met.
            if ((length == 0) || (length > tempLength)) {
                length = tempLength;
                path = currPerm;
            }
        }

        // Outputs correctly formatted results.
        for (int i = 0; i < path.size(); i++) {
            if (i == 0) {
                System.out.print("0 -> " + path.get(i) + " -> ");
            } else if (i == path.size() - 1) {
                System.out.println(path.get(i) + " -> 0");
            } else {
                System.out.print(path.get(i) + " -> ");
            }
        }
        System.out.println(length);
        System.out.println();
    }
}
