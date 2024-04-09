package model;

import database.CRUD;
import database.ConfigDB;
import entity.Avion;
import entity.Vuelo;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VueloModel implements CRUD {

    @Override
    public Object create(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Vuelo objVuelo = (Vuelo) obj;
        try {
            String sql = "INSERT INTO vuelo (destino,fecha_salida,hora_salida,id_avion) VALUES (?,?,?,?);";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setString(1, objVuelo.getDestino());
            objPrepare.setDate(2, Date.valueOf(objVuelo.getFecha_salida()));
            objPrepare.setTime(3, Time.valueOf(objVuelo.getHora_salida()));
            objPrepare.setInt(4,objVuelo.getIdAvion());
            objPrepare.execute();
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()){
                objVuelo.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Vuelo creado correctamente");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al crear el vuelo: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return objVuelo;
    }
    @Override
    public List<Object> findAll() {
        Connection objConnection = ConfigDB.openConnection();
        List<Object> listaVuelos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM vuelo INNER JOIN avion ON avion.id = vuelo.id_avion;";
            PreparedStatement ojbPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = ojbPrepare.executeQuery();
            while (objResult.next()){
                Vuelo objVuelo = new Vuelo();
                Avion objAvion = new Avion();
                objVuelo.setId(objResult.getInt("vuelo.id"));
                objVuelo.setDestino(objResult.getString("vuelo.destino"));
                objVuelo.setFecha_salida(objResult.getString("vuelo.fecha_salida"));
                objVuelo.setHora_salida(objResult.getString("vuelo.hora_salida"));

                objAvion.setId(objResult.getInt("avion.id"));
                objAvion.setModelo(objResult.getString("avion.modelo"));
                objAvion.setCapacidad(objResult.getInt("avion.capacidad"));

                objVuelo.setObjAvion(objAvion);

                listaVuelos.add(objVuelo);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al generar lista de vuelos: "+e.getMessage());
        }
        return listaVuelos;
    }

    @Override
    public boolean update(Object obj) {
        //abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //casting
        Vuelo objVuelo = (Vuelo) obj;
        //bandera
        boolean isUpdate = false;
        try {
            //sentencia SQL
            String sql = "UPDATE vuelo SET destino=?, fecha_salida=?, hora_salida=?, id_avion=? WHERE id=?;";
            //preparar statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a ?
            objPrepare.setString(1, objVuelo.getDestino());
            objPrepare.setDate(2, Date.valueOf(objVuelo.getFecha_salida()));
            objPrepare.setTime(3,Time.valueOf(objVuelo.getHora_salida()));
            objPrepare.setInt(4,objVuelo.getIdAvion());
            objPrepare.setInt(5,objVuelo.getId());

            //cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();

            if (totalRowAffected>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"Vuelo actualizado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar vuelo: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {
        //abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //casting
        Vuelo objVuelo = (Vuelo) obj;
        //bandera
        boolean isDeleted = false;
        //try
        try {
            //sentencia SQL
            String sql = "DELETE FROM vuelo WHERE vuelo.id=?;";
            //preparar statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a ?
            objPrepare.setInt(1, objVuelo.getId());
            //cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            if (totalRowAffected>0){
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"Registro de vuelo eliminado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al eliminar vuelo: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //retornar estado bandera
        return isDeleted;
    }
}
