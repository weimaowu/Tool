package app.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import app.ui.activity.R;


/**
 * Created by HSAEE on 2016-12-31.
 */

public class Cityadapter extends BaseAdapter{
    private ArrayList<Map<String,String>> arraylist;
    private Context context;
    private LayoutInflater layoutInflater;

    public Cityadapter(Context context1, ArrayList<Map<String, String>> data) {
        context=context1;
        layoutInflater=LayoutInflater.from(context);
        arraylist=data;
    }



    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=new ViewHolder();
        if (view == null) {
            view=layoutInflater.inflate(R.layout.city_list,null);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.list_icon);
            viewHolder.textView= (TextView) view.findViewById(R.id.list_city);
            viewHolder.textView2= (TextView) view.findViewById(R.id.list_temp);
            view.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setBackgroundResource(new WeatherSet(context).data(arraylist.get(i).get("icon").toString()));
        viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewHolder.textView.setText(arraylist.get(i).get("city"));
        viewHolder.textView2.setText(arraylist.get(i).get("temp"));
        return view;

    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
    }
}
