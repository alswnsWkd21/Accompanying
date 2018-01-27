package smc.minjoon.accompanying.MainMapButton;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import smc.minjoon.accompanying.R;
import smc.minjoon.accompanying.TmapLibrary;

public class TmapActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";
    LocationManager lm;
    Location userlocation;
    TextView tv01;
    TextView tv02;
    TextView tv03;
    Button mapbtn01;
    Button mapbtn02;
    TMapPoint point2;
    ImageButton imgbtn01;
    TMapView tmapview;
    int i;
    int count =0;
    double longitude;
    double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        grantExternalStoragePermission();
        tv01 = (TextView) findViewById(R.id.maptv01);
        tv02 = (TextView) findViewById(R.id.maptv02);
        mapbtn01 = (Button) findViewById(R.id.mapbtn01);
        mapbtn02 = (Button) findViewById(R.id.mapbtn02);
        imgbtn01 = (ImageButton) findViewById(R.id.imgbtn1);
        final TMapData tmapdata = new TMapData();
        tmapview = new TMapView(TmapActivity.this);
        tmapview.setSKPMapApiKey("60842859-875b-31b3-8fa1-944bcfd3d2d6");
        LinearLayout mapViewContainer = (LinearLayout) findViewById(R.id.map_view);
        mapViewContainer.addView(tmapview);
//        if (isGrantStorage) {
//// 일반처리.   // MapView 객체생성 및 API Key 설정
//            Toast.makeText(this, "권한을 허락하셨습니다.", Toast.LENGTH_SHORT).show();
//            lm.removeUpdates( mLocationListener );    // Stop the update if it is in progress.
//            lm.requestLocationUpdates( LocationManager.GPS_PROVIDER , 100, 1, mLocationListener );
//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
//        }
        AlertDialog.Builder alert = new AlertDialog.Builder(TmapActivity.this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialo, int which) {

                dialo.dismiss();     //닫기
            }
        });
        alert.setMessage("현재 동행길은 제주도지역 사회복지편의시설만 등록된 상태입니다. 1월28일까지 수도권및 도시별 사회복지편의시설이 등록됩니다.");
        alert.setCancelable(false);
        alert.show();

        imgbtn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmapview.setTrackingMode(true);
            }
        });
        tmapview.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback(){
            @Override
            public boolean onPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, final TMapPoint tMapPoint, PointF pointF) {
                try{
                    final TMapMarkerItem markeritem = arrayList.get(0);
                    tv01.setText(markeritem.getCalloutTitle());
                    tv02.setText(markeritem.getCalloutSubTitle());
                    point2 =markeritem.getTMapPoint();
                    mapbtn01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(markeritem.getCalloutSubTitle()!=null){
                                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + markeritem.getName())));
                            }else{
                                AlertDialog.Builder alert = new AlertDialog.Builder(TmapActivity.this);
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialo, int which) {

                                        dialo.dismiss();     //닫기
                                    }
                                });
                                alert.setMessage("전화정보가 없는 마커이므로 전화걸기가 제한됩니다.");
                                alert.setCancelable(false);
                                alert.show();

                            }
                        }
                    });
                    mapbtn02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TMapPoint point1 = new TMapPoint(latitude,longitude);
                            new TmapLibrary(TmapActivity.this, tmapview).searchRoute(point1, point2);


//                            tmapdata.findPathData(point1, point2, new TMapData.FindPathDataListenerCallback() {
//                                @Override
//                                public void onFindPathData(TMapPolyLine polyLine) {
//                                    tmapview.addTMapPath(polyLine);
//                                }
//                            });
//                              자동차경로도 긁어온다
//                            tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, point1, point2,
//                                    new TMapData.FindPathDataListenerCallback() {
//                                        @Override
//                                        public void onFindPathData(TMapPolyLine polyLine) {
//                                            tmapview.addTMapPath(polyLine);
//                                        }
//                                    });
                        }
                    });
                }
                catch (IndexOutOfBoundsException e){
                    TMapMarkerItem marker = new TMapMarkerItem();
                    marker.setTMapPoint(tMapPoint);
                    marker.setVisible(TMapMarkerItem.VISIBLE);
                    Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.target);
                    marker.setIcon(bitmap);
                    tmapview.addMarkerItem("click",marker);
                    mapbtn02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TMapPoint point1 = new TMapPoint(latitude,longitude);
                            new TmapLibrary(TmapActivity.this, tmapview).searchRoute(point1, tMapPoint);
//                            tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, point1, tMapPoint,
//                                    new TMapData.FindPathDataListenerCallback() {
//                                        @Override
//                                        public void onFindPathData(TMapPolyLine polyLine) {
//                                            tmapview.addTMapPath(polyLine);
//                                        }
//                                    });
                        }
                    });

                    return false;
                }
                return false;
            }


            @Override
            public boolean onPressUpEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint, PointF pointF) {
                return false;
            }
        });
        // Using TedPermission library

    }



    @Override
    protected void onResume() {
        super.onResume();

        new MyAsyncTask().execute("http://www.jeju.go.kr/rest/JejuWelfareFacilitieService/getJejuWelfareFacilitieList");
//        for(int i = 1; i<100; i++){
//            new MyAsyncTask2().execute("http://www.bokjiro.go.kr/openapi/nwel/getDisConvFaclList?crtiKey=H51MQdXy3gLtjtsKCg1zDAj4x4r4J3tm8DppZJBj6ue5vSjup5h1pkj5hhM%2BerOj8I3xfkU4TNMKPT%2Fz7L9Ltg%3D%3D&pageNo="+String.valueOf(i)+"&numOfRows=1000&faltycd=21");
//        }

    }
    class MyAsyncTask extends AsyncTask<String,TMapMarkerItem,Void>{
        protected  void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            try{
                URL url  = new URL(params[0]);
                InputStream is = url.openStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                Element root = doc.getDocumentElement();
                NodeList nl = root.getElementsByTagName("item");

                for(int i =0; i<nl.getLength(); i++){
                    Element e = (Element)nl.item(i);
                    TMapPoint tpoint = new TMapPoint(Double.parseDouble(e.getElementsByTagName("latitude").item(0).getTextContent()), Double.parseDouble(e.getElementsByTagName("longitude").item(0).getTextContent()));
                    TMapMarkerItem marker = new TMapMarkerItem();// 마커를 추가하기위한 코드 시작
                    marker.setName(e.getElementsByTagName("phone").item(0).getTextContent());
                    marker.setTMapPoint(tpoint);
                    marker.setCanShowCallout(true);
                    marker.setCalloutTitle(e.getElementsByTagName("name").item(0).getTextContent());
                    marker.setCalloutSubTitle(e.getElementsByTagName("addr").item(0).getTextContent());
                    marker.setVisible(TMapMarkerItem.VISIBLE); // 기본으로 제공하는 BluePin 마커 모양.
                    publishProgress(marker);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(TMapMarkerItem... values) {

            tmapview.addMarkerItem(Integer.toString(++count),values[0]);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
    class MyAsyncTask2 extends AsyncTask<String,TMapMarkerItem,Void>{
        protected  void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(String... params) {
            try{
                URL url  = new URL(params[0]);
                InputStream is = url.openStream();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                Element root = doc.getDocumentElement();
                NodeList nl = root.getElementsByTagName("servList");

                for(int i =0; i<nl.getLength(); i++){

                    Element e = (Element)nl.item(i);

                    if(e.getElementsByTagName("faclTyCd").item(0).getTextContent().equals("UC0H04") || e.getElementsByTagName("faclTyCd").item(0).getTextContent().equals("UC0A04")|| e.getElementsByTagName("faclTyCd").item(0).getTextContent().equals("UC0A06")|| e.getElementsByTagName("faclTyCd").item(0).getTextContent().equals("UC0H05")){
                        TMapPoint tpoint = new TMapPoint(Double.parseDouble(e.getElementsByTagName("faclLat").item(0).getTextContent()), Double.parseDouble(e.getElementsByTagName("faclLng").item(0).getTextContent()));
                        TMapMarkerItem marker = new TMapMarkerItem();// 마커를 추가하기위한 코드 시작
//                        marker.setName(e.getElementsByTagName("faclNm").item(0).getTextContent());
                        marker.setTMapPoint(tpoint);
                        marker.setCanShowCallout(true);
                        marker.setCalloutTitle(e.getElementsByTagName("faclNm").item(0).getTextContent());
                        marker.setCalloutSubTitle(e.getElementsByTagName("lcMnad").item(0).getTextContent());
                        marker.setVisible(TMapMarkerItem.VISIBLE); // 기본으로 제공하는 BluePin 마커 모양.
                        publishProgress(marker);
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(TMapMarkerItem... values) {

            tmapview.addMarkerItem(Integer.toString(++count),values[0]);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


    private void grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)){
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
            }
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)) {

                //만약 두개의 퍼미션이 전부 허락되어있다면 true반환
            } else {
                AlertDialog.Builder alert = new AlertDialog.Builder(TmapActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialo, int which) {
                        ActivityCompat.requestPermissions(TmapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE}, 1);
                        dialo.dismiss();     //닫기
                    }
                });
                alert.setMessage("도움길 기능을 효율적으로 사용하기 위해서 몇 가지의 권한이 필요합니다 \n\n" +
                        "위치정보:  현재의 위치를 위해 위치정보가 필요합니다 \n\n"+
                        "PHONE:   시설에 전화를 연결하기 위해 필요합니다.");
                alert.setCancelable(false);
                alert.show();
            }
        } else {
//            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "External Storage Permission is Grant ");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);

        }
    }



    private final LocationListener mLocationListener = new LocationListener() {

        float accuracy;
        @Override
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            accuracy = location.getAccuracy();
            userlocation = location;
            tmapview.setLocationPoint(longitude, latitude);
            tmapview.setCenterPoint(longitude, latitude);
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.location);
            tmapview.setIcon(bitmap);
            tmapview.setTrackingMode(true);
            tmapview.setIconVisibility(true);
            tmapview.setZoomLevel(10);
            tv01.setText("현재위치입니다.");
            lm.removeUpdates( this );
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };
}

