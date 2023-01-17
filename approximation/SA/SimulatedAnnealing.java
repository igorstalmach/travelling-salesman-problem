import java.util.ArrayList;
import java.util.LinkedList;

public class SimulatedAnnealing {
    // Variables storing parameters of the algorithm.
    String filePath;
    long haltTime;
    double startTemp;
    double stopTemp;
    double coolingCoef;

    // Variable storing distances between cities.
    ArrayList<ArrayList<Integer>> citiesDist;

    public SimulatedAnnealing(String filePath, long haltTime, double startTemp, double stopTemp, double coolingCoef) {
        this.filePath = filePath;
        this.haltTime = haltTime;
        this.startTemp = startTemp;
        this.stopTemp = stopTemp;
        this.coolingCoef = coolingCoef;
    }

    // Main method of the algorithm.
    public void runAlgorithm(){
        // Reading from file.
        citiesDist = ReadFromFile.readFile(filePath);
        int cityNumber = citiesDist.get(0).get(0);
        citiesDist.remove(0);

        // Creating the base path.
        Path currPath = new Path(citiesDist);
        currPath.createStartingPath();

        // Creating a rival path to the previous one.
        Path championPath = new Path(citiesDist);
        championPath.setPath(currPath.getPath());

        // Creating the final path.
        Path bestPath = new Path(currPath.getPath(), citiesDist);

        // Main loop of the algorithm.
        long startTime = System.currentTimeMillis();
        long stopTime = 0;
        // Loop ensuring stop time condition.
        do {
            // Loop ensuring the right number of iterations at each temperature level.
            for (int i = 0; i < cityNumber * 100; i++) {
                // Checking if the final temperature has not been exceeded.
                if (startTemp > stopTemp) {
                    stopTime = System.currentTimeMillis();
                    // Exit loop if the time has been exceeded.
                    if (stopTime - startTime > haltTime) {
                        break;
                    }

                    // Index swap in the rival path.
                    championPath.swapCities();

                    // Comparison of the length of the base path and the rival path.
                    if (championPath.getDistance() - currPath.getDistance() <= 0) {
                        currPath.setPath(championPath.getPath());

                        // Save the path as best when the new base path is shorter than the current best path.
                        if (currPath.getDistance() < bestPath.getDistance()) {
                            bestPath.setPath(currPath.getPath());
                        }

                        // Checking the probability condition.
                    } else if (Math.random() < Math.exp(-(championPath.getDistance() - currPath.getDistance()) / startTemp)) {
                        currPath.setPath(championPath.getPath());

                    // Undo the index swap if none of the conditions are met
                    } else {
                        championPath.revertSwap();
                    }
                }
            }

            // Exit loop when the final temperature has been exceeded.
            // If the final temperature is "0", the temperature criterion is ignored.
            if ((startTemp < stopTemp) && (stopTemp != 0)) {
                break;
            }

            // Temperature reduction.
            startTemp *= coolingCoef;
            stopTime = System.currentTimeMillis();

        } while ( stopTime - startTime < haltTime );

        // Ensuring that zeros are displayed on both sides of the path.
        LinkedList<Integer> best = bestPath.getPath();
        best.addFirst(0);
        best.addLast(0);

        // Displaying results.
        System.out.println("Path: " + best);
        System.out.println("Path length: " + bestPath.getPathDist());
    }
}
