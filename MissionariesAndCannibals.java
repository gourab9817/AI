import java.util.*;

class State {
    int m; // Number of missionaries on the left bank
    int c; // Number of cannibals on the left bank
    boolean boat; // true if the boat is on the left bank, false otherwise

    State(int m, int c, boolean boat) {
        this.m = m;
        this.c = c;
        this.boat = boat;
    }

    // Override equals and hashCode for State comparison in HashSet
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        State state = (State) o;
        return m == state.m && c == state.c && boat == state.boat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, c, boat);
    }
}

public class MissionariesAndCannibals {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of missionaries: ");
        int missionaries = scanner.nextInt();
        System.out.print("Enter the number of cannibals: ");
        int cannibals = scanner.nextInt();

        solve(missionaries, cannibals);
    }

    public static void solve(int missionaries, int cannibals) {
        Queue<State> queue = new LinkedList<>();
        HashSet<State> visited = new HashSet<>();
        HashMap<State, State> parent = new HashMap<>();

        State initialState = new State(missionaries, cannibals, true);
        queue.add(initialState);
        visited.add(initialState);
        parent.put(initialState, null);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (currentState.m == 0 && currentState.c == 0 && !currentState.boat) {
                printSolution(currentState, parent);
                break;
            }

            for (State nextState : generateSuccessors(currentState)) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                    parent.put(nextState, currentState);
                }
            }
        }
    }

    public static List<State> generateSuccessors(State currentState) {
        List<State> successors = new ArrayList<>();

        // Generate all possible valid moves
        int[][] moves = { { 1, 0 }, { 2, 0 }, { 0, 1 }, { 0, 2 }, { 1, 1 } };

        for (int[] move : moves) {
            int mDelta = currentState.boat ? -move[0] : move[0];
            int cDelta = currentState.boat ? -move[1] : move[1];

            // Ensure the number of missionaries and cannibals on both banks is valid
            if (isValid(currentState.m + mDelta, currentState.c + cDelta) &&
                    isValid(3 - currentState.m - mDelta, 3 - currentState.c - cDelta)) {
                successors.add(new State(currentState.m + mDelta, currentState.c + cDelta, !currentState.boat));
            }
        }

        return successors;
    }

    public static boolean isValid(int m, int c) {
        // Ensure no more cannibals than missionaries on both banks
        return m >= 0 && m <= 3 && c >= 0 && c <= 3 && (m == 0 || m >= c);
    }

    public static void printSolution(State finalState, HashMap<State, State> parent) {
        Stack<State> path = new Stack<>();
        State currentState = finalState;

        while (currentState != null) {
            path.push(currentState);
            currentState = parent.get(currentState);
        }

        while (!path.isEmpty()) {
            State state = path.pop();
            System.out.println("(" + state.m + "M, " + state.c + "C, " + (state.boat ? "BLEFT" : "BRIGHT") + ")");
        }
    }
}
