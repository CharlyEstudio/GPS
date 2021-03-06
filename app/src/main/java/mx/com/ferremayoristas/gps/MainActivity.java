package mx.com.ferremayoristas.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int LOCATION_CODE = 100;
    public Location mlocListener;
    public LocationManager mlocManager;

    public TextView imei;
    public String imeiVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imeiVar = obtenerIMEI();
        imei = findViewById(R.id.imei);
        imei.setText(imeiVar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Estamos en el caso del télefono
        String permission = permissions[0];
        int result = grantResults[0];
        switch (requestCode) {
            case LOCATION_CODE:
                mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                mlocListener = new Location();
                mlocListener.setMainActivity(this);
                if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Comprobar si ah sido aceptado o denegado la petición del permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        // Concedio el permiso
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, (LocationListener) mlocListener);
                        iniciarServicioBack();
                    } else {
                        // No concedio el permiso
                        Toast.makeText(MainActivity.this, "No concedio el permiso en UBICACIÓN.", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private String obtenerIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "not 1";
            }
            return telephonyManager.getImei();
        } else {
            return telephonyManager.getDeviceId();
        }

    }

    public String enviar() {
        String enviarImei = obtenerIMEI();
        return enviarImei;
    }

    public void iniciarServicioBack() {
        // stopService(new Intent(getApplicationContext(), Notificaciones.class)); // Terminar
        Intent intent = new Intent(MainActivity.this, SegundoPlano.class);
        startService(intent);
    }

    static class ChecarBackGroud extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return "ok";
        }

        @Override
        protected void onPostExecute(String result) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new ChecarBackGroud().execute();
                }
            }, 10000);
            super.onPostExecute(result);
        }
    }
}
