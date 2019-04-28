package mx.com.ferremayoristas.gps;

import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Location implements LocationListener {
    MainActivity mainActivity;
    String imeiData;

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.imeiData = mainActivity.imeiVar;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(android.location.Location location) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://177.244.55.122:4111/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GpsService service = retrofit.create(GpsService.class);

        // IMEI de prueba
        String numImei = "359270078018344";

        Ubicacion ubicacion = new Ubicacion();

        ubicacion.setAltitud(location.getAltitude());
        ubicacion.setExactitud(location.getAccuracy());
        ubicacion.setLat(location.getLatitude());
        ubicacion.setLng(location.getLongitude());
        ubicacion.setVelocidad(location.getSpeed());

        if (imeiData != null) {
            Call<Ubicacion> updateUbicacionCall = service.postUbicacion(imeiData, ubicacion);
            updateUbicacionCall.enqueue(new Callback<Ubicacion>() {
                @Override
                public void onResponse(Call<Ubicacion> call, Response<Ubicacion> response) {
                    Ubicacion ubicacion1 = response.body();
                    System.out.println("IMEI: " + imeiData + " Lat: " + ubicacion1.getLat() + " - Lng: " + ubicacion1.getLng());
                }

                @Override
                public void onFailure(Call<Ubicacion> call, Throwable t) {
                    System.out.println("Error: " + t);
                }
            });
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
