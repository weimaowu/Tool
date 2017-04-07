package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import app.presenter.MyAdvertisement;
import app.presenter.Parser;

/**
 * Created by HSAEE on 2016-12-02.
 */

public class Expressage extends Activity{
    private EditText editText ;
    private ImageButton imageButton;
    private Spinner spinner;
    private Button button;
    private TextView textView,textView2;
    private String number="shentong";
    private String number2="申通";
    String string=null;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.expressage);
        textView2= (TextView) findViewById(R.id.textView3);
        imageButton= (ImageButton) findViewById(R.id.exp_menu);
        editText= (EditText) findViewById(R.id.exp_ed);
        button= (Button) findViewById(R.id.exp_btn);
        textView= (TextView) findViewById(R.id.exp_tv);
        spinner= (Spinner) findViewById(R.id.exp_spinner);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.spinner_item,list_data());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Expressage.this,MainActivity.class);
                startActivity(intent);
                Expressage.this.finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handler =new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String stringdata = msg.obj.toString();
                        textView.setText(stringdata);
                    }
                };
                Parser parser=new Parser(number,editText.getText().toString());
                parser.expresult(handler);

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view;
                tv.setTextSize(23.0f);    //设置大小
//                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                switch (position){
                    case 0:
                        number="shentong";
                        number2="申通";
                        break;
                    case 1:
                        number="ems";
                        number2="EMS";
                        break;
                    case 2:
                        number="shunfeng";
                        number2="顺丰";
                        break;
                    case 3:
                        number="yuantong";
                        number2="圆通";
                        break;
                    case 4:
                        number="zhongtong";
                        number2="中通";
                        break;
                    case 5:
                        number="yunda";
                        number2="韵达";
                        break;
                    case 6:
                        number="tiantian";
                        number2="天天";
                        break;
                    case 7:
                        number="huitongkuaidi";
                        number2="汇通";
                        break;
                    case 8:
                        number="quanfengkuaidi";
                        number2="全峰";
                        break;
                    case 9:
                        number="debangwuliu";
                        number2="德邦";
                        break;
                    case 10:
                        number="zhaijisong";
                        number2="急宅送";
                        break;

                }
                textView2.setText("已经选择："+number2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        handler.post(new Runnable() {
            @Override
            public void run() {
                MyAdvertisement advertisement=new MyAdvertisement();
                advertisement.request(Expressage.this);
            }
        });
    }
    private ArrayList<String> list_data(){
        ArrayList<String> list=new ArrayList<String>();
        list.add("申通");
        list.add("EMS");
        list.add("顺丰");
        list.add("圆通");
        list.add("中通");
        list.add("韵达");
        list.add("天天");
        list.add("汇通");
        list.add("全峰");
        list.add("德邦");
        list.add("宅急送");
        return list;
    }
    private String request(String str,String string){
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = "http://www.kuaidi100.com/query?type="+str+"&postid="+string;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(1000);
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("===",result);
        return result;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Expressage.this,MainActivity.class);
            startActivity(intent);
            Expressage.this.finish();
        }
        return true;
    }
}
