// ignore_for_file:  sort_constructors_first
// ignore_for_file: avoid_classes_with_only_static_members, public_member_api_docs

import 'package:flutter/services.dart';

class X5Sdk {
  static const MethodChannel _channel = MethodChannel('webview_flutter_x5');

  static Future<bool> init() async {
    final bool result = await _channel.invokeMethod<bool>('init') ?? false;
    return result;
  }

  ///设置内核下载安装事件
  static void setX5SdkListener(X5SdkListener listener) {
    _channel.setMethodCallHandler((MethodCall call) async {
      switch (call.method) {
        case 'onDownloadFinish':
          listener.onDownloadFinish(call.arguments as int);
          break;
        case 'onDownloadProgress':
          listener.onDownloadProgress(call.arguments as int);
          break;
        default:
          throw MissingPluginException(
              '${call.method} was invoked but has no handler');
      }
    });
  }

  static Future<void> reset() {
    return _channel.invokeMethod<void>('reset');
  }

  static Future<bool> isX5WebViewAvailable() async {
    final bool result = await _channel.invokeMethod<bool>('isX5Core') ?? false;
    return result;
  }

  static Future<X5CoreVersion> getX5CoreVersion() async {
    final Map<Object?, Object?>? result =
        await _channel.invokeMethod<Map<Object?, Object?>>('getX5CoreVersion');
    if (result == null) {
      throw PlatformException(code: '10001', message: '获取内核信息失败');
    }
    return X5CoreVersion.fromMap(Map<String, dynamic>.from(result));
  }
}

typedef DownloadFinish = void Function(int code);
typedef DownloadProgress = void Function(int progress);

typedef InterceptUrlCallBack = void Function(
    String url, Map<String, String> headers);

///X5内核的下载和安装监听
///
//int	DOWNLOAD_CANCEL_NOT_WIFI	111，非Wi-Fi，不发起下载 setDownloadWithoutWifi(boolean) 进行设置
// int	DOWNLOAD_CANCEL_REQUESTING	133，下载请求中，不重复发起，取消下载
// int	DOWNLOAD_FLOW_CANCEL	-134，带宽不允许，下载取消。Debug阶段可webview访问 debugtbs.qq.com 安装线上内核
// int	DOWNLOAD_NO_NEED_REQUEST	-122，不发起下载请求，以下触发请求的条件均不符合：
// 1、距离最后请求时间24小时后（可调整系统时间）
// 2、请求成功超过时间间隔，网络原因重试小于11次
// 3、App版本变更
// int	DOWNLOAD_SUCCESS	100，内核下载成功
// int	INSTALL_FOR_PREINIT_CALLBACK	243，预加载中间态，非异常，可忽略
// int	INSTALL_SUCCESS	200，首次安装成功
// int	NETWORK_UNAVAILABLE	101，网络不可用
// int	STARTDOWNLOAD_OUT_OF_MAXTIME	127，发起下载次数超过1次（一次进程只允许发起一次下载）
class X5SdkListener {
  X5SdkListener({
    required this.onDownloadFinish,
    required this.onDownloadProgress,
  });

  ///下载完成监听
  DownloadFinish onDownloadFinish;

  ///下载进度监听
  DownloadProgress onDownloadProgress;
}

class X5CoreVersion {
  int sdkVersion;
  int coreVersion;
  bool isX5Core;
  X5CoreVersion({
    required this.sdkVersion,
    required this.coreVersion,
    required this.isX5Core,
  });

  factory X5CoreVersion.fromMap(Map<String, dynamic> map) {
    return X5CoreVersion(
      sdkVersion: map['sdk_version'] as int,
      coreVersion: map['core_version'] as int,
      isX5Core: map['is_x5_core'] as bool,
    );
  }
}
