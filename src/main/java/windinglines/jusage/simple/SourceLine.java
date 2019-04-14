package windinglines.jusage.simple;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


/**
 * Hold information about a line in a SourceFile. The intent is to contain just a subset of the lines.
 */
public class SourceLine {
  String _content;
  int _start;

  public SourceLine(String content, int start) {
    _content = content;
    _start = start;
  }

  public String getContent() {
    return _content;
  }

  public int getStart() {
    return _start;
  }

  public JsonElement serialize() {
    final JsonObject object = new JsonObject();
    object.addProperty("content", _content);
    object.addProperty("start", _start);
    object.addProperty("kind", "package-import");
    return object;
  }
}
