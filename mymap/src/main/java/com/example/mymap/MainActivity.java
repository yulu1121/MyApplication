package com.example.mymap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient client;
    private boolean isFirstLoc = true;
    private LatLng currentLocation;
    private GeoCoder geoCoder;
    private PoiSearch poiSearch;
    private SearchView searchView;
    private String mCity;
    private EditText placeEdit;
    private Button goBtn;
    private RadioGroup methodRG;
    private RadioButton walkRb;
    private RadioButton busRb;
    private RadioButton driveRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initCurrentLocation();
        initOverLay();
        initPoiSearch();
        initRoutePlanSearch();
    }
    //初始化路线规划
    private void initRoutePlanSearch(){
        //获得RoutePlanSearch的对象
        final RoutePlanSearch planSearch = RoutePlanSearch.newInstance();
        //定义获得结果的监听
        planSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                if(walkingRouteResult.error==SearchResult.ERRORNO.RESULT_NOT_FOUND||
                        walkingRouteResult==null){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(walkingRouteResult.error==SearchResult.ERRORNO.NO_ERROR){
                    //创建步行道路的覆盖物
                    WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
                    //设置点击事件监听
                    baiduMap.setOnMarkerClickListener(overlay);
                    //设置路线
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //添加到地图
                    overlay.zoomToSpan();
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
                if(transitRouteResult.error==SearchResult.ERRORNO.RESULT_NOT_FOUND||
                        transitRouteResult==null){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(transitRouteResult.error==SearchResult.ERRORNO.NO_ERROR){
                    //创建步行道路的覆盖物
                   TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
                    //设置点击事件监听
                    baiduMap.setOnMarkerClickListener(overlay);
                    //设置路线
                    overlay.setData(transitRouteResult.getRouteLines().get(0));
                    //添加到地图
                    overlay.zoomToSpan();
                    overlay.addToMap();
                }
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                if(drivingRouteResult.error==SearchResult.ERRORNO.RESULT_NOT_FOUND||
                        drivingRouteResult==null){
                    Toast.makeText(MainActivity.this, "没有找到结果", Toast.LENGTH_SHORT).show();
                }else if(drivingRouteResult.error==SearchResult.ERRORNO.NO_ERROR){
                    //创建步行道路的覆盖物
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                    //设置点击事件监听
                    baiduMap.setOnMarkerClickListener(overlay);
                    //设置路线
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    //添加到地图
                    overlay.zoomToSpan();
                    overlay.addToMap();
                }
            }
        });
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(walkRb.isChecked()){
                    //搜索步行的结果
                    planSearch.walkingSearch(new WalkingRoutePlanOption()
                                            .from(PlanNode.withLocation(currentLocation))
                                            .to(PlanNode.withCityNameAndPlaceName(mCity,placeEdit.getText().toString())));
                }else if(driveRb.isChecked()){
                    planSearch.drivingSearch(new DrivingRoutePlanOption()
                                            .from(PlanNode.withLocation(currentLocation))
                                            .to(PlanNode.withCityNameAndPlaceName(mCity,placeEdit.getText().toString())));
                }else{
                    planSearch.transitSearch(new TransitRoutePlanOption()
                                            .city(mCity)
                                            .from(PlanNode.withLocation(currentLocation))
                                            .to(PlanNode.withCityNameAndPlaceName(mCity,placeEdit.getText().toString())));
                }
            }
        });
    }
    //初始化覆盖物
    private void initOverLay() {
        //定义图标
        BitmapDescriptor ooA = BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        BitmapDescriptor ooB = BitmapDescriptorFactory.fromResource(R.mipmap.icon_markb);
        BitmapDescriptor ooC = BitmapDescriptorFactory.fromResource(R.mipmap.icon_markc);
        //定义经纬度
        LatLng llA = new LatLng(30.481594, 114.412826);
        LatLng llB = new LatLng(30.483594, 114.412826);
        LatLng llC = new LatLng(30.485594, 114.412826);
        //创建覆盖物的参数
        OverlayOptions olA = new MarkerOptions().position(llA).icon(ooA).zIndex(5).draggable(true);
        OverlayOptions olB = new MarkerOptions().position(llB).icon(ooB).zIndex(5).draggable(true);
        OverlayOptions olC = new MarkerOptions().position(llC).icon(ooC).zIndex(5).draggable(true);
        //添加覆盖物
        final Marker mkA= (Marker) baiduMap.addOverlay(olA);
        final Marker mkB= (Marker) baiduMap.addOverlay(olB);
        final Marker mkC= (Marker) baiduMap.addOverlay(olC);
        //添加点击覆盖物的监听
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Button btn = new Button(MainActivity.this);
                btn.setBackgroundColor(Color.WHITE);
                if(marker==mkA){
                    btn.setText("点击了A");
                }else if(marker==mkB){
                    btn.setText("点击了B");
                    btn.setKeepScreenOn(false);
                }else if(marker==mkC){
                    btn.setText("点击了C");
                    btn.setKeepScreenOn(false);
                }
                InfoWindow window = new InfoWindow(btn,marker.getPosition(),-40);
                baiduMap.showInfoWindow(window);
                return true;
            }
        });
    }
    //继承兴趣类
    class MyPoiOverLay extends PoiOverlay{
        public MyPoiOverLay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            //获得当前的POI
            PoiInfo poi = getPoiResult().getAllPoi().get(i);
            String info = poi.name+"---"+poi.address;
            Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
            Log.e("xxx","poi"+info);
            return true;
        }
    }
    //初始化搜索功能
    private void initPoiSearch(){
        //获得poisearch对象
        poiSearch = PoiSearch.newInstance();
        //定义获得结果的监听
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if(poiResult==null||poiResult.error== SearchResult.ERRORNO.RESULT_NOT_FOUND){
                    Toast.makeText(MainActivity.this, "地址没有找到", Toast.LENGTH_SHORT).show();
                }else if(poiResult.error==SearchResult.ERRORNO.NO_ERROR) {
                    //地图清空覆盖物
                    baiduMap.clear();
                    //创建覆盖物
                    PoiOverlay overLay = new MyPoiOverLay(baiduMap);
                    //设置搜索结果
                    overLay.setData(poiResult);
                    //添加到地图
                    overLay.addToMap();
                    //进行缩放
                    overLay.zoomToSpan();
                    //设置监听
                    baiduMap.setOnMarkerClickListener(overLay);
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                //按城市进行搜索
//                poiSearch.searchInCity(new PoiCitySearchOption()
//                                        .city(mCity)
//                                        .keyword(query)
//                                        .pageNum(0)
//                                        .pageCapacity(10));
                poiSearch.searchNearby(new PoiNearbySearchOption()
                                        .location(currentLocation)
                                        .keyword(query)
                                        .radius(10000)
                                        .pageNum(0)
                                        .pageCapacity(10));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private void initGeoCoder(LatLng ll){
        geoCoder = GeoCoder.newInstance();
        //进行反向编码
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                mCity = reverseGeoCodeResult.getAddressDetail().city;
                String address = reverseGeoCodeResult.getAddress();
                Log.e("xxx",address);
                double latitude = reverseGeoCodeResult.getLocation().latitude;
                Log.e("xxx",":"+latitude);
            }
        });
    }
    private void initCurrentLocation() {
        //获得百度地图对象
        baiduMap = mapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //创建定位客户端
        client = new LocationClient(this);
        //设置定位的选项
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);//设置扫描间隔
        client.setLocOption(option);
        client.start();
        client.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation!=null){
                    //创建数据
                    MyLocationData data =new  MyLocationData.Builder()
                            .accuracy(bdLocation.getRadius())
                            .direction(bdLocation.getDirection())
                            .latitude(bdLocation.getLatitude())
                            .longitude(bdLocation.getLongitude())
                            .build();
                    //给地图设置定位数据
                    baiduMap.setMyLocationData(data);
                    if(isFirstLoc){
                        isFirstLoc = false;
                        LatLng latLng = new LatLng(bdLocation.getLatitude(),
                                bdLocation.getLongitude());
                        MapStatusUpdate upadate = MapStatusUpdateFactory.newLatLng(latLng);
                        //移动地图
                        baiduMap.animateMapStatus(upadate);
                        //保存当前位置
                        currentLocation = latLng;
                        initGeoCoder(latLng);
                    }
                }
            }
        });
    }

    private void initView() {
        mapView = (MapView) findViewById(R.id.bmapView);
        searchView = (SearchView) findViewById(R.id.search_view);
        placeEdit = (EditText) findViewById(R.id.place_et);
        goBtn = (Button) findViewById(R.id.go_btn);
        methodRG = (RadioGroup)findViewById(R.id.method_rg);
        walkRb = (RadioButton)findViewById(R.id.walk_rb);
        busRb = (RadioButton)findViewById(R.id.bus_rb);
        driveRb = (RadioButton)findViewById(R.id.drive_rb);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
