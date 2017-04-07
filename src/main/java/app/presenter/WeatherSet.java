package app.presenter;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import app.ui.activity.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by HSAEE on 2017-04-05.
 */

public class WeatherSet {
    private Context context;
    public WeatherSet( Context context){

        this.context=context;
    }
    public int data(String str){

        int []icon={R.drawable.weather_1,R.drawable.weather_2,R.drawable.weather_3,R.drawable.weather_4,R.drawable.weather_5
                ,R.drawable.weather_6,R.drawable.weather_7,R.drawable.weather_8,R.drawable.weather_9,R.drawable.weather_10
                ,R.drawable.weather_11,R.drawable.weather_12};
        int png=0;
        switch (Integer.parseInt(str)){
            case 900:
            case 901:
            case 999:
            case 100:
            case 200:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
            case 209:
            case 210:
            case 211:
            case 212:
            case 213:
                png=icon[8];
                break;
            case 101:
            case 102:
                png=icon[0];
                break;
            case 103:
                png=icon[1];
                break;

            case 104:
                png=icon[5];
                break;
            case 300:
            case 301:
                png=icon[11];
                break;
            case 302:
            case 303:
                png=icon[10];
                break;

            case 304:
            case 305:
            case 306:
            case 307:
            case 308:
            case 309:
            case 310:
            case 311:
            case 312:
            case 313:
                png=icon[6];
                break;
            case 400:
            case 401:
            case 402:
            case 403:

            case 404:
            case 405:
            case 406:
                png=icon[4];
                break;
            case 407:
                png=icon[3];
                break;
            case 500:
            case 501:
                png=icon[9];
                break;
            case 502:
            case 503:
            case 504:
            case 505:
            case 506:
            case 507:
            case 508:
                png=icon[7];
                break;



        }
        return png;
    }

    public int detection(String cityid){
        ArrayList<Map<String,String>> data;
        data=load();
        int item=-1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).get("cityid").equals(cityid)){
                item=i;
                break;
            }
        }
        return item;
    }
    public void save(String cityid, String city, String temp, String icon) {
        ArrayList<Map<String,String>> data;
        String str = "";
        data=load();
        if (data.size()>0) {
            for (int i = 0; i < data.size(); i++) {
                str=str+
                        data.get(i).get("cityid")+",,"+
                        data.get(i).get("city")+",,"+
                        data.get(i).get("temp")+",,"+
                        data.get(i).get("icon")+"\r\n";
            }
        }
        str=cityid+",,"+city+",,"+temp+",,"+icon+"\r\n"+str;
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("message.txt", MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            Log.d("打印2",str);
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
    public void removedata(int item){
        ArrayList<Map<String,String>> data;
        String str = "";
        data=load();
        data.remove(item);
        if (data.size()>0) {
            for (int i = 0; i < data.size(); i++) {
                str=str+
                        data.get(i).get("cityid")+",,"+
                        data.get(i).get("city")+",,"+
                        data.get(i).get("temp")+",,"+
                        data.get(i).get("icon")+"\r\n";
            }
        }
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput("message.txt", MODE_PRIVATE);
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
    public ArrayList<Map<String,String>> load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
        try {
            in = context.openFileInput("message.txt");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.length()>12){
                    Map<String,String> map=new HashMap<String,String>();
                    String[] ss = line.split(",,");
                    map.put("cityid",ss[0]);
                    map.put("city",ss[1]);
                    map.put("temp",ss[2]);
                    map.put("icon",ss[3]);
                    list.add(map);
                }
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
}
