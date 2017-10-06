package smc.minjoon.accompanying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static smc.minjoon.accompanying.R.layout.item;

public class NoteeditActivity extends AppCompatActivity {
    EditText et01;
    EditText et02;
    EditText et03;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteedit);
        Intent intent = getIntent();
        et01 = (EditText) findViewById(R.id.et01);
        et02 = (EditText) findViewById(R.id.et02);
        et03 = (EditText) findViewById(R.id.et03);
        if (intent.getSerializableExtra("item") != null) { // 만약 보내진 정보 있다면 즉!!! content에서 수정버튼으로 여기에 온거라면 data읽어라!!!!! 그리고 읽은데이터 editText에 보여줘라 !!!
            SingleItem item = (SingleItem) intent.getSerializableExtra("item");
            String title=(String)item.getTitle().toString();
            String content=(String)item.getContent().toString();
            String number =(String)item.getNumber().toString();
            id = item.get_id();
            et01.setText(title);
            et02.setText(content);
            et03.setText(number);
        }


        Button btn01 = (Button) findViewById(R.id.btn01);
        Button btn02 = (Button) findViewById(R.id.btn02);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // SAVE버튼 눌렀을 때
                String title = et01.getText().toString();  // et01에 있는 글자 가져와서 title에
                String content = et02.getText().toString(); // et02에 있는 글자 가져와서 content에
                String number = et03.getText().toString();
                SingleItem item = new SingleItem();  // item을 만들어서 전에 여기로 보내졌던 item의 _id 여기 객체에 넣고 !! why!!! 업데이트 할 때 _id로 위치 알아내니까!! edittext에 쓰였던 title content item 객체에 넣어라
                item.set_id(id);
                item.setTitle(title);
                item.setContent(content);
                item.setNumber(number);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("item", item);  // 그리고 넣은 데이터 보낸다

                //이부분이 Main에서 왔으면 Main으로가고 content에서 왔으면 content로 가는것 같다.
                setResult(0, intent); // 그냥보내는거 아니고 돌려보내는거!!
                finish();

            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// CANCLE버튼이면 그냥 닫아라
                finish();
            }
        });
    }
}

