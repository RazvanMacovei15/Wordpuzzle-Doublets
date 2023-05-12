import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordProcessor {
    public static List<String> readWordsFromFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static Map<String, List<String>> groupWordsByWildcard(List<String> words) {
        Map<String, List<String>> neighbouringNodesOfWildcard = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                String wildCard = word.substring(0, i) + "*" + word.substring(i + 1);
                if (!neighbouringNodesOfWildcard.containsKey(wildCard)) {
                    neighbouringNodesOfWildcard.put(wildCard, new ArrayList<>());
                }
                List<String> nodesForWildcard = neighbouringNodesOfWildcard.get(wildCard);
                nodesForWildcard.add(word);
            }
        }
        return neighbouringNodesOfWildcard;
    }
}
