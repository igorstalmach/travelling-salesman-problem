import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFromFile {
    // Klasa odczytująca dane z pliku, które następnie są przekazywane w postaci jednej zmiennej.
    public static ArrayList<ArrayList<Integer>> readFile(String filePath) {
        ArrayList<ArrayList<Integer>> cities = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Odczytanie liczby miast (pierwszej linijki).
            ArrayList<Integer> city_number = new ArrayList<>();
            city_number.add(scanner.nextInt());
            cities.add(city_number);

            // Odczytywanie wartości w kolejnych liniach i tworzenie dwuwymiarowej kolekcji.
            for (int i = 0; i < city_number.get(0); i++) {
                ArrayList<Integer> temp_array = new ArrayList<>();
                for (int j = 0; j < city_number.get(0); j++) {
                    temp_array.add(scanner.nextInt());
                }
                cities.add(temp_array);
            }
            scanner.close();

        // Obsługa błędów.
        } catch (FileNotFoundException e) {
            System.out.println("Error: Nie znaleziono pliku.");
            System.exit(3);
        }

        return cities;
    }
}
