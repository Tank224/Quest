package com.example.quest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.PedestrianCrossing;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PolylineMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.transport.bicycle.BicycleRouter;
import com.yandex.mapkit.transport.masstransit.BicycleRouterV2;
import com.yandex.mapkit.transport.masstransit.MasstransitRouteSerializer;
import com.yandex.mapkit.transport.masstransit.MasstransitRouter;
import com.yandex.mapkit.transport.masstransit.PedestrianRouter;
import com.yandex.mapkit.transport.masstransit.Route;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.SummarySession;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.mapkit.transport.time.AdjustedClock;
import com.yandex.runtime.Error;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CompositeIcon;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapWindow;
import com.yandex.mapkit.map.RotationType;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.mapkit.transport.masstransit.PedestrianRouter;
import com.yandex.mapkit.transport.*;
public class MapActivity extends AppCompatActivity implements UserLocationObjectListener
{
    private ImageView iRun;
    private final String API_KEY = "24f4bec2-a73b-4b58-bac7-357eb4593a20";
    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private PedestrianRouter pedestrianRouter;
    private MapObjectCollection mapObjects;
    private Point currentUserLocation;
    List<Route> route = new ArrayList<>();
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(API_KEY);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            return;
//        }
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.mapview);
        mapView.getMap().move(new CameraPosition(new Point(57.76793278010096, 40.92686117065408), 10, 1, 0));
        iRun = findViewById(R.id.iRun);
        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);

        userLocationLayer.setObjectListener(this);
        pedestrianRouter = TransportFactory.getInstance().createPedestrianRouter();

        mapObjects = mapView.getMap().getMapObjects().addCollection();
        mapObjects.setVisible(true);
        requestRoutes();
        ImageProvider imageProvider = ImageProvider.fromResource(this, R.drawable.paper);
        mapObjects.addPlacemark(MainActivity.point1).setIcon(imageProvider, new IconStyle().setScale(0.07f).setZIndex(20f));
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();

    }
    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        currentUserLocation = userLocationView.getArrow().getGeometry();
//        mapView.getMap().move(new CameraPosition(currentUserLocation, 14, 0, 0));
        userLocationLayer.setAnchor(
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.5)),
                new PointF((float)(mapView.getWidth() * 0.5), (float)(mapView.getHeight() * 0.83)));
    }

    @Override
    public void onObjectRemoved(UserLocationView view) {

    }

    @Override
    public void onObjectUpdated(UserLocationView view, ObjectEvent event) {
        currentUserLocation = view.getArrow().getGeometry();
    }

    private void requestRoutes(){
        List<RequestPoint> requestPoints = new ArrayList<>();
        requestPoints.add(new RequestPoint(MainActivity.point2, RequestPointType.WAYPOINT, null, null));
        requestPoints.add(new RequestPoint(MainActivity.point1, RequestPointType.WAYPOINT, null, null));
        pedestrianRouter.requestRoutes(requestPoints, new TimeOptions(null, null), false, new Session.RouteListener() {
                @Override
                public void onMasstransitRoutes(@NonNull List<Route> list) {
                    for (Route route : list) {
                        PolylineMapObject polyline = mapObjects.addPolyline(route.getGeometry());
                        styleMainRoute(polyline);
                        polyline.setStrokeColor(0xFF0000FF); // Цвет линии маршрута
                        polyline.setStrokeWidth(5); // Ширина линии маршрута
                    }
                }
                @Override
                public void onMasstransitRoutesError(@NonNull Error error) {
                    Log.d("Location", "Latitude: " + error.toString() + ", Longitude: " + error.toString());
                }
        });
    }
    private void styleMainRoute(PolylineMapObject polyline) {
        polyline.setZIndex(10f);
        polyline.setStrokeColor(0xFF0000FF);
        polyline.setStrokeWidth(5f);
    }
    public void onIRunClick(View view) {
//        float[] results = new float[1];
//        Location.distanceBetween(currentUserLocation.getLatitude(), currentUserLocation.getLongitude(), MainActivity.point1.getLatitude(), MainActivity.point1.getLongitude(), results);
//        if (results[0] <= 200) {
            Intent intent = new Intent(MapActivity.this, QuestActivity.class);
            startActivity(intent);
//        }
    }
}