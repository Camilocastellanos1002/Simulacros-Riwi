package entity;

import java.util.Date;

public class Reservacion {

    private int id;

    private String fecha_reservacion;

    private String asiento;

    private int id_pasajero;
    private Pasajero objPasajero;

    private int id_vuelo;
    private Vuelo objvuelo;

    public Reservacion() {
    }

    public Reservacion(String fecha_reservacion, String asiento, int id_pasajero, Pasajero objPasajero, int id_vuelo, Vuelo objvuelo) {
        this.fecha_reservacion = fecha_reservacion;
        this.asiento = asiento;
        this.id_pasajero = id_pasajero;
        this.objPasajero = objPasajero;
        this.id_vuelo = id_vuelo;
        this.objvuelo = objvuelo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_reservacion() {
        return fecha_reservacion;
    }

    public void setFecha_reservacion(String fecha_reservacion) {
        this.fecha_reservacion = fecha_reservacion;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public int getId_pasajero() {
        return id_pasajero;
    }

    public void setId_pasajero(int id_pasajero) {
        this.id_pasajero = id_pasajero;
    }

    public Pasajero getObjPasajero() {
        return objPasajero;
    }

    public void setObjPasajero(Pasajero objPasajero) {
        this.objPasajero = objPasajero;
    }

    public int getId_vuelo() {
        return id_vuelo;
    }

    public void setId_vuelo(int id_vuelo) {
        this.id_vuelo = id_vuelo;
    }

    public Vuelo getObjvuelo() {
        return objvuelo;
    }

    public void setObjvuelo(Vuelo objvuelo) {
        this.objvuelo = objvuelo;
    }

    @Override
    public String toString() {
        return "Reservacion: \n" +
                "pasajero: \n" + objPasajero.getNombre()+ " " +objPasajero.getApellidos()+"\n"+
                "fecha_reservacion: " + fecha_reservacion +"\n"+
                "vuelo: "+ objvuelo.getDestino()+ ", asiento: " + asiento +" "+
                '\n';
    }
}
