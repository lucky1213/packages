package io.flutter.plugins.webviewflutter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;

import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.util.HashMap;

import io.flutter.Log;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class X5WebViewFlutterPlugin implements MethodChannel.MethodCallHandler {
    private static final String CHANNEL_NAME = "webview_flutter_x5";
    private MethodChannel channel;

    private Context context;

    private Activity activity;

    X5WebViewFlutterPlugin() {
    }

    public void updateActivity(Activity a) {
        activity = a;
        context = a.getApplicationContext();
    }

    public void setUp(BinaryMessenger messenger) {
        channel = new MethodChannel(messenger, CHANNEL_NAME);
        channel.setMethodCallHandler(this);
    }

    public void destroy() {
        channel.setMethodCallHandler(null);
        channel = null;
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "init":
                init(result);
                break;
            case "isX5Core":
                result.success(QbSdk.isX5Core());
                break;
            case "reset":
                QbSdk.reset(context);
                result.success(true);
                break;
            case "getX5InitInfo":

                // 当前进程内，SDK是否使用的X5内核，不触发加载、只关注当前状态
                final boolean b = QbSdk.isX5Core();
                final boolean c = QbSdk.getIsInitX5Environment();
                final boolean e = QbSdk.isTbsCoreInited();
                final int d = QbSdk.getTbsVersion(context);

                HashMap<String, Object> map = new HashMap<>();
                map.put("tbs_version", d);
                map.put("is_x5_core", b);
                map.put("is_tbs_core_inited", e);
                Log.e("x5SDK", "getTbsVersion ==>"+d);
                Log.e("x5SDK", "getIsInitX5Environment ==>"+c);
                Log.e("x5SDK", "isX5Core ==>"+b);
                Log.e("x5SDK", "isTbsCoreInited ==>"+e);
                result.success(map);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 29) {
            return true;
        }
        final String[] permission = {Permission.WRITE_EXTERNAL_STORAGE};
        return XXPermissions.isGranted(context,permission);
    }

    private void init(MethodChannel.Result result) {
        // 是否是x5
        final boolean isX5Core = QbSdk.isX5Core();
        final int version = QbSdk.getTbsVersion(context);
        if (isX5Core && version > 0) {
            result.success(true);
            return;
        }
        Log.e("x5SDK", "准备初始化");
        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        // 非wifi下下载tbs设置接口，默认为false不可下载，设置为true 应用层必须提示用户 流量资费
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initTbsSettings(map);

        TbsListener listener = new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.e("X5Sdk","onDownloadFinish"+i);
                activity.runOnUiThread(() -> {
                    channel.invokeMethod("onDownloadFinish", i);
                });
            }

            @Override
            public void onInstallFinish(int i) {
                Log.e("x5SDK","onInstallFinish"+i);
                activity.runOnUiThread(() -> {
                    channel.invokeMethod("onInstallFinish", i);
                });
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.e("x5SDK","onDownloadProgress"+i);
                activity.runOnUiThread(() -> {
                    channel.invokeMethod("onInstallFinish", i);
                });
            }
        };

        QbSdk.setTbsListener(listener);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("x5SDK", "onViewInitFinished:" + arg0);
                result.success(arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.e("x5SDK", "onCoreInitFinished");
            }
        };
        QbSdk.initX5Environment(context, cb);
    }
}
