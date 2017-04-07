package app.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.model.api.Expressageapi;
import app.model.entity.ExpressageAnalysis;

/**
 * Created by HSAEE on 2017-04-06.
 */

public class Parser {
    private String string,number2,number1;
    private Expressageapi expressageapi;
    private HashMap<String,Object> hashMap;
    public Parser(String number1,String number2){
       this.number1=number1;
        this.number2=number2;
    }
    public void expresult(final Handler handler) {
        expressageapi=new Expressageapi(number1,number2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                string=expressageapi.request();
                StringBuffer sbf = new StringBuffer();
                ExpressageAnalysis parser=new ExpressageAnalysis(string);
                hashMap=parser.analysis();
                if (hashMap!=null&&hashMap.size()!=0){
                    ArrayList<Map<String,Object>> arrayList;
                    arrayList= (ArrayList<Map<String,Object>>) hashMap.get("data");
                    for (int i = arrayList.size()-1; i >= 0; i--) {
                        sbf.append(arrayList.get(i).get("time")+"\n");
                        sbf.append(arrayList.get(i).get("context")+"\n");
                    }
                    Log.d("----",sbf.toString());
                }else {
                    sbf.append("查询失败请检查运单号是否对应快递公司");
                }
                Message msg = handler.obtainMessage();
                //handler.sendMessage(msg);
                msg.obj = sbf.toString();
                //sendMessage()方法，无论是在主线程当中发送，还是在Thread当中发送都是可以的
                handler.sendMessage(msg);
            }
        }).start();
    }
}
