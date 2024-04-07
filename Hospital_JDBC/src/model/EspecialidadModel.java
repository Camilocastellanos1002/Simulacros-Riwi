package model;

import database.CRUD;
import database.ConfigDB;
import entity.Especialidad;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadModel implements CRUD {
    //1.metodo
    @Override
    public Object create(Object obj) {
        //1. abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. instanciar objeto
        Especialidad objEspecialidad = (Especialidad) obj;
        try {
            //3.sentencia sql
            String sql ="INSERT INTO especialidad (nombre,descripcion) VALUES (?,?);";
            //4. ejecutar sentencia con la devolucion de los valores
            PreparedStatement objPrepare = objConnection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            //5. asignar valores a los ?
            objPrepare.setString(1,objEspecialidad.getNombre());
            objPrepare.setString(2,objEspecialidad.getDescripcion());
            //6. ejecutar el query, execute devuelve un estado
            objPrepare.execute();
            //7.
            ResultSet objResult = objPrepare.getGeneratedKeys();
            //8. conseguir id
            while (objResult.next()){
                objEspecialidad.setId(objResult.getInt(1));
            }
            JOptionPane.showMessageDialog(null,"Especialidad creada satisfactoriamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error creando especialidad: "+e.getMessage());
        }
        //9 cerrar conexion
        ConfigDB.closeConnection();
        //10 devolver objeto
        return objEspecialidad;
    }


    @Override
    public boolean update(Object obj) {
        //1.abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2. crear y castear el objeto que llego como parametro de tipo especialidad
        Especialidad objEspecialidad = (Especialidad) obj;
        //3. bandera de estado
        boolean isUpdate = false;
        //4.ejecuciones con posibles errores
        try {
            //5. sentencia sql
            String sql = "UPDATE especialidad SET nombre = ?,descripcion= ? WHERE id= ?;";
            //6. crear statemente para la sentencia sql
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //7. asignar valores a los  ? en el statement
            objPrepare.setString(1,objEspecialidad.getNombre());
            objPrepare.setString(2,objEspecialidad.getDescripcion());
            objPrepare.setInt(3,objEspecialidad.getId());

            //8. ejecutar el query que devuelve las filas afectadas
            int totalAffectedRow = objPrepare.executeUpdate();
            //9. validar si hubo cambios
            if (totalAffectedRow>0){
                isUpdate = true;
                JOptionPane.showMessageDialog(null,"Actualizacion generada correctamente");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al actualizar especialidad: "+e.getMessage());
        }
        //10. cerramos la conexion
        ConfigDB.closeConnection();
        //11. devolver estado de la bandera si se elimino o no el registro
        return isUpdate;
    }

    @Override
    public boolean delete(Object obj) {

        //1.abrir conexion
        Connection objConnection = ConfigDB.openConnection();
        //2.castear que llega como parametro
        Especialidad objEspecialidad = (Especialidad) obj;
        //3. bandera de estado si se elimino o no
        boolean isDelete = false;
        //4. Tod o puede fallar entonces un trycatch
        try {
            //5. sentencia sql
            String sql = "DELETE FROM especialidad WHERE id = ?;";
            //6. ejecutar sentencia sql preparando el statement
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            //7. asignarle valor a los signos de interrogacion
            objPrepare.setInt(1,objEspecialidad.getId());
            //8. ejecutar el query con el executeUpdate que devuelve un entero de cuantas filas afectadas
            int totalAffectedRows = objPrepare.executeUpdate();
            //9. verificar si se hizo alguna modificacion
            if (totalAffectedRows>0){
                isDelete = true;
                JOptionPane.showMessageDialog(null,"Eliminacion generada correctamente");
            }
            //crear excepciones
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al eliminar especialidad: "+e.getMessage());

        }
        //10. cerrar conexion
        ConfigDB.closeConnection();
        //11. devolver estado de bandera
        return isDelete;
    }

    public Especialidad findById(int id) {
        Connection objConnection = ConfigDB.openConnection();
        Especialidad objEspecialidad = null;
        try {
            String sql = "SELECT * FROM especialidad WHERE id=?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1,id);
            ResultSet objResult = objPrepare.executeQuery();
            if (objResult.next()){
                objEspecialidad = new Especialidad();
                objEspecialidad.setId(objResult.getInt("id"));
                objEspecialidad.setNombre(objResult.getString("nombre"));
                objEspecialidad.setDescripcion(objResult.getString("descripcion"));
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al buscar especialidad: "+e.getMessage());
        }
        ConfigDB.closeConnection();
        return objEspecialidad;
    }

    //2. metodo
    @Override
    public List<Object> findAll() {
        List<Object> listEspecialidad = new ArrayList<>();

        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM especialidad;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()){
                Especialidad objEspecialidad = new Especialidad();
                objEspecialidad.setId(objResult.getInt("id"));
                objEspecialidad.setNombre(objResult.getString("nombre"));
                objEspecialidad.setDescripcion(objResult.getString("descripcion"));

                listEspecialidad.add(objEspecialidad);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }

        ConfigDB.closeConnection();
        return listEspecialidad;
    }
    public List<Especialidad> findByName(String name){
        List<Especialidad> listEspecialidades = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM especialidad WHERE nombre like ?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setString(1,"%"+name+"%");
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()){
                Especialidad objEspecialidad =new Especialidad();

                objEspecialidad.setId(objResult.getInt("id"));
                objEspecialidad.setNombre(objResult.getString("nombre"));
                objEspecialidad.setDescripcion(objResult.getString("descripcion"));

                listEspecialidades.add(objEspecialidad);
            }

        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al encontrar especialidad por nombre: "+e.getMessage());
        }

        ConfigDB.closeConnection();
        return listEspecialidades;
    }
}
