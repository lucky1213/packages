// Copyright 2013 The Flutter Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package io.flutter.plugins.webviewflutter;

import android.os.Build;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugins.webviewflutter.GeneratedAndroidWebView.WebViewClientFlutterApi;
import java.util.HashMap;
import java.util.Objects;

/**
 * Flutter Api implementation for {@link WebViewClient}.
 *
 * <p>Passes arguments of callbacks methods from a {@link WebViewClient} to Dart.
 */
public class WebViewClientFlutterApiImpl extends WebViewClientFlutterApi {
  // To ease adding additional methods, this value is added prematurely.
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private final BinaryMessenger binaryMessenger;

  private final InstanceManager instanceManager;
  private final WebViewFlutterApiImpl webViewFlutterApi;

  static GeneratedAndroidWebView.WebResourceErrorData createWebResourceErrorData(
      WebResourceError error) {
    return new GeneratedAndroidWebView.WebResourceErrorData.Builder()
        .setErrorCode((long) error.getErrorCode())
        .setDescription(error.getDescription().toString())
        .build();
  }
  static GeneratedAndroidWebView.WebResourceRequestData createWebResourceRequestData(
      WebResourceRequest request) {
    final GeneratedAndroidWebView.WebResourceRequestData.Builder requestData =
        new GeneratedAndroidWebView.WebResourceRequestData.Builder()
            .setUrl(request.getUrl().toString())
            .setIsForMainFrame(request.isForMainFrame())
            .setHasGesture(request.hasGesture())
            .setMethod(request.getMethod())
            .setRequestHeaders(
                request.getRequestHeaders() != null
                    ? request.getRequestHeaders()
                    : new HashMap<>());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      requestData.setIsRedirect(request.isRedirect());
    }

    return requestData.build();
  }

  /**
   * Creates a Flutter api that sends messages to Dart.
   *
   * @param binaryMessenger handles sending messages to Dart
   * @param instanceManager maintains instances stored to communicate with Dart objects
   */
  public WebViewClientFlutterApiImpl(
      @NonNull BinaryMessenger binaryMessenger, @NonNull InstanceManager instanceManager) {
    super(binaryMessenger);
    this.binaryMessenger = binaryMessenger;
    this.instanceManager = instanceManager;
    webViewFlutterApi = new WebViewFlutterApiImpl(binaryMessenger, instanceManager);
  }

  /** Passes arguments from {@link WebViewClient#onPageStarted} to Dart. */
  public void onPageStarted(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull String urlArg,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    onPageStarted(getIdentifierForClient(webViewClient), webViewIdentifier, urlArg, callback);
  }

  /** Passes arguments from {@link WebViewClient#onPageFinished} to Dart. */
  public void onPageFinished(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull String urlArg,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    onPageFinished(getIdentifierForClient(webViewClient), webViewIdentifier, urlArg, callback);
  }

  /**
   * Passes arguments from {@link WebViewClientCompat#onReceivedError(WebView,
   * WebResourceRequest, WebResourceError)} to Dart.
   */
  public void onReceivedRequestError(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull WebResourceRequest request,
      @NonNull WebResourceError error,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    onReceivedRequestError(
        getIdentifierForClient(webViewClient),
        webViewIdentifier,
        createWebResourceRequestData(request),
        createWebResourceErrorData(error),
        callback);
  }

  /**
   * Passes arguments from {@link WebViewClient#onReceivedError(WebView, int, String, String)} to
   * Dart.
   */
  public void onReceivedError(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull Long errorCodeArg,
      @NonNull String descriptionArg,
      @NonNull String failingUrlArg,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    onReceivedError(
        getIdentifierForClient(webViewClient),
        webViewIdentifier,
        errorCodeArg,
        descriptionArg,
        failingUrlArg,
        callback);
  }

  /**
   * Passes arguments from {@link WebViewClient#shouldOverrideUrlLoading(WebView,
   * WebResourceRequest)} to Dart.
   */
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public void requestLoading(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull WebResourceRequest request,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    requestLoading(
        getIdentifierForClient(webViewClient),
        webViewIdentifier,
        createWebResourceRequestData(request),
        callback);
  }

  /**
   * Passes arguments from {@link WebViewClient#shouldOverrideUrlLoading(WebView, String)} to Dart.
   */
  public void urlLoading(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull String urlArg,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    urlLoading(getIdentifierForClient(webViewClient), webViewIdentifier, urlArg, callback);
  }

  /** Passes arguments from {@link WebViewClient#doUpdateVisitedHistory} to Dart. */
  public void doUpdateVisitedHistory(
      @NonNull WebViewClient webViewClient,
      @NonNull WebView webView,
      @NonNull String url,
      boolean isReload,
      @NonNull Reply<Void> callback) {
    webViewFlutterApi.create(webView, reply -> {});

    final Long webViewIdentifier =
        Objects.requireNonNull(instanceManager.getIdentifierForStrongReference(webView));
    doUpdateVisitedHistory(
        getIdentifierForClient(webViewClient), webViewIdentifier, url, isReload, callback);
  }

  private long getIdentifierForClient(WebViewClient webViewClient) {
    final Long identifier = instanceManager.getIdentifierForStrongReference(webViewClient);
    if (identifier == null) {
      throw new IllegalStateException("Could not find identifier for WebViewClient.");
    }
    return identifier;
  }
}
