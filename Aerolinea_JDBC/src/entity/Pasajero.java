package entity;

public class Pasajero {
    private int id;
    private String nombre;
    private String apellidos;
    private String documentoID;

    public Pasajero() {
    }
    public Pasajero(String nombre, String apellidos, String documentoID) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.documentoID = documentoID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDocumentoID() {
        return documentoID;
    }

    public void setDocumentoID(String documentoID) {
        this.documentoID = documentoID;
    }

    @Override
    public String toString() {
        return "Pasajero: \n" +
                "nombre y apellidos'" + nombre + ' ' + apellidos + ' ' +
                ", documentoID='" + documentoID + '\'' +
                '\n';
    }
}
