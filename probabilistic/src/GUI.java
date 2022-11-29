import java.util.Scanner;

public class GUI {

    // Variables storing the entered parameters.
    String file = "";
    long haltTime = 60000;
    double startTemp = 10000;
    double stopTemp = 0;
    double coolingCoef = 0.95;

    public void gui() {
        // Menu.
        System.out.println("""
                
                [1] - run simulated annealing
                [2] - attach a file
                [3] - enter starting temperature
                [4] - enter final temperature
                [5] - enter temperature reduction coefficient
                [6] - enter stop condition (execution time [s])
                [7] - terminate program
                """);

        System.out.println("--Currently selected parameters--");
        if (file.isEmpty()) {
            System.out.println("Attached file: none");
        } else {
            System.out.println("Attached file: " + file);
        }
        System.out.println("Starting temperature: " + startTemp);
        System.out.println("Final temperature: " + stopTemp);
        System.out.println("Temperature reduction coefficient: " + coolingCoef);
        System.out.println("Stop condition: " + (haltTime / 1000) + " s");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a value: ");
        int value = Integer.parseInt(scanner.nextLine());

        if (value == 7) {
            System.exit(0);
        }

        if (value == 3) {
            System.out.println("Enter starting temperature: ");
            startTemp = scanner.nextDouble();
        }

        if (value == 4) {
            System.out.println("Enter final temperature: ");
            stopTemp = scanner.nextDouble();
        }

        if (value == 5) {
            System.out.println("Enter temperature reduction coefficient (within (0, 1)): ");
            coolingCoef = scanner.nextDouble();
        }

        if (value == 6) {
            System.out.println("Enter the execution time in seconds: ");
            haltTime = scanner.nextInt();
            haltTime *= 1000;
        }

        if (value == 2) {
            System.out.println("Enter a file name (with extension): ");
            file = scanner.nextLine();

            // Error if the file does not have a '.txt' extension.
            if ((file.length() <= 4) || (!file.substring(file.length() - 4).equals(".txt"))) {
                System.out.println("Error: only text files are supported.");
                file = "";
                System.exit(1);
            } else {
                System.out.println();
            }
        }

        if (value == 1) {
            SimulatedAnnealing sa = new SimulatedAnnealing(file, haltTime, startTemp, stopTemp, coolingCoef);
            long start = System.currentTimeMillis();
            sa.runAlgorithm();
            long finish = System.currentTimeMillis();

            System.out.println("Execution time: " + (finish - start) + " [ms]");
            System.out.println();
        }
    }
}
