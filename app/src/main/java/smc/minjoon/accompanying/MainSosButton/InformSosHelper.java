package smc.minjoon.accompanying.MainSosButton;

/**
 * Created by SY on 2017-10-07.
 */


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import smc.minjoon.accompanying.R;


public class InformSosHelper extends ActionBarActivity implements OnClickListener{



    Button btn[] = new Button[3];

    ViewPager viewPager = null;

    Thread thread = null;

    Handler handler = null;

    int p=0;	//페이지번호

    int v=1;	//화면 전환 뱡향



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.infrom_sos_helper);



        //viewPager

        viewPager = (ViewPager)findViewById(R.id.viewPager);

        MyViewPagerAdapterHelper adapter = new MyViewPagerAdapterHelper(getSupportFragmentManager());



        viewPager.setAdapter(adapter);



        btn[0] = (Button)findViewById(R.id.btn_d);

        btn[1] = (Button)findViewById(R.id.btn_e);

        btn[2] = (Button)findViewById(R.id.btn_f);



        for(int i=0;i<btn.length; i++){

            btn[i].setOnClickListener(this);

        }







//        handler = new Handler(){
//
//
//
//            public void handleMessage(android.os.Message msg) {
//
//                if(p==0){
//
//                    viewPager.setCurrentItem(1);
//
//                    p++;
//
//                    v=1;
//
//                }if(p==1&&v==0){
//
//                    viewPager.setCurrentItem(1);
//
//                    p--;
//
//                }if(p==1&&v==1){
//
//                    viewPager.setCurrentItem(2);
//
//                    p++;
//
//                }if(p==2){
//
//                    viewPager.setCurrentItem(1);
//
//                    p--;
//
//                    v=0;
//
//                }
//
//            }
//
//        };
//
//
//
//        thread = new Thread(){
//
//            //run은 jvm이 쓰레드를 채택하면, 해당 쓰레드의 run메서드를 수행한다.
//
//            public void run() {
//
//                super.run();
//
//                while(true){
//
//                    try {
//
//                        Thread.sleep(2000);
//
//                        handler.sendEmptyMessage(0);
//
//                    } catch (InterruptedException e) {
//
//                        // TODO Auto-generated catch block
//
//                        e.printStackTrace();
//
//                    }
//
//
//
//
//
//                }
//
//            }
//
//        };
//
//        thread.start();



    }



    @Override

    public void onClick(View v) {



        switch(v.getId()){

            case R.id.btn_d:

                viewPager.setCurrentItem(0);

                Toast.makeText(this,"A버튼", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_e:

                viewPager.setCurrentItem(1);

                Toast.makeText(this,"B버튼", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_f:

                viewPager.setCurrentItem(2);

                Toast.makeText(this,"C버튼", Toast.LENGTH_SHORT).show();

                break;

            default:

                break;



        }



    }

}


