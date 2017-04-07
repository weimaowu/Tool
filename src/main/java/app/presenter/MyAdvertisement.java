package app.presenter;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;

/**
 * Created by HSAEE on 2017-04-05.
 */

public class MyAdvertisement {
    public void request(Activity activity) {
        // 检查配置，SDK运行失败时可以用来检查配置是否齐全
        // SpotManager.getInstance(this).checkPermission(this);
        // 广告条接口调用（适用于应用）
        // 将广告条adView添加到需要展示的layout控件中
        // LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
        // AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        // adLayout.addView(adView);

        // 广告条接口调用（适用于游戏）

        // 实例化LayoutParams(重要)
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        // 设置广告条的悬浮位置
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
        // 实例化广告条
        AdView adView = new AdView(activity, AdSize.FIT_SCREEN);
        // 调用Activity的addContentView函数
        activity.addContentView(adView, layoutParams);

        // 监听广告条接口
        adView.setAdListener(new AdViewListener() {

            @Override
            public void onSwitchedAd(AdView arg0) {
                Log.i("YoumiAdDemo", "广告条切换");
            }

            @Override
            public void onReceivedAd(AdView arg0) {
                Log.i("YoumiAdDemo", "请求广告成功");

            }

            @Override
            public void onFailedToReceivedAd(AdView arg0) {
                Log.i("YoumiAdDemo", "请求广告失败");
            }
        });

        // 插播接口调用
        // 开发者可以到开发者后台设置展示频率，需要到开发者后台设置页面（详细信息->业务信息->无积分广告业务->高级设置）
        // 自4.03版本增加云控制是否开启防误点功能，需要到开发者后台设置页面（详细信息->业务信息->无积分广告业务->高级设置）


    }
}
