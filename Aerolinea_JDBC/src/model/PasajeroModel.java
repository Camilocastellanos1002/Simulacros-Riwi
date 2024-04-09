package model;

import database.CRUD;
import database.ConfigDB;
import entity.Pasajero;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PasajeroModel implements CRUD {
    @Override
    public Object create(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Pasajero objPasajero = (Pasajero) obj;
        try {
            String sql = "INSERT INTO pasajero (nombre, apellidos, documento_identidad) VALUES (?,?,?);";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setString(1,objPasajero.getNombre());
            objPrepare.setString(2,objPasajero.getApellidos());
            objPrepare.setString(3,objPasajero.getDocumentoID());
            objPrepare.execute();
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()){
                objPasajero.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Pasajero registrado correctamente");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al registrar pasajero: "+e.getMessage());
        }

        ConfigDB.closeConnection();
        return objPasajero;
    }
    @Override
    public List<Object> findAll() {
        Connection objConnection = ConfigDB.openConnection();
        List<Object> listaPasajeros = new ArrayList<>();
        try {
            String sql = "SELECT * FROM pasajero;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()){
                Pasajero objPasajero = new Pasajero();
                objPasajero.setId(objResult.getInt("id"));
                objPasajero.setNombre(objResult.getString("nombre"));
                objPasajero.setApellidos(objResult.getString("apellidos"));
                objPasajero.setDocumentoID(objResult.getString("documento_identidad"));
                listaPasajeros.add(objPasajero);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al ver la lista de pasajeros: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return listaPasajeros;
    }

    @Override
    public boolean update(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Pasajero objPasajero = (Pasajero) obj;
        boolean isUpdate = false;
        try {
            String sql = "UPDATE pasajero SET nombre=?,apellidos=?, documento_identidad=? WHERE id=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setString(1,objPasajero.getNombre());
            objPrepare.setString(2,objPasajero.getApellidos());
            objPrepare.setString(3,objPasajero.getDocumentoID());
            objPrepare.setInt(4,objPasajero.getId());

            int totalRowsAffected = objPrepare.executeUpdate();
            if (totalRowsAffected>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"pasajero actualizado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar el pasajero: "+e.getMessage());
        }

        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Pasajero objPasajero = (Pasajero) obj;
        boolean isDelete = false;
        try {
            String sql = "DELETE FROM pasajero WHERE id=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1,objPasajero.getId());
            int totalArrowsAffected = objPrepare.executeUpdate();
            if (totalArrowsAffected>0){
                isDelete = true;
                JOptionPane.showMessageDialog(null,"pasajero eliminado correctamente");
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al eliminar el pasajero: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return isDelete;
    }
}
