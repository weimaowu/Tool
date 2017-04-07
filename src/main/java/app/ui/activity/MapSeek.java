package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HSAEE on 2017-03-17.
 */

public class MapSeek extends Activity implements View.OnClickListener{
    private ListView mListView;
    private List<String> seeklist;
    private Button seek_btn,grogshop_btn,cate,bank,cybercafe,sight,supermarket,hospital,
            bath,toilet,expressage,gasStation;
    private  String matter;
    private EditText ed;
    private ImageButton imageButton;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapseek);


        info();
        seeklist=getseekName();
        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,seeklist);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tz(seeklist.get(i));
            }
        });
    }

    private void info() {
        mListView= (ListView) findViewById(R.id.seek_list);
        ed= (EditText) findViewById(R.id.ed_text);
        seek_btn= (Button) findViewById(R.id.seek_btn);
        grogshop_btn= (Button) findViewById(R.id.grogshop_btn);
        cate= (Button) findViewById(R.id.cate);
        bank= (Button) findViewById(R.id.bank);
        cybercafe= (Button) findViewById(R.id.cybercafe);
        sight= (Button) findViewById(R.id.sight);
        supermarket= (Button) findViewById(R.id.supermarket);
        hospital= (Button) findViewById(R.id.hospital);
        bath= (Button) findViewById(R.id.bath);
        toilet= (Button) findViewById(R.id.toilet);
        expressage= (Button) findViewById(R.id.expressage);
        gasStation= (Button) findViewById(R.id.gasStation);
        imageButton= (ImageButton) findViewById(R.id.menu_v);
        seek_btn.setOnClickListener(this);
        grogshop_btn.setOnClickListener(this);
        cate.setOnClickListener(this);
        bank.setOnClickListener(this);
        cybercafe.setOnClickListener(this);
        supermarket.setOnClickListener(this);
        hospital.setOnClickListener(this);
        bath.setOnClickListener(this);
        toilet.setOnClickListener(this);
        expressage.setOnClickListener(this);
        gasStation.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }


    private void tz(String s){
        adapter.setNotifyOnChange(true);
        Intent intent=new Intent();
        intent.putExtra("matter",s);
        intent.setClass(MapSeek.this,Attribution.class);
        startActivity(intent);
        MapSeek.this.finish();
    }
    private void setseekName(String string) {
        ArrayList<String> data;
        String str = "";
        data=getseekName();
        if (data.contains(string)) {
            data.remove(string);
        }
        if (data.size()>30){
            data.remove(29);
        }
        for (int i = 0; i < data.size(); i++) {
            str=str+data.get(i).toString()+"\r\n";
        }
        str=string+"\r\n"+str;
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("seek.txt", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private ArrayList<String> getseekName(){
        FileInputStream in = null;
        BufferedReader reader = null;
        ArrayList<String> list=new ArrayList<String>();
        try {
            in = openFileInput("seek.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {

                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.seek_btn:
                matter=ed.getText().toString();
                setseekName(matter);
                tz(matter);
                break;
            case R.id.grogshop_btn:
                matter="酒店";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.cate:
                matter="美食";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.bank:
                matter="银行";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.cybercafe:
                matter="网吧";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.sight:
                matter="景点";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.supermarket:
                matter="超市";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.hospital:
                matter="医院";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.bath:
                matter="洗浴";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.toilet:
                matter="厕所";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.expressage:
                matter="快递";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.gasStation:
                matter="加油站";
                setseekName(matter);
                tz(matter);
                break;
            case R.id.menu_v:
                Intent intent=new Intent();
                intent.setClass(MapSeek.this,Attribution.class);
                startActivity(intent);
                MapSeek.this.finish();
                break;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(MapSeek.this,Attribution.class);
            startActivity(intent);
            MapSeek.this.finish();
        }
        return true;
    }
}
