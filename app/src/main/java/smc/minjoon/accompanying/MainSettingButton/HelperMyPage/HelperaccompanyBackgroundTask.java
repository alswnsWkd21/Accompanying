package smc.minjoon.accompanying.MainSettingButton.HelperMyPage;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import smc.minjoon.accompanying.MainSettingButton.UserMyPage.Accompany.ConfirmUserList2Activity;
import smc.minjoon.accompanying.MainSettingButton.UserMyPage.Reservation.ConfirmUserListActivity;

/**
 * Created by skaqn on 2018-01-22.
 */

public class HelperaccompanyBackgroundTask extends AsyncTask<Void,Void,String>
{
    String target;
    String param2;
    Context activity;
    String param;

    public HelperaccompanyBackgroundTask(Context activity, String id, String param){
        this.activity = activity;
        this.param =param;
        param2 = id;
    }

    @Override
    protected void onPreExecute(){
        target="http://syoung1394.cafe24.com/"+param+param2;
    }
    @Override
    protected String doInBackground(Void... voids) {
        try{
            URL url =new URL(target);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            StringBuilder stringBuilder=new StringBuilder();
            while((temp=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(temp+"\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

    @Override
    public void onPostExecute(String result){
        if(param.equals("HelperReservationList.php?helperID=")){
            Intent intent=new Intent(activity, ConfirmUserListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("userList", result);
            activity.startActivity(intent);
        }else{
            Intent intent=new Intent(activity, ConfirmUserList2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("userList", result);
            activity.startActivity(intent);
        }

    }
}
