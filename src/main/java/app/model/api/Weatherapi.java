package app.model.api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HSAEE on 2017-04-05.
 */

public class Weatherapi {
    String cityid;
    public Weatherapi(String city){
        cityid=city;
    }
    public String request() {
//        https://free-api.heweather.com/x3/weather?city=北京&key=9be8ef5c83d94876aafb14331298965e

        String httpUrl = "https://free-api.heweather.com/x3/weather?city=";
        String httpArg = cityid+"&key=9be8ef5c83d94876aafb14331298965e";
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl +httpArg;
        try {
            Log.d("httpurl",httpUrl);
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
//            connection.setRequestProperty("apikey", "9be8ef5c83d94876aafb14331298965e");
            connection.connect();
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
        Log.d("========","a"+result);
        return result;
    }
}
