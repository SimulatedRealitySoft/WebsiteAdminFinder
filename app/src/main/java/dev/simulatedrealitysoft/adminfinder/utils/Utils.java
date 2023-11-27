package dev.simulatedrealitysoft.adminfinder.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static PowerManager.WakeLock cpuWakeLock;
    static WifiManager.WifiLock mWifiLock;
    public Activity activity;

    public Utils(Activity activity2) {
        this.activity = activity2;
    }

    public static void keepWiFiOn(Context context, boolean z) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (z) {
            if (mWifiLock == null) {
                mWifiLock = wifiManager.createWifiLock(1, "ContentValues");
            }
            mWifiLock.setReferenceCounted(false);
            if (!mWifiLock.isHeld()) {
                mWifiLock.acquire();
            }
        } else if (mWifiLock == null && mWifiLock != null && mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    public static void keepScreenOn(Activity activity2, boolean z) {
        if (z) {
            activity2.getWindow().addFlags(128);
        } else {
            activity2.getWindow().clearFlags(128);
        }
    }

    public static void keepCPUOn(Context context, boolean z) {
        PowerManager powerManager;
        if (cpuWakeLock == null && (powerManager = (PowerManager) context.getSystemService("power")) != null) {
            cpuWakeLock = powerManager.newWakeLock(536870913, "ContentValues");
            cpuWakeLock.setReferenceCounted(true);
        }
        if (cpuWakeLock == null) {
            return;
        }
        if (z) {
            cpuWakeLock.acquire();
            Log.d("ContentValues", "CPU locked");
        } else if (cpuWakeLock.isHeld()) {
            cpuWakeLock.release();
            Log.d("ContentValues", "CPU unlocked");
        }
    }

    public static String TimeStampOn(Context context) {
        return new SimpleDateFormat("[ddMMyy HH:mm:ss]").format(new Date());
    }
}
