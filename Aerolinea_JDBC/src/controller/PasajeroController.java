package controller;

import entity.Avion;
import entity.Pasajero;
import model.PasajeroModel;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class PasajeroController {

    public static PasajeroModel instanciarModeloPasajero(){
        return new PasajeroModel();
    }

    public static void create(){
        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre del pasajero: ");
        String apellidos = JOptionPane.showInputDialog(null,"Ingrese los apellidos del pasajero: ");
        String documento = JOptionPane.showInputDialog(null,"Ingrese el documento del pasajero: ");

        instanciarModeloPasajero().create(new Pasajero(nombre,apellidos,documento));

    }
    public static void read(){
        String listaPasajeros = readAll(instanciarModeloPasajero().findAll());
        JOptionPane.showMessageDialog(null,listaPasajeros);
    }
    public static String readAll(List<Object> list){
        String listaPasajeros= "Lista de pasajeros: \n";
        for (Object i:list){
            Pasajero ojbPasajero = (Pasajero) i;
            listaPasajeros+=ojbPasajero.toString()+"\n";
        }
        return listaPasajeros;
    }
    public static void update(){
        Object[] optionPasajeros = Utils.listToArray(instanciarModeloPasajero().findAll());
        Pasajero objSelected = (Pasajero) JOptionPane.showInputDialog(null,
                "Selecione una avion que deseea editar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionPasajeros,
                optionPasajeros[0]
        );
        objSelected.setNombre(JOptionPane.showInputDialog(null,"Ingrese el nuevo nombre del pasajero: ",objSelected.getNombre()));
        objSelected.setApellidos(JOptionPane.showInputDialog(null,"Ingrese los nuevos apellidos del pasajero: ",objSelected.getApellidos()));
        objSelected.setDocumentoID(JOptionPane.showInputDialog(null,"Ingrese el nuevo documento del pasajero: ",objSelected.getDocumentoID()));

        instanciarModeloPasajero().update(objSelected);

    }
    public static void delete(){
        Object[] optionesPasajero = Utils.listToArray(instanciarModeloPasajero().findAll());
        Avion objSelected = (Avion) JOptionPane.showInputDialog(null,
                "Selecione el avion que desea eliminar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionesPasajero,
                optionesPasajero[0]
        );
        instanciarModeloPasajero().delete(objSelected);
    }

}
