import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:media_collector/media_collector.dart';

void main() {
  const MethodChannel channel = MethodChannel('media_collector');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await MediaCollector.platformVersion, '42');
  });
}
