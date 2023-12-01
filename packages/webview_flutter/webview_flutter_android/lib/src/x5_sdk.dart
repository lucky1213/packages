// ignore_for_file: avoid_classes_with_only_static_members, public_member_api_docs

import 'package:flutter/services.dart';

class X5Sdk {
  static const MethodChannel _channel = MethodChannel('webview_flutter_x5');

  static Future<bool> init() async {
    final bool result = await _channel.invokeMethod<bool>('init') ?? false;
    return result;
  }

  static Future<void> reset() {
    return _channel.invokeMethod<void>('reset');
  }

  static Future<bool> isX5WebViewAvailable() async {
    final bool result = await _channel.invokeMethod<bool>('isX5Core') ?? false;
    return result;
  }

  static Future<Map<String, dynamic>?> getX5InitInfo() async {
    final Map<Object?, Object?>? result =
        await _channel.invokeMethod<Map<Object?, Object?>>('getX5InitInfo');
    return result == null ? null : Map<String, dynamic>.from(result);
  }
}
