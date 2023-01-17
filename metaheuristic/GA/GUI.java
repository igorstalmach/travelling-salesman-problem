import java.util.InputMismatchException;
import java.util.Scanner;

public class GUI {

    // Variables storing the entered parameters.
    String file = "";
    long stopTime = 10000;
    int populationSize = 50;
    double mutationCoefficient = 0.01;
    double crossoverCoefficient = 0.8;
    int mutationTechnique = 1;
    int crossoverTechnique = 1;
    int value;
    boolean printPath = true;

    public void gui() {
        // Menu.
        System.out.println("""
                
                [1] - run algorithm
                [2] - attach a file
                [3] - enter stop condition (execution time [s])
                [4] - enter initial population size
                [5] - enter mutation coefficient
                [6] - choose mutation method
                [7] - enter crossover coefficient
                [8] - choose crossover method
                [9] - change result path visibility
                [10] - quit program
                """);

        System.out.println("--Chosen parameters--");
        if (file.isEmpty()) {
            System.out.println("Attached file: none");
        } else {
            System.out.println("Attached file: " + file);
        }
        System.out.println("Stop condition: " + (stopTime / 1000) + " s");
        System.out.println("Initial population size: " + populationSize);
        System.out.println("Mutation coefficient: " + mutationCoefficient);
        if (mutationTechnique == 1) {
            System.out.println("Mutation method: Swapping places of one pair of genes in a population chromosome.");
        } else {
            System.out.println("Mutation method: Swapping places of two pairs of genes in a population chromosome.");
        }
        System.out.println("Crossover coefficient: " + crossoverCoefficient);
        if (crossoverTechnique == 1) {
            System.out.println("Crossover method: One point.");
        } else {
            System.out.println("Crossover method: Two points.");
        }
        System.out.println("Result path visibility: " + printPath);
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a value: ");
        try {
            value = Integer.parseInt(scanner.nextLine());

            switch (value) {
                case 1 -> {
                    if (file.isEmpty()) {
                        System.out.println("[Error] No file selected.");
                        break;
                    }
                    GeneticAlgorithm ga = new GeneticAlgorithm(file, stopTime, populationSize, mutationCoefficient, mutationTechnique, crossoverCoefficient, crossoverTechnique, printPath);
                    ga.runAlgorithm();
                }
                case 2 -> {
                    System.out.println("Enter a file name (with extension): ");
                    file = scanner.nextLine();
                    if ((file.length() <= 4) || (!file.substring(file.length() - 4).equals(".txt"))) {
                        System.out.println();
                        System.out.println("[Error] Only .txt text files are supported.");
                        System.out.println();
                        file = "";
                    }
                }
                case 3 -> {
                    System.out.println("Stop condition (execution time [s]): ");
                    stopTime = scanner.nextLong();
                    stopTime *= 1000;
                }
                case 4 -> {
                    System.out.println("Initial population size: ");
                    populationSize = scanner.nextInt();
                }
                case 5 -> {
                    System.out.println("Mutation coefficient: ");
                    mutationCoefficient = scanner.nextDouble();
                }
                case 6 -> {
                    System.out.println("Choose mutation method: ");
                    System.out.println("[1] - Swapping places of one pair of genes in a population chromosome.");
                    System.out.println("[2] - Swapping places between two pairs of genes in a population chromosome.");
                    mutationTechnique = scanner.nextInt();
                    if (mutationTechnique < 1 || mutationTechnique > 2) {
                        System.out.println("[Error] Expected number in the range [1, 2].");
                    }
                }
                case 7 -> {
                    System.out.println("Crossover coefficient: ");
                    crossoverCoefficient = scanner.nextDouble();
                }
                case 8 -> {
                    System.out.println("Choose crossover method: ");
                    System.out.println("[1] - One point crossing.");
                    System.out.println("[2] - Two point crossing.");
                    crossoverTechnique = scanner.nextInt();
                    if (crossoverTechnique < 1 || crossoverTechnique > 2) {
                        System.out.println("[Error] Expected number in the range [1, 2].");
                    }
                }
                case 9 -> {
                    printPath = !printPath;
                    if (printPath) {
                        System.out.println("[Info] Result path visibility enabled.");
                    } else {
                        System.out.println("[Info] Result path visibility disabled.");
                    }
                }
                case 10 -> System.exit(0);
                default -> System.out.println("[Error] Expected number in the range [1, 9].");
            }
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("[Error] Expected integer.");
        }
    }
}
