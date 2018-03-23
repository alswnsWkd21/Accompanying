package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperAccompany;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-20.
 */


public class AccompanyPopupRequest  extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/AccompanyPopup.php";
    private Map<String, String> parameters;

    public AccompanyPopupRequest(String userid,String userName,  String userWork,String userPlace, String userPhone, String userLat, String userlong ,String helperID, String helperName, String helperPhone, String helperLat, String helperlong,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("userid",userid );
        parameters.put("userName", userName);
        parameters.put("userPlace", userPlace);
        parameters.put("userWork", userWork);
        parameters.put("userPhone", userPhone);
        parameters.put("userLat", userLat);
        parameters.put("userlong", userlong);
        parameters.put("helperID", helperID);
        parameters.put("helperName", helperName);
        parameters.put("helperLat", helperLat);
        parameters.put("helperlong", helperlong);
        parameters.put("helperPhone", helperPhone);


    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}