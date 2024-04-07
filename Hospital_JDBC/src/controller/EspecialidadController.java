package controller;

import utils.Utils;
import entity.Especialidad;
import model.EspecialidadModel;

import javax.swing.*;
import java.util.List;

public class EspecialidadController {

    public static void create(){
        //1. asignar valores por el usuario
        String nombre =JOptionPane.showInputDialog(null,"Ingrese nombre de la especialidad: \n");
        String descripcion= JOptionPane.showInputDialog(null,"Ingrese la descripcion de la especialidad: \n");

        //metodo que crea el objeto especialidad con los valores
        instanciarModelo().create(new Especialidad(nombre,descripcion));

    }

    //este metodo permite instanciar el modelo, en vez de estar creando en cada llamado como se muestra en la lineas comentadas
    public  static EspecialidadModel instanciarModelo(){
        /*
        Especialidad objEspecialidad = new Especialidad();
        objEspecialidad.setNombre(nombre);
        objEspecialidad.setDescripcion(descripcion);
        objEspecialidad = (Especialidad) objEspecialidadModel.create(objEspecialidad);
        JOptionPane.showMessageDialog(null,objEspecialidad.toString());*/
        return new EspecialidadModel();
    }


    public static void getAll(){
        String listEspecialidad = getAllString(instanciarModelo().findAll());
        JOptionPane.showMessageDialog(null,listEspecialidad);
    }


    public static String getAllString(List<Object> list){
        String listEspecialidad = "Lista de especialidades: \n";
        for (Object i: list){
            Especialidad objEspecialidad = (Especialidad) i;
            listEspecialidad+=objEspecialidad.toString()+"\n";
        }
        return listEspecialidad;
    }

    public static void delete(){
        //crear aaraylist de objetos
        Object[] options = Utils.listToArray(instanciarModelo().findAll());//entregando la creacion de la instancia del modelo

        Especialidad objSelected = (Especialidad) JOptionPane.showInputDialog(null,
                "Selecione una especialidad para eliminar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        instanciarModelo().delete(objSelected);
    }

    public static void getByName(){
        String name = JOptionPane.showInputDialog(null,"Ingrese el nombre a buscar: ");
        EspecialidadModel objEspecialidadModel = new EspecialidadModel();
        String listString = "Coincidencias: \n";
        for (Especialidad i:objEspecialidadModel.findByName(name)){
            listString+=i.toString()+"\n";
        }
        JOptionPane.showMessageDialog(null,listString);
    }

    public static void update(){
        //1. crear aaraylist de objetos para crear el select
        Object[] options = Utils.listToArray(instanciarModelo().findAll());//entregando la creacion de la instancia del modelo
        //2. select
        Especialidad objSelected = (Especialidad) JOptionPane.showInputDialog(null,
                "Selecione una especialidad para actualizar: ",
                "",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        //3.pedir al usuario el ingreso del nuevo nombre, asignando el valor por defecto en caso de que no ingrese ningun nuevo valor
        objSelected.setNombre(JOptionPane.showInputDialog(null,"Ingresa el nuevo nombre de la especialidad: ", objSelected.getNombre()));
        objSelected.setDescripcion(JOptionPane.showInputDialog(null,"Ingrese la nueva descripcion de la especialidad: ",objSelected.getDescripcion()));

        instanciarModelo().update(objSelected);
    }
}
