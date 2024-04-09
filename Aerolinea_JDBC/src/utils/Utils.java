package utils;

import java.util.List;

public class Utils {
    public static <T> T[] listToArray(List<T> list){
        //crear un arreglo de objetos del tamaño de la lista y casteandolo
        T[] array = (T[]) new Object[list.size()];

        int i=0;
        for (T iterador: list){
            array[i++] = iterador;
        }
        return array;
    }
}
