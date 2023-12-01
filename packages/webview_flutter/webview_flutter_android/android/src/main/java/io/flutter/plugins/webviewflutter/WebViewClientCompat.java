package io.flutter.plugins.webviewflutter;

import com.tencent.smtt.sdk.WebViewClient;


/**
 * Compatibility version of {@link com.tencent.smtt.sdk.WebViewClient}.
 */
// Note: some methods are marked as RequiresApi 21, because only an up-to-date WebView APK would
// ever invoke these methods (and WebView can only be updated on Lollipop and above). The app can
// still construct a WebViewClientCompat on a pre-Lollipop devices, and explicitly invoke these
// methods, so each of these methods must also handle this case.
@SuppressWarnings("HiddenSuperclass")
public class WebViewClientCompat extends WebViewClient {
}
