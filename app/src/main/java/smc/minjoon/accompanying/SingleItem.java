package smc.minjoon.accompanying;

/**
 * Created by skaqn on 2017-10-06.
 */

import java.io.Serializable;


public class SingleItem implements Serializable{
    private int _id;
    private String title;
    private String content;
    private String number;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public SingleItem(int _id, String title, String content, String number) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.number = number;
    }

    public SingleItem(){

    }
    public SingleItem(String title, String content, String number) {
        this.title = title;
        this.content = content;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
