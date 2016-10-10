/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.retaurants.chinaspice;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    public static String cover_img, text1, text2;
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        cover_img = data.getString("image");
        text1 = data.getString("storename");
        text2 = data.getString("message");

        sendNotification(cover_img, text1, text2);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        // [END_EXCLUDE]
    }
    // [END receive_message]



    @SuppressLint("InlinedApi")
    private void sendNotification(String img, String text1, String text2) {
        //	Log.e("here", "here");
        //displayNotification("heloo","hello");
   //     Log.e("cover_img",cover_img);
    //    Log.e("text1", text1);
    //    Log.e("text2",text2);

		if(img.equals("0")){

			displayNotification(text1,text2);

		}else{

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, setBigPictureStyleNotification(img,text1,text2));


        }
    }

    protected void displayNotification(String text1,String text2) {

   /* Invoking the default notification service */
        NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(text1);
        mBuilder.setContentText(text2);
       /// mBuilder.setSmallIcon(R.drawable.store_noti);

   /* Creates an explicit intent for an Activity in your app */
      ///  Intent resultIntent = new Intent(this, HomeScreen.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
      ///  stackBuilder.addParentStack(HomeScreen.class);

   /* Adds the Intent that starts the Activity to the top of the stack */
       /// stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(9999, mBuilder.build());
    }


    private Notification setBigPictureStyleNotification(String url,String text1,String text2) {

        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
		/*notiStyle.setBigContentTitle("Big Picture Expanded");
		notiStyle.setSummaryText("Nice big picture.");*/

        notiStyle.bigPicture(getBitmapFromURL(url));
        ///Intent resultIntent = new Intent(this, HomeScreen.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
      ///  stackBuilder.addParentStack(HomeScreen.class);
       /// stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(this)
        ///        .setSmallIcon(R.drawable.store_noti)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(text1)
                .setContentText(text2)
                .setStyle(notiStyle).build();
    }


    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
