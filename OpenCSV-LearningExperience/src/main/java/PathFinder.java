import java.util.*;

public class PathFinder {
    public static List<String> findShortestPath(GraphBuilder.Graph<String> graph, String start, String end) {
        Queue<String> toVisitQueue = new LinkedList<>();
        Set<String> visitedNodes = new HashSet<>();
        Map<String, List<String>> paths = new HashMap<>();
        paths.put(start, List.of(start));
        toVisitQueue.add(start);
        visitedNodes.add(start);

        while (!toVisitQueue.isEmpty()) {
            String node = toVisitQueue.remove();
            if (node.equals(end)) {
                return paths.get(node);
            }
            List<String> neighbours = graph.getNeighbours(node);
            for (String neighbour : neighbours) {
                if (visitedNodes.contains(neighbour)) {
                    continue;
                }
                visitedNodes.add(neighbour);
                toVisitQueue.add(neighbour);
                List<String> previousPath = new ArrayList<>(paths.get(node));
                previousPath.add(neighbour);
                paths.put(neighbour, previousPath);
            }
        }
        return Collections.emptyList();
    }
}
