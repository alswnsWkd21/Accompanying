package smc.minjoon.accompanying.LockScreen.News;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import smc.minjoon.accompanying.R;

/**
 * Created by skaqn on 2017-01-26.
 */

public class LockItemView extends LinearLayout {
    public TextView tv01;
    public ImageView iv01;

    public LockItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // item.xml을 !!inflater을 해줘야 Activity에서 쓸 수 있다. Activity랑 연결된 xml들은       setContentView(R.layout.activity_main); 이 함수를 통해서 inflater된다.
        inflater.inflate(R.layout.lockitem, this, true);
        tv01 = (TextView) findViewById(R.id.tv01);
        iv01 = (ImageView) findViewById(R.id.iv01);

    }
    public void setTitle(String title) {
        tv01.setText(title);
    }

    public void setIv01(Bitmap img) {
        iv01.setImageBitmap(img);
    }
}
