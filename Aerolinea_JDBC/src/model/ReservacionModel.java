package model;

import database.CRUD;
import database.ConfigDB;
import entity.Avion;
import entity.Pasajero;
import entity.Reservacion;
import entity.Vuelo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservacionModel implements CRUD {

    public Object create(Object obj) {
        //crear conexion
        Connection objConnection = ConfigDB.openConnection();
        //casting del objeto
        Reservacion objReservacion = (Reservacion) obj;
        //try
        try {
            //sentencia sql
            String sql = "INSERT INTO reservacion (fecha_reservacion,asiento,id_pasajero,id_vuelo) VALUES (?,?,?,?);";
            //preparar statement y devolucion de llaves
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //dar valores a  ?
            objPrepare.setString(1,objReservacion.getFecha_reservacion());
            objPrepare.setString(2,objReservacion.getAsiento());
            objPrepare.setInt(3,objReservacion.getId_pasajero());
            objPrepare.setInt(4,objReservacion.getId_vuelo());
            //ejecutar query
            objPrepare.execute();
            //obtener llaves generadas
            ResultSet objResult = objPrepare.getGeneratedKeys();
            //mientras haya un registro
            while (objResult.next()){
                //agregar id
                objReservacion.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"reservacion creada");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error creando reservacion: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //retornar objeto reservacion
        return objReservacion;
    }


    @Override
    public List<Object> findAll() {

        //1. abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. crear lista de reservas
        List<Object> listaReservas = new ArrayList<>();
        //3. try
        try {
            //4. sentencia sql
            // linea de sql que hace la consulta y une dos tablas
            //SELECT * FROM cita INNER JOIN paciente ON paciente.id = cita.id_paciente  INNER JOIN medico ON medico.id = cita.id_medico;
            String sql = "SELECT * FROM reservacion INNER JOIN vuelo ON vuelo.id = reservacion.id_vuelo INNER JOIN pasajero ON pasajero.id = reservacion.id_pasajero;";
            //5. crear statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //6. se ejecuta el query
            ResultSet objResult = objPrepare.executeQuery();

            //7. para cada registro de la tabla
            while (objResult.next()){
                //crear objeto que va a guardar la info
                Reservacion objReservacion = new Reservacion();
                Vuelo objVuelo = new Vuelo();
                Pasajero objPasajero = new Pasajero();

                //asignar valores
                //al tener varias tablas ó hacer INNER JOIN, especificar cada atributo de las tablas
                //reservacion
                objReservacion.setId(objResult.getInt("reservacion.id"));
                objReservacion.setFecha_reservacion(objResult.getString("reservacion.fecha_reservacion"));
                objReservacion.setAsiento(objResult.getString("reservacion.asiento"));
                objReservacion.setId_pasajero(objResult.getInt("reservacion.id_pasajero"));
                objReservacion.setId_vuelo(objResult.getInt("reservacion.id_vuelo"));
                //vuelo
                objVuelo.setDestino(objResult.getString("vuelo.destino"));
                objVuelo.setFecha_salida(objResult.getString("vuelo.fecha_salida"));
                objVuelo.setHora_salida(objResult.getString("vuelo.fecha_salida"));
                //pasajero
                objPasajero.setNombre(objResult.getString("pasajero.nombre"));
                objPasajero.setApellidos(objResult.getString("pasajero.apellidos"));
                objPasajero.setDocumentoID(objResult.getString("pasajero.documento_identidad"));

                //objetos tipo vuelo y pasajero
                objReservacion.setObjvuelo(objVuelo);
                objReservacion.setObjPasajero(objPasajero);

                //guardar objeto a la lista
                listaReservas.add(objReservacion);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al generar lista de reservas: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //devolver lista
        return listaReservas;
    }

    @Override
    public boolean update(Object obj) {
        //conexion
        Connection objconnection = ConfigDB.openConnection();
        //casting del objeto recibido
        Reservacion objReservacion = (Reservacion) obj;
        //bandera de estado
        boolean isUpdate = false;
        //try
        try {
            //sentencia sql
            String sql = "UPDATE reservacion SET fecha_reservacion =?, asiento=?, id_pasajero=?,id_vuelo= ? WHERE id=?;";
            //preparamos el statement
            PreparedStatement objPrepare = objconnection.prepareStatement(sql);
            //asignamos valores a ?
            objPrepare.setDate(1,Date.valueOf(objReservacion.getFecha_reservacion()));
            objPrepare.setString(2,objReservacion.getAsiento());
            objPrepare.setInt(3,objReservacion.getId_pasajero());
            objPrepare.setInt(4, objReservacion.getId_vuelo());
            objPrepare.setInt(6,objReservacion.getId());

            //ejcutamos query o obtenemos cantidad de filas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            //verificamos si hubo cambios
            if (totalRowAffected>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"Actualizacion de la reserva generada exitosamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Erro al actualizar reserva: "+e.getMessage());
        }
        //cerramos conexion
        ConfigDB.closeConnection();
        //retornamos valores
        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {
        //1.abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. castear y obtener objeto
        Reservacion objReservacion = (Reservacion) obj;
        //3.crear bandera de estado
        boolean isDeleted = false;
        //4. try
        try {
            String sql = "DELETE FROM reservacion WHERE reservacion.id = ?;";
            //preparar statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a ?
            objPrepare.setInt(1,objReservacion.getId());
            //cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            if (totalRowAffected>0){
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"La reservacion se eliminado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showInputDialog(null,"Error al eliminar reservacion: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        return isDeleted;
    }
}
