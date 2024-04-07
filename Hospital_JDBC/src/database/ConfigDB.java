package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {

    static Connection objConnection = null;

    //metodo para abrir conexion
    public static Connection openConnection(){
        try {
            //implementar driver para la conxion
            Class.forName("com.mysql.cj.jdbc.Driver");

            //variables de conexion
            String url = "jdbc:mysql://b5envs9h8achspngv6uq-mysql.services.clever-cloud.com:3306/b5envs9h8achspngv6uq";
            String user = "ukmnirfttemj08ug";
            String password = "VbenvcArOWAgbPR1yOSe";

            //establecer conexion
            objConnection = (Connection) DriverManager.getConnection(url,user,password);
            System.out.println("Conexion establecida correctamente");

            //expesiones en caso de generar errores
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error, no se pudo establecer la conexion con la base de datos"+e.getMessage());
        }catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Error >>> driver no instalado");
        }
        //retornar estado conexion
        return objConnection;
    };

    //funcion para cerrar conexion
    public static void closeConnection(){
        try {
            //si existe conexion, cerrarla
           if (objConnection!=null){
               objConnection.close();
               objConnection=null;
               System.out.println("Conexion cerrada correctamente");
           }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage());
        }
    };
}
