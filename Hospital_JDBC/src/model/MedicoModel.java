package model;

import database.CRUD;
import database.ConfigDB;
import entity.Especialidad;
import entity.Medico;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoModel implements CRUD {
    @Override
    public Object create(Object obj) {
        //1. abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. castear el objeto que viene como parametro
        Medico objMedico = (Medico) obj;
        //3. tod o puede fallar
        try {
            //4. sentencia sql
            String sql = "INSERT INTO medico (nombre,apellidos,id_especialidad) VALUES (?,?,?);";
            //5.preparar el statement y que genere llaves
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //6. dar valores al statemente
            objPrepare.setString(1,objMedico.getNombre());
            objPrepare.setString(2,objMedico.getApellidos());
            objPrepare.setInt(3,objMedico.getId_especialidad());
            //7. ejecutar el query
            objPrepare.execute();
            //8. obtener los dalores de las llaves
            ResultSet objResult = objPrepare.getGeneratedKeys();
            //9. por registro
            while (objResult.next()){
                objMedico.setId(objResult.getInt(1));
            }

            JOptionPane.showMessageDialog(null,"Medico creado");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error Creando medico: "+e.getMessage());
        }
        //10. cerrar conexion
        ConfigDB.closeConnection();
        //11. devolver objeto
        return objMedico;
    }

    @Override
    public boolean update(Object obj) {
        //abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //castear
        Medico objMedico = (Medico) obj;
        //bandera de estado
        boolean isUpdate = false;
        //try
        try {
            //sentencia sql
            String sql = "UPDATE medico SET nombre=?, apellidos=?,id_especialidad=?;";
            //preparamos statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a los ?
            objPrepare.setString(1,objMedico.getNombre());
            objPrepare.setString(2,objMedico.getApellidos());
            objPrepare.setInt(3,objMedico.getId_especialidad());
            //ejecutar el query
            int totalRowAffected = objPrepare.executeUpdate();
            if (totalRowAffected>0){
                isUpdate= true;
                JOptionPane.showMessageDialog(null,"Registro actualizado correctamente");
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar medico: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //retornar
        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {
        //1. abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. casting
        Medico objMedico = (Medico) obj;
        //3. bandera
        boolean isDeleted = false;
        //4.try
        try {
            //5.sentencia sql
            String sql = "DELETE FROM medico WHERE medico.id=? ;";
            //6. cracion del prepareStatement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //7.darle valores al ?
            objPrepare.setInt(1,objMedico.getId());
            //8. ejecutar query con la devolucion de la cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            //9. verificar
            if (totalRowAffected>0){
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"Medico eliminado exitosamente");
            }
        }catch (Exception e){
            JOptionPane.showInputDialog(null,"Error al eliminar medico: "+e.getMessage());
        }
        //10. cerrar conexion
        ConfigDB.closeConnection();
        //11 retornar bandera
        return isDeleted;
    }

    @Override
    public List<Object> findAll() {
        //1. lista de objetos
        List<Object>  listMedico = new ArrayList<>();
        //2. abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //3. tod o puede fallar
        try {
            //4. sentencia sql
                //seleccionar la tabla medico
            String sql = "SELECT * FROM medico INNER JOIN especialidad ON especialidad.id = medico.id_especialidad;"; //ingresa datos de la tabla especialidad a la tabla medicos

            //5. crear preparestatement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //6. obtener resultados y ejecutar query para devolver un objeto de la lista de los registros de la DB
            ResultSet objResult = objPrepare.executeQuery();
            //7. por cada registro
            while (objResult.next()){
                //crear objeto medico
                Medico objMedico = new Medico();
                //crear objeto especialidad
                Especialidad objEspecialidad = new Especialidad();
                //llenar datos del objeto medico
                objMedico.setId(objResult.getInt("medico.id")); //al hacer un inner join especificar que tabla
                objMedico.setNombre(objResult.getString("medico.nombre"));
                objMedico.setApellidos(objResult.getString("medico.apellidos"));
                objMedico.setId_especialidad(objResult.getInt("medico.id_especialidad"));
                //llenar datos del objeto especialidad
                objEspecialidad.setId(objResult.getInt("especialidad.id"));
                objEspecialidad.setNombre(objResult.getString("especialidad.nombre"));
                objEspecialidad.setDescripcion(objResult.getString("especialidad.descripcion"));

                //guardar los datos de especialidad en el medico
                objMedico.setObjEspecialidad(objEspecialidad);
                //listar medicos
                listMedico.add(objMedico);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
        //8. cerrar conexion
        ConfigDB.closeConnection();
        //9 devolver lista de medicos
        return listMedico;
    }

}
