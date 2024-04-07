package database;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {

    //objeto para realizar la conexion
    static Connection objConnection = null;
    //funcion para abrir la conexion
    public static Connection openConnection(){
        //tod o puede pasar
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            //datos para la conexion
            String url = "jdbc:mysql://bsetqrjkjgpzxoeyhedh-mysql.services.clever-cloud.com:3306/bsetqrjkjgpzxoeyhedh";
            String user ="urghlznfejew86yf";
            String password= "2gvvDSXlP5suQHMumCCB";

            //establecer conexion con los datos suministrados
            objConnection = (Connection) DriverManager.getConnection(url,user,password);
            System.out.println("Conexion establecida");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Error al utilizar el driver: "+e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"No se puedo establecer conexion con la base de datos : "+e.getMessage());
        }
        return objConnection;
    };

    public static void closeConnection(){
        try {
            if (objConnection!=null){
                objConnection.close();
                objConnection=null;
                System.out.println("Conexion finalizada");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error al finalizar la conexion: "+e.getMessage());
        }
    }
}
