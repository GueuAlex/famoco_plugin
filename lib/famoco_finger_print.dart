import 'package:flutter/services.dart';

class FamocoFingerPrintPlugin {
  static const MethodChannel _channel = MethodChannel('famoco_finger_print');

  static Future<String> getStringFromPlugin() async {
    try {
      final String fingerprintData =
          await _channel.invokeMethod('captureFingerprint');
      return fingerprintData;
    } catch (e) {
      return 'Erreur lors de l\'obtention des données d\'empreintes: $e';
    }
  }
}
