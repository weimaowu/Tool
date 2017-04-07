package app.presenter;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

import app.util.PoiOverlay;

/**
 * Created by HSAEE on 2017-04-05.
 */

public class MyPoiOverlay extends PoiOverlay {
 PoiSearch mpoiSearch;
    public MyPoiOverlay(BaiduMap arg0,PoiSearch poiSearch) {
        super(arg0);
        mpoiSearch=poiSearch;
    }

    // 检索Poi详细信息.获取PoiOverlay
    @Override
    public boolean onPoiClick(int arg0) {
        super.onPoiClick(arg0);
        PoiInfo poiInfo = getPoiResult().getAllPoi().get(arg0);
        mpoiSearch.searchPoiDetail(new PoiDetailSearchOption()
                .poiUid(poiInfo.uid));
        return true;
    }

}