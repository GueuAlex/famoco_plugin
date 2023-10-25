import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'famoco_finger_print_platform_interface.dart';

/// An implementation of [FamocoFingerPrintPlatform] that uses method channels.
class MethodChannelFamocoFingerPrint extends FamocoFingerPrintPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('famoco_finger_print');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
