package smc.minjoon.accompanying.LockScreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import smc.minjoon.accompanying.LockScreen.ScreenService;

/**
 * Created by skaqn on 2017-10-03.
 */

public class PackageReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
            // 앱이 설치되었을 때
            Intent i = new Intent(context, ScreenService.class);
            context.startService(i);
        } else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
            // 앱이 삭제되었을 때
        } else if(action.equals(Intent.ACTION_PACKAGE_REPLACED)){
// 앱이 업데이트 되었을 때
            Intent i = new Intent(context, ScreenService.class);
            context.startService(i);
        }
    }
}


