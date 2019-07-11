package sg.edu.rp.c346.demolocationdetection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient client;
    Button btnget, btnupdate, btnremove;
    LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnget = findViewById(R.id.btnGetLastLocation);
        btnupdate = findViewById(R.id.btnGetLocationUpdate);
        btnremove = findViewById(R.id.btnRemoveLocationUpdate);

        final FusedLocationProviderClient
                client = LocationServices.getFusedLocationProviderClient(this);


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location data = locationResult.getLastLocation();
                    double lat = data.getLatitude();
                    double lng = data.getLongitude();
                }
            }

            ;
        };



                btnget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkPermission() == true) {


                            Task<Location> task = client.getLastLocation();
                            task.addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        String msg = "Lat : " + location.getLatitude() + " Lng : " + location.getLongitude();
                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    } else {
                                        String msg = "No Last Known Location found";
                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });

                        } else {
                            String msg = "permisiion noy granted to retrieve location info";
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                        }


                        btnupdate.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View view) {

                                                             if (checkPermission() == true) {
                                                                 final LocationRequest mLocationRequest = LocationRequest.create();
                                                                 mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                                                 mLocationRequest.setInterval(10000);
                                                                 mLocationRequest.setFastestInterval(5000);
                                                                 mLocationRequest.setSmallestDisplacement(100);

                                                                 client.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                                                             } else {
                                                                 String msg = "permisiion noy granted to retrieve location info";
                                                                 Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                                 ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

                                                             }
                                                         }
                                                     });
                    }


//                            btnremove.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                }
//                            });
//                        }


                            private boolean checkPermission() {
                                int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                                        MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
                                int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                                        MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                                if (permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED
                                        || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED) {
                                    return true;
                                } else {
                                    return false;
                                }
                            }
                        });
                    }
                }























