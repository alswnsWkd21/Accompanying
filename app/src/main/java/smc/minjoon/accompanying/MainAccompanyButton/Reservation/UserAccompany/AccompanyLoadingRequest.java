package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserAccompany;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-20.
 */


public class AccompanyLoadingRequest  extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/AccompanyLoading.php";
    private Map<String, String> parameters;

    public AccompanyLoadingRequest(String userid,String userName, String userPlace, String userWork,String userPhone, String userLat, String userlong ,Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("accompanyPlace", userPlace);
        parameters.put("accompanyWork", userWork);
        parameters.put("userLat", userLat);
        parameters.put("userlong", userlong);
        parameters.put("userName", userName);
        parameters.put("userPhone", userPhone);
        parameters.put("userID", userid);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}