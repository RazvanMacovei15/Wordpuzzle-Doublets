import java.util.*;

public class GraphBuilder {
    public static Graph<String> buildGraphFromWildcardMap(Map<String, List<String>> nodesByWildcard) {
        Graph<String> graph = new Graph<>();

        Collection<List<String>> wildcardValues = nodesByWildcard.values();

        for (List<String> neighbouringWords : wildcardValues) {
            for (String word : neighbouringWords) {
                graph.addNode(word);
            }
            for (int i = 0; i < neighbouringWords.size() - 1; i++) {
                String a = neighbouringWords.get(i);
                for (int j = i + 1; j < neighbouringWords.size(); j++) {
                    String b = neighbouringWords.get(j);
                    graph.addEdge(a, b);
                }
            }
        }
        return graph;
    }
    static class Graph<T> {

        private final Map<T, List<T>> adjacencyList;

        //constructor for the Graph<T>
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

        public void addEdge_Two(T a, T b, boolean bidirectional){
            if(adjacencyList.containsKey(a)){
                addNode(a);
            }
            if(adjacencyList.containsKey(b)){
                addNode(b);
            }
            if(bidirectional){
                adjacencyList.get(b).add(a);
                adjacencyList.get(a).add(b);
            }
        }

        public List<T> getNeighbours(T node) {
            return adjacencyList.get(node);
        }

        public void countVertices(){
            System.out.println("Total number of vertices: " + adjacencyList.keySet().size());
        }

        public void countEdges(boolean bidirectional){
            double count =0;
            for(T v:adjacencyList.keySet()){
                count += adjacencyList.get(v).size();
            }
            if(bidirectional){
                count = count/2;
            }
            System.out.println("total number of edges: " + count);
        }

        public void containsVertex(T node){
            if (adjacencyList.containsKey(node)){
                System.out.println("the graph contains '" + node + "' as a vertex");
            } else {
                System.out.println("the graph does not contain '" + node + "' as a vertex" );
            }
        }

        public void containsEdge(T node1, T node2){
            if(adjacencyList.get(node1).contains(node2)){
                System.out.println("The graph has an edge between "+ node1 + " and " + node2 + ".");
            }else{
                System.out.println("There is no edge between "+ node1 + " and " + node2 + ".");
            }
        }
    }
}