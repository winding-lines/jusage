package windinglines.jusage.simple;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Represent one file in the project.
 */
public class SourceFile {
  Path _path;
  List<SourceLine> _matchingLines;

  public SourceFile(Path path, List<SourceLine> matchingLines) {
    _path = path;
    _matchingLines = matchingLines;
  }

  /**
   * Explicit serialize so that we don't require reflection.
   *
   * @return
   */
  public JsonObject serialize() {
    JsonObject out = new JsonObject();
    out.addProperty("path", _path.toString());
    final JsonArray array = new JsonArray();
    for (SourceLine l : _matchingLines) {
      array.add(l.serialize());
    }
    out.add("lines", array);
    return out;
  }

  /**
   * Track the current position in the file.
   */
  static class SourceTracker {
    public int position = 0;
  }

  /**
   * Find lines matching the desired package.
   *
   * @param file to process
   * @param packageName to match
   * @return None if no files match or an Optional SourceFile if there are matches.
   * @throws IOException
   */
  public static Optional<SourceFile> index(Path file, String packageName) throws IOException {

    try (Stream<String> stream = Files.lines(file)) {
      final SourceTracker acc = new SourceTracker();

      // get matching lines and track the start position for every one of them
      final List<SourceLine> matching = stream.map(line -> {
        int start = acc.position;

        // SIDE EFFECT
        acc.position += line.length();

        return new SourceLine(line, start);
      }).filter(line -> line._content.contains(packageName)).collect(Collectors.toList());
      if (!matching.isEmpty()) {
        return Optional.of(new SourceFile(file, matching));
      } else {
        return Optional.empty();
      }
    }
  }
}
