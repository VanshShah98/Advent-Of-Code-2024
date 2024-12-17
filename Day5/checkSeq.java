package Day5;

import java.io.*;
import java.util.*;

public class checkSeq {

    // Extract data into rules and updates
    public static Map<Integer, Set<Integer>> extractRules(List<String> data) {
        Map<Integer, Set<Integer>> rules = new HashMap<>();

        int separatorIndex = data.indexOf(""); // Find the empty line separating rules and updates

        for (int i = 0; i < separatorIndex; i++) {
            String[] parts = data.get(i).split("\\|");
            int key = Integer.parseInt(parts[0]);
            int value = Integer.parseInt(parts[1]);
            rules.putIfAbsent(key, new HashSet<>());
            rules.get(key).add(value);
        }

        return rules;
    }

    public static List<List<Integer>> extractUpdates(List<String> data) {
        List<List<Integer>> updates = new ArrayList<>();

        int separatorIndex = data.indexOf("");

        for (int i = separatorIndex + 1; i < data.size(); i++) {
            String[] parts = data.get(i).split(",");
            List<Integer> update = new ArrayList<>();
            for (String part : parts) {
                update.add(Integer.parseInt(part));
            }
            updates.add(update);
        }

        return updates;
    }

    
    public static boolean isValid(Map<Integer, Set<Integer>> rules, List<Integer> update) {
        for (int i = 0; i < update.size(); i++) {
            for (int j = i + 1; j < update.size(); j++) {
                if (!rules.getOrDefault(update.get(i), Collections.emptySet()).contains(update.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Fix invalid updates
    public static List<Integer> fixUpdate(Map<Integer, Set<Integer>> rules, List<Integer> update) {
        Map<Integer, Set<Integer>> filteredRules = new HashMap<>();
        for (Integer key : update) {
            filteredRules.put(key, new HashSet<>(rules.getOrDefault(key, Collections.emptySet())));
            filteredRules.get(key).retainAll(update);
        }

        List<Integer> orderedKeys = new ArrayList<>(filteredRules.keySet());
        orderedKeys.sort((a, b) -> filteredRules.get(b).size() - filteredRules.get(a).size());

        return orderedKeys;
    }

    // Part 1 Logic
    public static int part1(List<String> data) {
        Map<Integer, Set<Integer>> rules = extractRules(data);
        List<List<Integer>> updates = extractUpdates(data);

        int sum = 0;
        for (List<Integer> update : updates) {
            if (isValid(rules, update)) {
                sum += update.get(update.size() / 2);
            }
        }
        return sum;
    }

    // Part 2 Logic
    public static int part2(List<String> data) {
        Map<Integer, Set<Integer>> rules = extractRules(data);
        List<List<Integer>> updates = extractUpdates(data);

        int sum = 0;
        for (List<Integer> update : updates) {
            if (!isValid(rules, update)) {
                List<Integer> fixedUpdate = fixUpdate(rules, update);
                sum += fixedUpdate.get(update.size() / 2);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        try {
            // Read input data
            BufferedReader reader = new BufferedReader(new FileReader("F:\\AdventofCode\\Day5\\input.txt"));
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
