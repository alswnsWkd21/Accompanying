package smc.minjoon.accompanying.MainSettingButton.ContactButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import smc.minjoon.accompanying.R;

public class NumberActivity extends AppCompatActivity {
    final public static int WRITE = 0;
    final public static int EDIT = 0;
    final public static int DELETE = 1;
    final public static int VIEW = 2;

    public ListViewAdapter adapter;
    private ListView listview01;
    int index;
    String status;
    String status2;
    DBManager helper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        listview01 = (ListView) findViewById(R.id.listview01);
        adapter = new ListViewAdapter();
        helper = new DBManager(this, "Number", null, 1); // SQLiteOpenHelper 객체 생성하면서 데이터베이스 및 테이블 생성 또 부가적인 기능들 생성

        adapter.items = helper.getResult();// 데이터베이스에 있는 정보들 adaper안의 Arraylist items에 모두 들여놓았다!!
        listview01.setAdapter(adapter);// listview에 adapter셋팅
//        add("sdfsdf","sdfsdf");
        Button btn01 = (Button) findViewById(R.id.btn01);
        Button btn02 = (Button) findViewById(R.id.btn02);
        Button btn03 = (Button) findViewById(R.id.btn03);

        listview01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingleItem item = adapter.items.get(position);                // listview item눌렀을 때 그 누른 item position을 가져와서 items에서 그 position을 토대로 item정보 찾아와서 item객체에 넣어준다.

                index = position;
                Intent intent = new Intent(NumberActivity.this, ContentActivity.class); // intent 메인화면에서 content화면으로 설정
                intent.putExtra("item", item); // 아까 가져온 객체를 content화면으로 넘긴다
                intent.putExtra("position", position);
                startActivityForResult(intent, 1);// 결과값을 받을 수 있는 함수 request 번호는 1번
            }
        });

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 글쓰기 버튼을 눌렀을 때 noteedit클래스로 가게 만들었다. 결과값받을 수 있는 함수를 써야 noteedit에서 쓴 정보를 다시 돌려받아서 listview에 뿌릴 수 있다.
                Intent intent = new Intent(NumberActivity.this, NoteeditActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status="ok";
                for(int i=0; i<adapter.items.size(); i++){
                    if(adapter.items.get(i).getTitle().equals("112")){
                        status = "no";
                        AlertDialog.Builder alert = new AlertDialog.Builder(NumberActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialo, int which) {

                                dialo.dismiss();
                            }
                        });
                        alert.setMessage("이미 112를 추가하셨습니다.");
                        alert.setCancelable(false);
                        alert.show();
                 }
                }
                if(status.equals("ok")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(NumberActivity.this);
                    alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialo, int which) {
                            helper.insert("112", "장애인의 도움요청입니다. 위치를 확인해주세요", "112");
                            adapter.items=helper.getResult(); // Database에 있는 모든 값들을 Arraylist items에 넣는다.
                            adapter.notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialo, int which) {
                            dialo.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("sos기능 실행 시 112에도 sos요청을 보낼 수 있습니다. 119 기능을 실행 시 장난으로 sos요청하는 것을 엄격히 금합니다. 112를 추가하시겠습니까?");
                    alert.setCancelable(false);
                    alert.show();
                }
        }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status2="ok";
                for(int i=0; i<adapter.items.size(); i++){
                    if(adapter.items.get(i).getTitle().equals("119")){
                        status2 = "no";
                        AlertDialog.Builder alert = new AlertDialog.Builder(NumberActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialo, int which) {

                                dialo.dismiss();
                            }
                        });
                        alert.setMessage("이미 119를 추가하셨습니다.");
                        alert.setCancelable(false);
                        alert.show();
                    }
                }
                if(status2.equals("ok")){
                    AlertDialog.Builder alert = new AlertDialog.Builder(NumberActivity.this);
                    alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialo, int which) {
                            helper.insert("119", "장애인의 도움요청입니다. 위치를 확인해주세요", "119");
                            adapter.items=helper.getResult(); // Database에 있는 모든 값들을 Arraylist items에 넣는다.
                            adapter.notifyDataSetChanged();
                        }
                    });
                    alert.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialo, int which) {
                            dialo.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("sos기능 실행 시 119에도 sos요청을 보낼 수 있습니다. 119 기능을 실행 시 장난으로 sos요청하는 것을 엄격히 금합니다. 119를 추가하시겠습니까?");
                    alert.setCancelable(false);
                    alert.show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // startActivitiyForResult함수를 썼 을 때 돌려받을 때 모든 돌려받는 값들이 이 함수로 온다.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) { // requestCode 0 이였던 것들 여기로 들어온다. 즉 글쓰기 버튼누를 때
            if (resultCode == 0) { //requestCode 0 and resultCode 0 인 것 즉  글쓰기 버튼 누르고 저장 한 값들 여기로 온다.
                try {
                    SingleItem item = (SingleItem) data.getSerializableExtra("item"); // noteedit 화면에 날라온 item이라는 객체를 item객체에 넣어준다.
                    String title = item.getTitle(); //item객체에 담겨있는 title  get해서 변수에 넣어준다.
                    String content = item.getContent(); // item객체에 담겨있는 conten 를 get해서 변수에 넣어준다.
                    String number = item.getNumber();


//
//fillData()

//                    adapter.addItem(item);
                    helper.insert(title, content, number); // DBManager에 구현된 메서드로 데이터베이스 추가하는 함수다
                    // items를 다 지운다    why!!!!!!!!!!!!!!!  다시 데이터베이스 값 다 가져올라고 이거 안하고 가져오면 겹치는 일 발생 할 까봐  // 오잉 이거 안해도 되넿ㅎ
                    adapter.items=helper.getResult(); // Database에 있는 모든 값들을 Arraylist items에 넣는다.
                    adapter.notifyDataSetChanged();// adapter를 갱신한다. 그럼 다시 items 들 listview에 뿌려주겠지???    즉 여기 있는 코드들은 database에 정보추가해주고 다시 그 database가지고 listview 리셋시키는 과정
                } catch (Exception e) {
                    e.printStackTrace();
                }
//        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();

            } else if (resultCode == 1) {

            }
        } else if (requestCode == 1) { // requestCode 1인 곳 즉 listview item을 클릭했을 때
            if (resultCode == 0) {  // 즉 리스트뷰 클릭해서 수정 하면 여기로 결과 값 들어온다.
                try {
                    SingleItem item = (SingleItem) data.getSerializableExtra("item"); // noteedit 화면에 날라온 item이라는 객체를 item객체에 넣어준다.

//                    adapter.update(index, item);
                    helper.update(item.get_id(), item.getTitle(), item.getContent(), item.getNumber()); // 데이터베이스 업데이트 하는 함수
                    // 위에랑 똑같은과정 이하생략
                    adapter.items=helper.getResult();
                    adapter.notifyDataSetChanged();
//
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 1) {
                adapter.items=helper.getResult();
                adapter.notifyDataSetChanged();

            }
        }


    }

    private void fillData() {
        // Get all of the rows from the database and crea
        // e the item list
        adapter = new ListViewAdapter();
        listview01.setAdapter(adapter);
    }


    public class ListViewAdapter extends BaseAdapter { //  여기서 data 즉 items 관리 및 getview 해준다.
        public ArrayList<SingleItem> items = new ArrayList<SingleItem>();

        @Override
        public int getCount() {
            return items.size();
        } // getview얼마나 해줄껀지 결정

        public void addItem(SingleItem item) { // listview add 할껀데 여기서는 필요 없다.
            items.add(item);
        }

        public void update(int position, SingleItem item) { // listview update하는건데 여기서는 필요 없다.
            items.set(position, item);
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            SingleItemView view = new SingleItemView(getApplicationContext()); // item.xml을 id로 가져올 수 있도록 즉 쓸 수 있도록 만든 클래스를 가져온다.
            view.tv01.setTextColor(Color.BLACK);
            final SingleItem curltem = items.get(position); // curItem이라는 객체에 items 각각의 position으로 객체정보 넣어줌

            view.setTitle(curltem.getTitle()); // 그리고 그 각각의 item에 title만들어 준다.

            view.btn01.setOnClickListener(new View.OnClickListener() { // 삭제 버튼 눌렀을 때
                @Override
                public void onClick(View v) {
//                    items.remove(position);
                    helper.delete(curltem.get_id()); // _id를 이용해서 데이터베이스에서 정보 삭제  Arraylist items 각각의 item 는 이미 database를 getResult해서 넣은 정보들 이기 때문에 _id 가  들어가 있다. 그걸이요해서 데이터 베이스에서 지운다.
                    adapter.items.clear(); // 이하 생략
                    adapter.items=helper.getResult();
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
