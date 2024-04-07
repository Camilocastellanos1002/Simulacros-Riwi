package controller;

import entity.Especialidad;
import entity.Medico;
import model.MedicoModel;
import utils.Utils;

import javax.swing.*;
import java.util.List;

public class MedicoController {
    public static void getAll(){
        String listMedico = getAll(instanciaModeloMedico().findAll());
        JOptionPane.showMessageDialog(null,listMedico);
    }
    public static String getAll(List<Object> list){

        String listMedico = "Lista de medicos: \n";
        for (Object i: list){
            Medico objMedico = (Medico) i;
            listMedico+=objMedico.toString()+"\n";
        }
        return listMedico;
    }

    public static void create(){

        //1. perdir valores
        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre del nuevo medico: ");
        String apellidos = JOptionPane.showInputDialog(null,"Ingrese los apellidos del nuevo medico: ");

        //2. listar especialidades
        Object[] optionEspecialidad = Utils.listToArray(EspecialidadController.instanciarModelo().findAll());

        //3.selector
         Especialidad objEspecialidad =(Especialidad) JOptionPane.showInputDialog(  //la opcion seleccionada es casteada y guardada como objeto
                                                                                    //especialidad
                null,   //no tiene parentcomponent
                "Seleccione una especialidad: ",    //mensaje
                "",                                 //no tiene titulo
                JOptionPane.QUESTION_MESSAGE,       //ventana de pregunto
                null,                               //no tiene ningun icono
                optionEspecialidad,                 //opciones
                optionEspecialidad[0]               //opcion por defecto
        );

         //3. crear el modelo de medico asignando el objeto medico con los valores asignados y nesesarios en el constructor
         instanciaModeloMedico().create(new Medico(nombre,apellidos,objEspecialidad.getId(),objEspecialidad));

    }

    //metodo que crea la instancia de medico cada vez que es llamado
    public static MedicoModel instanciaModeloMedico(){
        return new MedicoModel();
    }

    public static void delete(){
        Object[] options = Utils.listToArray(instanciaModeloMedico().findAll());
        Medico objMedico =(Medico) JOptionPane.showInputDialog(  //la opcion seleccionada es casteada y guardada como objeto
                //especialidad
                null,   //no tiene parentcomponent
                "Seleccione el medico a eliminar: ",    //mensaje
                "",                                 //no tiene titulo
                JOptionPane.QUESTION_MESSAGE,       //ventana de pregunto
                null,                               //no tiene ningun icono
                options,                 //opciones
                options[0]               //opcion por defecto
        );
        instanciaModeloMedico().delete(objMedico);
    }

    public static void update(){
        //lista de medicos para actualizar
        Object[] options = Utils.listToArray(instanciaModeloMedico().findAll());
        Medico objMedico =(Medico) JOptionPane.showInputDialog(  //la opcion seleccionada es casteada y guardada como objeto
                //especialidad
                null,   //no tiene parentcomponent
                "Seleccione el medico que deseaa actualizar: ",    //mensaje
                "",                                 //no tiene titulo
                JOptionPane.QUESTION_MESSAGE,       //ventana de pregunto
                null,                               //no tiene ningun icono
                options,                 //opciones
                options[0]               //opcion por defecto
        );
        //valores actualizar
        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre del nuevo medico: ",objMedico.getNombre());
        String apellidos = JOptionPane.showInputDialog(null,"Ingrese los apellidos del nuevo medico: ",objMedico.getApellidos());

        Object[] optionEspecialidad = Utils.listToArray(EspecialidadController.instanciarModelo().findAll());

        Especialidad objEspecialidad =(Especialidad) JOptionPane.showInputDialog(  //la opcion seleccionada es casteada y guardada como objeto
                //especialidad
                null,   //no tiene parentcomponent
                "Seleccione una especialidad: ",    //mensaje
                "",                                 //no tiene titulo
                JOptionPane.QUESTION_MESSAGE,       //ventana de pregunto
                null,                               //no tiene ningun icono
                optionEspecialidad,                 //opciones
                optionEspecialidad[0]               //opcion por defecto
        );
        instanciaModeloMedico().update(new Medico(nombre,apellidos,objEspecialidad.getId(),objEspecialidad));
    }
}
