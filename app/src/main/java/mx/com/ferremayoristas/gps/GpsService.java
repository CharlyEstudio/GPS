package mx.com.ferremayoristas.gps;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GpsService {
    @PUT("gps/")
    Call<Ubicacion> postUbicacion(@Query("imei") String imei, @Body Ubicacion ubicacion);
}
