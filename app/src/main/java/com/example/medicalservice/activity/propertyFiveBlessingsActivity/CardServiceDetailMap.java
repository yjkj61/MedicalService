package com.example.medicalservice.activity.propertyFiveBlessingsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.medicalservice.R;
import com.example.medicalservice.baseFile.BaseActivity;
import com.example.medicalservice.databinding.ActivityCardServiceDetailMapBinding;

import java.util.ArrayList;
import java.util.List;

public class CardServiceDetailMap extends BaseActivity<ActivityCardServiceDetailMapBinding> {

    private int peopleNumber = 0;

    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.map.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = viewBinding.map.getMap();
            initMap();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        viewBinding.map.onSaveInstanceState(outState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        super.initView();
        viewBinding.back.setOnClickListener(v -> finish());

        viewBinding.reduce.setOnClickListener(v -> {
            if (peopleNumber == 0) return;
            peopleNumber -= 1;

            viewBinding.peopleNumber.setText(peopleNumber + "");
        });

        viewBinding.plus.setOnClickListener(v -> {

            peopleNumber += 1;

            viewBinding.peopleNumber.setText(peopleNumber + "");
        });


    }


    private void initMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        final String[] cityCode = {""};
        LatLonPoint startPoint = new LatLonPoint(0, 0);

        GeocodeSearch geocodeSearch = new GeocodeSearch(this);

        RouteSearch routeSearch = new RouteSearch(this);

        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

                Log.d("TAG", "onRegeocodeSearched: " + regeocodeResult.getRegeocodeAddress().getFormatAddress());

                viewBinding.address.setText(regeocodeResult.getRegeocodeAddress().getFormatAddress());
                cityCode[0] = regeocodeResult.getRegeocodeAddress().getCityCode();

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

                if (geocodeResult.getGeocodeAddressList().size() > 0) {
                    RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint, geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint());


                    MarkerOptions markerOption = new MarkerOptions();
                    markerOption.position(new LatLng(geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude(), geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude()));
                    markerOption.title("终点").snippet("");

                    markerOption.draggable(true);//设置Marker可拖动
                    markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.postion_icon)));
                    // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                    markerOption.setFlat(true);//设置marker平贴地图效果

                    aMap.addMarker(markerOption);

                    RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, 1, null, null, "");

                    routeSearch.calculateDriveRouteAsyn(query);
                }


            }
        });

        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

                if (driveRouteResult.getPaths().size() > 0) {
                    List<DriveStep> driveSteps = driveRouteResult.getPaths().get(0).getSteps();

                    List<LatLng> latLngs = new ArrayList<>();

                    driveSteps.forEach(item -> item.getPolyline().forEach(child -> latLngs.add(new LatLng(child.getLatitude(), child.getLongitude()))));

                    aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(10).color(Color.GREEN));
                }

            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });


        aMap.setOnMyLocationChangeListener(location -> {
            startPoint.setLatitude(location.getLatitude());
            startPoint.setLongitude(location.getLongitude());
            RegeocodeQuery query = new RegeocodeQuery(startPoint, 100, GeocodeSearch.AMAP);
            geocodeSearch.getFromLocationAsyn(query);


        });


        viewBinding.goAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                GeocodeQuery query = new GeocodeQuery(viewBinding.goAddress.getText().toString(), cityCode[0]);

                geocodeSearch.getFromLocationNameAsyn(query);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        viewBinding.map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        viewBinding.map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewBinding.map.onDestroy();
    }


}