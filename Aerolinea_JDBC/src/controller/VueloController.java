package controller;

import entity.Avion;
import entity.Vuelo;
import model.VueloModel;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class VueloController {

    public static VueloModel instaciarModeloVuelo(){
        return new VueloModel();
    }
    public static void create(){
        String destino = JOptionPane.showInputDialog(null,"Ingrese el destino del vuelo: ");
        String fecha = JOptionPane.showInputDialog(null,"Ingrese la fecha de salida del vuelo (YYYY-MM-DD): ");
        String hora  = JOptionPane.showInputDialog(null,"Ingrese la hora de salida del vuelo (HH:mm:ss): ");

        Object [] optionAvion = Utils.listToArray( AvionController.instanciarModeloAvion().findAll());

        Avion AvionSeleccionado = (Avion) JOptionPane.showInputDialog(
                null,
                "Seleccione el Avion: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionAvion,
                optionAvion[0]
        );

        instaciarModeloVuelo().create(new Vuelo(destino,fecha,hora,AvionSeleccionado.getId(),AvionSeleccionado));

    }

    public static void read() {
        String listaVuelos = read(instaciarModeloVuelo().findAll());
        JOptionPane.showMessageDialog(null,listaVuelos);
    }

    public static String read(List<Object> list){
        String listaVuelos = "Lista de vuelos: \n";
        for (Object i : list){
            Vuelo objVuelo = (Vuelo) i;
            listaVuelos+=objVuelo.toString()+"\n";
        }
        return listaVuelos;
    }

    public static void delete(){
        Object [] opcionVuelo = Utils.listToArray(instaciarModeloVuelo().findAll());
        Vuelo vueloSeleccionado = (Vuelo) JOptionPane.showInputDialog(
                null,
                "Seleccione el vuelo que desea eliminar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionVuelo,
                opcionVuelo[0]
        );
        instaciarModeloVuelo().delete(vueloSeleccionado);
    }
    public static void update(){
        //selector de vuelos
        Object[] opcionVuelos = Utils.listToArray(VueloController.instaciarModeloVuelo().findAll());
        //menu de citas
        Vuelo vueloSeleccionado = (Vuelo) JOptionPane.showInputDialog(
                null,
                "Seleccione el vuelo que desea actualizar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionVuelos,
                opcionVuelos[0]
        );
        //valores nuevos para la actualizacion
        vueloSeleccionado.setDestino(JOptionPane.showInputDialog(null,"Ingrese el nuevo destino del vuelo: ",vueloSeleccionado.getDestino()));
        vueloSeleccionado.setFecha_salida(JOptionPane.showInputDialog(null,"ingrese la nueva fecha del vuelo (YYYY-MM-DD): ",vueloSeleccionado.getFecha_salida()));
        vueloSeleccionado.setHora_salida(JOptionPane.showInputDialog(null,"Ingrese la nueva hora de salida del vuelo (HH:mm:ss): ",vueloSeleccionado.getHora_salida()));

        //selectores de aviones
        Object[] opcionAvion = Utils.listToArray(AvionController.instanciarModeloAvion().findAll());

        //selector de medicos
        Avion avionSeleccionado = (Avion) JOptionPane.showInputDialog(
                null,
                "Seleccione el medico asignado a la cita: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionAvion,
                opcionAvion[0]
        );

        //se obtiene el id del avion para el vuelo
        vueloSeleccionado.setIdAvion(avionSeleccionado.getId());

        //se otiene el objeto avion para el objeto vuelo
        vueloSeleccionado.setObjAvion(avionSeleccionado);

        //se le asigna el vuelo actualizado al modelo de vuelos
        instaciarModeloVuelo().update(vueloSeleccionado);
    }
}
