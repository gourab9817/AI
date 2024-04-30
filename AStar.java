import java.util.*;

public class AStar {

    public static void main(String[] args) {
        int n = 3;
        int[][] initialState = { { 2, 8, 3 }, { 1, 6, 4 }, { 7, 0, 5 } };
        int[][] goalState = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5 } };

        List<State> solution = solve(initialState, goalState, n);

        if (solution != null) {
            System.out.println("Solution found:");
            for (State state : solution) {
                state.printState();
                System.out.println();
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    public static List<State> solve(int[][] initialState, int[][] goalState, int n) {
        PriorityQueue<State> openSet = new PriorityQueue<>();
        Set<State> closedSet = new HashSet<>();

        State startState = new State(initialState, 0, null, n);
        openSet.add(startState);

        while (!openSet.isEmpty()) {
            State currentState = openSet.poll();

            if (currentState.equals(new State(goalState, 0, null, n))) {
                return backtrack(currentState);
            }

            closedSet.add(currentState);

            for (State neighbor : currentState.generateNeighbors()) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGScore = currentState.gScore + 1;

                if (!openSet.contains(neighbor) || tentativeGScore < neighbor.gScore) {
                    neighbor.gScore = tentativeGScore;
                    neighbor.parent = currentState;
                    neighbor.hScore = calculateHeuristic(neighbor.state, goalState, n);
                    openSet.add(neighbor);
                }
            }
        }

        return null;
    }

    private static List<State> backtrack(State state) {
        List<State> path = new ArrayList<>();
        path.add(state);

        while (state.parent != null) {
            state = state.parent;
            path.add(state);
        }

        Collections.reverse(path);
        return path;
    }

    private static int calculateHeuristic(int[][] state, int[][] goalState, int n) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (state[i][j] != 0 && state[i][j] != goalState[i][j]) {
                    count += calculateManhattanDistance(state[i][j], goalState, n);
                }
            }
        }
        return count;
    }

    private static int calculateManhattanDistance(int value, int[][] goalState, int n) {
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (goalState[i][j] == value) {
                    minDistance = Math.min(minDistance, Math.abs(i - (value - 1) / n) + Math.abs(j - (value - 1) % n));
                }
            }
        }
        return minDistance;
    }

    private static class State implements Comparable<State> {
        private final int[][] state;
        private int gScore;
        private int hScore;
        private State parent;
        private final int n;

        public State(int[][] state, int gScore, State parent, int n) {
            this.state = state;
            this.gScore = gScore;
            this.hScore = 0;
            this.parent = parent;
            this.n = n;
        }

        public List<State> generateNeighbors() {
            List<State> neighbors = new ArrayList<>();
            int emptyRow = 0;
            int emptyCol = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (state[i][j] == 0) {
                        emptyRow = i;
                        emptyCol = j;
                        break;
                    }
                }
            }

            for (int[] dir : new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }) {
                int newRow = emptyRow + dir[0];
                int newCol = emptyCol + dir[1];
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n) {
                    int[][] newState = copyState(state);
                    swap(newState, newRow * n + newCol, emptyRow * n + emptyCol);
                    neighbors.add(new State(newState, gScore + 1, this, n));
                }
            }

            return neighbors;
        }

        private void swap(int[][] state, int i, int j) {
            int temp = state[i / n][i % n];
            state[i / n][i % n] = state[j / n][j % n];
            state[j / n][j % n] = temp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof State))
                return false;
            State state1 = (State) o;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (state[i][j] != state1.state[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(gScore + hScore, other.gScore + other.hScore);
        }

        public void printState() {
            for (int[] row : state) {
                for (int cell : row) {
                    System.out.print(cell + " ");
                }
                System.out.println();
            }
        }
    }

    private static int[][] copyState(int[][] state) {
        int n = state.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(state[i], 0, copy[i], 0, n);
        }
        return copy;
    }
}
