package com.neil.dailyzhihu.utils.network;

public class NetworkUtil {
    private NetworkUtil() {
    }

    public static void getNetworkInfo(NetworkListener listener) {
        IpAddrThread ipAddrThread = new IpAddrThread(listener);
        ipAddrThread.run();
    }
}
