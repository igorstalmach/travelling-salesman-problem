import java.util.Scanner;

public class GUI {

    // Zmienna przechowująca ścieżkę do pliku.
    String file = "";

    public void gui() {
        // Obsługa menu.
        System.out.println("""
                [1] - przegląd zupełny (BF)
                [2] - branch and bound (B & B)
                [3] - wybierz plik z danymi
                [4] - zakończ program
                """);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Wybierz wartość: ");
        int value = Integer.parseInt(scanner.nextLine());

        if (value == 4) {
            scanner.close();
            System.exit(0);
        }

        if (value == 3) {
            System.out.println("Podaj nazwę pliku wraz z rozszerzeniem: ");
            file = scanner.nextLine();

            // Błąd, jeśli plik nie posiada rozszerzenia '.txt'.
            if ((file.length() <= 4) || (!file.substring(file.length() - 4).equals(".txt"))) {
                System.out.println("Error: Obsługiwane są wyłącznie pliki tekstowe");
                file = "";
                System.exit(2);
            } else {
                System.out.println();
            }
        }

        if (file.isEmpty()) {
            System.out.println("Error: Przed wybraniem algorytmu należy załączyć plik");
            System.exit(1);
        }

        if (value == 1) {
            long start = System.currentTimeMillis();
            BruteForce.BF(file);
            long finish = System.currentTimeMillis();
            System.out.println("Czas wykonywania: " + (finish - start) + " [ms]");
            System.out.println();
        }

        if (value == 2) {
            BranchAndBound bnb = new BranchAndBound();
            long start = System.currentTimeMillis();
            bnb.BaB(file);
            long finish = System.currentTimeMillis();
            System.out.println("Czas wykonywania: " + (finish - start) + " [ms]");
            System.out.println();
        }

    }
}
