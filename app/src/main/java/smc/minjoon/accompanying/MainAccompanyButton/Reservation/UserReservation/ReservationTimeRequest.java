package smc.minjoon.accompanying.MainAccompanyButton.Reservation.UserReservation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-24.
 */


public class ReservationTimeRequest extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/ReservationTimeRequest.php";
    private Map<String, String> parameters;

    public ReservationTimeRequest(String reservationID, String time,String startTime, String endTime, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("reservationID", reservationID);
        parameters.put("reservationWorkTime", time);
        parameters.put("startTime", startTime);
        parameters.put("endTime", endTime);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}