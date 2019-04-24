package mx.com.ferremayoristas.gps;

public class Ubicacion {

    private double lat;
    private double lng;
    private double altitud;
    private double exactitud;
    private float velocidad;

    public Ubicacion() {}

    public Ubicacion(double lat, double lng, double altitud, double exactitud, float velocidad) {
        this.lat = lat;
        this.lng = lng;
        this.altitud = altitud;
        this.exactitud = exactitud;
        this.velocidad = velocidad;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public double getExactitud() {
        return exactitud;
    }

    public void setExactitud(double exactitud) {
        this.exactitud = exactitud;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
}
