package smc.minjoon.accompanying.MainSettingButton.ContactButton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import smc.minjoon.accompanying.R;

public class ContentActivity extends AppCompatActivity {


    SingleItem item;
    TextView tv02;
    TextView tv03;
    TextView tv04;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);
        final DBManager helper = new DBManager(this, "NOTE7", null, 1);
        final Intent intent = getIntent();
        item = (SingleItem) intent.getSerializableExtra("item");  // listview item클릭해서 왔지 그 listview 에 있던 item정보를 받아서

        tv02 = (TextView) findViewById(R.id.tv02);
        tv03 = (TextView) findViewById(R.id.tv03);
        tv04 = (TextView) findViewById(R.id.tvnum02);



        tv02.setText(item.getTitle());// textview에 뿌려준다.
        tv03.setText(item.getContent());// textview에 뿌려준다.
        tv04.setText(item.getNumber());

        Button btn01 = (Button) findViewById(R.id.btn01);
        Button btn02 = (Button) findViewById(R.id.btn02);
        Button btn03 = (Button) findViewById(R.id.btn03);

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {  // 수정 버튼 눌럿을 때
                Intent intent = new Intent(getApplicationContext(), NoteeditActivity.class);
                intent.putExtra("item", item);//content로 보내진 data를 다시 noteedit로 보낸다.
                startActivityForResult(intent, 0);
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 삭제 버튼 눌럿을 때
                int _id = item.get_id();
                helper.delete(_id);
                setResult(1, null);
                finish();


            }
        });
        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//목록버튼 눌럿을 때 그냥 이창을 종료하면 Main화면으로 간다.ㅎ
//               Intent intent =new Intent(getApplicationContext(), MainActivity.class);

                finish();

////                startActivity(intent);
//                setResult(1,intent);
//                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {   // 수정을 통하여 noteedit로 보냈을 때
            if (resultCode == 0) { // noteedit에서 정보가 날라오면 여기로0
                try {
                    //수정을 눌러서 저장했을 때 content창 수정해주는 코드 그리고 수정된 아이템 바로 Main에 보낸다.
//                    Intent intent = getIntent();
                    item = (SingleItem) data.getSerializableExtra("item");  // noteedit에서 보낸 수정된 정보를 여기서 받고 textview에 다시 뿌려준다.
                    tv02.setText(item.getTitle());
                    tv03.setText(item.getContent());
                    tv04.setText(item.getNumber());
                    Intent i = new Intent();
                    i.putExtra("item", item); // 여기서 끝이 아니라 그 수정된 정보를 여기만 뿌려주는게 아니라 MainActivity에도 뿌려주어야 한다.


                    // 이 데이터 전달!!!
                    setResult(0, i); // 다시 MainActivity로 전해주는 코드
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
