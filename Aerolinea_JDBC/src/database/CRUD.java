package database;

import java.util.List;

public interface CRUD {

    //metodo de read
    public List<Object> findAll();
    //metodo para crear
    public Object create (Object obj);
    //metodo para actualizar
    public boolean update (Object obj);
    //metodo para eliminar
    public boolean delete (Object obj);

}
