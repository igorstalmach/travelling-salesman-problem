import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFromFile {
    // A class that reads data from a file, which is then passed as a single variable.
    public static ArrayList<ArrayList<Integer>> readFile(String filePath) {
        ArrayList<ArrayList<Integer>> cities = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Reading the number of cities (first line).
            ArrayList<Integer> city_number = new ArrayList<>();
            city_number.add(scanner.nextInt());
            cities.add(city_number);

            // Reading the values on consecutive lines and creating a two-dimensional collection.
            for (int i = 0; i < city_number.get(0); i++) {
                ArrayList<Integer> temp_array = new ArrayList<>();
                for (int j = 0; j < city_number.get(0); j++) {
                    temp_array.add(scanner.nextInt());
                }
                cities.add(temp_array);
            }
            scanner.close();

        // Error handling.
        } catch (FileNotFoundException e) {
            System.out.println("[Error] File not found.");
        }

        return cities;
    }
}
