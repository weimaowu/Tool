package app.model.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by HSAEE on 2016-12-15.
 */

public class WeatherAnalysis {
    String string;
    public WeatherAnalysis(String string){
        this.string=string;


    }
    public HashMap<String,Object> analysis(){

        HashMap<String,Object> hashMap=new HashMap<String,Object>();

        try {
            JSONObject jsonObject =new JSONObject(string);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather data service 3.0");
            JSONObject object = jsonArray.getJSONObject(0);
            JSONObject object1 = object.getJSONObject("aqi");
            JSONObject object1_2 = object1.getJSONObject("city");
            hashMap.put("qlty","空气"+object1_2.opt("qlty"));
            hashMap.put("aqi","指数:"+object1_2.opt("aqi"));
            JSONObject object3 = object.getJSONObject("basic");
            hashMap.put("city",object3.opt("city"));
            Log.d("----",object3.opt("city").toString());
            JSONArray jsonArray2=object.getJSONArray("daily_forecast");
            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject object2_1= jsonArray2.getJSONObject(i);
                JSONObject object2_3 = object2_1.getJSONObject("cond");
                JSONObject object2_4 = object2_1.getJSONObject("tmp");
                hashMap.put("data"+i,object2_1.opt("date"));
                hashMap.put("code_d"+i,object2_3.opt("code_d"));
                hashMap.put("txt_d"+i,object2_3.opt("txt_d"));
                hashMap.put("tmp"+i,object2_4.opt("min")+"~"+object2_4.opt("max")+"°C");
            }
            JSONObject object2 = object.getJSONObject("now");
            hashMap.put("fl",object2.opt("fl")+"°C");
            hashMap.put("tmp",object2.opt("tmp"+"°C"));
            hashMap.put("pres",object2.opt("pres"));
            JSONObject object2_1 = object2.getJSONObject("cond");
            hashMap.put("code",object2_1.opt("code"));
            hashMap.put("txt",object2_1.opt("txt"));
            JSONObject object2_2 = object2.getJSONObject("wind");
            hashMap.put("sc",object2_2.opt("sc")+"级");
            JSONObject object6 = object.getJSONObject("suggestion");
            JSONObject object6_1 = object6.getJSONObject("air");
            hashMap.put("air",object6_1.opt("brf"));
            hashMap.put("air_txt","\t\t"+object6_1.opt("txt"));
            JSONObject object6_2 = object6.getJSONObject("drsg");
            hashMap.put("drsg",object6_2.opt("brf"));
            hashMap.put("drsg_txt","\t\t"+object6_2.opt("txt"));
            JSONObject object6_3 = object6.getJSONObject("flu");
            hashMap.put("flu",object6_3.opt("brf"));
            hashMap.put("flu_txt","\t\t"+object6_3.opt("txt"));
            JSONObject object6_4 = object6.getJSONObject("sport");
            hashMap.put("sport",object6_4.opt("brf"));
            hashMap.put("sport_txt","\t\t"+object6_4.opt("txt"));
            JSONObject object6_5 = object6.getJSONObject("trav");
            hashMap.put("trav",object6_5.opt("brf"));
            hashMap.put("trav_txt","\t\t"+object6_5.opt("txt"));
            JSONObject object6_6 = object6.getJSONObject("uv");
            hashMap.put("uv",object6_6.opt("brf"));
            hashMap.put("uv_txt","\t\t"+object6_6.opt("txt"));
            JSONObject object6_7 = object6.getJSONObject("cw");
            hashMap.put("cw",object6_7.opt("brf"));
            hashMap.put("cw_txt","\t\t"+object6_7.opt("txt"));
            JSONObject object6_8 = object6.getJSONObject("comf");
            hashMap.put("comf",object6_8.opt("brf"));
            hashMap.put("comf_txt","\t\t"+object6_8.opt("txt"));

        } catch (JSONException e) {
            hashMap.clear();
            return hashMap;
        }

        return hashMap;
    }
}
