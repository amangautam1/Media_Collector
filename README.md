# media_collector
A flutter plugin to get all images and videos from storage.
**Supports Android only**

# Usage
Add this to pubspec.yaml
```dart
dependencies:
 media_collector: ^0.0.1
```
Import the followings.
```dart
import  'package:media_collector/media_collector.dart';
import  'package:media_collector/model/image.dart';
import  'package:media_collector/model/video.dart';
```

And get images and videos as below,
```dart
List<Images> images =  await MediaCollector.getImages;
List<Videos> videos =  await MediaCollector.getVideos;
```
The output will be list of Images/Videos objects. The structure of both type of objects is as follows,
```dart
String fileName;
String path;
double size;  //in KB
DateTime lastModified;
String directory;
String extention;
``` 


