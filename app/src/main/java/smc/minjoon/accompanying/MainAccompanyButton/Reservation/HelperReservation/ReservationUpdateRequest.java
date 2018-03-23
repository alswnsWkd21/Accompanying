package smc.minjoon.accompanying.MainAccompanyButton.Reservation.HelperReservation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skaqn on 2018-01-22.
 */



public class ReservationUpdateRequest extends StringRequest {
    final static private String URL ="http://syoung1394.cafe24.com/ReservationUpdate.php";
    private Map<String, String> parameters;

    public ReservationUpdateRequest(String reservationID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("reservationID", reservationID);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}