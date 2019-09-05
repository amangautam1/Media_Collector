import 'dart:io';
import 'package:path/path.dart' as p;

class Videos {
  String fileName;
  String path;
  double size;
  DateTime lastModified;
  String directory;
  String extention;
  Videos(this.fileName, this.path, this.directory, this.size, this.extention,
      this.lastModified);

  static Videos fromUri(String uri) {
    File file = File(uri);
    String fileName = p.basename(uri);
    String path = uri;
    double size = file.lengthSync() / 1024;
    DateTime lastModified = file.lastModifiedSync();
    String directory = p.dirname(uri);
    String extention = p.extension(uri);
    return Videos(fileName, path, directory, size, extention, lastModified);
  }
}
