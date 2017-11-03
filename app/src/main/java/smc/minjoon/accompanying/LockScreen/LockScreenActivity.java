package smc.minjoon.accompanying.LockScreen;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import smc.minjoon.accompanying.AutoRepeatButton;
import smc.minjoon.accompanying.LockScreen.News.LockItem;
import smc.minjoon.accompanying.LockScreen.News.LockItemView;
import smc.minjoon.accompanying.MainSettingButton.ContactButton.DBManager;
import smc.minjoon.accompanying.MainSettingButton.ContactButton.SingleItem;
import smc.minjoon.accompanying.LockScreen.News.OpenGraph;
import smc.minjoon.accompanying.LockScreen.News.OpenGraphData;
import smc.minjoon.accompanying.PopupActivity;
import smc.minjoon.accompanying.R;

public class LockScreenActivity extends AppCompatActivity {

    String com;
    DBManager helper;
    ListView listview;
    BroadcastReceiver br;
    private ListViewAdapter2 adapter;
    private ArrayList<LockItem> data=new ArrayList<LockItem>();// 그 정보들 담아줄 공간
    ArrayList<SingleItem> items = new ArrayList<SingleItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);


        helper = new DBManager(this, "Number", null, 1);
        listview=(ListView)findViewById(R.id.listview01);
        adapter = new ListViewAdapter2();
        listview.setAdapter(adapter); //리스트 뷰에 어뎁터 세팅해라
        items= helper.getResult();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(); //
                i.setAction(Intent.ACTION_VIEW);
                LockItem lockItem =adapter.getItem(position);
                i.setData(Uri.parse(lockItem.getLink()));//링크가져오고
                startActivity(i);//그 주소로 간다
            }
        });
        LinearLayout ll = (LinearLayout) findViewById(R.id.layout01);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        final Vibrator vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        AutoRepeatButton btnsos = new AutoRepeatButton(LockScreenActivity.this);
        //여기부터 수정했음
//        ll.width = WindowManager.LayoutParams.WRAP_CONTENT; //버튼의 너비를 설정(픽셀단위로도 지정가능)
//        ll.height = WindowManager.LayoutParams.WRAP_CONTENT; //버튼의 높이를 설정(픽셀단위로도 지정 가능)
//        ll.gravity = Gravity.CENTER; //버튼의 Gravity를 지정

        btnsos.setText("sos"); //버튼에 들어갈 텍스트를 지정(String)
        btnsos.setBackgroundColor(Color.RED);
        //여기까지!

        ll.addView(btnsos);

        btnsos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vide.vibrate(500);
            }
        });
        btnsos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                vide.vibrate(1000);
                Intent intent = new Intent(LockScreenActivity.this, PopupActivity.class);
                intent.putExtra("data", "Test Popup");
                startActivityForResult(intent, 1);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(com.equals("Close")){
                            cancel();
                        }else{
                            for (int i =0; i<items.size(); i++){
                                sendSMS(items.get(i).getNumber(),items.get(i).getContent());
                            }

                        }
                    }
                },10000);
                return true;
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                com = data.getStringExtra("result");

            }
        }
    }




    private void sendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        break;
                }
            }
        };
        registerReceiver(br, new IntentFilter(SENT));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(br);
        }catch(IllegalArgumentException e){

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyAsyncTask().execute("http://www.hani.co.kr/rss/society/");

    }
    class MyAsyncTask extends AsyncTask<String,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data.clear();
        }
        @Override
        protected Void doInBackground(String... uris) {
            try {
                //네트워크 연동
                URL url=new URL(uris[0]);//네트워크 갔다와라
                InputStream is=url.openStream();//문열어서 가져와 xml가져옴

                //xml분석
                //dom불러오는것
                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();//팩토리한테 파스 하나 만들어 주라고함 그럼 팩토리가 무슨 게 있는 지 찾고 거기에 맞춰줌
                DocumentBuilder builder=factory.newDocumentBuilder();//

                Document doc=builder.parse(is);
                Element root=doc.getDocumentElement();//rootelement 전체를 묶어주는 태그를 말한다. 그걸 불러주는거 즉  RSS를 찾아오는 것
                //ITEM태그들을 찾아온다
                NodeList nl=root.getElementsByTagName("item");// 그 전체중에 아이템들만 찾아와라 노드리스트 노드들을 여러개 저장하는것
                //화면에 보여줄 데이터 개수
                int length=(nl.getLength()>20)? 20 : nl.getLength(); // 노드의 길이가 7보다 크냐?? 왜냐 화면에 7개만 보여줄려

                //화면에 보여줄 데이터 구함
                for(int i=0; i<5; i++){
                    //각 ITEM 태그 안에서 TITLE LINK DESCRIPTION의 내용을 꺼내기
                    Element e=(Element)nl.item(i);//첫번째 아이템 꺼내와
                    String title=e.getElementsByTagName("title").item(0).getTextContent();//타이틀을 꺼내라
                    String link =e.getElementsByTagName("link").item(0).getTextContent();

                    String desc =e.getElementsByTagName("description").item(0).getTextContent();
                    final OpenGraph openGraph = new OpenGraph.Builder(link)
                            .logger(new OpenGraph.Logger() {
                                @Override
                                public void log(String tag, String msg) {
                                    // print log
                                }
                            })
                            .build();
                    final OpenGraphData data1 = openGraph.getOpenGraph();
                    data1.getImage();
//                    try{
//                        URL myFileUrl = new URL(data1.getImage());
//                        HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
//                        conn.setDoInput(true);
//                        conn.connect();
//
//                        InputStream is2 = conn.getInputStream();
//
//                        bmImg = BitmapFactory.decodeStream(is2);
//
//
//                    }catch(IOException a){
//                        a.printStackTrace();
//                    }
                    LockItem lockItem =new LockItem(title, link, desc,data1.getImage());

                    data.add(lockItem);//어레이 리스트에 그 데이터를 담아준다.

                }
            } catch (ParserConfigurationException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (SAXException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //작업 종료시에 화면 다시 표시
            adapter.notifyDataSetChanged();
            Toast.makeText(LockScreenActivity.this, "load ok", Toast.LENGTH_SHORT).show();
        }
    }
    public class ListViewAdapter2 extends BaseAdapter { //  여기서 data 즉 items 관리 및 getview 해준다.
//        public ArrayList<SingleItem> items2 = new ArrayList<SingleItem>();

        @Override
        public int getCount() {
            return data.size();
        } // getview얼마나 해줄껀지 결정

        public void addItem(LockItem lockItem) { // listview add 할껀데 여기서는 필요 없다.
            data.add(lockItem);
        }

        public void update(int position, LockItem lockItem) { // listview update하는건데 여기서는 필요 없다.
            data.set(position, lockItem);
        }

        @Override
        public LockItem getItem(int position) {

            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LockItemView view = new LockItemView(getApplicationContext()); // item.xml을 id로 가져올 수 있도록 즉 쓸 수 있도록 만든 클래스를 가져온다.
            view.tv01.setTextColor(Color.BLACK);
            final LockItem curltem = data.get(position); // curItem이라는 객체에 items 각각의 position으로 객체정보 넣어줌

            view.setTitle(curltem.getTitle()); // 그리고 그 각각의 item에 title만들어 준다.
            Picasso.with(LockScreenActivity.this).load(curltem.getImagelink()).into(view.iv01);

//            view.btn01.setOnClickListener(new View.OnClickListener() { // 삭제 버튼 눌렀을 때
//                @Override
//                public void onClick(View v) {
////                    items.remove(position);
//
//                }
//            });
            return view;
        }
    }

}

