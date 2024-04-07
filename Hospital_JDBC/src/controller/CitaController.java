package controller;

import entity.Cita;
import entity.Medico;
import entity.Paciente;
import model.CitaModel;
import model.PacienteModel;
import utils.Utils;

import javax.swing.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CitaController {

    public static String getAll(List<Object> list ){
        String listCita = "Lista de citas: \n";
        for (Object i: list){
            Cita objCita = (Cita) i;
            listCita+=objCita.toString()+"\n";
        }
        return listCita;
    }

    public static void getAll(){

        //llamar metodo getAll para obtener lista, instanciando el modelo de cita con el metodo find all
        String listCita = getAll(instanciaModeloCita().findAll());
        JOptionPane.showMessageDialog(null,listCita);
    }

    public static void create(){

        //pedir valores
        String fecha= JOptionPane.showInputDialog(null,"Ingrese la fecha de la cita (YYYY-MM-DD): ");
        String tiempo = JOptionPane.showInputDialog(null,"Ingrese la hora de la cita: (HH:mm:ss): ");
        String motivo = JOptionPane.showInputDialog(null,"Ingrese motivo de la cita: ");

        //llamar instancia de la listas de pacientes y medicos
        Object[] optionPacientes = Utils.listToArray(PacienteController.instancePacienteModel().findAll());
        Object[] optionMedicos = Utils.listToArray(MedicoController.instanciaModeloMedico().findAll());

        //selector de pacientes
        Paciente pacienteSeleccionado = (Paciente) JOptionPane.showInputDialog(
                null,
                "Seleccione el paciente: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionPacientes,
                optionPacientes[0]
        );
        //selector de medicos
        Medico medicoSeleccionado = (Medico) JOptionPane.showInputDialog(
                null,
                "Seleccione el medico asignado a la cita: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionMedicos,
                optionMedicos[0]
        );
        //creando la instancia de la cita
        instanciaModeloCita().create(new Cita(pacienteSeleccionado.getId(),medicoSeleccionado.getId(),fecha,tiempo,motivo,medicoSeleccionado,pacienteSeleccionado));
    }
    public static CitaModel instanciaModeloCita(){
        return new CitaModel();
    }

    public static void delete(){
        Object [] optionsCita = Utils.listToArray(instanciaModeloCita().findAll());
        Cita medicoSeleccionado = (Cita) JOptionPane.showInputDialog(
                null,
                "Seleccione el medico asignado a la cita: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionsCita,
                optionsCita[0]
        );
        instanciaModeloCita().delete(medicoSeleccionado);
    }

    public static void update(){
        //selector de citas
        Object[] optionCitas = Utils.listToArray(CitaController.instanciaModeloCita().findAll());
        //menu de citas
        Cita citaSeleccionada = (Cita) JOptionPane.showInputDialog(
                null,
                "Seleccione la cita a actualizar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionCitas,
                optionCitas[0]
        );
        //valores nuevos para la actualizacion
        citaSeleccionada.setFecha_cita(JOptionPane.showInputDialog(null,"Ingrese la nueva fecha de la cita (YYYY-MM-DD): ",citaSeleccionada.getFecha_cita()));
        citaSeleccionada.setHora_cita(JOptionPane.showInputDialog(null,"Ingrese la hora de la cita: (HH:mm:ss): ",citaSeleccionada.getHora_cita()));
        citaSeleccionada.setMotivo(JOptionPane.showInputDialog(null,"Ingrese motivo de la cita: ",citaSeleccionada.getMotivo()));

        //selectores de pacientes y medicos para la cita
        Object[] opcionPacientes = Utils.listToArray(PacienteController.instancePacienteModel().findAll());
        Object[] opcionMedicos = Utils.listToArray(MedicoController.instanciaModeloMedico().findAll());

        //selector de pacientes y asignacion del objeto paciente a la cita seleccionada
        citaSeleccionada.setObjPaciente((Paciente) JOptionPane.showInputDialog(
                null,
                "Seleccione el paciente: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionPacientes,
                opcionPacientes[0]
        ));
        // se obtiene el id del paciente
        citaSeleccionada.setId_paciente(citaSeleccionada.getObjPaciente().getId());
            //citaSeleccionada.setObjPaciente(pacienteSeleccionado); se asigna mejor arriba


        //selector de medicos
        Medico medicoSeleccionado = (Medico) JOptionPane.showInputDialog(
                null,
                "Seleccione el medico asignado a la cita: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionMedicos,
                opcionMedicos[0]
        );
        //se obtiene el id del medico para la cita
        citaSeleccionada.setId_medico(medicoSeleccionado.getId());
        //se otiene el objeto medico para el objeto cita
        citaSeleccionada.setObjMedico(medicoSeleccionado);

        //se le asigna la cita actualizada al modelo de citas
        instanciaModeloCita().update(citaSeleccionada);

    }
}
