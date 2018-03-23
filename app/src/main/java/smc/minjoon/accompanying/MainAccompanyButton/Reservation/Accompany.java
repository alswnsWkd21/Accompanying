package smc.minjoon.accompanying.MainAccompanyButton.Reservation;

/**
 * Created by skaqn on 2018-01-25.
 */

public class Accompany {
    String userID;
    String helperID;
    String accompanyID;
    String accompanyPlace;
    String accompanyDate;
    String accompanyTime;
    String accompanyWork;
    String status;
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

    public String getAccompanyID() {
        return accompanyID;
    }

    public void setAccompanyID(String accompanyID) {
        this.accompanyID = accompanyID;
    }

    public String getAccompanyPlace() {
        return accompanyPlace;
    }

    public void setAccompanyPlace(String accompanyPlace) {
        this.accompanyPlace = accompanyPlace;
    }

    public String getAccompanyDate() {
        return accompanyDate;
    }

    public void setAccompanyDate(String accompanyDate) {
        this.accompanyDate = accompanyDate;
    }

    public String getAccompanyTime() {
        return accompanyTime;
    }

    public void setAccompanyTime(String accompanyTime) {
        this.accompanyTime = accompanyTime;
    }

    public String getAccompanyWork() {
        return accompanyWork;
    }

    public void setAccompanyWork(String accompanyWork) {
        this.accompanyWork = accompanyWork;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Accompany(String userID,String accompanyID, String accompanyPlace, String accompanyDate, String accompanyTime, String accompanyWork, String helperID, String status) {
        this.userID = userID;
        this.accompanyID = accompanyID;
        this.accompanyPlace = accompanyPlace;
        this.accompanyDate = accompanyDate;
        this.accompanyTime = accompanyTime;
        this.accompanyWork = accompanyWork;
        this.helperID = helperID;
        this.status = status;
    }
}
