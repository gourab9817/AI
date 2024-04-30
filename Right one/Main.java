import java.util.*;

public class Main {
    public static void main(String[] args) {
        WeightedGraph weightedGraph = new WeightedGraph();

        weightedGraph.addVertex('A');
        weightedGraph.addVertex('B');
        weightedGraph.addVertex('C');
        weightedGraph.addVertex('D');
        weightedGraph.addVertex('E');
        weightedGraph.addVertex('G');
        weightedGraph.addVertex('H');
        weightedGraph.addVertex('I');
        weightedGraph.addVertex('J');

        weightedGraph.addEdge('A', 'B', 2);
        weightedGraph.addEdge('A', 'C', 4);
        weightedGraph.addEdge('A', 'D', 3);
        weightedGraph.addEdge('A', 'E', 1);
        weightedGraph.addEdge('B', 'C', 4);
        weightedGraph.addEdge('B', 'G', 1);
        weightedGraph.addEdge('C', 'A', 4);
        weightedGraph.addEdge('C', 'B', 4);
        weightedGraph.addEdge('C', 'G', 5);
        weightedGraph.addEdge('C', 'H', 3);
        weightedGraph.addEdge('C', 'D', 2);
        weightedGraph.addEdge('C', 'E', 5);
        weightedGraph.addEdge('D', 'A', 3);
        weightedGraph.addEdge('D', 'C', 2);
        weightedGraph.addEdge('D', 'I', 1);
        weightedGraph.addEdge('D', 'E', 2);
        weightedGraph.addEdge('E', 'A', 1);
        weightedGraph.addEdge('E', 'D', 2);
        weightedGraph.addEdge('E', 'C', 5);
        weightedGraph.addEdge('E', 'J', 3);
        weightedGraph.addEdge('G', 'B', 1);
        weightedGraph.addEdge('G', 'C', 5);
        weightedGraph.addEdge('H', 'C', 3);
        weightedGraph.addEdge('H', 'I', 1);
        weightedGraph.addEdge('I', 'H', 1);
        weightedGraph.addEdge('I', 'D', 1);
        weightedGraph.addEdge('I', 'J', 1);
        weightedGraph.addEdge('J', 'E', 3);
        weightedGraph.addEdge('J', 'I', 1);

        char start = 'A';
        char goal = 'I';

        System.out.println("DFS all paths from " + start + " to " + goal + ":");
        List<List<Character>> dfsPaths = weightedGraph.dfsAllPaths(start, goal);
        printPaths(dfsPaths, weightedGraph);

        System.out.println("\nBFS all paths from " + start + " to " + goal + ":");
        List<List<Character>> bfsPaths = weightedGraph.bfsAllPaths(start, goal);
        printPaths(bfsPaths, weightedGraph);
    }

    private static void printPaths(List<List<Character>> paths, WeightedGraph weightedGraph) {
        for (List<Character> path : paths) {
            int cost = weightedGraph.calculatePathCost(path);
            System.out.println("Path: " + path + ", Cost: " + cost);
        }

        List<Character> optimalPath = weightedGraph.findOptimalPath(paths);
        int optimalCost = weightedGraph.calculatePathCost(optimalPath);

        System.out.println("\nOptimal Path: " + optimalPath);
        System.out.println("Total Cost: " + optimalCost);
    }
}