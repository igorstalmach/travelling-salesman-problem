import java.util.LinkedList;

public class Crossover {
    // Crossing chromosomes according to a single point, selecting 50% of one parent and another 50% of the next parent.
    public static LinkedList<LinkedList<Integer>> onePointCrossover(LinkedList<Integer> firstParent, LinkedList<Integer> secondParent) {
        LinkedList<LinkedList<Integer>> newChildren = new LinkedList<>();
        LinkedList<Integer> firstChild = new LinkedList<>();
        LinkedList<Integer> secondChild = new LinkedList<>();

        // Loop performing the transfer. Protection against adding a repeated city - if a city from the default
        // selected parent is already in the child, the algorithm tries to add a city at the same index from the other parent.
        // If that city is also in the child the algorithm skips the iteration.
        for (int i = 0; i < GeneticAlgorithm.cityNumber - 1; i++) {
            if (i < (GeneticAlgorithm.cityNumber - 1) / 2) {
                firstChild.add(firstParent.get(i));
                secondChild.add(secondParent.get(i));

            } else {
                if (!firstChild.contains(secondParent.get(i))) {
                    firstChild.add(secondParent.get(i));
                } else {
                    if (!firstChild.contains(firstParent.get(i))) {
                        firstChild.add(firstParent.get(i));
                    }
                }

                if (!secondChild.contains(firstParent.get(i))) {
                    secondChild.add(firstParent.get(i));
                } else {
                    if (!secondChild.contains(secondParent.get(i))) {
                        secondChild.add(secondParent.get(i));
                    }
                }
            }
        }

        // If gaps arise during crossover as a result of the above protection, method fills in the
        // path with the cities that are ultimately not in the path.
        if (firstChild.size() != firstParent.size()) {
            completePath(firstChild);
        }

        if (secondChild.size() != secondParent.size()) {
            completePath(secondChild);
        }

        newChildren.add(firstChild);
        newChildren.add(secondChild);

        return newChildren;
    }

    // Crossing chromosomes according to two points, selecting 0-25% and 75-100% of one parent and 25-75% of the other parent.
    public static LinkedList<LinkedList<Integer>> twoPointCrossover(LinkedList<Integer> firstParent, LinkedList<Integer> secondParent) {
        LinkedList<LinkedList<Integer>> newChildren = new LinkedList<>();
        LinkedList<Integer> firstChild = new LinkedList<>();
        LinkedList<Integer> secondChild = new LinkedList<>();

        // Loop performing the transfer. Protection against adding a repeated city - if a city from the default
        // selected parent is already in the child, the algorithm tries to add a city at the same index from the other parent.
        // If that city is also in the child the algorithm skips the iteration.
        for (int i = 0; i < GeneticAlgorithm.cityNumber - 1; i++) {
            if (i < (GeneticAlgorithm.cityNumber - 1) * 0.25) {
                firstChild.add(firstParent.get(i));
                secondChild.add(secondParent.get(i));

            } else if (i < (GeneticAlgorithm.cityNumber - 1) * 0.75) {
                if (!firstChild.contains(secondParent.get(i))) {
                    firstChild.add(secondParent.get(i));
                } else {
                    if (!firstChild.contains(firstParent.get(i))) {
                        firstChild.add(firstParent.get(i));
                    }
                }

                if (!secondChild.contains(firstParent.get(i))) {
                    secondChild.add(firstParent.get(i));
                } else {
                    if (!secondChild.contains(secondParent.get(i))) {
                        secondChild.add(secondParent.get(i));
                    }
                }

            } else {
                if (!firstChild.contains(firstParent.get(i))) {
                    firstChild.add(firstParent.get(i));
                } else {
                    if (!firstChild.contains(secondParent.get(i))) {
                        firstChild.add(secondParent.get(i));
                    }
                }

                if (!secondChild.contains(secondParent.get(i))) {
                    secondChild.add(secondParent.get(i));
                } else {
                    if (!secondChild.contains(firstParent.get(i))) {
                        secondChild.add(firstParent.get(i));
                    }
                }
            }
        }

        // If gaps arise during crossover as a result of the above protection, method fills in the
        // path with the cities that are ultimately not in the path.
        if (firstChild.size() != firstParent.size()) {
            completePath(firstChild);
        }

        if (secondChild.size() != secondParent.size()) {
            completePath(secondChild);
        }

        newChildren.add(firstChild);
        newChildren.add(secondChild);

        return newChildren;
    }

    // A method that fills the path with cities that were not in the path as a result of the
    // the correct crossover protection.
    private static LinkedList<Integer> completePath(LinkedList<Integer> path) {
        for (int i = 1; i < GeneticAlgorithm.cityNumber; i++) {
            if(!path.contains(i)) {
                path.add(i);
            }
        }
        return path;
    }
}
