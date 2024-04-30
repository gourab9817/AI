import java.util.*;

public class EightPuzzleBestFirstSearch {
    private static final int[][] GOAL_STATE = new int[3][3];
    private static int[][] INITIAL_STATE;

    private static void initializeStates() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the goal state:");
        readState(GOAL_STATE, scanner);

        System.out.println("Enter the initial state:");
        INITIAL_STATE = new int[3][3];
        readState(INITIAL_STATE, scanner);

        scanner.close();
    }

    private static void readState(int[][] state, Scanner scanner) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                state[i][j] = scanner.nextInt();
            }
        }
    }

    private static int calculateMisplacedTiles(int[][] state) {
        int misplacedTiles = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != GOAL_STATE[i][j] && state[i][j] != 0) {
                    misplacedTiles++;
                }
            }
        }
        return misplacedTiles;
    }

    static class PuzzleState {
        int[][] state;
        int misplacedTiles;

        public PuzzleState(int[][] state) {
            this.state = state;
            this.misplacedTiles = calculateMisplacedTiles(state);
        }

        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            PuzzleState that = (PuzzleState) obj;
            return Arrays.deepEquals(state, that.state);
        }

        public int hashCode() {
            return Arrays.deepHashCode(state);
        }
    }

    private static List<int[][]> bestFirstSearch() {
        PriorityQueue<PuzzleState> queue = new PriorityQueue<>(Comparator.comparingInt(ps -> ps.misplacedTiles));
        Map<PuzzleState, Integer> visited = new HashMap<>();
        Map<PuzzleState, PuzzleState> parent = new HashMap<>();

        PuzzleState initialState = new PuzzleState(INITIAL_STATE);
        queue.offer(initialState);
        visited.put(initialState, 0);

        while (!queue.isEmpty()) {
            PuzzleState currentState = queue.poll();
            if (Arrays.deepEquals(currentState.state, GOAL_STATE)) {
                return reconstructPath(parent, currentState);
            }
            List<PuzzleState> neighbors = generateNeighbors(currentState);
            for (PuzzleState neighbor : neighbors) {
                int cost = visited.get(currentState) + 1;
                if (!visited.containsKey(neighbor) || cost < visited.get(neighbor)) {
                    visited.put(neighbor, cost);
                    parent.put(neighbor, currentState);
                    queue.offer(neighbor);
                }
            }
        }
        return null;
    }

    private static List<PuzzleState> generateNeighbors(PuzzleState currentState) {
        List<PuzzleState> neighbors = new ArrayList<>();
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Possible directions: up, down, left, right

        for (int i = 0; i < currentState.state.length; i++) {
            for (int j = 0; j < currentState.state[i].length; j++) {
                if (currentState.state[i][j] == 0) {
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        if (ni >= 0 && ni < currentState.state.length && nj >= 0 && nj < currentState.state[i].length) {
                            int[][] newState = cloneState(currentState.state);
                            newState[i][j] = currentState.state[ni][nj];
                            newState[ni][nj] = 0;
                            neighbors.add(new PuzzleState(newState));
                        }
                    }
                }
            }
        }
        return neighbors;
    }

    private static List<int[][]> reconstructPath(Map<PuzzleState, PuzzleState> parent, PuzzleState goalState) {
        List<int[][]> path = new ArrayList<>();
        PuzzleState currentState = goalState;
        while (currentState != null) {
            path.add(0, currentState.state);
            currentState = parent.get(currentState);
        }
        return path;
    }

    private static int[][] cloneState(int[][] state) {
        int[][] newState = new int[state.length][];
        for (int i = 0; i < state.length; i++) {
            newState[i] = state[i].clone();
        }
        return newState;
    }

    private static void printState(int[][] state) {
        for (int[] row : state) {
            for (int val : row) {
                if (val == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(val + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        initializeStates();

        List<int[][]> solution = bestFirstSearch();
        if (solution != null) {
            System.out.println("Solution found:");
            for (int i = 0; i < solution.size(); i++) {
                int[][] state = solution.get(i);
                System.out.println("Step " + i + ":");
                printState(state);
            }
        } else {
            System.out.println("No solution found.");
        }
    }
}