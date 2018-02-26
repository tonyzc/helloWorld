package com.example.fuzhicheng.hybrid;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by fuzhicheng on 2018/1/12.
 */

public class JavaScriptInterface {
    public JavaScriptInterface() {
    }

    @JavascriptInterface
    public void hello(String msg){
        Log.e(CommonWebActivity.TAG, "JS 调用了native 的hello方法" );
    }
}
