import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DistanceCalculator {
    public static Map<String, List<String>> constructGraph(String filename) throws IOException {
        Map<String, List<String>> graph = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("->");
                String node = parts[0].trim();
                String[] connections = parts[1].split(",");
                for (int i = 0; i < connections.length; i++) {
                    connections[i] = connections[i].trim(); // to trim leading and trailing spaces between nodes
                }
                graph.put(node, Arrays.asList(connections));
            }
        }
        return graph;
    }

    public static int calculateDistance(Map<String, List<String>> graph, String start, String end) {
        HashSet<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distances = new HashMap<>();

        queue.add(start);
        int count = 0;
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            String node = queue.poll();
            if (node.equals(end)) {
                return distances.get(node);
            }

            if (graph.containsKey(node)) {
                for (int i = 0; i < graph.get(node).size(); i++) {
                    String neighbor =  graph.get(node).get(i);
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                        distances.put(neighbor, distances.get(node) + 1);
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        String filename = args[0];
        String startNode = args[1];
        String endNode = args[2];

        try {
            Map<String, List<String>> graph = constructGraph(filename);
            int distance = -1;
            if (graph.containsKey(startNode)) {
            distance = calculateDistance(graph, startNode, endNode);
            }
            else {
                distance = calculateDistance(graph, endNode, startNode);
            }
            if (distance == -1) {
                System.out.println("Nodes are not connected.");
            } else {
                System.out.println(distance);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
