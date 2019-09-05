import 'dart:async';

import 'package:flutter/services.dart';
import 'package:media_collector/model/video.dart';
import 'model/image.dart';

class MediaCollector {
  static const MethodChannel _channel = const MethodChannel('media_collector');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<List<Images>> get getImages async {
    var result = await _channel.invokeMethod('getImages');
    if (result is String) {
      return null;
    } else {
      var list = result.map((r) => Images.fromUri(r)).toList();
      return List.from(list);
    }
  }

  static Future<List<Videos>> get getVideos async {
    var result = await _channel.invokeMethod('getVideos');

    if (result is String) {
      return null;
    } else {
      var list = result.map((r) => Videos.fromUri(r)).toList();
      return List.from(list);
    }
  }

}
