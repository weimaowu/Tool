package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.model.imple.Readcity;


/**
 * Created by HSAEE on 2016-12-13.
 */

public class Selectcity extends Activity{
    ImageButton image;
    EditText editText;
    ListView listView;

    ArrayList<String> arrayList=new ArrayList<String>();
    ArrayList<String> list=new ArrayList<String>();
    Readcity read=new Readcity();
    ArrayAdapter<String> adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather2);

        image= (ImageButton) findViewById(R.id.backImage);
        editText= (EditText) findViewById(R.id.seek);
        listView= (ListView) findViewById(R.id.myAllCityList);
        arrayList=read.city(Selectcity.this);
        getadapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                地区ID
                String cityid=read.chengshi(Selectcity.this).get(read.city(Selectcity.this).indexOf(arrayList.get(i)));
                Intent intent=new Intent();
                intent.putExtra("city",arrayList.get(i));
                intent.putExtra("cityid",cityid);
                intent.setClass(Selectcity.this, Weather.class);
                startActivity(intent);
                Selectcity.this.finish();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Handler hand=new Handler();
                hand.post(new Runnable() {
                    @Override
                    public void run() {
                        String str=editText.getText().toString();
                        getdata(str);
                        getadapter();
                    }
                });


            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Selectcity.this,MainActivity.class);
                startActivity(intent);
                Selectcity.this.finish();
            }
        });

        Toast.makeText(this,"定位失败，请手动选择城市",Toast.LENGTH_SHORT);
    }

    private void getadapter(){
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,arrayList);
        listView.setAdapter(adapter);
    }
    private void getdata(String s){
        arrayList=read.city(Selectcity.this);
        list.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).contains(s)){
                list.add(arrayList.get(i));
            }
        }
        arrayList=list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(Selectcity.this,MainActivity.class);
            startActivity(intent);
            Selectcity.this.finish();
        }
        return true;
    }
}
