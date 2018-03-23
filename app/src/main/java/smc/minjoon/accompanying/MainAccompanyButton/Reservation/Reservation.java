package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

/**
 * Created by SY on 2018-01-07.
 */

public class Reservation {
    String userID;
    String helperID;
    String reservationID;
    String reservationPlace;
    String reservationDate;
    String reservationTime;
    String reservationWork;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHelperID() {
        return helperID;
    }

    public void setHelperID(String helperID) {
        this.helperID = helperID;
    }

    public String getReservationPlace() {
        return reservationPlace;
    }

    public void setReservationPlace(String reservationPlace) {
        this.reservationPlace = reservationPlace;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getReservationWork() {
        return reservationWork;
    }

    public void setReservationWork(String reservationWork) {
        this.reservationWork = reservationWork;
    }

    public Reservation(String userID,String reservationID, String reservationPlace, String reservationDate, String reservationTime, String reservationWork, String helperID, String status) {
        this.userID = userID;
        this.reservationID = reservationID;
        this.reservationPlace = reservationPlace;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationWork = reservationWork;
        this.helperID = helperID;
        this.status = status;
    }

//    public Reservation(String userID, String reservationID, String reservationPlace, String reservationDate, String reservationTime, String reservationWork) {
//        this.userID = userID;
//        this.reservationID = reservationID;
//        this.reservationPlace = reservationPlace;
//        this.reservationDate = reservationDate;
//        this.reservationTime = reservationTime;
//        this.reservationWork = reservationWork;
//    }
//
//    public Reservation(String userID, String reservationID, String reservationPlace, String reservationDate, String reservationTime, String reservationWork, String helperID) {
//        this.userID = userID;
//        this.reservationID = reservationID;
//        this.reservationPlace = reservationPlace;
//        this.reservationDate = reservationDate;
//        this.reservationTime = reservationTime;
//        this.reservationWork = reservationWork;
//        this.helperID = helperID;
//    }
}
