package controller;

import entity.Avion;
import model.AvionModel;
import utils.Utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AvionController {

    public static AvionModel instanciarModeloAvion(){
        return new AvionModel();
    }

    public static void create(){
        String modelo = JOptionPane.showInputDialog(null,"Ingrese el modelo del avion: ");
        int capacidad = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la capacidad del avion: "));

        instanciarModeloAvion().create(new Avion(modelo,capacidad));
    }
    public static void read(){
        String listaAviones = readAll(instanciarModeloAvion().findAll());
        JOptionPane.showMessageDialog(null,listaAviones);
    }
    public static String readAll(List<Object> list){
        String listaAviones= "Lista de aviones: \n";
        for (Object i:list){
            Avion ojbAvion = (Avion) i;
            listaAviones+=ojbAvion.toString()+"\n";
        }
        return listaAviones;
    }
    public static void update(){
        Object[] optionAviones = Utils.listToArray(instanciarModeloAvion().findAll());
        Avion objSelected = (Avion) JOptionPane.showInputDialog(null,
                "Selecione una avion que deseea editar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionAviones,
                optionAviones[0]
        );
        objSelected.setModelo(JOptionPane.showInputDialog(null,"Ingrese el nuevo modelo del avion: ",objSelected.getModelo()));
        objSelected.setCapacidad(Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la nueva capacidad del avion: ",objSelected.getCapacidad())));

        instanciarModeloAvion().update(objSelected);
    }
    public static void delete(){
        Object[] optionesAvion = Utils.listToArray(instanciarModeloAvion().findAll());
        Avion objSelected = (Avion) JOptionPane.showInputDialog(null,
                "Selecione el avion que desea eliminar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionesAvion,
                optionesAvion[0]
        );
        instanciarModeloAvion().delete(objSelected);
    }

}
