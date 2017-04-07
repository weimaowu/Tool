package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HSAEE on 2016/9/9.
 */
public class Currencies extends Activity implements View.OnClickListener{
    private String number1="0";
    private double number2=0.1497,text;
    private TextView text1,text2;
    private ImageButton menu;
    private Spinner spinner_1,spinner_2;
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private Button ac,one,two,three,four,five,six,seven,eight,nine,zero,dian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currencies);
        info();
        listdata();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_2.setAdapter(adapter);
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        number2=0.1497;
                        settext("");
                        break;
                    case 1:
                        number2=0.1329;
                        settext("");
                        break;
                    case 2:
                        number2=0.1126;
                        settext("");
                        break;
                    case 3:
                        number2=15.2948;
                        settext("");
                        break;
                    case 4:
                        number2=164.8711;
                        settext("");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.C_one:
                settext("1");
                break;
            case R.id.C_two:
                settext("2");
                break;
            case R.id.C_three:
                settext("3");
                break;
            case R.id.C_four:
                settext("4");
                break;
            case R.id.C_five:
                settext("5");
                break;
            case R.id.C_six:
                settext("6");
                break;
            case R.id.C_seven:
                settext("7");
                break;
            case R.id.C_eight:
                settext("8");
                break;
            case R.id.C_nine:
                settext("9");
                break;
            case R.id.C_zero:
                settext("0");
                break;
            case R.id.C_dian:
                if (number1.equals("")){settext("0.");
                }else {if (!number1.contains(".")) {settext(".");}
                }
                break;
            case R.id.C_ac:
                number1="0";
                text1.setText("");
                text2.setText("");
                break;
            case R.id.menu_C:
                Intent intent = new Intent();
                intent.setClass(Currencies.this,MainActivity.class);
                startActivity(intent);
                Currencies.this.finish();
                break;

        }

    }
    private void info(){
        text1=(TextView) findViewById(R.id.currencies_tv1);
        text2=(TextView) findViewById(R.id.currencies_tv2);
        spinner_2= (Spinner) findViewById(R.id.C_spinner_2);
        ac=(Button) findViewById(R.id.C_ac);
        one=(Button) findViewById(R.id.C_one);
        two=(Button) findViewById(R.id.C_two);
        three=(Button) findViewById(R.id.C_three);
        four=(Button) findViewById(R.id.C_four);
        five=(Button) findViewById(R.id.C_five);
        six=(Button) findViewById(R.id.C_six);
        seven=(Button) findViewById(R.id.C_seven);
        eight=(Button) findViewById(R.id.C_eight);
        nine=(Button) findViewById(R.id.C_nine);
        zero=(Button) findViewById(R.id.C_zero);
        dian=(Button) findViewById(R.id.C_dian);
        menu= (ImageButton) findViewById(R.id.menu_C);
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
    private void listdata(){
        list.add("美元");
        list.add("英镑");
        list.add("欧元");
        list.add("日币");
        list.add("韩元");
    }

    private void settext(String str){
        number1=number1+str;
        text=Double.parseDouble(number1);
        text1.setText(String.valueOf(text));
        text2.setText(currencies(number1));
    }
    private String currencies(String str){
        String s="";double money=0;
        if (str.charAt(str.length()-1)=='.')
        {str=str.substring(0,str.length()-1);}
        money=Double.parseDouble(str);
        s=String.valueOf(money*number2);
        int i = s.indexOf(".");
        if (s.length()-i>3){
            if (s.charAt(i + 3) >= '5') {
                s=s.substring(0,i+3);
                Log.v("",s);
                money=Double.parseDouble(s)+0.01;
                s=String.valueOf(money);
                if (s.length()-i>3){
                    s=s.substring(0,i+3);
                }
                money=Double.parseDouble(s);
            }else {s=s.substring(0,i+3);money=Double.parseDouble(s);}
        } else {s=s.substring(0,i+2);
            money=Double.parseDouble(s);
            }
        return String.valueOf(money);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Currencies.this,MainActivity.class);
            startActivity(intent);
            Currencies.this.finish();
        }
        return true;
    }
}
