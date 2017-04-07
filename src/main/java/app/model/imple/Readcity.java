package app.model.imple;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by HSAEE on 2016-12-01.
 */

public class Readcity{
    private InputStream in;
    private ArrayList<String> arrayList_chengshi;
    private ArrayList<String> arrayList_city;
    public ArrayList<String> chengshi(Context context){
        try {
            arrayList_chengshi=new ArrayList<String>();
            in=context.getAssets().open("text/cityid.txt");
            InputStreamReader inputReader = new InputStreamReader(in,"GBK");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null){
                arrayList_chengshi.add(line);
            }
            inputReader.close();
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList_chengshi;
    }
    public ArrayList<String> city(Context context){
        try {
            arrayList_city=new ArrayList<String>();
            in=context.getAssets().open("text/city.txt");
            InputStreamReader inputReader = new InputStreamReader(in,"GBK");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            while((line = bufReader.readLine()) != null){
                arrayList_city.add(line);
            }
            inputReader.close();
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList_city;
    }
}
