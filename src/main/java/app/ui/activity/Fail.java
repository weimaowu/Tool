package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by HSAEE on 2016-12-21.
 */

public class Fail extends Activity{
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.connection_fail);
        imageButton= (ImageButton) findViewById(R.id.fail);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent();
                in.setClass(Fail.this,MainActivity.class);
                startActivity(in);
                Fail.this.finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Fail.this,MainActivity.class);
            startActivity(intent);
            Fail.this.finish();
        }
        return true;
    }
}
