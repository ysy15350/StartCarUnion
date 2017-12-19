package com.ysy15350.startcarunion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import base.BaseActivity;
import common.CommFunAndroid;

@ContentView(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity {

    /**
     *
     */
    @ViewInject(R.id.ll_webview)
    private LinearLayout ll_webview;

    /**
     * 网页控件
     */
    @ViewInject(R.id.webview)
    private WebView webview;

    public String mUrl = "http://www.baidu.com";

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        title = title == null ? "" : title;
        setFormHead(title);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        // CommFunMessage.ShowCustomWaitDialog(WebViewActivity.this, "加载中...");

        webview.clearHistory();
        loadWeb();
    }

    @SuppressLint("JavascriptInterface")
    private void loadWeb() {
        try {

            Intent intent = getIntent();

            mUrl = intent.getStringExtra("param");

            if (CommFunAndroid.isNullOrEmpty(mUrl)) {
                // SysFunction.ShowMsgBox(getApplicationContext(), "请求失败！");
                finish();
                return;
            }

            WebSettings settings = webview.getSettings();
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setSupportZoom(true);// 允许放大缩小
            settings.setBuiltInZoomControls(false);// 显示放大缩小按钮
            settings.setDisplayZoomControls(false);// api 11以上
            settings.setSupportMultipleWindows(true);
            settings.setGeolocationEnabled(true);// 启用地理定位

            webview.clearHistory();
            webview.clearFormData();
            webview.clearCache(true);
            webview.destroyDrawingCache();

            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(this, "android");

            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            // webview.loadUrl(url);
            HashMap<String, String> additionalHttpHeaders = new HashMap<String, String>();

            additionalHttpHeaders.put("LK_PLAT", "2");

            webview.loadUrl(mUrl, additionalHttpHeaders);// "http://115.28.78.72:800/About/wzggdetail?id=10021"
            // webview.loadUrl("http://192.168.0.152:81/aboutus_1.html");
            webview.setWebViewClient(new MyWebViewClient());
            webview.setWebChromeClient(new MyWebChromeClient());

            webview.setHorizontalScrollbarOverlay(false);
            webview.setHorizontalScrollBarEnabled(false);

        } catch (Exception e) {
            // TODO: handle exception
            String s = e.getMessage();

            String ss = s;
        }

        // webview.loadUrl("file:///android_asset/aboutus.html");

        // NavigateTitle = (TextView) findViewById(R.id.NavigateTitle);
        // NavigateTitle.setBackgroundDrawable(null);
        // NavigateTitle.setText("关于我们");

        // webview = (WebView) findViewById(R.id.webview);
        //
        // WebSettings ws = webview.getSettings();
        // ws.setJavaScriptEnabled(true);
        // ws.setAllowFileAccess(true);
        // ws.setBuiltInZoomControls(false);
        // ws.setSupportZoom(false);
        //
        // /**
        // * 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ： 适应内容大小
        // * 2、LayoutAlgorithm.SINGLE_COLUMN: 适应屏幕，内容将自动缩放
        // *
        // */
        // // ws.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        // ws.setDefaultTextEncodingName("utf-8"); // 设置文本编码
        // ws.setAppCacheEnabled(true);
        // ws.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置缓存模式</span>
        //
        // webview.addJavascriptInterface(new DemoJavaScriptInterface(),
        // "demo");
        //
        // // 加载需要显示的网页
        // // webview.loadUrl("https://m.longkin.net/user/safeques");
        //
        // webview.loadUrl("file:///android_asset/aboutus.html");//
        // 本地文件存放在：assets
        // 文件中

        // 设置Web视图
        // webview.setWebViewClient(new HelloWebViewClient());
    }

    final class DemoJavaScriptInterface {

        DemoJavaScriptInterface() {
        }

        /**
         * This is not called on the UI thread. Post a runnable to invoke
         * loadUrl on the UI thread.
         */
        public void clickOnAndroid() {
            Toast.makeText(getBaseContext(), "123", Toast.LENGTH_SHORT).show();
            // mHandler.post(new Runnable() {
            // public void run() {
            // // mWebView.loadUrl("javascript:wave()");
            // Toast.makeText(getBaseContext(), "123",
            // Toast.LENGTH_SHORT).show();
            // }
            // });

        }
    }

    /**
     * 如果点击链接在当前browser中响应，而不是打开系统browser，需要覆盖WebViewClient
     *
     * @author Administrator
     */
    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            // view.loadUrl(url);

            HashMap<String, String> additionalHttpHeaders = new HashMap<String, String>();

            additionalHttpHeaders.put("LK_PLAT", "test");

            view.loadUrl(url, additionalHttpHeaders);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            // function.ShowCustomWaitDialog("系统提示", "加载中...");
        }

        /**
         * 载入页面完成的事件
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            if (CommFunAndroid.isEquals(url, mUrl) || url.contains(mUrl) || mUrl.contains(url)) {
                // CommFunMessage.HideCustomWaitDialog();
            }

        }

        /**
         * 加载发生错误
         */
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
            // CommFunMessage.ShowMsgBox(WebViewActivity.this, "加载失败");
            // CommFunMessage.HideCustomWaitDialog();

            ll_webview.getChildAt(0).setVisibility(View.GONE);

            // Bitmap bitmap = ((BitmapDrawable) (getResources()
            // .getDrawable(R.drawable.banner))).getBitmap();
            //
            // ImageView imageView = new ImageView(getActivity());
            // imageView.setImageBitmap(bitmap);
            //
            // imageView.setLayoutParams(new LayoutParams(
            // LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

            // ll_webview.addView(ViewCommon.nodataView(WebViewActivity.this,
            // R.layout.withoutdata));
        }

        /**
         * 接收到Http请求的事件
         */
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            // TODO Auto-generated method stub
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

    }

    final class MyWebChromeClient extends WebChromeClient {

        /**
         * 捕获js alert弹窗
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

            // JS中alert的内容，在android无法弹出

            showMsg(message);

            result.confirm();
            return true;
        }

        /**
         * 捕获js confirm弹窗
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            // TODO Auto-generated method stub
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            super.onProgressChanged(view, newProgress);

            setProgress(newProgress * 100);
            if (newProgress == 100) {
                // CommFunMessage.HideCustomWaitDialog();
            }
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    @Event(value = R.id.btn_back)
    private void btn_backClick(View view) {
        this.finish();
    }

}
