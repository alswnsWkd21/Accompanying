package smc.minjoon.accompanying.MainSosButton.Sos;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.widget.AppCompatButton;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import smc.minjoon.accompanying.Login.MainActivity;
import smc.minjoon.accompanying.MainSettingButton.ContactButton.DBManager;
import smc.minjoon.accompanying.MainSettingButton.ContactButton.SingleItem;
import smc.minjoon.accompanying.MainSosButton.PopupActivity;
import smc.minjoon.accompanying.R;

/**
 * Created by skaqn on 2017-10-03.
 */

public class AlwaysOnTopService extends Service {
    private AutoRepeatButton mPopupView;
    private WindowManager.LayoutParams mParams;
    private WindowManager mWindowManager;
    private SeekBar mSeekBar;
    double latitude;
    double longitude;
    int status =0;
    LocationManager lm;
    private Context mContext = new MainActivity();

    BroadcastReceiver br;
    String com;
    ArrayList<SingleItem> items = new ArrayList<SingleItem>();
    DBManager helper;
    private float START_X, START_Y;
    private int PREV_X, PREV_Y;
    private int MAX_X = -1, MAX_Y = -1;


    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new DBManager(this, "Number", null, 1);
        items= helper.getResult();
        final Vibrator vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mPopupView = new AutoRepeatButton(AlwaysOnTopService.this);
        mPopupView.setBackgroundResource(R.drawable.widget); //이거 수정한거!!
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        lm.requestLocationUpdates( LocationManager.GPS_PROVIDER , 100, 1, mLocationListener );
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mPopupView, mParams);

        status=1;
        mPopupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vide.vibrate(500);
            }
        });
        mPopupView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vide.vibrate(1000);
                Intent intent = new Intent(AlwaysOnTopService.this, PopupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", "Test Popup");
//                startActivityForResult(intent, 1);
                startActivity(intent);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(com.equals("Close")){
                            cancel();
                        }else{
                            for (int i =0; i<items.size(); i++){
                                sendSMS(items.get(i).getNumber(),items.get(i).getContent() + "\nhttp://maps.google.com/?q="+latitude+","+longitude);
                            }

                        }
                    }
                },10000);
                return true;
            }
        });

        addOpacityController();


    }


    private final LocationListener mLocationListener = new LocationListener() {

        float accuracy;
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void sendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        break;
                }
            }
        };
        registerReceiver(br, new IntentFilter(SENT));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(matrix);

        MAX_X = matrix.widthPixels - mPopupView.getWidth();
        MAX_Y = matrix.heightPixels - mPopupView.getHeight();
    }

    private void optimizePosition() {

        if(mParams.x > MAX_X) mParams.x = MAX_X;
        if(mParams.y > MAX_Y) mParams.y = MAX_Y;
        if(mParams.x < 0) mParams.x = 0;
        if(mParams.y < 0) mParams.y = 0;
    }

    private void addOpacityController() {
        mSeekBar = new SeekBar(this);
        mSeekBar.setMax(100);
        mSeekBar.setProgress(100);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override public void onProgressChanged(SeekBar seekBar, int progress,	boolean fromUser) {
                Log.v("tag", String.valueOf(mSeekBar.getProgress()));

                if(mSeekBar.getProgress()==0){
                    if(status ==1){
                        mWindowManager.removeView(mPopupView);
                        status=0;
                    }
                }else if(mSeekBar.getProgress()==100){
                    if(status==0){
                        mWindowManager.addView(mPopupView, mParams);
                        mParams.alpha = progress / 100.0f;
                        mWindowManager.updateViewLayout(mPopupView, mParams);
                        status=1;
                    }
                }else{
                    if(status==1){
                        mParams.alpha = progress / 100.0f;
                        mWindowManager.updateViewLayout(mPopupView, mParams);
                    }
                }


            }
        });


        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;

        mWindowManager.addView(mSeekBar, params);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setMaxPosition();
        optimizePosition();
    }

    @Override
    public void onDestroy() {
        if(mWindowManager != null) {
            if(mPopupView != null){
                mWindowManager.removeView(mPopupView);
                status =0;
            }
            if(mSeekBar != null) mWindowManager.removeView(mSeekBar);
        }
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        com = intent.getStringExtra("result");
        return startId;
    }






    public class AutoRepeatButton extends AppCompatButton {
        private int num;
        private long initialRepeatDelay = 1000;
        private long repeatIntervalInMilliseconds = 1000;

        private Runnable repeatClickWhileButtonHeldRunnable = new Runnable() {
            @Override
            public void run() {
                //Perform the present repetition of the click action provided by the user
                // in setOnClickListener().
                performClick();
                num +=1;
                if(num >=5){
                    performLongClick();
                    num =0;
                    return;
                }
                //Schedule the next repetitions of the click action, using a faster repeat
                // interval than the initial repeat delay interval.
                postDelayed(repeatClickWhileButtonHeldRunnable, repeatIntervalInMilliseconds);
            }
        };

        private void commonConstructorCode() {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        //Just to be sure that we removed all callbacks,
                        // which should have occurred in the ACTION_UP
                        if(MAX_X == -1)
                            setMaxPosition();
                        START_X = event.getRawX();
                        START_Y = event.getRawY();
                        PREV_X = mParams.x;
                        PREV_Y = mParams.y;

                        removeCallbacks(repeatClickWhileButtonHeldRunnable);
                        //Perform the default click action.
                        num =0;
                        performClick();
                        num +=1;
                        //Schedule the start of repetitions after a one half second delay.
                        postDelayed(repeatClickWhileButtonHeldRunnable, initialRepeatDelay);
                    } else if (action == MotionEvent.ACTION_UP) {
                        //Cancel any repetition in progress.
                        removeCallbacks(repeatClickWhileButtonHeldRunnable);
                    }else if(action == MotionEvent.ACTION_MOVE){
                        int x = (int)(event.getRawX() - START_X);
                        int y = (int)(event.getRawY() - START_Y);


                        mParams.x = PREV_X + x;
                        mParams.y = PREV_Y + y;

                        optimizePosition();
                        mWindowManager.updateViewLayout(mPopupView, mParams);
                    }

                    //Returning true here prevents performClick() from getting called
                    // in the usual manner, which would be redundant, given that we are
                    // already calling it above.
                    return true;
                }
            });
        }

        public AutoRepeatButton(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            commonConstructorCode();
        }


        public AutoRepeatButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            commonConstructorCode();
        }

        public AutoRepeatButton(Context context) {
            super(context);
            commonConstructorCode();
        }


    }
}
