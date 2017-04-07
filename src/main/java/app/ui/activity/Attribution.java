package app.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

import app.presenter.MyOrientationListener;
import app.presenter.MyPoiOverlay;
import app.util.DrivingRouteOverlay;
import app.util.TransitRouteOverlay;
import app.util.WalkingRouteOverlay;

/**
 * Created by HSAEE on 2016-11-25.
 */

public class Attribution extends Activity implements View.OnClickListener{
    private EditText editText;
    private List<PoiInfo> dataList;
    private PoiSearch mpoiSearch;
    private MapView mMapView = null;
    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    //当前的精度
    private float mCurrentAccracy;
    //方向传感器的监听器
    private MyOrientationListener myOrientationListener;
    //方向传感器X方向的值
    private int mXDirection;
    //是否是已经定位
    private  boolean isFristLocation = false;
    //第一次定位放大地图
    private boolean big=true;
    private ArrayAdapter<String> sugAdapter = null;
    private BaiduMap mBaiduMap;
    private ImageButton ib_large,ib_small,ib_mode,ib_loc,ib_traffic;
    //搜索关键字
    private String matter=null,city=null;
    private BMapManager mapManager = null;
    private RoutePlanSearch routePlanSearch;// 路径规划搜索接口
    //模式切换，正常模式
    private boolean modeFlag = true;
    //判断是否有覆盖层
    private boolean cover=false;
    //当前地图缩放级别
    private float zoomLevel;
    // LatLng地理坐标基本数据结构
    private LatLng latlng;
    //导航初始与终点
    private LatLng latlngend;
    private int load_Index = 0;
    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private double mLatitude;
    private double mLongtitude;
    private int update=1000000;
    private int index = -1;
    private int totalLine = 0;// 记录某种搜索出的方案数量
    private int drivintResultIndex = 0;// 驾车路线方案index
    private int transitResultIndex = 0;// 换乘路线方案index
    private Button btn_walk,btn_bus,btn_drive;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_attribution);
        Intent intent=getIntent();
        matter=intent.getStringExtra("matter");
        //初始化控件
        initView();
        //监听跳转
        monitor();
        //初始化地图
        initMap();

        //定位
        initLocation();
        // 初始化传感器
        initOritationListener();
        //导航
        navi();
        backmap();
    }
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
                {
                    @Override
                    public void onOrientationChanged(float x)
                    {
                        mXDirection = (int) x;
                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(mLatitude)
                                .longitude(mLongtitude).build();
                        //获取当前精度
//                        mCurrentAccracy=locData.accuracy;
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        // 设置自定义图标
                        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                                .fromResource(R.drawable.map_gps_locked);
                        MyLocationConfiguration config = new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker);
                        mBaiduMap.setMyLocationConfigeration(config);
                        if (big){mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                            mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());big=false;}

                    }
                });
    }
    private void seekMap() {
        latlng = new LatLng(mLatitude, mLongtitude);
        BitmapDescriptor bimp = new BitmapDescriptorFactory().fromResource(R.drawable.icon_mark);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(latlng).icon(bimp);
        // 将这些添加到地图中去
// 实例化PoiSearch3
        mpoiSearch = PoiSearch.newInstance();
        // 注册搜索事件监听
        mpoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                // 获取poiResult
                if (poiResult == null
                        || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    if (city!=null&&matter!=null){
                        Toast.makeText(Attribution.this, "未找到结果",
                                Toast.LENGTH_SHORT).show();
                    }


                    return;
                }
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    mBaiduMap.clear();
                    MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap,mpoiSearch);
                    poiOverlay.setData(poiResult);
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();
                    int totalPage = poiResult.getTotalPageNum();
//                        Toast.makeText(
//                                Attribution.this,"总共查到" + poiResult.getTotalPoiNum() + "个兴趣点,分为"
//                                        + totalPage + "页", Toast.LENGTH_SHORT).show();
                    cover=true;
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(Attribution.this, "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                } else {// 正常返回结果的时候，此处可以获得很多相关信息
//
//                    Toast.makeText(
//                            Attribution.this, poiDetailResult.getName() + ": "
//                                    + poiDetailResult.getAddress(),
//                            Toast.LENGTH_LONG).show();
                    Log.d("-----"+poiDetailResult.getName() ,poiDetailResult.getAddress());
                    String s=poiDetailResult.getName();
                    String s1=poiDetailResult.getAddress();
                    latlngend=poiDetailResult.getLocation();
                    showPopwindow(s,s1);
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
//        searchButtonProcess();
        nearbySearch(0);
    }
    private void monitor() {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Attribution.this,MapSeek.class);
                startActivity(intent);
                Attribution.this.finish();

            }
        });

    }
    private void initMap() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        // 不显示缩放比例尺
        mMapView.showZoomControls(false);
        // 不显示百度地图Logo
        mMapView.removeViewAt(1);
        //百度地图
        mBaiduMap = mMapView.getMap();
        // 改变地图状态
        MapStatus mMapStatus = new MapStatus.Builder().zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        //设置地图状态改变监听器
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
            }
            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
            }
            @Override
            public void onMapStatusChange(MapStatus arg0) {
                //当地图状态改变的时候，获取放大级别
                zoomLevel = arg0.zoom;
            }
        });
    }
    private void navi(){
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch
                .setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
                    @Override
                    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

                        mBaiduMap.clear();
                        if (walkingRouteResult == null
                                || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            Toast.makeText(Attribution.this, "抱歉，未找到结果",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                            // TODO
                            return;
                        }
                        if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                            WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(
                                    mBaiduMap);
                            walkingRouteOverlay.setData(walkingRouteResult.getRouteLines()
                                    .get(drivintResultIndex));
                            mBaiduMap.setOnMarkerClickListener(walkingRouteOverlay);
                            walkingRouteOverlay.addToMap();
                            walkingRouteOverlay.zoomToSpan();
                            int  totalLine = walkingRouteResult.getRouteLines().size();
//                            Toast.makeText(Attribution.this,
//                                    "共查询出" + totalLine + "条符合条件的线路", Toast.LENGTH_SHORT).show();
cover=true;
                        }
//                        if (totalLine > 1) {
//                            btn_next.setEnabled(true);
//                        }
                    }

                    @Override
                    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

                        mBaiduMap.clear();
                        if (transitRouteResult == null
                                || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            Toast.makeText(Attribution.this, "抱歉，未找到结果",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                            // drivingRouteResult.getSuggestAddrInfo()
                            return;
                        }
                        if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                            TransitRouteOverlay transitRouteOverlay = new TransitRouteOverlay(
                                    mBaiduMap);
                            transitRouteOverlay.setData(transitRouteResult.getRouteLines()
                                    .get(drivintResultIndex));// 设置一条驾车路线方案
                            mBaiduMap.setOnMarkerClickListener(transitRouteOverlay);
                            transitRouteOverlay.addToMap();
                            transitRouteOverlay.zoomToSpan();
                            totalLine = transitRouteResult.getRouteLines().size();
                            cover=true;
//                            Toast.makeText(Attribution.this,
//                                    "共查询出" + totalLine + "条符合条件的线路", Toast.LENGTH_SHORT).show();
//                            if (totalLine > 1) {
//                                btn_next.setEnabled(true);
//                            }
//                            // 通过getTaxiInfo()可以得到很多关于打车的信息
//                            Toast.makeText(
//                                    Attribution.this,
//                                    "该路线打车总路程"
//                                            + transitRouteResult.getTaxiInfo()
//                                            .getDistance(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

                    }

                    @Override
                    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                        mBaiduMap.clear();
                        if (drivingRouteResult == null
                                || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            Toast.makeText(Attribution.this, "抱歉，未找到结果",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                            // drivingRouteResult.getSuggestAddrInfo()
                            return;
                        }
                        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                            DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                                    mBaiduMap);
                            Log.d("11111111111",drivintResultIndex+"");
                            drivingRouteOverlay.setData(drivingRouteResult.getRouteLines()
                                    .get(drivintResultIndex));// 设置一条驾车路线方案
                            mBaiduMap.setOnMarkerClickListener(drivingRouteOverlay);
                            drivingRouteOverlay.addToMap();
                            drivingRouteOverlay.zoomToSpan();
                            totalLine = drivingRouteResult.getRouteLines().size();
                            cover=true;
//                            Toast.makeText(Attribution.this,
//                                    "共查询出" + totalLine + "条符合条件的线路", Toast.LENGTH_SHORT).show();
//                            if (totalLine > 1) {
//                                btn_next.setEnabled(true);
//                            }
                            // 通过getTaxiInfo()可以得到很多关于打车的信息
//                            Toast.makeText(
//                                    Attribution.this,
//                                    "该路线打车总路程"
//                                            + drivingRouteResult.getTaxiInfo()
//                                            .getDistance(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

                    }

                    @Override
                    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

                    }
                });
    }
    /**
     *步行
     */
    private void walkSearch() {
        WalkingRoutePlanOption walkOption = new WalkingRoutePlanOption();
        walkOption.from(PlanNode.withLocation(latlng));
        walkOption.to(PlanNode.withLocation(latlngend));
        routePlanSearch.walkingSearch(walkOption);
    }
    /**
     * 驾车线路查询
     */
    private void drivingSearch(int index) {
        DrivingRoutePlanOption drivingOption = new DrivingRoutePlanOption();
        drivingOption.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);// 设置驾车路线策略
        drivingOption.from(PlanNode.withLocation(latlng));// 设置起点
        drivingOption.to(PlanNode.withLocation(latlngend));// 设置终点
        routePlanSearch.drivingSearch(drivingOption);// 发起驾车路线规划
    }

    /**
     * 换乘路线查询
     */
    private void transitSearch(int index) {
        TransitRoutePlanOption transitOption = new TransitRoutePlanOption();
        transitOption.city(city);// 设置换乘路线规划城市，起终点中的城市将会被忽略
        transitOption.from(PlanNode.withLocation(latlng));
        transitOption.to(PlanNode.withLocation(latlngend));
        transitOption.policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);// 设置换乘策略
        routePlanSearch.transitSearch(transitOption);
    }
    private void initLocation() {
        //定位客户端的设置
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();

        //注册监听
        mLocationClient.registerLocationListener(mLocationListener);
        //配置定位
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//坐标类型
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//打开Gps
        option.setScanSpan(update);//1000毫秒定位一次
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocationClient.setLocOption(option);
    }
    private void initView() {

        editText= (EditText) findViewById(R.id.map_seek);
        ib_large = (ImageButton)findViewById(R.id.ib_large);
        ib_large.setOnClickListener(this);
        ib_small = (ImageButton)findViewById(R.id.ib_small);
        ib_small.setOnClickListener(this);
        ib_mode = (ImageButton)findViewById(R.id.ib_mode);
        ib_mode.setOnClickListener(this);
        ib_loc = (ImageButton)findViewById(R.id.ib_loc);
        ib_loc.setOnClickListener(this);
        ib_traffic = (ImageButton)findViewById(R.id.ib_traffic);
        ib_traffic.setOnClickListener(this);
    }
    //周边搜索后图标详细信息
    private void showPopwindow(String s1,String s2){
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_popwind, null);
        TextView textView= (TextView) view.findViewById(R.id.site_name);
        TextView textView1= (TextView) view.findViewById(R.id.site_distance);
        textView.setText(s1);
        textView1.setText(s2);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(Attribution.this.findViewById(R.id.bmapView),
                Gravity.BOTTOM, 0, 0);
        window.setOutsideTouchable(true);
        // 这里检验popWindow里的button是否可以点击
        ImageButton first = (ImageButton) view.findViewById(R.id.image_navigation);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkSearch();
                window.dismiss();
                Popwindow();
            }
        });

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }
    //导航选择线路
    private void Popwindow(){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_popwindna, null);
        btn_walk= (Button) view.findViewById(R.id.popwalk);
        btn_bus= (Button) view.findViewById(R.id.popbus);
        btn_drive= (Button) view.findViewById(R.id.popdrive);
        btn_walk.setOnClickListener(this);
        btn_bus.setOnClickListener(this);
        btn_drive.setOnClickListener(this);


        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);

        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(Attribution.this.findViewById(R.id.bmapView),
                Gravity.BOTTOM, 0, 0);
        window.setOutsideTouchable(true);

    }
    /**
     * 城市搜索
     */
    private void searchButtonProcess() {
        // PoiCitySearchOption（）poi城市内检索参数 city搜索城市 keyword key搜索关键字 pageNum -
        // 分页编号
        mpoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city)
                .keyword(matter)
                .pageNum(load_Index));

    }
    /**
     * 附近检索,范围搜索需要指定圆心.以圆形的方式进行搜索.
     */
    private void nearbySearch(int page) {
        mBaiduMap.clear();
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        nearbySearchOption.location(latlng);
        nearbySearchOption.keyword(matter);
        nearbySearchOption.radius(1000);// 检索半径，单位是米
        nearbySearchOption.pageNum(page);
        mpoiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_large:
                if (zoomLevel < 18) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                    ib_small.setEnabled(true);
                } else {
                    showInfo("已经放至最大，可继续滑动操作");
                    ib_large.setEnabled(false);
                }
                break;
            case R.id.ib_small:
                if (zoomLevel > 6) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                    ib_large.setEnabled(true);
                } else {
                    ib_small.setEnabled(false);
                    showInfo("已经缩至最小，可继续滑动操作");
                }
                break;
            case R.id.ib_mode://卫星模式和普通模式
                if(modeFlag){
                    modeFlag = false;
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    showInfo("开启卫星模式");
                }else{
                    modeFlag = true;
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    showInfo("开启普通模式");
                }
                break;
            case R.id.ib_loc:
                if (isFristLocation){
                    backmap();
                }
                showInfo("返回自己位置");
                break;
            case R.id.ib_traffic://是否开启交通图
                if(mBaiduMap.isTrafficEnabled()){
                    mBaiduMap.setTrafficEnabled(false);
                    ib_traffic.setBackgroundResource(R.drawable.map_conmon);
                    showInfo("关闭实时交通图");
                }else{
                    mBaiduMap.setTrafficEnabled(true);
                    ib_traffic.setBackgroundResource(R.drawable.map_traffic);
                    showInfo("开启实时交通图");
                }
                break;
            case R.id.popwalk:
                btn_walk.setTextColor(getResources().getColor(R.color.gray));
                btn_bus.setTextColor(getResources().getColor(R.color.white));
                btn_drive.setTextColor(getResources().getColor(R.color.white));
                index = 2;
                walkSearch();
                break;
            case R.id.popbus:
                btn_walk.setTextColor(getResources().getColor(R.color.white));
                btn_bus.setTextColor(getResources().getColor(R.color.gray));
                btn_drive.setTextColor(getResources().getColor(R.color.white));
                index = 1;
                transitResultIndex = 0;

                transitSearch(transitResultIndex);
                break;
            case R.id.popdrive:
                btn_walk.setTextColor(getResources().getColor(R.color.white));
                btn_bus.setTextColor(getResources().getColor(R.color.white));
                btn_drive.setTextColor(getResources().getColor(R.color.gray));
                index = 0;
                drivintResultIndex = 0;

                drivingSearch(drivintResultIndex);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
        // 开启方向传感器
        myOrientationListener.start();
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        //关闭定位
        mBaiduMap.setMyLocationEnabled(false);
        if(mLocationClient.isStarted()){
            mLocationClient.stop();
        }

        // 关闭方向传感器
        myOrientationListener.stop();
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    private void  backmap(){

            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latlng);
            //mBaiduMap.setMapStatus(status);//直接到中间
            mBaiduMap.animateMapStatus(status);//动画的方式到中间


//                showInfo("位置：" + location.getAddrStr());

    }
    //显示消息
    private void showInfo(String str){
        Toast.makeText(Attribution.this, str, Toast.LENGTH_SHORT).show();
    }
    //自定义的定位监听
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取城市
            city=location.getCity();
            //将获取的location信息给百度map
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mXDirection)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mLatitude=location.getLatitude();
            mLongtitude=location.getLongitude();
            mBaiduMap.setMyLocationData(data);
            // 设置自定义图标
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.map_gps_locked);
            MyLocationConfiguration configuration=new MyLocationConfiguration(mCurrentMode,true,mCurrentMarker);


            mBaiduMap.setMyLocationConfigeration(configuration);
            //获取经纬度
            latlng= new LatLng(location.getLatitude(),location.getLongitude());
            isFristLocation = true;
            // 第一次定位时，将地图位置移动到当前位置
            backmap();

            if (city!=null&&matter!=null){
                seekMap();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mpoiSearch!=null){
            mpoiSearch.destroy();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (cover){mBaiduMap.clear();cover=false;}else {
            Intent intent = new Intent();
            intent.setClass(Attribution.this,MainActivity.class);
            startActivity(intent);
            Attribution.this.finish();
        }

        return true;
    }
}

