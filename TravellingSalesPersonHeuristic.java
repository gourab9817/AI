
import java.util.Arrays;

public class TravellingSalesPersonHeuristic {

    static int[][] distances = {
        {0, 12, 6, 8, 15},
        {12, 0, 20, 12, 10},
        {6, 20, 0, 15, 30},
        {8, 12, 15, 0, 8},
        {15, 10, 30, 8, 0}
    };

    public static void main(String[] args) {
        int numCities = distances.length;
        boolean[] visited = new boolean[numCities];
        int[] path = new int[numCities];
        int totalDistance = 0;

        // Start from city A (index 0)
        int currentCity = 0;
        visited[currentCity] = true;
        path[0] = currentCity;

        for (int i = 1; i < numCities; i++) {
            int nextCity = -1;
            int minDist = Integer.MAX_VALUE;

            for (int j = 0; j < numCities; j++) {
                if (!visited[j] && distances[currentCity][j] < minDist) {
                    minDist = distances[currentCity][j];
                    nextCity = j;
                }
            }

            visited[nextCity] = true;
            path[i] = nextCity;
            totalDistance += minDist;
            currentCity = nextCity;
        }

        // Return to the starting city
        totalDistance += distances[currentCity][0];

        System.out.println("Optimal path: " + Arrays.toString(path));
        System.out.println("Total distance: " + totalDistance + " km");
    }
}
