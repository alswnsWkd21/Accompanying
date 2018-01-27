package smc.minjoon.accompanying.Login;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperAccompany.AccompanyPopupActivity;
import smc.minjoon.accompanying.R;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //추가한것
        sendNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("response"),remoteMessage.getData().get("users") );
    }

    private void sendNotification(String messageBody, String messageBody2, String messageBody3) {

        String userID = null;
        String userName = null;
        String userPhone= null;
        String userWork= null;
        String userPlace = null;
        double userLat = 0;
        double userlong =0;
        Intent intent = new Intent(this, AccompanyPopupActivity.class);
        if (messageBody != null && messageBody.length() != 0) {
            Log.v("message", messageBody);

        } else {
            // 값이 없는 경우 처리
        }
        if (messageBody2 != null && messageBody2.length() != 0) {
            JSONObject jsonResponse = null;
            try {
                jsonResponse = new JSONObject(messageBody2);
                String helperID = jsonResponse.getString("helperID");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.v("message2", messageBody2);
        } else {
            // 값이 없는 경우 처리
        }

        if (messageBody3 != null && messageBody3.length() != 0) {

            try {
                JSONArray jsonResponse = new JSONArray(messageBody3);
                for (int i = 0; i < jsonResponse.length(); i++){
                    JSONObject order = jsonResponse.getJSONObject(i);
                    userID = order.getString("userID");
                    userName = order.getString("userName");
                    userWork = order.getString("userWork");
                    userPhone = order.getString("userPhone");
                    userPlace = order.getString("userPlace");
                    userLat = order.getDouble("userLat");
                    userlong = order.getDouble("userlong");
                }




                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);
                intent.putExtra("userWork", userWork);
                intent.putExtra("userPlace", userPlace);
                intent.putExtra("userLat", userLat);
                intent.putExtra("userPhone", userPhone);
                intent.putExtra("userlong", userlong);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.v("message3", messageBody3);

        } else {

            // 값이 없는 경우 처리
        }



        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.noti)
                .setContentTitle("도움요청이 들어왔습니다.")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}


