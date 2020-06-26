package com.yunjaena.core.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringDef;
import androidx.core.app.NotificationCompat;

import com.yunjaena.core.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NotificationManager {
    private static final String GROUP_DELIVERY = "delivery";

    @TargetApi(Build.VERSION_CODES.O)
    public static void createChannel(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;

        NotificationChannelGroup group1 = new NotificationChannelGroup(GROUP_DELIVERY, GROUP_DELIVERY);
        getManager(context).createNotificationChannelGroup(group1);

        NotificationChannel channelMessage = new NotificationChannel(Channel.MESSAGE,
                context.getString(R.string.notification_channel_message_title), android.app.NotificationManager.IMPORTANCE_HIGH);
        channelMessage.setDescription(context.getString(R.string.notification_channel_message_description));
        channelMessage.setGroup(GROUP_DELIVERY);
        channelMessage.setLightColor(Color.GREEN);
        channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager(context).createNotificationChannel(channelMessage);

    }

    private static android.app.NotificationManager getManager(Context context) {
        return (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void deleteChannel(Context context, String channel) {
        getManager(context).deleteNotificationChannel(channel);
    }


    @TargetApi(Build.VERSION_CODES.O)
    public static void sendNotification(Context context, int id, String channel, String title, String body) {
        Notification.Builder builder = new Notification.Builder(context, channel)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setAutoCancel(true);

        getManager(context).notify(id, builder.build());
    }


    public static void sendNotification(Context context, int id, String title, String body) {
        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O) return;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(getSmallIcon())
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        getManager(context).notify(id, builder.build());
    }


    private static int getSmallIcon() {
        return R.drawable.ic_coffee;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            Channel.MESSAGE
    })
    public @interface Channel {
        String MESSAGE = "message";
    }
}
