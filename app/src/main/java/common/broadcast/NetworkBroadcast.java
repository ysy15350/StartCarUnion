package common.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;

import com.ysy15350.startcarunion.StartActivity;

public class NetworkBroadcast extends BroadcastReceiver {

//	http://blog.csdn.net/liuhe688/article/details/6955668

    @Override
    public void onReceive(Context context, Intent intent) {
        State wifiState = null;
        State mobileState = null;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        int type = -1;//网络连接类型；0:mobile(手机网路);1:WIFI;默认-1
        String msg = "手机无网络";

        if (wifiState != null && mobileState != null && State.CONNECTED != wifiState
                && State.CONNECTED == mobileState) {
            type = 0;
            msg = "手机网络已连接";

        } else if (wifiState != null && mobileState != null && State.CONNECTED != wifiState
                && State.CONNECTED != mobileState) {
            // 手机没有任何的网络
            type = -1;
            msg = "手机无网络";

        } else if (wifiState != null && State.CONNECTED == wifiState) {
            // 无线网络连接成功
            type = 1;
            msg = "Wifi已连接";
        }
        Log.i("networkState", msg);
        sendBroadcart(context, type, msg);
    }

    private void sendBroadcart(Context context, int type, String msg) {

        // ---------------------
        Intent mIntent = new Intent(StartActivity.MESSAGE_RECEIVED_ACTION);
        mIntent.putExtra("type", type);
        mIntent.putExtra("msg", msg);

        // 发送广播
        context.sendBroadcast(mIntent);
    }
}
