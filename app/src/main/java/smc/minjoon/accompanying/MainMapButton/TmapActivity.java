package smc.minjoon.accompanying.MainMapButton;
import android.Manifest;
import android.content.Context;
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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
<<<<<<< HEAD
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

=======
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapAddressInfo;
>>>>>>> 6af0f8709ab76e6cc4fe1df48040d1a614ca9507
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
<<<<<<< HEAD

=======
>>>>>>> 6af0f8709ab76e6cc4fe1df48040d1a614ca9507
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

public class TmapActivity extends AppCompatActivity {
    private String TAG = "permissionstatus";
    LocationManager lm;
    Location userlocation;
    TextView tv01;
    TextView tv02;
    TextView tv03;
    Button mapbtn01;
    Button mapbtn02;
    ImageButton imgbtn01;
    TMapView tmapview;
    int count =0;
    double longitude;
    double latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmap);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGrantStorage = grantExternalStoragePermission();
        tv01 = (TextView) findViewById(R.id.maptv01);
        tv02 = (TextView) findViewById(R.id.maptv02);
        mapbtn01 = (Button) findViewById(R.id.mapbtn01);
        mapbtn02 = (Button) findViewById(R.id.mapbtn02);
        imgbtn01 = (ImageButton) findViewById(R.id.imgbtn1);
        final TMapData tmapdata = new TMapData();
        if (isGrantStorage) {
// 일반처리.   // MapView 객체생성 및 API Key 설정
            Toast.makeText(this, "권한을 허락하셨습니다.", Toast.LENGTH_SHORT).show();
            tmapview = new TMapView(TmapActivity.this);
            tmapview.setSKPMapApiKey("60842859-875b-31b3-8fa1-944bcfd3d2d6");
            LinearLayout mapViewContainer = (LinearLayout) findViewById(R.id.map_view);
            mapViewContainer.addView(tmapview);
            LocationManager lm = (LocationManager)getSystemService(Context. LOCATION_SERVICE);
            lm.removeUpdates( mLocationListener );    // Stop the update if it is in progress.
            lm.requestLocationUpdates( LocationManager.GPS_PROVIDER , 100, 1, mLocationListener );
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);
        }
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
                    final TMapPoint point2 =markeritem.getTMapPoint();
                    mapbtn01.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(markeritem.getCalloutSubTitle()!=null){
                                startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + markeritem.getName())));
                            }
                        }
                    });
                    mapbtn02.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TMapPoint point1 = new TMapPoint(latitude,longitude);
//                            tmapdata.findPathData(point1, point2, new TMapData.FindPathDataListenerCallback() {
//                                @Override
//                                public void onFindPathData(TMapPolyLine polyLine) {
//                                    tmapview.addTMapPath(polyLine);
//                                }
//                            });
//                              자동차경로도 긁어온다
                            tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, point1, point2,
                                    new TMapData.FindPathDataListenerCallback() {
                                        @Override
                                        public void onFindPathData(TMapPolyLine polyLine) {
                                            tmapview.addTMapPath(polyLine);
                                        }
                                    });
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
                            tmapdata.findPathDataWithType(TMapData.TMapPathType.CAR_PATH, point1, tMapPoint,
                                    new TMapData.FindPathDataListenerCallback() {
                                        @Override
                                        public void onFindPathData(TMapPolyLine polyLine) {
                                            tmapview.addTMapPath(polyLine);
                                        }
                                    });
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





    private boolean grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                Log.v(TAG, "Permission is granted");
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
                return true;
                //만약 두개의 퍼미션이 전부 허락되어있다면 true반환
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                //만약 두개의 퍼미션이 전부 안되어 있다면 요청
                return false;
            }
        } else {
            Toast.makeText(this, "External Storage Permission is Grant", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "External Storage Permission is Grant ");
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, mLocationListener);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,mLocationListener);
            return true;
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                finish();
                startActivity(new Intent(this, TmapActivity.class));
            } else {
                Toast.makeText(this, "access fine권한을 거절하셨습니다. 도움길서비스에 제약이 있습니다.", Toast.LENGTH_LONG).show();
            }
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

