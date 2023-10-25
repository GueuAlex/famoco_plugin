import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:famoco_finger_print/famoco_finger_print_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelFamocoFingerPrint platform = MethodChannelFamocoFingerPrint();
  const MethodChannel channel = MethodChannel('famoco_finger_print');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
