package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-22.
 */



public class ReservationFinishRequest extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/ReservationFinish.php";
    private Map<String, String> parameters;

    public ReservationFinishRequest(String reservationID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("reservationID", reservationID);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}