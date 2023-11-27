package dev.simulatedrealitysoft.adminfinder.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import dev.simulatedrealitysoft.adminfinder.R;

public class NotificationBar {
    public static void notify(Context context, String str, String str2, int i) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(i);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(str);
        builder.setContentText(str2);
        ((NotificationManager) context.getSystemService("notification")).notify(1, builder.build());
    }
}
