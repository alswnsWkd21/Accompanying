package smc.minjoon.accompanying;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;

/**
 * Created by skaqn on 2018-01-17.
 */

public class TmapLibrary {

    Activity activity;
    TMapView tmapview;
    TMapData data = new TMapData();
    int id =0;
    public TmapLibrary(Activity activity, TMapView tmapview){
        this.activity = activity;
        this.tmapview = tmapview;
    }

    public void searchRoute(TMapPoint start, TMapPoint end){

        data.findPathData(start, end, new TMapData.FindPathDataListenerCallback() {

            @Override
            public void onFindPathData(final TMapPolyLine path) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        path.setLineWidth(5);
                        path.setLineColor(Color.RED);
                        tmapview.addTMapPath(path);
                        Bitmap s = ((BitmapDrawable) ContextCompat.getDrawable( activity, android.R.drawable.ic_input_delete)).getBitmap();
                        Bitmap e = ((BitmapDrawable)ContextCompat.getDrawable(activity, android.R.drawable.ic_input_get)).getBitmap();
                        tmapview.setTMapPathIcon(s, e);

                    }
                });
            }
        });
    }


    //    매개변수 경도 위도 주변의 객체들을 지도에 검색하고 addmarker해주는 함수
    public void searchPOI(String search1, double latitude, double longitude) {
        TMapData data = new TMapData();


        data.findAroundNamePOI(new TMapPoint(latitude,longitude),search1, new TMapData.FindAroundNamePOIListenerCallback(){
            @Override
            public void onFindAroundNamePOI(final ArrayList<TMapPOIItem> arrayList) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (TMapPOIItem poi : arrayList) {
                            addMarker(poi);
                            Log.v("dd","POI Name: " + poi.getPOIName().toString() + ", " +
                                    "Address: " + poi.getPOIAddress().replace("null", "")  + ", " +
                                    "Point: " + poi.getPOIPoint().toString());
//                                mAdapter.add(new POI(poi));

                        }
//                            addMarker(126.645582,33.539672, "첫 등록집");
                        if (arrayList.size() > 0) {
                            TMapPOIItem poi = arrayList.get(0);

                        }
                    }
                });
            }
        });
    }



    //    addMarker해주는 함수 TMapPOIItem이 왔을 때 !
    public void addMarker(TMapPOIItem poi) {
        TMapMarkerItem item = new TMapMarkerItem();
        item.setTMapPoint(poi.getPOIPoint());
        Bitmap icon = BitmapFactory.decodeResource(activity.getApplicationContext().getResources(),R.drawable.target);
        item.setIcon(icon);
        item.setPosition(0.5f, 1);
        item.setCalloutTitle(poi.getPOIName());
        item.setCalloutSubTitle(poi.getPOIContent());
        Bitmap left = ((BitmapDrawable) ContextCompat.getDrawable(activity, android.R.drawable.ic_dialog_alert)).getBitmap();
        item.setCalloutLeftImage(left);
        Bitmap right = ((BitmapDrawable) ContextCompat.getDrawable(activity, android.R.drawable.ic_input_get)).getBitmap();
        item.setCalloutRightButtonImage(right);
        item.setCanShowCallout(true);
        tmapview.addMarkerItem(poi.getPOIID(), item);
        tmapview.setZoomLevel(17);
    }
    //    addMarker해주는 함수 경도 위도 값이 왔을 때 !

    private void addMarker(double lat, double lng, String title) {
        TMapMarkerItem item = new TMapMarkerItem();
        TMapPoint point = new TMapPoint(lat, lng);
        item.setTMapPoint(point);
        Bitmap icon = ((BitmapDrawable) ContextCompat.getDrawable(activity, android.R.drawable.ic_input_add)).getBitmap();
        item.setIcon(icon);
        item.setPosition(0.5f, 1);
        item.setCalloutTitle(title);
        item.setCalloutSubTitle("sub " + title);
        Bitmap left = ((BitmapDrawable) ContextCompat.getDrawable(activity, android.R.drawable.ic_dialog_alert)).getBitmap();
        item.setCalloutLeftImage(left);
        Bitmap right = ((BitmapDrawable) ContextCompat.getDrawable(activity, android.R.drawable.ic_input_get)).getBitmap();
        item.setCalloutRightButtonImage(right);
        item.setCanShowCallout(true);

        tmapview.addMarkerItem("a" + id, item);
        id++;
    }
}
