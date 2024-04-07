package model;

import database.CRUD;
import database.ConfigDB;
import entity.Paciente;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteModel implements CRUD {
    @Override
    public Object create(Object obj) {
        //crear conexion
        Connection objConnection = ConfigDB.openConnection();
        //castear objeto que llega
        Paciente objPaciente = (Paciente) obj;
        try {
            //sentencia sql
            String sql = "INSERT INTO paciente (nombre,apellidos,fecha_nacimiento,documento_identidad) VALUES (?,?,?,?);";
            //preparar statement con devolucion de llaves
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //dar valor a signos de interrogacion
            objPrepare.setString(1,objPaciente.getNombre());
            objPrepare.setString(2,objPaciente.getApellidos());
            objPrepare.setDate(3, Date.valueOf(objPaciente.getFecha_nacimiento()));
            objPrepare.setString(4,objPaciente.getDocumento_identidad());
            //ejecutar el queery
            objPrepare.execute();
            //obtener llaves
            ResultSet objResult = objPrepare.getGeneratedKeys();
            //validad
            while (objResult.next()){
                objPaciente.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"paciente creado exitosamente");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error creando paciente: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //retornar objeto
        return objPaciente;
    }

    @Override
    public boolean update(Object obj) {
        //abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //casting
        Paciente objPaciente = (Paciente) obj;
        //bandera
        boolean isUpdate = false;
        try {
            //sentencia SQL
            String sql = "UPDATE paciente SET nombre=?, apellidos=?, fecha_nacimiento=?, documento_identidad=? WHERE id=?;";
            //preparar statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a ?
            objPrepare.setString(1,objPaciente.getNombre());
            objPrepare.setString(2,objPaciente.getApellidos());
            objPrepare.setDate(3,Date.valueOf(objPaciente.getFecha_nacimiento()));
            objPrepare.setString(4,objPaciente.getDocumento_identidad());
            objPrepare.setInt(5,objPaciente.getId());

            //cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();

            if (totalRowAffected>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"Registro de paciente actualizado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar paciente: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {
        //abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //casting
        Paciente objPaciente = (Paciente) obj;
        //bandera
        boolean isDeleted = false;
        //try
        try {
            //sentencia SQL
            String sql = "DELETE FROM paciente WHERE paciente.id=?;";
            //preparar statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a ?
            objPrepare.setInt(1,objPaciente.getId());
            //cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            if (totalRowAffected>0){
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"Registro de paciente eliminado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al eliminar paciente: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //retornar estado bandera
        return isDeleted;
    }

    @Override
    public List<Object> findAll() {
        //lista de paciente
        List<Object> listPaciente = new ArrayList<>();
        //abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //try
        try {
            //sentencia sql
            String sql = "SELECT * FROM paciente;";
            //crear statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //ejecutar query
            ResultSet objResult = objPrepare.executeQuery();
            //asignar todos los registros de la base de datos
            while (objResult.next()){
                //objeto paciente
                Paciente objPaciente = new Paciente();
                //llenar datos
                objPaciente.setId(objResult.getInt("id"));
                objPaciente.setNombre(objResult.getString("nombre"));
                objPaciente.setApellidos(objResult.getString("apellidos"));
                objPaciente.setFecha_nacimiento(objResult.getString("fecha_nacimiento"));
                objPaciente.setDocumento_identidad(objResult.getString("documento_identidad"));
                //adicionar a la lista
                listPaciente.add(objPaciente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //devolver lista de objetos
        return listPaciente;
    }

    public Paciente findByDoc(String doc){
        Connection objConnection = ConfigDB.openConnection();
        Paciente objPaciente = null;
        try {
            String sql = "SELECT * FROM paciente WHERE documento_identidad=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setString(1,doc);
            ResultSet objResult = objPrepare.executeQuery();
            if (objResult.next()){
                objPaciente = new Paciente();
                objPaciente.setNombre(objResult.getString("nombre"));
                objPaciente.setApellidos(objResult.getString("apellidos"));
                objPaciente.setFecha_nacimiento(objResult.getString("fecha_nacimiento"));
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al encontrar el paciente \npor el documento de identidad: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return objPaciente;
    }
}
