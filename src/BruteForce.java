import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.commons.collections4.iterators.PermutationIterator;

public class BruteForce {
    public static void BF(String filePath) {
        // Odczytywanie z pliku.
        System.out.println("Info: Reading from file");
        ArrayList<ArrayList<Integer>> cities = ReadFromFile.readFile(filePath);
        int cityNumber = cities.get(0).get(0);
        cities.remove(0);

        // Generowanie listy liczb do permutacji.
        System.out.println("Info: Started permutations");
        LinkedList<Integer> toPermute = new LinkedList<>();
        for (int i = 1; i < cityNumber; i++) {
            toPermute.add(i);
        }

        // Generowanie wszystkich możliwych permutacji.
        PermutationIterator<Integer> permutations = new PermutationIterator<>(toPermute);

        // Zmienne przechowujące ostateczną ścieżkę oraz jej długość.
        int length = 0;
        ArrayList<Integer> path = new ArrayList<>();

        // Pętla główna programu.
        System.out.println("Info: Started bruteforce");
        while (permutations.hasNext()) {
            // Zmienne pomocnicze przechowujące obecną permutację, poprzednie miasto
            // oraz tymczasową długość ścieżki obliczonej z daną permutacją.
            ArrayList<Integer> currPerm = (ArrayList<Integer>) permutations.next();
            int prev = 0;
            int tempLength = 0;

            // Dodawanie kolejno odległości każdego z miast w danej permutacji.
            for (Integer currNum : currPerm) {
                tempLength += cities.get(currNum).get(prev);
                // Obecne miasto staje się poprzednim.
                prev = currNum;
            }
            // Dodanie odległości powrotu do miasta początkowego.
            tempLength += cities.get(0).get(prev);

            // Zapisanie drogi oraz permutacji, gdy spełniony jest warunek.
            if ((length == 0) || (length > tempLength)) {
                length = tempLength;
                path = currPerm;
            }
        }

        // Wypisanie poprawnie sformatowanych wyników.
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
