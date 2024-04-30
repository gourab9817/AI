import java.util.*;

class WeightedGraph {
    private Map<Character, Map<Character, Integer>> adjacencyList;

    public WeightedGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(char vertex) {
        adjacencyList.put(vertex, new HashMap<>());
    }

    public void addEdge(char source, char destination, int weight) {
        adjacencyList.get(source).put(destination, weight);
        adjacencyList.get(destination).put(source, weight); // Assuming an undirected graph
    }

    public List<List<Character>> dfsAllPaths(char start, char goal) {
        List<List<Character>> allPaths = new ArrayList<>();
        List<Character> currentPath = new ArrayList<>();
        Set<Character> visited = new HashSet<>();
        dfsAllPathsHelper(start, goal, visited, currentPath, allPaths);
        return allPaths;
    }

    private void dfsAllPathsHelper(char current, char goal, Set<Character> visited, List<Character> currentPath,
            List<List<Character>> allPaths) {
        visited.add(current);
        currentPath.add(current);

        if (current == goal) {
            allPaths.add(new ArrayList<>(currentPath));
        } else {
            for (char neighbor : adjacencyList.get(current).keySet()) {
                if (!visited.contains(neighbor)) {
                    dfsAllPathsHelper(neighbor, goal, visited, currentPath, allPaths);
                }
            }
        }

        visited.remove(current);
        currentPath.remove(currentPath.size() - 1);
    }

    public List<List<Character>> bfsAllPaths(char start, char goal) {
        List<List<Character>> allPaths = new ArrayList<>();
        Queue<List<Character>> queue = new LinkedList<>();

        List<Character> initialPath = new ArrayList<>();
        initialPath.add(start);
        queue.add(initialPath);

        while (!queue.isEmpty()) {
            List<Character> currentPath = queue.poll();
            char currentVertex = currentPath.get(currentPath.size() - 1);

            if (currentVertex == goal) {
                allPaths.add(new ArrayList<>(currentPath));
            }

            for (char neighbor : adjacencyList.get(currentVertex).keySet()) {
                if (!currentPath.contains(neighbor)) {
                    List<Character> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        return allPaths;
    }

    public List<Character> findOptimalPath(List<List<Character>> allPaths) {
        int minCost = Integer.MAX_VALUE;
        List<Character> optimalPath = null;

        for (List<Character> path : allPaths) {
            int currentCost = calculatePathCost(path);
            if (currentCost < minCost) {
                minCost = currentCost;
                optimalPath = new ArrayList<>(path);
            }
        }

        return optimalPath;
    }

    public int calculatePathCost(List<Character> path) {
        int cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            char current = path.get(i);
            char next = path.get(i + 1);
            cost += adjacencyList.get(current).get(next);
        }
        return cost;
    }
}
