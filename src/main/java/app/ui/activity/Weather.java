package app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import app.presenter.Cityadapter;
import app.presenter.WeatherPrsenter;
import app.presenter.WeatherSet;
import app.ui.view.AlignTextView;
import app.ui.view.SlideCutListView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by HSAEE on 2016-12-01.
 */

public class Weather extends Activity implements SlideCutListView.RemoveListener,PtrHandler{
    private AlignTextView atv_uv_content,atv_fish_content,atv_sport_content,
            atv_flu_content,atv_drsg_content,atv_cw_content,atv_comf_content,atv_air_content;

    private CircleImageView weather_icon;
    private ImageButton imageButton;
    private ImageView weather_icon_1,weather_icon_2,weather_icon_3,weather_add1,weather_add2,weather_add;
    private TextView defeated;
    private TextView weather_defe;
    private TextView weather_city;
    private TextView weather_quality;
    private TextView weather_index;
    private TextView weather_text;
    private TextView weather_temperature;
    private TextView weather_newThermometer;
    private TextView weather_newWind;
    private TextView weather_newstress;
    private TextView weather_date_1;
    private TextView weather_date_2;
    private TextView weather_date_3;
    private TextView weather_text_1;
    private TextView weather_text_2;
    private TextView weather_text_3;
    private TextView weather_temperature_1;
    private TextView weather_temperature_2;
    private TextView weather_temperature_3;
    private TextView tv_air;
    private TextView tv_comf;
    private TextView tv_cw;
    private TextView tv_drsg;
    private TextView tv_flu;
    private TextView tv_sport;
    private TextView tv_fish;
    private TextView tv_uv;
    private SlideCutListView listVie;

    private String cityname,cityid;
    private HashMap<String,Object> hashMap;

    private LinearLayout ly,linearLayout;
    private DrawerLayout dy;
    private Cityadapter cityadapter;
    private PtrClassicFrameLayout ptr;
    private WeatherPrsenter weatherPrsenter;
    private WeatherSet weatherSet;
    private Handler handler;
    // 控制ToolBar的变量
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;

    private static final int ALPHA_ANIMATIONS_DURATION = 200;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;


    LinearLayout mIvPlaceholder; // 大图片
    LinearLayout mLlTitleContainer; // Title的LinearLayout
    FrameLayout mFlTitleContainer; // Title的FrameLayout
    AppBarLayout mAblAppBar; // 整个可以滑动的AppBar
    TextView mTvToolbarTitle; // 标题栏Title
    Toolbar mTbToolbar; // 工具栏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        info();
        ButterKnife.bind(this);
        ptr = (PtrClassicFrameLayout)findViewById(R.id.weather_pcf);
        mTbToolbar.setTitle("");
        // AppBar的监听
        mAblAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleAlphaOnTitle(percentage);
                handleToolbarTitleVisibility(percentage);
            }
        });
        Intent in=getIntent();
        cityname=in.getStringExtra("city");
        cityid=in.getStringExtra("cityid");
        network();
        listVie.setRemoveListener(this);
        cityadapter=new Cityadapter(this,weatherSet.load());
        listVie.setAdapter(cityadapter);
        listVie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cityid=weatherSet.load().get(i).get("cityid");
                network();
                dy.closeDrawer(ly);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherSet.load();
                dy.openDrawer(ly);
            }
        });
        weather_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipweather();
            }
        });
        weather_add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipweather();
            }
        });
        weather_add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipweather();
            }
        });
        weather_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsTheTitleVisible){dy.openDrawer(ly);}
            }
        });
        initParallaxValues();
        ptr.setPtrHandler(this);
    }

    // 设置自动滑动的动画效果
    private void initParallaxValues() {
        CollapsingToolbarLayout.LayoutParams petDetailsLp =
                (CollapsingToolbarLayout.LayoutParams) mIvPlaceholder.getLayoutParams();

        CollapsingToolbarLayout.LayoutParams petBackgroundLp =
                (CollapsingToolbarLayout.LayoutParams) mFlTitleContainer.getLayoutParams();

        petDetailsLp.setParallaxMultiplier(0.9f);
        petBackgroundLp.setParallaxMultiplier(0.3f);

        mIvPlaceholder.setLayoutParams(petDetailsLp);
        mFlTitleContainer.setLayoutParams(petBackgroundLp);
    }

    // 处理ToolBar的显示
    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTvToolbarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    // 控制Title的显示
    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mLlTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    // 设置渐变的动画
    private static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
    private void skipweather(){
        Intent intent = new Intent();
        intent.setClass(Weather.this, Selectcity.class);
        startActivity(intent);
        Weather.this.finish();
    }
    private void network(){
        weatherPrsenter=new WeatherPrsenter(cityid);

       handler =new Handler(){
           @Override
           public void handleMessage(Message msg) {
               super.handleMessage(msg);
               hashMap = (HashMap<String, Object>) msg.obj;
               if (hashMap==null)
               {weather_defe.setText("查询失败");
                   defeated.setText("");
               } else {
                   setdata();}
           }
       };
        weatherPrsenter.apidata(handler);
    }
    private void thread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    SystemClock.sleep(60000);
                    network();
                }

            }

        }).start();
    }

    private void setdata(){

        if (weatherSet.detection(cityid)!=-1) {
            weatherSet.removedata(weatherSet.detection(cityid));
        }


        weatherSet.save(cityid,hashMap.get("city").toString(),hashMap.get("tmp0").toString(),hashMap.get("code").toString());
        cityadapter=new Cityadapter(this,weatherSet.load());
        listVie.setAdapter(cityadapter);
        tv_air.setText(hashMap.get("air").toString());
        tv_comf.setText(hashMap.get("comf").toString());
        tv_cw.setText(hashMap.get("cw").toString());
        tv_drsg.setText(hashMap.get("drsg").toString());
        tv_flu.setText(hashMap.get("flu").toString());
        tv_sport.setText(hashMap.get("sport").toString());
        tv_fish.setText(hashMap.get("trav").toString());
        tv_uv.setText(hashMap.get("uv").toString());
        atv_air_content.setText(hashMap.get("air_txt").toString());
        atv_comf_content.setText(hashMap.get("comf_txt").toString());
        atv_cw_content.setText(hashMap.get("cw_txt").toString());
        atv_drsg_content.setText(hashMap.get("drsg_txt").toString());
        atv_flu_content.setText(hashMap.get("flu_txt").toString());
        atv_sport_content.setText(hashMap.get("sport_txt").toString());
        atv_fish_content.setText(hashMap.get("trav_txt").toString().replaceAll("旅游","钓鱼"));
        atv_uv_content.setText(hashMap.get("uv_txt").toString());

        weather_city.setText(hashMap.get("city").toString());
        weather_quality.setText(hashMap.get("qlty").toString());
        weather_index.setText(hashMap.get("aqi").toString());
        weather_icon.setImageResource(weatherSet.data(hashMap.get("code").toString()));
        weather_text.setText(hashMap.get("txt").toString());
        mTvToolbarTitle.setText(hashMap.get("txt").toString());
        weather_temperature.setText(hashMap.get("fl").toString());
        weather_newThermometer.setText(hashMap.get("fl").toString());
        weather_newstress.setText(hashMap.get("pres").toString());
        weather_newWind.setText(hashMap.get("sc").toString());
        weather_date_1.setText(hashMap.get("data0").toString());
        weather_icon_1.setImageResource(weatherSet.data(hashMap.get("code_d0").toString()));
        weather_text_1.setText(hashMap.get("txt_d0").toString());
        weather_temperature_1.setText(hashMap.get("tmp0").toString());
        weather_date_2.setText(hashMap.get("data1").toString());
        weather_icon_2.setImageResource(weatherSet.data(hashMap.get("code_d1").toString()));
        weather_text_2.setText(hashMap.get("txt_d1").toString());
        weather_temperature_2.setText(hashMap.get("tmp1").toString());
        weather_date_3.setText(hashMap.get("data2").toString());
        weather_icon_3.setImageResource(weatherSet.data(hashMap.get("code_d2").toString()));
        weather_text_3.setText(hashMap.get("txt_d2").toString());
        weather_temperature_3.setText(hashMap.get("tmp2").toString());

    }
    private void info(){
        weatherSet=new WeatherSet(this);
        listVie= (SlideCutListView) findViewById(R.id.city_listview);
        linearLayout= (LinearLayout) findViewById(R.id.cehua_layout);
        ViewGroup.LayoutParams lp;
        lp=linearLayout.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        int screenHeigh = dm.heightPixels;
        lp.width=screenWidth-56; lp.height=screenHeigh;
        linearLayout.setLayoutParams(lp);
        mAblAppBar= (AppBarLayout) findViewById(R.id.main_abl_app_bar);
        mTvToolbarTitle= (TextView) findViewById(R.id.main_tv_toolbar_title);
        mTbToolbar= (Toolbar) findViewById(R.id.main_tb_toolbar);
        mFlTitleContainer= (FrameLayout) findViewById(R.id.main_fl_title);
        mIvPlaceholder= (LinearLayout) findViewById(R.id.main_iv_placeholder);
        mLlTitleContainer= (LinearLayout) findViewById(R.id.main_ll_title_container);
        ly= (LinearLayout) findViewById(R.id.manage);
        dy= (DrawerLayout) findViewById(R.id.drawer);
        tv_air= (TextView) findViewById(R.id.air);
        tv_comf= (TextView) findViewById(R.id.comf);
        tv_cw= (TextView) findViewById(R.id.cw);
        tv_drsg= (TextView) findViewById(R.id.drsg);
        tv_flu= (TextView) findViewById(R.id.flu);
        tv_sport= (TextView) findViewById(R.id.sport);
        tv_fish= (TextView) findViewById(R.id.fish);
        tv_uv= (TextView) findViewById(R.id.uv);
        atv_air_content= (AlignTextView) findViewById(R.id.air_content);
        atv_comf_content= (AlignTextView) findViewById(R.id.comf_content);
        atv_cw_content= (AlignTextView) findViewById(R.id.cw_content);
        atv_drsg_content= (AlignTextView) findViewById(R.id.drsg_content);
        atv_flu_content= (AlignTextView) findViewById(R.id.flu_content);
        atv_sport_content= (AlignTextView) findViewById(R.id.sport_content);
        atv_fish_content= (AlignTextView) findViewById(R.id.fish_content);
        atv_uv_content= (AlignTextView) findViewById(R.id.uv_content);

        imageButton= (ImageButton) findViewById(R.id.weather_ib);
        weather_add1= (ImageView) findViewById(R.id.weather_add);
        weather_add2= (ImageView) findViewById(R.id.weather_add2);
        weather_newWind= (TextView) findViewById(R.id.weather_newWind);
        weather_newstress= (TextView) findViewById(R.id.weather_newstress);
        weather_icon= (CircleImageView) findViewById(R.id.weather_icon);
        weather_icon_1= (ImageView) findViewById(R.id.weather_icon_1);
        weather_icon_2= (ImageView) findViewById(R.id.weather_icon_2);
        weather_icon_3= (ImageView) findViewById(R.id.weather_icon_3);
        weather_city= (TextView) findViewById(R.id.weather_city);
        weather_quality= (TextView) findViewById(R.id.weather_quality);
        weather_index= (TextView) findViewById(R.id.weather_index);
        weather_defe= (TextView) findViewById(R.id.textView);
        defeated= (TextView) findViewById(R.id.defeated);
        weather_text= (TextView) findViewById(R.id.weather_text);
        weather_temperature= (TextView) findViewById(R.id.weather_temperature);
        weather_temperature_1= (TextView) findViewById(R.id.weather_temperature_1);
        weather_temperature_2= (TextView) findViewById(R.id.weather_temperature_2);
        weather_temperature_3= (TextView) findViewById(R.id.weather_temperature_3);
        weather_newThermometer= (TextView) findViewById(R.id.weather_newThermometer);
        weather_date_1= (TextView) findViewById(R.id.weather_date_1);
        weather_date_2= (TextView) findViewById(R.id.weather_date_2);
        weather_date_3= (TextView) findViewById(R.id.weather_date_3);
        weather_text_1= (TextView) findViewById(R.id.weather_text_1);
        weather_text_2= (TextView) findViewById(R.id.weather_text_2);
        weather_text_3= (TextView) findViewById(R.id.weather_text_3);
        weather_add= (ImageView) findViewById(R.id.weather_add);

    }
    // 半角转化为全角的方法
    public String ToSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127 && c[i]>32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.setClass(Weather.this,MainActivity.class);
            startActivity(intent);
            Weather.this.finish();
        }
        return true;
    }
    @Override
    public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
        switch (direction) {
            case RIGHT:
                weatherSet.removedata(position);
                cityadapter=new Cityadapter(this,weatherSet.load());
                listVie.setAdapter(cityadapter);
                break;
            case LEFT:
                Toast.makeText(this, "向左删除 "+ position, Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//禁止上拉后刷新
        return mIsTheTitleContainerVisible;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr.refreshComplete();
                network();
            }
        }, 2000);
    }

}
