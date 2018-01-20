package smc.minjoon.accompanying.Login;

/**
 * Created by skaqn on 2018-01-18.
 */
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "refresh해야합니다");
        SessionManager sessionmanager = new SessionManager(MyFirebaseInstanceIDService.this);
        // Get updated InstanceID token.
//        if(sessionmanager.getKeyId()==null){
//            Log.d(TAG, "Refreshed token: " + "실패");
//        }else{
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            sessionmanager.createLoginSession(refreshedToken, "refresh");
            Log.d(TAG, "Refreshed token: " + refreshedToken);
//            String id =sessionmanager.getKeyId();
//            String kind = sessionmanager.getKeyKind();
//            sendRegistrationToServer(refreshedToken);
//        }
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if (success) {
//                                Log.v("refresh","리프레쉬됨");
//                            } else {
//                                Log.v("refresh","안됨");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                };
//                SessionRequest sessionRequest1 = new SessionRequest(kind,id, token, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(MyFirebaseInstanceIDService.this);
//                queue.add(sessionRequest1);

        }

    }
