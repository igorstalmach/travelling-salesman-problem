import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class GeneticAlgorithm {
    static ArrayList<ArrayList<Integer>> citiesDist;
    private LinkedList<Chromosome> population;
    private final String filePath;
    private final long haltTime;
    private final int populationSize;
    private final double mutationCoefficient;
    private final double crossoverCoefficient;
    private final int mutationTechnique;
    private final int crossoverTechnique;
    private final boolean printPath;
    static int cityNumber;

    GeneticAlgorithm(String file, long stopTime, int populationSize, double mutationCoefficient, int mutationTechnique, double crossoverCoefficient, int crossoverTechnique, boolean printPath) {
        this.filePath = file;
        this.haltTime = stopTime;
        this.populationSize = populationSize;
        this.mutationCoefficient = mutationCoefficient;
        this.mutationTechnique = mutationTechnique;
        this.crossoverCoefficient = crossoverCoefficient;
        this.crossoverTechnique = crossoverTechnique;
        this.population = new LinkedList<>();
        this.printPath = printPath;
    }

    // Create initial population.
    private void generateStartingPopulation() {
        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome());
            population.getLast().createPath();
        }
    }

    // Assigning fitness to chromosomes in the range (0, 1).
    private void evaluateFitness() {
        for (Chromosome chromosome : population) {
            chromosome.setFitness((double)1 / chromosome.getPathDist());
        }
    }

    // Selection of the parent according to the tournament method, i.e., selecting a random number of chromosomes of the population
    // and then selecting the one with the highest fitness.
    private Chromosome pickParent() {
        Random rand = new Random();
        int possibleParentsAmount = rand.nextInt(populationSize - 2) + 1;
        LinkedList<Chromosome> parents = new LinkedList<>();

        for (int i = 0; i < possibleParentsAmount; i++) {
            parents.add(population.get(rand.nextInt(populationSize - 1)));
        }

        Chromosome bestChromosome = new Chromosome();
        double currBestFitness = 0;

        for (Chromosome tempChromosome : parents) {
            if (tempChromosome.getFitness() > currBestFitness) {
                currBestFitness = tempChromosome.getFitness();
                bestChromosome = tempChromosome;
            }
        }

        return bestChromosome;
    }

    // Creation of a new population.
    private void generateNewPopulation() {
        Collections.shuffle(population);

        Random rand = new Random();
        LinkedList<Chromosome> tempPopulation = new LinkedList<>();

        for (int i = 0; i < populationSize / 2; i++) {
            // Random coefficients to decide whether crossover and mutation will be performed.
            double randomCrossoverCoefficient = rand.nextDouble();
            double firstRandomMutationCoefficient = rand.nextDouble();
            double secondRandomMutationCoefficient = rand.nextDouble();

            Chromosome firstNewChromosome = new Chromosome();
            Chromosome secondNewChromosome = new Chromosome();

            Chromosome firstNewParent = pickParent();
            Chromosome secondNewParent = pickParent();

            // Checking the coefficient condition. Crossover or transition parents without changes.
            if (randomCrossoverCoefficient <= crossoverCoefficient) {
                LinkedList<LinkedList<Integer>> newChildren;
                if (crossoverTechnique == 1) {
                    newChildren = Crossover.onePointCrossover(firstNewParent.getPath(), secondNewParent.getPath());
                } else {
                    newChildren = Crossover.twoPointCrossover(firstNewParent.getPath(), secondNewParent.getPath());
                }
                firstNewChromosome.setPath(newChildren.get(0));
                secondNewChromosome.setPath(newChildren.get(1));
            } else {
                firstNewChromosome = firstNewParent;
                secondNewChromosome = secondNewParent;
            }

            // Checking the conditions of the coefficients. Perform mutation or transition chromosomes without changes.
            if (firstRandomMutationCoefficient <= mutationCoefficient) {
                if (mutationTechnique == 1) {
                    firstNewChromosome.setPath(Mutation.onePairSwapMutation(firstNewChromosome.getPath()));
                } else {
                    firstNewChromosome.setPath(Mutation.twoPairSwapMutation(firstNewChromosome.getPath()));
                }
            }

            if (secondRandomMutationCoefficient <= mutationCoefficient) {
                if (mutationTechnique == 1) {
                    secondNewChromosome.setPath(Mutation.onePairSwapMutation(secondNewChromosome.getPath()));
                } else {
                    secondNewChromosome.setPath(Mutation.twoPairSwapMutation(secondNewChromosome.getPath()));
                }
            }

            tempPopulation.add(firstNewChromosome);
            tempPopulation.add(secondNewChromosome);
        }
        population = tempPopulation;
    }

    // Selection of the shortest path from a given population.
    private Chromosome findBestPath() {
        int minPath = Integer.MAX_VALUE;
        Chromosome bestChromosome = new Chromosome();

        for (Chromosome chromosome : population) {
            if (chromosome.getPathDist() < minPath) {
                minPath = chromosome.getPathDist();
                bestChromosome = chromosome;
            }
        }

        return bestChromosome;
    }

    // Main method of the algorithm.
    public void runAlgorithm() {
        // Read from file.
        citiesDist = ReadFromFile.readFile(filePath);
        cityNumber = citiesDist.get(0).get(0);
        citiesDist.remove(0);

        // Create the initial population and evaluate fitness of its chromosomes.
        generateStartingPopulation();
        evaluateFitness();

        // Ensuring that the time condition is met.
        long startTime = System.currentTimeMillis();
        long stopTime;
        Chromosome bestChromosome = new Chromosome();
        bestChromosome.setPathDist(Integer.MAX_VALUE);

        do {
            stopTime = System.currentTimeMillis();
            generateNewPopulation();
            evaluateFitness();

            // Selecting the chromosome with the shortest path in each population.
            Chromosome possibleBestChromosome = findBestPath();
            if(possibleBestChromosome.getPathDist() < bestChromosome.getPathDist()) {
                bestChromosome = possibleBestChromosome;
            }
        } while (stopTime - startTime < haltTime);

        // Preparing the path for displaying, as the city 0 is always at the beginning and end,
        // the algorithm does not take them into account during permutation.
        LinkedList<Integer> bestPath = bestChromosome.getPath();
        bestPath.add(0);
        bestPath.add(0, 0);

        if (printPath) {
            System.out.println("Najkrótsza ścieżka: " + bestPath);
        }

        System.out.println("Najkrótsza długość ścieżki: " + bestChromosome.getPathDist());
    }
}
