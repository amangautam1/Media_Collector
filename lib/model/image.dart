import 'dart:io';
import 'package:path/path.dart' as p;

class Images {
  String fileName;
  String path;
  double size;
  DateTime lastModified;
  String directory;
  String extention;
  Images(this.fileName, this.path, this.directory, this.size, this.extention,
      this.lastModified);

  static Images fromUri(String uri) {
    File file = File(uri);
    String fileName = p.basename(uri);
    String path = uri;
    double size = file.lengthSync() / 1024;
    DateTime lastModified = file.lastModifiedSync();
    String directory = p.dirname(uri);
    String extention = p.extension(uri);
    return Images(fileName, path, directory, size, extention, lastModified);
  }
}
