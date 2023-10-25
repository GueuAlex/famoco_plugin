import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'famoco_finger_print_method_channel.dart';

abstract class FamocoFingerPrintPlatform extends PlatformInterface {
  /// Constructs a FamocoFingerPrintPlatform.
  FamocoFingerPrintPlatform() : super(token: _token);

  static final Object _token = Object();

  static FamocoFingerPrintPlatform _instance = MethodChannelFamocoFingerPrint();

  /// The default instance of [FamocoFingerPrintPlatform] to use.
  ///
  /// Defaults to [MethodChannelFamocoFingerPrint].
  static FamocoFingerPrintPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FamocoFingerPrintPlatform] when
  /// they register themselves.
  static set instance(FamocoFingerPrintPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
