package com.example.fuzhicheng.hybrid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.fuzhicheng.myapplication.R;

/**
 * Created by fuzhicheng on 2018/1/11.
 * 通用web加载器
 */

public class CommonWebActivity extends Activity {
    private WebView mWebView;
    private Button mButton;
    public static final String TAG = "CommonWeb";
    private static final String LOCAL_URL = "file:///android_asset/html/my.html";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);
        mWebView = (WebView) findViewById(R.id.commo_webview);
        mButton = (Button) findViewById(R.id.web_btn);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置允许使用JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e(TAG,"onJsAlert: "+message+" url--"+url);
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "onJsConfirm: "+message+" url--"+url );
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.e(TAG, "onJsPrompt: "+message+"default value: "+defaultValue+" url--"+url );
                result.confirm();
                return true;
            }
        });
        mWebView.addJavascriptInterface(new JavaScriptInterface(),"test");
        mWebView.loadUrl(LOCAL_URL);
        initListener();
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                            mWebView.evaluateJavascript("javascript:callJSPrompt()",null);
                        }else {
//                            mWebView.loadUrl("javascript:callJS()");
                            mWebView.loadUrl("javascript:callJSPrompt()");
                        }

                    }
                });
            }
        });

    }
}
