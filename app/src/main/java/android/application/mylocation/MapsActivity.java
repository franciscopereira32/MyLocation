package android.application.mylocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.application.mylocation.databinding.ActivityMapsBinding;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Toast;


import java.text.BreakIterator;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private LatLng latLng;

    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;

    boolean isOpen = false;



    @Override

    //private val rotateOpen: Animation by Lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open)}

        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        fab1 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton4);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.from_botton_anim);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_open);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_close);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animteFab();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animteFab();
                Toast.makeText(MapsActivity.this, "pause", Toast.LENGTH_SHORT).show();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animteFab();
                Toast.makeText(MapsActivity.this, "stop", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void animteFab(){
        if (isOpen){
            fab.startAnimation(rotateForward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=false;
        }else{
            fab.startAnimation(rotateBackward);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        TextView textView = (TextView) findViewById(R.id.textVelocimetro);
        textView.setTextColor(Color.parseColor("#FF0000"));

        // Add a marker in Adelaide and move the camera
        LatLng adelaide = new LatLng(-34.921230, 138.599503);
        mMap.addMarker(new MarkerOptions().position(adelaide).title("Adelaide - SA").snippet("Australia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(adelaide));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animteFab();

                locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {

                        int speed = (int) ((location.getSpeed()*3600)/1000);
                        textView.setText(speed+" KM/H");

                        try {
                            CircleOptions circleOptions = new CircleOptions()
                                    .center(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .radius(10)
                                    .strokeColor(Color.RED);
                            Circle circle = mMap.addCircle(circleOptions);

                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            /**mMap.addMarker(new MarkerOptions().position(latLng)
                             .title("Minha Localização")
                             .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_fiber_manual_record_24)));
                             */
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                        catch (SecurityException e){
                            e.printStackTrace();
                        }
                    }
                };

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, locationListener);

                }
                catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        });

    }
}
//Teste git atualização