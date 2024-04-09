import controller.AvionController;
import controller.PasajeroController;
import controller.ReservacionController;
import controller.VueloController;
import database.ConfigDB;

import javax.swing.*;
public class Main {

    public static void main(String[] args) {
        int reservas =0;

        //ConfigDB.openConnection();
        //ConfigDB.closeConnection();
        String option = "";
        do {
            option= JOptionPane.showInputDialog(null, """
                    Menu principal: \n
                    1.Menu de aviones
                    2.Menu de vuelos
                    3.Menu de pasajeros
                    4.Menu de reservaciones
                    5.Salir
                    Selecione una opcion:
                    """);
            switch (option) {
                case "1":
                    String option_1 = "";
                    do {
                        option_1 = JOptionPane.showInputDialog(null, """
                                Menu Aviones: \n
                                1.Ingresar un nuevo avion
                                2.Editar informacion de un avion
                                3.Eliminar informacion de un avion
                                4.Mostrar lista de aviones
                                5.Salir
                                Selecione una opcion:
                                """);
                        switch (option_1) {
                            case "1":
                                AvionController.create();
                                break;
                            case "2":
                                AvionController.update();
                                break;
                            case "3":
                                AvionController.delete();
                                break;
                            case "4":
                                AvionController.read();
                                break;
                        }
                    } while (!option_1.equals("5"));
                    break;
                case "2":
                    String option_2 = "";
                    do {
                        option_2 = JOptionPane.showInputDialog(null, """
                                Menu de Vuelos: \n
                                1.Ingresar nuevo vuelo
                                2.Editar informacion de vuelo
                                3.Eliminar vuelo
                                4.Mostrar lista de vuelos
                                5.Salir
                                Selecione una opcion:
                                """);
                        switch (option_2) {
                            case "1":
                                VueloController.create();
                                break;
                            case "2":
                                VueloController.update();
                                break;
                            case "3":
                                VueloController.delete();
                                break;
                            case "4":
                                VueloController.read();
                                break;
                        }
                    } while (!option_2.equals("5"));
                    break;
                case "3":
                    String option_3="";
                    do {
                        option_3= JOptionPane.showInputDialog(null, """
                                Menu de Pasajeros: \n
                                1.Ingresar nuevo pasajero
                                2.Editar informacion de un pasajero
                                3.Eliminar pasajero
                                4.Mostrar lista de pasajeros
                                5.Salir
                                Selecione una opcion:
                                """);
                        switch (option_3){
                            case "1":
                                PasajeroController.create();
                                break;
                            case "2":
                                PasajeroController.update();
                                break;
                            case "3":
                                PasajeroController.delete();
                                break;
                            case "4":
                                PasajeroController.read();
                                break;
                        }
                    }while (!option_3.equals("5"));
                break;
                case "4":
                    String option_4="";
                    do {
                        option_4= JOptionPane.showInputDialog(null, """
                                Menu de reservaciones: \n
                                1.Ingresar nueva reservacion
                                2.Editar informacion de una reservacion
                                3.Eliminar reservacion
                                4.Mostrar lista de reservaciones
                                5.Salir
                                Selecione una opcion:
                                """);
                        switch (option_4){
                            case "1":
                                reservas++;
                                ReservacionController.create(reservas);
                            break;
                            case "2":
                                ReservacionController.update();
                            break;
                            case "3":
                                ReservacionController.delete();
                            break;
                            case "4":
                                ReservacionController.getAll();
                            break;
                        }
                    }while (!option_4.equals("5"));
                break;
            }
        }while (!option.equals("5"));
    }
}