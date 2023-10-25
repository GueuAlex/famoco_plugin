/* import 'package:flutter_test/flutter_test.dart';
import 'package:famoco_finger_print/famoco_finger_print.dart';
import 'package:famoco_finger_print/famoco_finger_print_platform_interface.dart';
import 'package:famoco_finger_print/famoco_finger_print_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFamocoFingerPrintPlatform
    with MockPlatformInterfaceMixin
    implements FamocoFingerPrintPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FamocoFingerPrintPlatform initialPlatform = FamocoFingerPrintPlatform.instance;

  test('$MethodChannelFamocoFingerPrint is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFamocoFingerPrint>());
  });

  test('getPlatformVersion', () async {
    FamocoFingerPrint famocoFingerPrintPlugin = FamocoFingerPrint();
    MockFamocoFingerPrintPlatform fakePlatform = MockFamocoFingerPrintPlatform();
    FamocoFingerPrintPlatform.instance = fakePlatform;

    expect(await famocoFingerPrintPlugin.getPlatformVersion(), '42');
  });
}
 */