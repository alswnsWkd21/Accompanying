package smc.minjoon.accompanying.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by skaqn on 2018-01-11.
 */
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_ID = "id";
    public static final String KEY_KIND="kind";
    public static final String KEY_PHONE="phone";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_OK = "ok";
    // Constructor
    public SessionManager(Context context){


        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession( String token, String ok ){
        // Storing login value as TRUE

// Storing name in pref
//        editor.putString(KEY_NAME, name);
// Storing email in pref
//        editor.putString(KEY_ID, id);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_OK, ok);
//        editor.putString(KEY_KIND, kind);
// commit changes
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(String activity, String kind){
        if(activity.equals("user")){
//            장애인일경우
            // Check login status
            if(!this.isLoggedIn()){
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(_context, DisabledLoginActivity.class);
                // Closing all the Activities
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                _context.startActivity(i);

            }else{
                if(!getKeyKind().equals(kind)){
                    logoutUser();
                }
            }

        }else{

//            도우미일 경우
            if(!this.isLoggedIn()){
                // user is not logged in redirect him to Login Activity
                Intent i = new Intent(_context, LoginActivity.class);
                // Closing all the Activities
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                _context.startActivity(i);

            }else{
                if(!getKeyKind().equals(kind)){
                    logoutUser();
                }
            }

        }


    }
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
// user email id
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        user.put(KEY_KIND, pref.getString(KEY_KIND, null));
// return user
        return user;
    }
    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.putString("name", null);
        editor.putString("id", null);
        editor.putString("kind", null);
        editor.putString("phone", null);
        editor.putBoolean("IsLoggedIn", false);
        editor.commit();
// After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SelectiveActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//// Add new Flag to start new Activity

//// Staring Login Activity
        _context.startActivity(i);
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public  String getKeyName() {
        return pref.getString(KEY_NAME, null);
    }

    public  String getKeyPhone() {
        return pref.getString(KEY_ID, null);
    }

    public  String getKeyId() {
        return pref.getString(KEY_ID, null);    }

    public  String getKeyKind() {
        return pref.getString(KEY_KIND, null);    }

    public  String getKeyToken() {
        return pref.getString(KEY_TOKEN, null);    }
    public  String getKeyOk() {
        return pref.getString(KEY_OK, null);    }
}
