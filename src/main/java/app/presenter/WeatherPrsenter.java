package app.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;

import app.model.api.Weatherapi;
import app.model.entity.WeatherAnalysis;

/**
 * Created by HSAEE on 2017-04-05.
 */

public class WeatherPrsenter {
    HashMap<String,Object> hashMap;
    String s;
    Weatherapi weatherapi;
    WeatherAnalysis weatherAnalysis;
    public WeatherPrsenter(String city){
        weatherapi=new Weatherapi(city);
    }
    public void apidata(final Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                s=weatherapi.request();
                weatherAnalysis=new WeatherAnalysis(s);
               hashMap=weatherAnalysis.analysis();
                Message msg = handler.obtainMessage();
                //handler.sendMessage(msg);
                Log.d("----",hashMap.size()+"s");
                msg.obj = hashMap;
                //sendMessage()方法，无论是在主线程当中发送，还是在Thread当中发送都是可以的
                handler.sendMessage(msg);
            }
        }).start();


    }

}





