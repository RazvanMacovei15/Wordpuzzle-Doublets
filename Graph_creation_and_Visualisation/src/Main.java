import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> words = WordProcessor.readWordsFromFile("src/words_alpha.txt");
        Map<String, List<String>> wordsByWildcard = WordProcessor.groupWordsByWildcard(words);
        GraphBuilder.Graph<String> graph = GraphBuilder.buildGraphFromWildcardMap(wordsByWildcard);
        List<String> path = PathFinder.findShortestPath(graph, "same", "cast");
        if (path == null) {
            System.out.println("Not found");
        } else {
            System.out.println("Path found: " + String.join(", ", path));
        }
        graph.countVertices();
    }
}