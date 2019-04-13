package windinglines.jusage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


/**
 * Represent a project which may use Quince.
 */
public class Project {
  Path _folder;
  List<SourceFile> _matchingFiles = new ArrayList<>();

  public Project(Path folder) {
    _folder = folder;
  }

  /**
   * Index all the java files in the folder and look for lines matching packageName.
   */
  public void index(String packageName) throws IOException {
    Files.walkFileTree(_folder, new FileVisitor<Path>() {
      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(".java")) {
          SourceFile.index(file, packageName).ifPresent(pf -> _matchingFiles.add(pf));
        }
        return FileVisitResult.CONTINUE;
      }

      @Override
      public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.TERMINATE;
      }

      @Override
      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
      }
    });
  }

  public JsonObject serialize() {
    if (_matchingFiles.isEmpty()) {
      return null;
    }
    JsonObject out = new JsonObject();
    out.addProperty("folder", _folder.toString());
    final JsonArray array = new JsonArray();
    for (SourceFile f : _matchingFiles) {
      array.add(f.serialize());
    }
    out.add("matching", array);
    return out;
  }

}
