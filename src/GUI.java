import java.util.Scanner;

public class GUI {

    // Variable storing a path to the file.
    String file = "";

    public void gui() {
        // Menu.
        System.out.println("""
                [1] - bruteforce
                [2] - branch and bound
                [3] - choose a file
                [4] - terminate program
                """);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a value: ");
        int value = Integer.parseInt(scanner.nextLine());

        if (value == 4) {
            scanner.close();
            System.exit(0);
        }

        if (value == 3) {
            System.out.println("Choose a filename (with extension): ");
            file = scanner.nextLine();

            // Error if the file does not have a '.txt' extension.
            if ((file.length() <= 4) || (!file.substring(file.length() - 4).equals(".txt"))) {
                System.out.println("Error: Only text files are supported.");
                file = "";
                System.exit(2);
            } else {
                System.out.println();
            }
        }

        if (file.isEmpty()) {
            System.out.println("Error: Attach a file before choosing an algorithm.");
            System.exit(1);
        }

        if (value == 1) {
            long start = System.currentTimeMillis();
            BruteForce.BF(file);
            long finish = System.currentTimeMillis();
            System.out.println("Execution time: " + (finish - start) + " [ms]");
            System.out.println();
        }

        if (value == 2) {
            BranchAndBound bnb = new BranchAndBound();
            long start = System.currentTimeMillis();
            bnb.BaB(file);
            long finish = System.currentTimeMillis();
            System.out.println("Execution time: " + (finish - start) + " [ms]");
            System.out.println();
        }

    }
}
