package com.softulp.ejemplolocalizacion2025;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivityViewModel extends AndroidViewModel {
    private FusedLocationProviderClient fused;
    private Context context;
    private MutableLiveData<Location> mLocation;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
        fused= LocationServices.getFusedLocationProviderClient(context);

    }

    public LiveData<Location> getMlocation(){
        if(mLocation==null){
            mLocation=new MutableLiveData<>();
        }
        return mLocation;
    }

    public void obtenerUbicacion(){

        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> tarea= fused.getLastLocation();
        tarea.addOnSuccessListener(context.getMainExecutor(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if(location!=null){
                    mLocation.postValue(location);

                }

            }
        });

    }


}
