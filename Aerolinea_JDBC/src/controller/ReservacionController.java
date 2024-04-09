package controller;

import entity.Pasajero;
import entity.Reservacion;
import entity.Vuelo;
import model.AvionModel;
import model.ReservacionModel;
import utils.Utils;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservacionController {

    public static String getAll(List<Object> list) {
        String listReservas = "Lista de reservas: \n";
        for (Object i : list) {
            Reservacion objCita = (Reservacion) i;
            listReservas += objCita.toString() + "\n";
        }
        return listReservas;
    }

    public static void getAll() {

        //llamar metodo getAll para obtener lista, instanciando el modelo de reservacion con el metodo find all
        String listaReservas = getAll(instanciaModeloReservacion().findAll());
        JOptionPane.showMessageDialog(null, listaReservas);
    }

    public static void create(int reservas) {


        Object[] objVuelos = Utils.listToArray(VueloController.instaciarModeloVuelo().findAll());
        Object[] objPasajero = Utils.listToArray(PasajeroController.instanciarModeloPasajero().findAll());

        Vuelo vueloSeleccionado = (Vuelo) JOptionPane.showInputDialog(
                null,
                "Seleccione el vuelo: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                objVuelos,
                objVuelos[0]
        );
        /*int capacidadVuelo = vueloSeleccionado.getObjAvion().getCapacidad();
        if (reservas<=capacidadVuelo){
            List<String> listaAsientos = new ArrayList<>();
            //listaAsientos = AvionController.
            String asiento = JOptionPane.showInputDialog(null,"Ingrese el asiento que desea: ");

            if (!listaAsientos.contains(asiento)){*/
        String asiento = JOptionPane.showInputDialog(null, "Ingrese el asiento que desea: ");

        Pasajero pasajeroSeleccionado = (Pasajero) JOptionPane.showInputDialog(
                null,
                "Seleccione el pasajero quien realiza la reserva: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                objPasajero,
                objPasajero[0]
        );
        //pedir valores de la reservacion
        String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de la reserva (YYYY-MM-DD): ");
        //}
        instanciaModeloReservacion().create(new Reservacion(fecha,asiento, pasajeroSeleccionado.getId(), pasajeroSeleccionado, vueloSeleccionado.getId(), vueloSeleccionado));
    }
    public static ReservacionModel instanciaModeloReservacion(){
        return new ReservacionModel();
    }

    public static void delete(){
        Object [] optionsReservacion  = Utils.listToArray(instanciaModeloReservacion().findAll());
        Reservacion reservacionSeleccionado = (Reservacion) JOptionPane.showInputDialog(
                null,
                "Seleccione la reservacion que desea eliminar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionsReservacion,
                optionsReservacion[0]
        );
        instanciaModeloReservacion().delete(reservacionSeleccionado);
    }

    public static void update(){

        //selector de reservar
        Object[] optionReservas = Utils.listToArray( ReservacionController.instanciaModeloReservacion().findAll());
        //menu de Reservas
        Reservacion reservaSeleccionada = (Reservacion) JOptionPane.showInputDialog(
                null,
                "Seleccione la reserva para actualizar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionReservas,
                optionReservas[0]
        );
        //valores nuevos para la actualizacion
        reservaSeleccionada.setFecha_reservacion( JOptionPane.showInputDialog(null,"Ingrese la nueva fecha de la cita (YYYY-MM-DD): ",reservaSeleccionada.getFecha_reservacion()));
        reservaSeleccionada.setAsiento(JOptionPane.showInputDialog(null,"Ingrese el asiento: ",reservaSeleccionada.getAsiento()));

        //selectores de pasajeros y vuelos para la reserva
        Object[] opcionPasajero = Utils.listToArray(PasajeroController.instanciarModeloPasajero().findAll());
        Object[] opcionVuelo = Utils.listToArray(VueloController.instaciarModeloVuelo().findAll());

        //selector de vuelos y asignacion del objeto vuelo a la reserva seleccionada
        reservaSeleccionada.setObjvuelo((Vuelo) JOptionPane.showInputDialog(
                null,
                "Seleccione el vuelo: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionVuelo,
                opcionVuelo[0]
        ));
        // se obtiene el id del paciente
        reservaSeleccionada.setId_vuelo(reservaSeleccionada.getId_vuelo());

        //selector de medicos
        Pasajero pasajeroSeleccionado = (Pasajero) JOptionPane.showInputDialog(
                null,
                "Seleccione el medico asignado a la cita: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionPasajero,
                opcionPasajero[0]
        );
        //se obtiene el id del pasajero para la reserva
        reservaSeleccionada.setId_pasajero(pasajeroSeleccionado.getId());
        //se otiene el objeto pasajero para el objeto reserva
        reservaSeleccionada.setObjPasajero(pasajeroSeleccionado);
        //se asigna la reserva actualizada al modelo de reservas
        instanciaModeloReservacion().update(reservaSeleccionada);
    }

}
