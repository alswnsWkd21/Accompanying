package smc.minjoon.accompanying.LockScreen;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;

import smc.minjoon.accompanying.PackageReceiver;
import smc.minjoon.accompanying.R;
import smc.minjoon.accompanying.RestartReceiver;

/**
 * Created by skaqn on 2017-10-03.
 */

public class ScreenService extends Service {
        private ScreenReceiver mReceiver = null;
        private PackageReceiver pReceiver;


        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            mReceiver = new ScreenReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mReceiver, filter);
            registerRestartAlarm(true);

            pReceiver = new PackageReceiver();
            IntentFilter pFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
            pFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            pFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            pFilter.addDataScheme("package");
            registerReceiver(pReceiver, pFilter);



        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId){
            super.onStartCommand(intent, flags, startId);
            Notification notification = new Notification.Builder(getApplicationContext()).setContentTitle("Screen Service").
                    setContentText("수정해야한다.").
                    setSmallIcon(R.drawable.example_appwidget_preview).
                    build(); // Notification에 굳잉 설정을 해주지않고 객체만 넣어서 이거 설정해줘야 한다.
            startForeground(1, notification );

            if(intent != null){
                if(intent.getAction()==null){
                    if(mReceiver==null){
                        mReceiver = new ScreenReceiver();
                        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
                        registerReceiver(mReceiver, filter);
                    }
                }
            }
            return START_REDELIVER_INTENT;
        }
        @Override
        public void onDestroy(){
            super.onDestroy();

            if(mReceiver != null){
                unregisterReceiver(mReceiver);
            }
            mReceiver.reenableKeyguard();
            registerRestartAlarm(false);
            if(pReceiver != null){
                unregisterReceiver(pReceiver);
            }
        }
    public void registerRestartAlarm(boolean isOn){
        Intent intent = new Intent(ScreenService.this, RestartReceiver.class);
        intent.setAction(RestartReceiver.ACTION_RESTART_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        if(isOn){
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, 1800000, sender);
        }else{
            am.cancel(sender);
        }
    }
}
