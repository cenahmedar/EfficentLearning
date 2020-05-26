package com.cenah.efficentlearning.notificationService;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.cenah.efficentlearning.R;
import com.cenah.efficentlearning.helpers.DateHelper;
import com.cenah.efficentlearning.models.NotificationModel;
import com.cenah.efficentlearning.zpages.student.activities.StudentHomeActivity;

public class MainNotification {

    private static final String NOTIFICATION_TAG = "Main";
    private static int id = 0;

    static void notify(final Context context, final NotificationModel exampleString, final int number) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        // final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.example_picture);

        Intent intent = new Intent(context, StudentHomeActivity.class);
        intent.putExtra("menuFragment", "notificaonFragment");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //final String ticker = exampleString.getMessage();
        final String text = DateHelper.dateToString(exampleString.getDate());
        final String title = exampleString.getMessage();
        //final String text = exampleString;

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(text)

                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later..setLargeIcon(picture)

                // Set ticker text (preview) information for this notification..setTicker(ticker)

                // Show a number. This is useful when stacking notifications of
                // a single type.
                .setNumber(number)

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                //.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")), PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentIntent(pendingIntent)
                // Show expanded text content on devices running Android 4.1 or
                // later.
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text).setBigContentTitle(title).setSummaryText(text))

                // Example additional actions for this notification. These will
                // only show on devices running Android 4.1 or later, so you
                // should ensure that the activity in this notification's
                // content intent provides access to the same actions in
                // another way.
                // .addAction(R.drawable.ic_action_stat_share, res.getString(R.string.action_share), PendingIntent.getActivity(context, 0, Intent.createChooser(new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, "Dummy text"), "Dummy title"), PendingIntent.FLAG_UPDATE_CURRENT))
                //  .addAction(R.drawable.ic_action_stat_reply, res.getString(R.string.action_reply), null)

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, id, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
        id++;
    }


    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
