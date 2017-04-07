package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import app.model.imple.NumberToCN;

/**
 * Created by HSAEE on 2016/9/6.
 */
public class Capitals extends Activity implements View.OnClickListener {
    String number1="";
    TextView text1,text2;
    ImageButton menu;
    Button ac,one,two,three,four,five,six,seven,eight,nine,zero,dian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.capitals);
        text1=(TextView) findViewById(R.id.daxie1);
        text2=(TextView) findViewById(R.id.daxie2);
        ac=(Button) findViewById(R.id.D_ac);
        one=(Button) findViewById(R.id.D_one);
        two=(Button) findViewById(R.id.D_two);
        three=(Button) findViewById(R.id.D_three);
        four=(Button) findViewById(R.id.D_four);
        five=(Button) findViewById(R.id.D_five);
        six=(Button) findViewById(R.id.D_six);
        seven=(Button) findViewById(R.id.D_seven);
        eight=(Button) findViewById(R.id.D_eight);
        nine=(Button) findViewById(R.id.D_nine);
        zero=(Button) findViewById(R.id.D_zero);
        dian=(Button) findViewById(R.id.D_dian);
        menu= (ImageButton) findViewById(R.id.menu_D);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        ac.setOnClickListener(this);
        dian.setOnClickListener(this);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        menu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.D_one:
                settext("1");
                break;
            case R.id.D_two:
                settext("2");
                break;
            case R.id.D_three:
                settext("3");
                break;
            case R.id.D_four:
                settext("4");
                break;
            case R.id.D_five:
                settext("5");
                break;
            case R.id.D_six:
                settext("6");
                break;
            case R.id.D_seven:
                settext("7");
                break;
            case R.id.D_eight:
                settext("8");
                break;
            case R.id.D_nine:
                settext("9");
                break;
            case R.id.D_zero:
                settext("0");
                break;
            case R.id.D_dian:
                if (number1.equals("")){settext("0.");
                }else {if (!number1.contains(".")) {settext(".");}
                }
                break;
            case R.id.D_ac:
                number1="";
                text1.setText("");
                text2.setText("");
                break;
            case R.id.menu_D:
                Intent intent = new Intent();
                intent.setClass(Capitals.this,MainActivity.class);
                startActivity(intent);
                Capitals.this.finish();
                break;
        }

    }
    public void settext(String str){

        number1=number1+str;
        text1.setText(number1);
        NumberToCN numberToCN=new NumberToCN(number1);

        text2.setText(numberToCN.Transformation());
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Capitals.this,MainActivity.class);
            startActivity(intent);
            Capitals.this.finish();
        }
        return true;
    }
}
