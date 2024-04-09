package model;

import database.CRUD;
import database.ConfigDB;
import entity.Avion;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AvionModel implements CRUD {
    @Override
    public Object create(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Avion objAvion = (Avion) obj;
        try {
            String sql = "INSERT INTO avion (modelo, capacidad) VALUES (?,?);";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setString(1,objAvion.getModelo());
            objPrepare.setInt(2,objAvion.getCapacidad());
            objPrepare.execute();
            ResultSet objResult = objPrepare.getGeneratedKeys();
            while (objResult.next()){
                objAvion.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Avion Registrado correctamente");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al crear el avion: "+e.getMessage());
        }

        ConfigDB.closeConnection();
        return objAvion;
    }
    @Override
    public List<Object> findAll() {
        Connection objConnection = ConfigDB.openConnection();
        List<Object> listaAviones = new ArrayList<>();
        try {
            String sql = "SELECT * FROM avion;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()){
                Avion objAvion = new Avion();
                objAvion.setId(objResult.getInt("id"));
                objAvion.setCapacidad(objResult.getInt("capacidad"));
                objAvion.setModelo(objResult.getString("modelo"));
                listaAviones.add(objAvion);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al ver la lista de aviones: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return listaAviones;
    }

    @Override
    public boolean update(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Avion objAvion = (Avion) obj;
        boolean isUpdate = false;
        try {
            String sql = "UPDATE avion SET modelo=?,capacidad=? WHERE id=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setString(1,objAvion.getModelo());
            objPrepare.setInt(2,objAvion.getCapacidad());
            objPrepare.setInt(3,objAvion.getId());
            int totalRowsAffected = objPrepare.executeUpdate();
            if (totalRowsAffected>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"Avion actualizado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar el avion: "+e.getMessage());
        }

        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Avion objAvion = (Avion) obj;
        boolean isDelete = false;
        try {
            String sql = "DELETE FROM avion WHERE id=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1,objAvion.getId());
            int totalArrowsAffected = objPrepare.executeUpdate();
            if (totalArrowsAffected>0){
                isDelete = true;
                JOptionPane.showMessageDialog(null,"Avion eliminado correctamente");
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar el avion: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return isDelete;
    }
}
