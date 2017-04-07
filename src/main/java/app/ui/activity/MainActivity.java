package app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SpotManager;

import java.io.File;
import java.util.ArrayList;

import app.model.imple.Readcity;
import app.model.imple.Share;
import app.presenter.MyAdvertisement;
import app.presenter.NetworkAvailable;
import app.presenter.WeatherGps;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Toolbar mToolbar;
    private NetworkAvailable networkAvailable =new NetworkAvailable();
    private ImageButton a, b, c, d, e, f, g, h, i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // 初始化接口，应用启动的时候调用
        // 参数：appId, appSecret, 调试模式
        AdManager.getInstance(this).init("f8c567d941cddd04", "7741c725202f2e7d", true);
        info();
        setmToolbar();
        MyAdvertisement advertisement=new MyAdvertisement();
        advertisement.request(MainActivity.this);
    }

    private void setmToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
///// App Logo
//        mToolbar.setLogo(R.drawable.ic_launcher);
//        // Title
//        mToolbar.setTitle("My Title");
//        // Sub Title
//        mToolbar.setSubtitle("Sub title");

        setSupportActionBar(mToolbar);

        // Navigation Icon 要設定在 setSupoortActionBar 才有作用
        // 否則會出現 back bottom
//        mToolbar.setNavigationIcon(R.drawable.menu_a);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weibo:
                Share share = new Share();
                if (share.checkInstallation("新浪微博")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
//      intent.setPackage("com.sina.weibo");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                    intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
                    intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "请选择"));
                } else {
                    share.install();
                }


                break;
            case R.id.qq:
                break;
            case R.id.weixin:
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void info() {
//		textView= (TextView) findViewById(R.id.theme);
        a = (ImageButton) findViewById(R.id.a);
        b = (ImageButton) findViewById(R.id.b);
        c = (ImageButton) findViewById(R.id.c);
        d = (ImageButton) findViewById(R.id.d);
        e = (ImageButton) findViewById(R.id.e);
        f = (ImageButton) findViewById(R.id.f);
        g = (ImageButton) findViewById(R.id.g);
        h = (ImageButton) findViewById(R.id.h);
        i = (ImageButton) findViewById(R.id.i);
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
        i.setOnClickListener(this);

    }

    public void calculator() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Calculator.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void capitals_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Capitals.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void cupitals_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Currencies.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void length_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, length.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void area_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Area.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void volume_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Volume.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void attribution_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Attribution.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void weather_skip() {
        WeatherGps gps=new WeatherGps(this);
        String string=gps.getCNBylocation();
        if (gps.getCNBylocation()!=null){
            ArrayList<String> arrayList_chengshi=new Readcity().chengshi(this);
            ArrayList<String> arrayList_city=new Readcity().city(this);

            String index=arrayList_chengshi.get(arrayList_city.indexOf(string));
            Intent intent = new Intent();
            Log.d("打印", index);
            intent.putExtra("cityid", index);
            intent.setClass(MainActivity.this, Weather.class);
            startActivity(intent);
            MainActivity.this.finish();
        }else {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Selectcity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
//        Intent intent = new Intent();
//        intent.setClass(MainActivity2.this, ActivityWeather.class);
//        startActivity(intent);
//        MainActivity2.this.finish();
    }

    public void expressage_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Expressage.class);
        startActivity(intent);
        MainActivity.this.finish();
    }

    public void fail_skip() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, Fail.class);
        startActivity(intent);
        MainActivity.this.finish();
    }



    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.a:
                cupitals_skip();
                break;
            case R.id.b:
                if (networkAvailable.isNetworkAvailable(this))
                    attribution_skip();
                else fail_skip();
                break;
            case R.id.c:
                if (networkAvailable.isNetworkAvailable(this))
                    expressage_skip();
                else fail_skip();

                break;

            case R.id.d:
                capitals_skip();
                break;
            case R.id.e:
                calculator();
                break;

            case R.id.f:
                if (networkAvailable.isNetworkAvailable(this))
                    weather_skip();
                else fail_skip();
                break;

            case R.id.g:
                length_skip();
                break;
            case R.id.h:
                area_skip();
                break;
            case R.id.i:
                volume_skip();
                break;

            default:
                break;
        }

    }

    public boolean fileIsExists() {
        try {
            File f = new File("/data/data/bao.tool/files/message.txt");
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // 如果有需要，可以点击后退关闭插播广告。
        if (!SpotManager.getInstance(MainActivity.this).disMiss(true)) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        // 如果不调用此方法，则按home键的时候会出现图标无法显示的情况。
        SpotManager.getInstance(MainActivity.this).disMiss(false);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        SpotManager.getInstance(this).unregisterSceenReceiver();
        super.onDestroy();
    }

}
