import java.util.ArrayList;
import java.util.Random;

public class GenerateRandomCities {
    public static void generate(int size) {
        ArrayList<ArrayList<Integer>> newMatrix = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> tempMatrix = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (i == j) {
                    tempMatrix.add(j, 0);
                } else {
                tempMatrix.add(j, rand.nextInt(100) + 1);
                }
            }
            newMatrix.add(tempMatrix);
        }
        System.out.println("test");
    }
}
