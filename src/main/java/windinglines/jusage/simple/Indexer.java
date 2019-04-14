package windinglines.jusage.simple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Index all the projects, i.e. subfolders of the passed in folder.
 */
public class Indexer {
  ArrayList<Project> _projects = new ArrayList<>();
  Path _baseFolder;
  Path _dbPath;
  String _packageName;

  public Indexer(String basename, String packageName) {
    _baseFolder = Paths.get(basename);
    _dbPath = _baseFolder.resolve("jusage-reference.json").normalize().toAbsolutePath();
    _packageName = packageName;
  }

  public Path getDbPath() {
    return _dbPath;
  }

  /**
   * Index all the sub-folders of the _baseFolder and look for _packageName.
   *
   */
  public void build() throws IOException {

    try (DirectoryStream<Path> folders = Files.newDirectoryStream(_baseFolder)) {
      for (Path folder : folders) {
        Project project = new Project(folder);
        project.index(_packageName);
        if (!project._matchingFiles.isEmpty()) {
          _projects.add(project);
        }
      }
    }
  }

  /**
   * Output the index with the current matches.
   *
   * Note: the serialization format is limited by libraries that can run
   * in native mode. For example I am not aware of any jdbc implementation.
   */
  public void write() {
    try (Writer writer = Files.newBufferedWriter(_dbPath)) {
      GsonBuilder builder = new GsonBuilder();
      builder.setPrettyPrinting();
      builder.registerTypeAdapter(Project.class,
          (JsonSerializer<Project>) (src, typeOfSrc, context) -> src.serialize());
      Gson _gson = builder.create();
      writer.write(_gson.toJson(_projects));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
