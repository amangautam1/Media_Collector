import 'package:flutter/material.dart';
import 'dart:async';
import 'package:media_collector/media_collector.dart';
import 'package:media_collector/model/image.dart';
import 'package:media_collector/model/video.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<Images> _images;
  List<Videos> _videos;
  bool isLoading = true;
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    List<Images> images;
    List<Videos> videos;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      images = await MediaCollector.getImages;
      videos = await MediaCollector.getVideos;
    
    } catch(e)  {

      print(e); 

    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _images = images;
      _videos = videos;
      isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
            child: isLoading
                ? CircularProgressIndicator()
                : Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Text("Total ${_images.length} images found."),
                      Text("Total ${_videos.length} videos found.")
                    ],
                  )),
      ),
    );
  }
}
