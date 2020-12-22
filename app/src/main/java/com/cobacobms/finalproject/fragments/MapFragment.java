package com.cobacobms.finalproject.fragments;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cobacobms.finalproject.R;
import com.cobacobms.finalproject.entity.Coordination;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.cobacobms.finalproject.utilities.Settings.getCoordination;
import static com.cobacobms.finalproject.utilities.Settings.setCoordination;

public class MapFragment extends Fragment implements LocationListener {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            Coordination coordination = getCoordination(MapFragment.this.getContext());
            if (!coordination.getLatitude().equals("0")) {
                LatLng myLocation = new LatLng(Double.parseDouble(coordination.getLatitude()), Double.parseDouble(coordination.getLongitude()));
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("اینجا هستید")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f));
            }
        }
    };

    private GoogleMap mMap;
    LocationManager locationManager;
    String provider;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        init();
        return view;
    }

    private void init() {
        provider = LocationManager.GPS_PROVIDER;
        checkPermission();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void showLocation() {
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkGPSSensor();
            locationManager.requestLocationUpdates(provider, 250, 10f, this);
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapFragment.this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        showLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    showLocation();
            }
            break;
        }
    }

    private void checkGPSSensor() {
        locationManager = (LocationManager) getActivity().getSystemService(Service.LOCATION_SERVICE);
        boolean isEnableGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isEnableGPS) {
            final AlertDialog alertDialog;
            final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("عدم اتصال به ماهواره");
            builder.setMessage("سیستم مکان یاب ماهوارهای (GPS) غیرفعال می باشد. جهت فعال کردن آن روی تنظیمات کلیک کنید\n");
            builder.setIcon(R.drawable.ic_baseline_location_off_24);
            builder.setPositiveButton("تنظیمات", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog = builder.create();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                alertDialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            alertDialog.show();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lngt = -1;
        double lat = -1;
        double alt = -1;
        lngt = location.getLongitude();
        lat = location.getLatitude();
        alt = location.getAltitude();

        LatLng myLocation = new LatLng(lat, lngt);

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(myLocation).title("اینجا هستید")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16.0f));

        if (lngt != -1 && lat != -1 && this.getContext() != null)
            setCoordination(this.getContext(), new Coordination(String.valueOf(lngt), String.valueOf(lat)));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationManager != null)
            locationManager.removeUpdates(this);
    }
}