package smc.minjoon.accompanying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfigActivity extends AppCompatActivity{

    private Button onBtn, offBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        onBtn= (Button)findViewById(R.id.btn12);
        offBtn= (Button)findViewById(R.id.btn13);

        onBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                startService(intent);
            }});

         offBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ConfigActivity.this, ScreenService.class);
                    stopService(intent);
                }});
        }
}
