package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HSAEE on 2016-09-12.
 */
public class Volume extends Activity implements View.OnClickListener{
    private String number1="0";
    private String db_1="0.001",db_2="0.001";
    private String it;
    private ImageButton menu;
    private TextView text1,text2;
    private Spinner spinner_1,spinner_2;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    private Button ac,one,two,three,four,five,six,seven,eight,nine,zero,dian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.volume_activity);
        anniu();
        Listdata();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_2.setAdapter(adapter);
        spinner_1.setAdapter(adapter);
        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                db_1=spinnerdata(position);
                settext("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                db_2=spinnerdata(position);
                settext("");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void settext(String str){
        double d=0;
        number1=number1+str;
        Calculator mainActivity=new Calculator();
        text1.setText(mainActivity.Mantissa(String.valueOf(Double.parseDouble(number1))));
        BigDecimal a = new BigDecimal(db_1);
        BigDecimal b = new BigDecimal(db_2);
        BigDecimal c = new BigDecimal(number1);
        text2.setText(String.valueOf(c.multiply(a.divide(b))));
    }
    public String spinnerdata(int i){
        switch (i){
            case 0:
                it="0.001";
                break;
            case 1:
                it="1";
                break;
            case 2:
                it="1000";
                break;
            case 3:
                it="1000000";
                break;

        }
        return it;
    }
    public void Listdata(){
        list.add("立方毫米");
        list.add("立方厘米");
        list.add("立方分米");
        list.add("立方米（升）");
    }
    public void anniu(){
        text1=(TextView) findViewById(R.id.volume_tv1);
        text2=(TextView) findViewById(R.id.volume_tv2);
        spinner_1= (Spinner) findViewById(R.id.v_spinner_1);
        spinner_2= (Spinner) findViewById(R.id.v_spinner_2);
        ac=(Button) findViewById(R.id.v_ac);
        one=(Button) findViewById(R.id.v_one);
        two=(Button) findViewById(R.id.v_two);
        three=(Button) findViewById(R.id.v_three);
        four=(Button) findViewById(R.id.v_four);
        five=(Button) findViewById(R.id.v_five);
        six=(Button) findViewById(R.id.v_six);
        seven=(Button) findViewById(R.id.v_seven);
        eight=(Button) findViewById(R.id.v_eight);
        nine=(Button) findViewById(R.id.v_nine);
        zero=(Button) findViewById(R.id.v_zero);
        dian=(Button) findViewById(R.id.v_dian);
        menu= (ImageButton) findViewById(R.id.menu_v);
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
            case R.id.v_one:
                settext("1");
                break;
            case R.id.v_two:
                settext("2");
                break;
            case R.id.v_three:
                settext("3");
                break;
            case R.id.v_four:
                settext("4");
                break;
            case R.id.v_five:
                settext("5");
                break;
            case R.id.v_six:
                settext("6");
                break;
            case R.id.v_seven:
                settext("7");
                break;
            case R.id.v_eight:
                settext("8");
                break;
            case R.id.v_nine:
                settext("9");
                break;
            case R.id.v_zero:
                settext("0");
                break;
            case R.id.v_dian:
                if (number1.equals("")){settext("0.");
                }else {if (!number1.contains(".")) {settext(".");}
                }
                break;
            case R.id.v_ac:
                number1="0";
                text1.setText("");
                text2.setText("");
                break;
            case R.id.menu_v:
                Intent intent = new Intent();
                intent.setClass(Volume.this,MainActivity.class);
                startActivity(intent);
                Volume.this.finish();
                break;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Volume.this,MainActivity.class);
            startActivity(intent);
            Volume.this.finish();
        }
        return true;
    }
}
