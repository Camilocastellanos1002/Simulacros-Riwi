package controller;

import entity.Paciente;
import model.PacienteModel;
import utils.Utils;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class PacienteController {
    public static void getAll(){
        String listPaciente = getAll(instancePacienteModel().findAll());
        JOptionPane.showMessageDialog(null,listPaciente);
    }
    public static String getAll(List<Object> list){
        String listPaciente = "Lista de pacientes: \n";
        for (Object i: list){
            Paciente objPaciente = (Paciente) i;
            listPaciente+=objPaciente.toString()+"\n";
        }
        return listPaciente;
    }

    public static void create(){

        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre del nuevo paciente: ");
        String apellidos = JOptionPane.showInputDialog(null,"Ingrese los apellidos del nuevo paciente: ");
        String doc = JOptionPane.showInputDialog(null,"Ingrese documento del paciente: ");
        String fechaN = JOptionPane.showInputDialog(null,"Ingrese fecha de nacimiento (YYYY-MM-DD): ");

        instancePacienteModel().create(new Paciente(nombre,apellidos,fechaN,doc));

    }

    public static PacienteModel instancePacienteModel(){
        return new PacienteModel();
    }

    public static void delete(){
        //crear lista de objetos los la lista de la instancia de modelo de paciente
        Object[] options= Utils.listToArray(instancePacienteModel().findAll());
        //select
        Paciente optionPaciente = (Paciente) JOptionPane.showInputDialog(
                null,
                "Seleccione el paciente a eliminar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        instancePacienteModel().delete(optionPaciente);
    }

    public static void update(){
        //crear lista de objetos los la lista de la instancia de modelo de paciente
        Object[] options= Utils.listToArray(instancePacienteModel().findAll());
        //select
        Paciente optionPaciente = (Paciente) JOptionPane.showInputDialog(
                null,
                "Seleccione el paciente para actualizar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        //actulizar datos
        optionPaciente.setNombre(JOptionPane.showInputDialog(null,"Ingrese el nuevo nombre del paciente: ",optionPaciente.getNombre()));
        optionPaciente.setApellidos(JOptionPane.showInputDialog(null,"Ingrese los apellidos del paciente: ",optionPaciente.getApellidos()));
        optionPaciente.setDocumento_identidad(JOptionPane.showInputDialog(null,"Ingrese el nuevo documento del paciente: ",optionPaciente.getDocumento_identidad()));
        optionPaciente.setFecha_nacimiento(JOptionPane.showInputDialog(null,"Ingrese la nueva fecha de nacimiento (YYYY-MM-DD): ",optionPaciente.getFecha_nacimiento()));

        instancePacienteModel().update(optionPaciente);
    }

    public static void getByDoc(){
        String documento = JOptionPane.showInputDialog(null,"Ingrese el documento que desea buscar: ");
        PacienteModel objPacienteM = new PacienteModel();
        String lista= "Coincidencias: \n";
        for (Paciente i: objPacienteM.findByDoc(documento)){
            lista+=i.toString()+"\n";
        }
        JOptionPane.showMessageDialog(null,lista);
    }

}
