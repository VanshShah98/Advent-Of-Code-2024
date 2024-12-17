package Day6;

import java.io.*;
import java.util.*;

public class GuradMov {

    // Method to find the guard position (marked as '^')
    public static int[] getGuardPos(char[][] map) {
        int rows = map.length, cols = map[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == '^') {
                    return new int[]{i, j};
                }
            }
        }
        return null; // No guard found
    }

    // Patrol function
    public static PatrolResult patrol(char[][] map, int[] pos, Integer idx) {
        if (pos == null) {
            pos = getGuardPos(map);
        }
        if (idx == null) {
            idx = 0;
        }

        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int rows = map.length, cols = map[0].length;

        Set<String> visited = new HashSet<>();
        Map<String, PositionEntry> visitedEntry = new HashMap<>();
        visited.add(pos[0] + "," + pos[1]);

        while (true) {
            int[] direction = directions[idx];
            int[] nextPos = {pos[0] + direction[0], pos[1] + direction[1]};

            // Check if out of bounds
            if (nextPos[0] < 0 || nextPos[0] >= rows || nextPos[1] < 0 || nextPos[1] >= cols) {
                return new PatrolResult(true, visited, visitedEntry); // Leaving the map
            }

            // Check for obstacle
            if (map[nextPos[0]][nextPos[1]] == '#') {
                idx = (idx + 1) % 4; // Change direction
                continue;
            } else {
                String nextKey = nextPos[0] + "," + nextPos[1];
                visited.add(nextKey);

                // Check for loop detection
                if (!visitedEntry.containsKey(nextKey)) {
                    visitedEntry.put(nextKey, new PositionEntry(pos, idx));
                } else if (visitedEntry.get(nextKey).equals(new PositionEntry(pos, idx))) {
                    return new PatrolResult(false, null, null); // Loop detected
                }

                pos = nextPos; // Move to the next position
            }
        }
    }

    // Part 1 Logic
    public static int part1(List<String> data) {
        char[][] map = dataToMap(data);
        PatrolResult result = patrol(map, null, null);
        return result.visited.size();
    }

    // Part 2 Logic
    public static int part2(List<String> data) {
        char[][] map = dataToMap(data);
        PatrolResult result = patrol(map, null, null);

        int[] guardPos = getGuardPos(map);
        String guardKey = guardPos[0] + "," + guardPos[1];
        result.visited.remove(guardKey); // Avoid guard position

        int loopCount = 0;

        // Create a deep copy of the map
        char[][] mapCopy = deepCopyMap(map);

        // Check each visited position
        for (String visit : result.visited) {
            String[] parts = visit.split(",");
            int vi = Integer.parseInt(parts[0]);
            int vj = Integer.parseInt(parts[1]);

            // Place an obstruction
            mapCopy[vi][vj] = '#';

            int[] pos = result.visitedEntry.get(visit).pos;
            int idx = result.visitedEntry.get(visit).idx;

            PatrolResult resultCopy = patrol(mapCopy, pos, idx);
            if (!resultCopy.isLeaving) {
                loopCount++;
            }

            // Reset the map
            mapCopy[vi][vj] = '.';
        }

        return loopCount;
    }

    // Utility: Convert data to a char matrix
    public static char[][] dataToMap(List<String> data) {
        int rows = data.size();
        int cols = data.get(0).length();
        char[][] map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            map[i] = data.get(i).toCharArray();
        }
        return map;
    }

    // Utility: Deep copy a char matrix
    public static char[][] deepCopyMap(char[][] map) {
        int rows = map.length, cols = map[0].length;
        char[][] copy = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(map[i], cols);
        }
        return copy;
    }

    public static void main(String[] args) {
        try {
            // Read input data
            BufferedReader reader = new BufferedReader(new FileReader("F:\\AdventofCode\\Day6\\input.txt"));
            List<String> data = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.trim());
            }
            reader.close();

            // Compute results
            int resultPart1 = part1(data);
            int resultPart2 = part2(data);

            // Print results
            System.out.println("Part 1 Result: " + resultPart1);
            System.out.println("Part 2 Result: " + resultPart2);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

// Helper class to store position and index
class PositionEntry {
    int[] pos;
    int idx;

    PositionEntry(int[] pos, int idx) {
        this.pos = pos;
        this.idx = idx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionEntry)) return false;
        PositionEntry that = (PositionEntry) o;
        return Arrays.equals(pos, that.pos) && idx == that.idx;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(pos), idx);
    }
}

// Helper class for patrol results
class PatrolResult {
    boolean isLeaving;
    Set<String> visited;
    Map<String, PositionEntry> visitedEntry;

    PatrolResult(boolean isLeaving, Set<String> visited, Map<String, PositionEntry> visitedEntry) {
        this.isLeaving = isLeaving;
        this.visited = visited;
        this.visitedEntry = visitedEntry;
    }
}
