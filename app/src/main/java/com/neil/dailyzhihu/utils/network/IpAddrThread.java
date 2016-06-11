package com.neil.dailyzhihu.utils.network;

import android.util.Log;

import java.io.IOException;
import java.net.UnknownHostException;

public class IpAddrThread extends Thread {
    private NetworkListener mNetworkListener;

    public IpAddrThread(NetworkListener listener) {
        mNetworkListener = listener;
    }

    @Override
    public void run() {
        try {
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 5 " + "202.108.22.5");
            int status = p.waitFor();
            if (status == 0) {
                Log.e("STATUS", "连接正常");
                mNetworkListener.onResult(new NetworkInfo("连接正常", true));
                // pass
                // mPingIpAddrResult = "连接正常";
//                    setThisIpTrue(IpNum);
            } else {
                Log.e("STATUS", "连接不可达到");
                mNetworkListener.onResult(new NetworkInfo("连接不可达到", false));
                // Fail:Host unreachable
                // mPingIpAddrResult = "连接不可达到";
            }
        } catch (UnknownHostException e) {
            Log.e("STATUS", "出现未知连接");
            mNetworkListener.onResult(new NetworkInfo("出现未知连接", false));
            // Fail: Unknown Host
            // mPingIpAddrResult = "出现未知连接";
        } catch (IOException e) {
            Log.e("STATUS", "连接出现IO异常");
            mNetworkListener.onResult(new NetworkInfo("连接出现IO异常", false));
            // Fail: IOException
            // mPingIpAddrResult = "连接出现IO异常";
        } catch (InterruptedException e) {
            Log.e("STATUS", "连接出现中断异常");
            mNetworkListener.onResult(new NetworkInfo("连接出现中断异常", false));
            // Fail: InterruptedException
            // mPingIpAddrResult = "连接出现中断异常";
        }
    }
}