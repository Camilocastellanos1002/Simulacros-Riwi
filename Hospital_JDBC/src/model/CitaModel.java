package model;

import database.CRUD;
import database.ConfigDB;
import entity.Cita;
import entity.Medico;
import entity.Paciente;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaModel implements CRUD {
    @Override
    public Object create(Object obj) {
        //crear conexion
        Connection objConnection = ConfigDB.openConnection();
        //casting del objeto
        Cita objCita = (Cita) obj;
        //try
        try {
            //sentencia sql
            String sql = "INSERT INTO cita (fecha_cita,hora_cita,motivo,id_paciente,id_medico) VALUES (?,?,?,?,?);";
            //preparar statement y devolucion de llaves
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //dar valores a  ?
            objPrepare.setDate(1, Date.valueOf(objCita.getFecha_cita()));
            objPrepare.setTime(2,Time.valueOf(objCita.getHora_cita()));
            objPrepare.setString(3,objCita.getMotivo());
            objPrepare.setInt(4,objCita.getId_paciente());
            objPrepare.setInt(5,objCita.getId_medico());
            //ejecutar query
            objPrepare.execute();
            //obtener llaves generadas
            ResultSet objResult = objPrepare.getGeneratedKeys();
            //mientras haya un registro
            while (objResult.next()){
                //agregar id
                objCita.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"cita creada");
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error creando cita: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //retornar objeto cita
        return objCita;
    }

    @Override
    public boolean update(Object obj) {
        //conexion
        Connection objconnection = ConfigDB.openConnection();
        //casting del objeto recibido
        Cita objCita = (Cita) obj;
        //bandera de estado
        boolean isUpdate = false;
        //try
        try {
            //sentencia sql
            String sql = "UPDATE cita SET fecha_cita =?, hora_cita=?, motivo=?,id_paciente= ?, id_medico =?  WHERE id=?;";
            //preparamos el statement
            PreparedStatement objPrepare = objconnection.prepareStatement(sql);
            //asignamos valores a ?
            objPrepare.setDate(1,Date.valueOf(objCita.getFecha_cita()));
            objPrepare.setTime(2,Time.valueOf(objCita.getHora_cita()));
            objPrepare.setString(3,objCita.getMotivo());
            objPrepare.setInt(4,objCita.getId_paciente());
            objPrepare.setInt(5,objCita.getId_medico());
            objPrepare.setInt(6,objCita.getId());

            //ejcutamos query o obtenemos cantidad de filas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            //verificamos si hubo cambios
            if (totalRowAffected>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"Actualizacion de cita generada exitosamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Erro al actualizar cita: "+e.getMessage());
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
        Cita objCita = (Cita) obj;
        //3.crear bandera de estado
        boolean isDeleted = false;
        //4. try
        try {
            String sql = "DELETE FROM cita WHERE cita.id = ?;";
            //preparar statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //dar valores a ?
            objPrepare.setInt(1,objCita.getId());
            //cantidad de columnas afectadas
            int totalRowAffected = objPrepare.executeUpdate();
            if (totalRowAffected>0){
                isDeleted = true;
                JOptionPane.showMessageDialog(null,"La cita se eliminado correctamente");
            }
        }catch (Exception e){
            JOptionPane.showInputDialog(null,"Error al eliminar cita: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        return isDeleted;
    }

    @Override
    public List<Object> findAll() {

        //1. abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. crear lista de citas
        List<Object> listaCita = new ArrayList<>();
        //3. try
        try {
            //4. sentencia sql
            // linea de sql que hace la consulta y une dos tablas
            //SELECT * FROM cita INNER JOIN paciente ON paciente.id = cita.id_paciente  INNER JOIN medico ON medico.id = cita.id_medico;
            String sql = "SELECT * FROM cita INNER JOIN paciente ON paciente.id = cita.id_paciente  INNER JOIN medico ON medico.id = cita.id_medico;";
            //5. crear statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //6. se ejecuta el query
            ResultSet objResult = objPrepare.executeQuery();

            //7. para cada registro de la tabla
            while (objResult.next()){
                //crear objeto que va a guardar la info
                Cita objCita = new Cita();
                Medico objtMedico = new Medico();
                Paciente objPaciente = new Paciente();
                //asignar valores
                //al tener varias tablas ó hacer INNER JOIN, especificar id de que tarjeta
                objCita.setId(objResult.getInt("cita.id"));
                objCita.setFecha_cita(objResult.getString("cita.fecha_cita"));
                objCita.setHora_cita(objResult.getString("cita.hora_cita"));
                objCita.setMotivo(objResult.getString("cita.motivo"));
                objCita.setId_paciente(objResult.getInt("cita.id_paciente"));
                objCita.setId_medico(objResult.getInt("cita.id_medico"));

                //obtener el nombre del medico
                objtMedico.setNombre(objResult.getString("medico.nombre"));
                objtMedico.setApellidos(objResult.getString("medico.apellidos"));
                //obtener el nombre del paciente
                objPaciente.setNombre(objResult.getString("paciente.nombre"));
                objPaciente.setApellidos(objResult.getString("paciente.apellidos"));


                //objetos tipo medicos y paciente
                objCita.setObjPaciente(objPaciente);
                objCita.setObjMedico(objtMedico);

                //guardar objeto a la lista
                listaCita.add(objCita);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
        //cerrar conexion
        ConfigDB.closeConnection();
        //devolver lista
        return listaCita;
    }
}
