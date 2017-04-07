package app.model.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HSAEE on 2016-12-09.
 */

public class ExpressageAnalysis {
    String string=null;
    public ExpressageAnalysis(String string){

        this.string=string;

    }
    public HashMap<String,Object> analysis(){
        ArrayList<HashMap<String,Object>> str=new ArrayList<HashMap<String,Object>>();
        HashMap<String,Object> hashMap=new HashMap<String,Object>();
        try {
            JSONObject jsonObject =new JSONObject(string);
            hashMap.put("nu",jsonObject.opt("nu"));
            hashMap.put("com",jsonObject.opt("com"));
            JSONArray jsonArray=jsonObject.getJSONArray("data");


            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject Object = jsonArray.getJSONObject(i);
                HashMap<String,Object> map=new HashMap<String,Object>();
                map.put("time",Object.opt("time"));
                map.put("context",Object.opt("context"));
                str.add(map);
            }
            hashMap.put("data",str);
            if (jsonArray.length()==0){
                hashMap.clear();
            }

        } catch (JSONException e) {
            hashMap.clear();
            return hashMap;
        }
        return hashMap;
    }
}
