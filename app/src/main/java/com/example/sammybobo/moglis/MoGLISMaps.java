package com.example.sammybobo.moglis;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sammybobo.moglis.interfaces.MoGLISWebInterface;
import com.example.sammybobo.moglis.models.LocationDetails;
import com.example.sammybobo.moglis.models.LocationUpdate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.zip.Inflater;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sammy bobo on 26/02/2016.
 */
public class MoGLISMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener
{
    private GoogleMap googleMap;
    AlertDialog.Builder alertDialog;
    String location_name;
    String location_details;
    TextView location_name_;
    TextView location_comment;
    Toolbar toolbar;
    GoogleApiClient mGoogleApiClient;
    Location last_location;
    LatLng current_latlng;
    LocationRequest locationRequest;
    LatLng hello;
    final String url = "http://10.0.3.2/MoGLIS/";
    Marker present_marker;
    MarkerOptions friends_markers;
    Button friends_button;
    MoGLISWebInterface moglisInterface;
    rx.Observable<LocationUpdate> request;
    ProgressDialog progressDialog;
    ArrayList<LocationDetails> _locationDetails;
    LatLng friend_latlng;
    Observable<ArrayList<LocationDetails>> getUserCoord;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid;
    Inflater inflater;
    Button add_location;
    final String user_id = "user_id";
    final String user_name = "user_name";
    final String user_emailaddress = "user_emailaddress";
    final String user_fullname = "user_fullname";
    final String user_phonenumber = "user_phonenumber";
    public static Context context;
    TextView location_coord;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_splash, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moglis_maps);
        context = getApplicationContext();
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString(user_id, "");
        add_location = (Button)findViewById(R.id.add_location);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting details");
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build();
            moglisInterface = retrofit.create(MoGLISWebInterface.class);
            getUserCoord = moglisInterface.get_locations_coordinates("getUserCoordinates", userid);
            getUserCoordinates();

        }
        catch (Exception e)
        {
            Log.d("moglis", "Retrofit not built");
            e.printStackTrace();
        }
        friends_button = (Button)findViewById(R.id.friends);
        SupportMapFragment supportMapFragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_map));
        supportMapFragment.getMapAsync(this);


        friends_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MoGLISMaps.this, Friends_recycler.class);
                startActivity(a);
            }
        });

        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MoGLISMaps.this);
                alertDialog.setTitle("Add new");
                alertDialog.setMessage("To add a new location to our database. Kindly click one of the buttons below");
                alertDialog.setPositiveButton("Present", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        LatLng latLng = new LatLng(last_location.getLatitude(),last_location.getLongitude());
                        push_full_location(latLng);

                    }
                });
                alertDialog.setNegativeButton("Add new", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(last_location.getLatitude(),                         last_location.getLongitude())).draggable(true);
                        Marker m = googleMap.addMarker(marker);

                        if (marker != null)
                        {

                        }
                        else
                        {
                            googleMap.addMarker(marker);
                        }
                        LatLng position;
                        position = m.getPosition();
                        Log.d("Position location", position.latitude + " "+position.longitude);
                    }

                });
                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

                alertDialog.show();

            }
        });
                if (mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();
                }
            }

            private void getUserCoordinates()
            {
                progressDialog.show();

                getUserCoord.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<ArrayList<LocationDetails>>() {
                            @Override
                            public void onCompleted() {
                                progressDialog.hide();
                            }

                            @Override
                            public void onError(Throwable e) {
                                progressDialog.hide();;
                                NetworkToast();
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(ArrayList<LocationDetails> locationDetails)
                            {
                                _locationDetails = locationDetails;
                                for (int y = 0; y < locationDetails.size(); y++)
                                {
                                    friend_latlng = new LatLng
                                            (
                                                    Double.parseDouble(locationDetails.get(y).getLatitude()),
                                                    Double.parseDouble(locationDetails.get(y).getLongitude())
                                            );
                                    friends_markers = new MarkerOptions().position(friend_latlng).title(locationDetails.get(y).getLocationName());
                                    googleMap.addMarker(friends_markers).setTitle(locationDetails.get(y).getLocationName());
                                }
                            }
                        });
            }


    private void NetworkToast() {
        Snackbar.make(add_location.getRootView(),"There's something up with your network", Snackbar.LENGTH_LONG).show();
    }


    private void getLocationUpdates()
    {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(20000);
        locationRequest.setFastestInterval(15000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    protected void onPause()
    {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        try
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(url)
                    .build();
            moglisInterface = retrofit.create(MoGLISWebInterface.class);
            getUserCoord = moglisInterface.get_locations_coordinates("getUserCoordinates", user_id);
            getUserCoordinates();

        }
        catch (Exception e)
        {
            Log.d("moglis", "Retrofit not built");
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.d("moglis", "Map is ready");
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerDragListener(this);
        this.googleMap.setOnMarkerClickListener(this);
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        last_location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (last_location != null)
        {
            current_latlng = new LatLng(last_location.getLatitude(), last_location.getLongitude());
            if (present_marker != null)
            {
                present_marker.remove();
            }
            push_location(last_location);
            hello = new LatLng(last_location.getLatitude(), last_location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(hello).title("Your current position");
            present_marker = googleMap.addMarker(markerOptions);

            googleMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(hello),1450, null);

        }

        getLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i)
    {
        if (i == CAUSE_SERVICE_DISCONNECTED){
            Toast.makeText(this, "Service disconnected, please try again", Toast.LENGTH_LONG).show();
        }
        if (i == CAUSE_NETWORK_LOST){
            Toast.makeText(this, "Network lost, please try again", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
    }


    @Override
    public void onLocationChanged(Location location)
    {
      //  getUserCoordinates();
        if (present_marker != null)
        {
            present_marker.remove();
        }

        push_location(location);
        hello = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(hello).title("Your current position");
        present_marker = googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(hello),1450, null);

}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                editor.clear();
                editor.commit();
               Intent a = new Intent(MoGLISMaps.this, MainActivity.class);
                startActivity(a);
                return true;

            case R.id.messages:

                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void push_location(final Location location){
        Log.d("value of integer",Integer.parseInt(userid)+"" );
        request = moglisInterface.push_location_coordinates("push_location", Integer.parseInt(userid), location.getLatitude(), location.getLongitude(),"","");
        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LocationUpdate>() {
                    @Override
                    public void onCompleted() {
                        Log.d("moglis", "It's gotten");

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        e.printStackTrace();
                        NetworkToast();
                    }

                    @Override
                    public void onNext(LocationUpdate locationUpdate)
                    {
                        Log.d("pushed successfully", locationUpdate.getLocationUpdate());
                    }
                });

    }

    public static Context getContext(){
        return context;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng position;
        position = marker.getPosition();
        Log.d("Position location", position.latitude + " "+position.longitude);
    }

    @Override
    public void onMarkerDragEnd(Marker marker)
    {
        LatLng position;
        position = marker.getPosition();
        push_full_location(position);
    }

    private void push_full_location(final LatLng location)
    {

        alertDialog = new AlertDialog.Builder(MoGLISMaps.this);
        alertDialog.setTitle("Enter location details");
        final View v = LayoutInflater.from(MoGLISMaps.this).inflate(R.layout.push_full_location, null);
        alertDialog.setView(v);
        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               location_comment  = (EditText)v.findViewById(R.id.location_comment);
                location_details = location_comment.getText().toString();
                location_name_ = (EditText)v.findViewById(R.id.location_name);
                location_name = location_name_.getText().toString();
                request = moglisInterface.push_location_coordinates("push_location", Integer.parseInt(userid), location.latitude, location.longitude,location_name,location_details);
                request.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<LocationUpdate>()
                        {
                            @Override
                            public void onCompleted()
                            {
                            }

                            @Override
                            public void onError(Throwable e)
                            {
                            }

                            @Override
                            public void onNext(LocationUpdate locationUpdate)
                            {
                                    Log.d("Location returned", locationUpdate.getLocationUpdate());
                            }
                        });
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        AlertDialog.Builder alertDial = new AlertDialog.Builder(MoGLISMaps.this);
        alertDial.setTitle("Location details");
        final View v = LayoutInflater.from(MoGLISMaps.this).inflate(R.layout.location_details, null);
        alertDial.setView(v);
        location_comment  = (TextView)v.findViewById(R.id.location_click_comment);
        //location_comment.setText();
        location_name_ = (TextView)v.findViewById(R.id.location_click_name);
        location_coord = (TextView)v.findViewById(R.id.location_click);
        location_name_.setText(marker.getTitle());
        location_coord.setText(marker.getPosition().latitude + " "+ marker.getPosition().longitude);
      //  _locationDetails.get(0).get
        alertDial.show();
        return false;
    }
}
