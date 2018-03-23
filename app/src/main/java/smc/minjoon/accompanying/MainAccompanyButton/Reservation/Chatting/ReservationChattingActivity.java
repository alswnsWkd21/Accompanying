package smc.minjoon.accompanying.MainAccompanyButton.Reservation.Chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import smc.minjoon.accompanying.Login.SessionManager;
import smc.minjoon.accompanying.R;

public class ReservationChattingActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    Button sendButton;
    Button backBtn;
    ArrayAdapter adapter;
    String userID;
    String helperID;
    String reservationID;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_chatting);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        listView = (ListView) findViewById(R.id.listview);
        editText = (EditText) findViewById(R.id.edittext);
        sendButton = (Button) findViewById(R.id.button);
        backBtn = (Button) findViewById(R.id.backBtn);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);
        final SessionManager sessionmanager = new SessionManager(ReservationChattingActivity.this);

        if(sessionmanager.getKeyKind().equals("helper")){

            Intent intent = getIntent();
            userID=intent.getStringExtra("userID");
            helperID =intent.getStringExtra("helperID");
            reservationID = intent.getStringExtra("reservationID");

            databaseReference.child(reservationID+helperID).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                    adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
                    listView.setSelection(adapter.getCount()-1);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ChatData chatData = new ChatData(helperID, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
                    databaseReference.child(reservationID+helperID).push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                    editText.setText("");
                }
            });

        }else{
            Intent i = getIntent();
            userID=i.getStringExtra("userID");
            helperID =i.getStringExtra("helperID");
            reservationID = i.getStringExtra("reservationID");
            databaseReference.child(reservationID+helperID).addChildEventListener(new ChildEventListener() {  // message는 child의 이벤트를 수신합니다.
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatData chatData = dataSnapshot.getValue(ChatData.class);  // chatData를 가져오고
                    adapter.add(chatData.getUserName() + ": " + chatData.getMessage());  // adapter에 추가합니다.
                    listView.setSelection(adapter.getCount()-1);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) { }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });

            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ChatData chatData = new ChatData(userID, editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기
                    databaseReference.child(reservationID+helperID).push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                    editText.setText("");
                }
            });
        }


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
//                여기에는 완료로 바꿔줘야 한다.그리고 시간도!!
            }
        });
    }
}
