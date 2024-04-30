import java.util.*;

public class BestFirstSearch {

    public static void main(String[] args) {
        // Define the graph as a map of nodes to their neighbors
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("S", Arrays.asList("A", "B"));
        graph.put("A", Arrays.asList("C", "E"));
        graph.put("B", Arrays.asList("D", "F"));
        graph.put("C", Collections.emptyList());
        graph.put("D", Collections.emptyList());
        graph.put("E", Collections.emptyList());
        graph.put("F", Collections.emptyList());

        // Define the heuristic function
        Map<String, Integer> heuristic = new HashMap<>();
        heuristic.put("S", 10);
        heuristic.put("A", 8);
        heuristic.put("B", 7);
        heuristic.put("C", 4);
        heuristic.put("D", 5);
        heuristic.put("E", 9);
        heuristic.put("F", 0);

        // Perform the Best-First Search
        String start = "S";
        String goal = "F";
        PriorityQueue<Node> queue = new PriorityQueue<>((n1, n2) -> {
            Integer h1 = heuristic.get(n1.getState());
            Integer h2 = heuristic.get(n2.getState());

            if (h1 == null && h2 == null) {
                return 0; // Both nodes have null heuristic, consider them equal
            } else if (h1 == null) {
                return 1; // Node with null heuristic is considered greater
            } else if (h2 == null) {
                return -1; // Node with null heuristic is considered greater
            } else {
                return h1 - h2; // Compare the heuristic values
            }
        });

        queue.add(new Node(start, 0));

        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            String node = current.getState();

            if (visited.contains(node)) {
                continue;
            }

            visited.add(node);

            if (node.equals(goal)) {
                System.out.println("Goal found!");
                System.out.println("Path:");
                printPath(parent, goal);
                break;
            }

            for (String neighbor : graph.get(node)) {
                int cost = current.getCost() + 1;
                queue.add(new Node(neighbor, cost));
                parent.put(neighbor, node);
            }
        }

        if (!visited.contains(goal)) {
            System.out.println("Goal not found!");
        }
    }

    private static void printPath(Map<String, String> parent, String goal) {
        String node = goal;
        Stack<String> path = new Stack<>();
        while (node != null) {
            path.push(node);
            node = parent.get(node);
        }

        System.out.print("[");
        while (!path.isEmpty()) {
            System.out.print(path.pop());
            if (!path.isEmpty()) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    static class Node {
        private final String state;
        private final int cost;

        public Node(String state, int cost) {
            this.state = state;
            this.cost = cost;
        }

        public String getState() {
            return state;
        }

        public int getCost() {
            return cost;
        }
    }
}
