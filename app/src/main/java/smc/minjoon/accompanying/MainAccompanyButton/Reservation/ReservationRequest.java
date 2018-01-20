package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationRequest extends StringRequest {

    final static private String URL ="http://syoung1394.cafe24.com/Reservation.php";
    private Map<String, String> parameters;

    public ReservationRequest(String userid, String reservationPlace, String reservationDate, String reservationTime, String reservationWork, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters=new HashMap<>();
        parameters.put("reservationPlace", reservationPlace);
        parameters.put("reservationDate", reservationDate);
        parameters.put("reservationTime", reservationTime);
        parameters.put("reservationWork", reservationWork);
        parameters.put("u_id", userid);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
