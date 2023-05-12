import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GraphBuilder {
    public static Graph<String> buildGraphFromWildcardMap(Map<String, List<String>> wordsByWildcard) {
        Graph<String> graph = new Graph<>();

        Collection<List<String>> wildcardValues = wordsByWildcard.values();

        for (List<String> neighbouringWords : wildcardValues) {
            for (String word : neighbouringWords) {
                graph.addNode(word);
            }
            for (int i = 0; i < neighbouringWords.size() - 1; i++) {
                String a = neighbouringWords.get(i);
                for (int j = i + 1; j < neighbouringWords.size(); j++) {
                    String b = neighbouringWords.get(j);
                    graph.addEdgeTwo(a, b, true);
                }
            }
        }

        return graph;
    }

    static class Graph<T> {

        private final Map<T, List<T>> adjacencyList;

        public Graph() {
            this.adjacencyList = new HashMap<>();
        }

        public void addNode(T node) {
            if (adjacencyList.containsKey(node)) {
                return;
            }
            adjacencyList.put(node, new LinkedList<>());
        }

        public void addEdge(T a, T b) {
            adjacencyList.get(a).add(b);
            adjacencyList.get(b).add(a);
        }

        public void addEdgeTwo(T a,T b, boolean bidirectional){
            if(adjacencyList.containsKey(a)){
                addNode(a);
            }
            if(adjacencyList.containsKey(b)){
                addNode(b);
            }
            if(bidirectional){
                adjacencyList.get(b).add(a);
            }
        }

        public List<T> getNeighbours(T node) {
            return adjacencyList.get(node);
        }

        public void countVertices(){
            System.out.println("Total number of vertices: " + adjacencyList.keySet().size());
        }
    }
}