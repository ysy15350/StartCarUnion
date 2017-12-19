package api.base.server;

import android.util.Log;

import com.ysy15350.startcarunion.log.dao.RequestLogDao;
import com.ysy15350.startcarunion.log.model.RequestLog;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.base.model.Config;
import api.base.model.Response;
import base.BaseData;
import base.config.ConfigHelper;
import common.CommFunAndroid;
import common.CommFunMessage;
import common.JsonConvertor;

public class Request implements IServer {


    private static final String TAG = "Request";

    RequestLogDao requestLogDao = RequestLogDao.getInstance();

    RequestLog requestLog = new RequestLog();

    /**
     * 服务器地址
     */
    private static String serverUrl = "";

    /**
     * 请求地址
     */
    private String mUrl;

    private String methodName;

    private Map<String, Object> mParams;

    /**
     * header信息
     */
    private String Authorization = "";

    private String requestTimeStr = "";

    RequestParams mRequestParams;

    /**
     * 是否使用缓存
     */
    boolean useCache;

    /**
     * 缓存时间(s)
     */
    int cacheMaxAge;

    /**
     * 自定义回调
     */
    private ApiCallBack mApiCallBack;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void setMethodName(String methodName) {
        this.methodName = methodName;

        if (serverUrl == null || "".equals(serverUrl)) {

            try {

                serverUrl = ConfigHelper.getServerUrl(Config.isDebug, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.mUrl = serverUrl + methodName;

    }

    @Override
    public void setParam(String key, Object value) {
        if (mParams == null)
            mParams = new HashMap<String, Object>();
        mParams.put(key, value);
        // mRequestParams.addQueryStringParameter(key, String.valueOf(value));
    }

    public void setApiCallBack(ApiCallBack apiCallBack) {
        this.mApiCallBack = apiCallBack;
    }


    @Override
    public void setCacheTime(int cacheTime) {
        this.useCache = true;
        this.cacheMaxAge = cacheTime * 1000;
    }

    /**
     * 执行请求
     */
    @Override
    public void request() {

        if (!CommFunAndroid.isNullOrEmpty(mUrl)) {

            mRequestParams = getRequestParams();

            mRequestParams.setAsJsonContent(true);// 以JSON方式发送


            if (mRequestParams.getUri().contains("send_token")) {
                Authorization = Config.getAuthorizationPre() + Config.getAUTHORIZATION();
            } else {
                Authorization = Config.getAuthorizationPre() + BaseData.getInstance().getToken();
            }
            Log.i("RequestServer", "Authorization=" + Authorization);

            mRequestParams.addHeader("Authorization", Authorization);

            if (!CommFunAndroid.isNullOrEmpty(BaseData.getInstance().getToken()) || mRequestParams.getUri().contains("send_token")) {
                requestTimeStr = CommFunAndroid.getDateString("yyyy-MM-dd HH:mm:ss SSS");//请求时间

            }
            x.http().post(mRequestParams, cacheCallback);
        }
    }

    private RequestParams getRequestParams() {

        mRequestParams = null;

        if (!CommFunAndroid.isNullOrEmpty(mUrl)) {

            mRequestParams = new RequestParams(mUrl);


            if (mParams != null) {

                // 请求接口数据
                List<Map.Entry<String, Object>> mappingList = new ArrayList<Map.Entry<String, Object>>(
                        mParams.entrySet());

                try {
                    if (mappingList != null) {

                        Log.i("RequestServer", "url=" + mUrl);

                        for (Map.Entry<String, Object> entry : mappingList) {
                            String key = entry.getKey();

                            Object value = entry.getValue();
                            if (value != null) {

                                if (value instanceof File) {
                                    File file = (File) value;
                                    Log.d(TAG, key + "=" + file.getPath() + ";exists=" + file.exists());
                                    mRequestParams.setMultipart(true);
                                    mRequestParams.addBodyParameter(key, file, "application/x-jpg");//"multipart/form-data"
                                } else {

                                    Log.d(TAG, key + "=" + value.toString());
                                    mRequestParams.addBodyParameter(key, String.valueOf(value));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }

            if (this.useCache) {

                mRequestParams.setCacheMaxAge(cacheMaxAge);// 缓存时间(秒)
            }

        }

        return mRequestParams;
    }

    /**
     * 框架回调
     */
    Callback.CacheCallback<String> cacheCallback = new Callback.CacheCallback<String>() {

        /**
         *
         */
        private boolean hasError = false;

        private boolean isCache = false;

        private String result = null;

        @Override
        public boolean onCache(String result) {
            // 得到缓存数据, 缓存过期后不会进入这个方法.
            // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
            //
            // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
            // 如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
            // 逻辑, 那么xUtils将请求新数据, 来覆盖它.
            //
            // * 如果信任该缓存返回 true, 将不再请求网络;
            // 返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
            // 如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
            //

            this.isCache = true;

            this.result = result;
            return useCache; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
        }


        @Override
        public void onSuccess(String result) {
            // 注意: 如果服务返回304 或 onCache 选择了信任缓存, 这时result为null.
            if (result != null) {
                this.isCache = false;
                this.result = result;

            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            hasError = true;


            CommFunMessage.hideWaitDialog();

            String msg = "服务器错误";
            try {

                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    msg = responseMsg;

                    // ...
                } else if (ex instanceof SocketTimeoutException) {
                    SocketTimeoutException socketException = (SocketTimeoutException) ex;
                    msg = socketException.getMessage();
                } else { // 其他错误
                    // ...
                    msg = ex.getMessage();
                    if (msg == null || "".equals(msg)) {
                        Throwable throwable = ex.getCause();
                        if (throwable != null) {
                            msg = throwable.getMessage();
                            if (msg == null || "".equals(msg)) {
                                msg = throwable.getLocalizedMessage();
                            }
                        }
                    }
                }

                if (msg == null || "".equals(msg))
                    msg = "服务器错误";


            } catch (Exception e) {
                e.printStackTrace();

            }
            if (mApiCallBack != null)
                mApiCallBack.onFailed(msg);

//            if (ex != null)
//                Log.e("RequestServer", ex.getMessage());
        }

        @Override
        public void onCancelled(CancelledException cex) {
            CommFunMessage.hideWaitDialog();
            // Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFinished() {
            CommFunMessage.hideWaitDialog();
            Log.d(TAG, String.format("url:%s,isCache:%b,result:%s", mUrl, isCache, result));
            if (!hasError && result != null) {

                Response response = JsonConvertor.fromJson(result, Response.class);

                if (response != null) {
                    int code = response.getCode();
                    if (code == 501 || code == 500) {
                        BaseData.setToken("");
                    }
                }

                // 成功获取数据
                if (mApiCallBack != null) {
                    // mApiCallBack.onSuccess(result);


                    mApiCallBack.onSuccess(isCache, response);

                    mApiCallBack.onSuccess(isCache, result);
                }

                writeLog(isCache, result, response);//记录日志
            }
        }
    };

    /**
     * 记录日志
     *
     * @param result
     */
    private void writeLog(final boolean isCache, final String result, final Response response) {
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    requestLog.setUrl(mUrl);
                    requestLog.setRequestTime(requestTimeStr);
                    requestLog.setAuthorization(Authorization);
                    if (mParams != null) {
                        String paramsStr = JsonConvertor.toJson(mParams).replace("\n", "");
                        requestLog.setParamStr(paramsStr);
                    }
                    if (response != null) {
                        requestLog.setToken(response.getToken());
                        requestLog.setCode(response.getCode() + "");
                        requestLog.setMessage(response.getMessage());
                    }
                    requestLog.setResponseStr(result);
                    requestLog.setCache(isCache);
                    requestLog.setCacheTime(cacheMaxAge / 1000);
                    requestLog.setResponseTime(CommFunAndroid.getDateString("yyyy-MM-dd HH:mm:ss SSS"));

                    String log = JsonConvertor.toJson(requestLog).replace("\\", "");


                    //FileUtils.writeJsonLog(log);

                    requestLogDao.insert(requestLog);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
